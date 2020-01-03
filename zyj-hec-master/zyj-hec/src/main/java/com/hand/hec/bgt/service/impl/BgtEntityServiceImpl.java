package com.hand.hec.bgt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.Company;
import com.hand.hap.fnd.service.ICompanyService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtEntity;
import com.hand.hec.bgt.dto.BgtEntityHierarchy;
import com.hand.hec.bgt.dto.BgtOrganization;
import com.hand.hec.bgt.mapper.BgtEntityMapper;
import com.hand.hec.bgt.service.IBgtEntityHierarchyService;
import com.hand.hec.bgt.service.IBgtEntityService;
import com.hand.hec.bgt.service.IBgtOrganizationService;

import com.hand.hap.fnd.dto.FndCompanyRefAccEntity;
import com.hand.hap.fnd.dto.FndCompanyRefBgtEntity;
import com.hand.hap.fnd.mapper.FndCompanyRefBgtEntityMapper;
import com.hand.hap.fnd.service.IFndCompanyRefAccEntityService;
import com.hand.hap.fnd.service.IFndCompanyRefBgtEntityService;
import com.hand.hec.gld.dto.GldAccEntityRefBe;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.service.IGldAccEntityRefBeService;
import com.hand.hec.gld.service.IGldAccountingEntityService;


/**
 * <p>
 * 预算实体ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtEntityServiceImpl extends BaseServiceImpl<BgtEntity> implements IBgtEntityService {

    @Autowired
    BgtEntityMapper entityMapper;

    @Autowired
    IBgtOrganizationService organizationService;

    @Autowired
    IFndCompanyService companyService;

    @Autowired
    IFndCompanyRefBgtEntityService refBgtEntityService;

    @Autowired
    IFndCompanyRefAccEntityService refAccEntityService;

    @Autowired
    IGldAccountingEntityService accountingEntityService;

    @Autowired
    FndCompanyRefBgtEntityMapper companyRefBgtEntityMapper;

    @Autowired
    IGldAccEntityRefBeService accEntityRefBeService;

    @Autowired
    IBgtEntityHierarchyService entityHierarchyService;

    @Override
    public List<BgtEntity> checkBgtEntity(String controlType, Long bgtOrgId, String filtrateMethod, Long entityId,
                    String controlEntityCodeFrom, String controlEntityCodeTo) {
        return entityMapper.checkBgtEntity(controlType, bgtOrgId, filtrateMethod, entityId, controlEntityCodeFrom,
                        controlEntityCodeTo);
    }

    @Override
    public BgtEntity queryDefaultBgtEntity(IRequest request, Long companyId) {
        return entityMapper.queryDefaultBgtEntity(companyId);
    }

    @Override
    public List<BgtEntity> queryForBatch(IRequest request, BgtEntity bgtEntity, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return entityMapper.queryForBatch(bgtEntity);
    }

    @Override
    public boolean sync(IRequest request, Long bgtOrgId) {
        // 1.0 获取基础数据
        BgtOrganization bgtOrganization = new BgtOrganization();
        bgtOrganization.setBgtOrgId(bgtOrgId);
        bgtOrganization = organizationService.selectByPrimaryKey(request, bgtOrganization);
        // 2.0 同步（管理架构）
        syncMagOrganization(request, bgtOrganization);
        // 3.0 同步（核算架构）
        syncAccOrganization(request, bgtOrganization);
        // 4.0 预算实体层级同步[预算架构]
        syncBgtOrganization(request, bgtOrganization);
        return true;
    }

    @Override
    public List<Map<String, Object>> queryByCompanyId(IRequest request, Long companyId) {
        return entityMapper.queryByCompanyId(companyId);
    }

    @Override
    public List<BgtEntity> queryAll(IRequest request, BgtEntity bgtEntity, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return entityMapper.queryAll(bgtEntity);
    }

    @Override
    public List<BgtEntity> bgtJournalBatch(IRequest request, Long bgtOrgId, Long rangeId) {
        return entityMapper.bgtJournalBatch(bgtOrgId,rangeId);
    }

    @Override
    public List<BgtEntity> queryDetailEntity(Long entityId) {
        return entityMapper.queryDetailEntity(entityId);
    }


    /**
     * 同步管理架构
     *
     * @param request
     * @param bgtOrganization
     * @author guiyuting 2019-03-04 15:05
     * @return
     */
    private boolean syncMagOrganization(IRequest request, BgtOrganization bgtOrganization) {
        List<FndCompany> companies = new ArrayList<>();
        String enabledFlag = BaseConstants.NO;
        if (bgtOrganization != null
                        && BgtEntity.SOURCE_TYPE_MANAGEMENT.equalsIgnoreCase(bgtOrganization.getSourceTypeCode())) {
            companies = companyService.queryByBgtOrgId(request, bgtOrganization.getBgtOrgId());
        }
        for (FndCompany company : companies) {
            /*
             * TODO csr_manager.enabled_flag = 'Y' guiyuting
             */
            // 2.0.1 启用标志
            if (checkActiveDate(company.getStartDateActive(), company.getEndDateActive())
                            && BaseConstants.YES.equalsIgnoreCase(company.getEnabledFlag())) {
                enabledFlag = BaseConstants.YES;
            }
            // 2.0.2 更新
            BgtEntity bgtEntity = this.updateBgtEntity(request, bgtOrganization, enabledFlag, company, null);
            // 2.0.3 回写管理公司预算实体
            this.updateCompanyRefBgtEntity(request, enabledFlag, bgtEntity, company, null);
        }
        return true;
    }

    /**
     * 同步核算架构
     *
     * @param request
     * @param bgtOrganization
     * @author guiyuting 2019-03-04 15:09
     * @return
     */
    private boolean syncAccOrganization(IRequest request, BgtOrganization bgtOrganization) {
        String enabledFlag = BaseConstants.NO;
        List<GldAccountingEntity> accountingEntityList =
                        accountingEntityService.queryByBgtOrg(request, bgtOrganization.getBgtOrgId());
        for (GldAccountingEntity accountingEntity : accountingEntityList) {
            /*
             * TODO csr_accounting.enabled_flag = 'Y' guiyuting
             */
            // 3.0.1 启用标志
            if (checkActiveDate(accountingEntity.getStartDateActive(), accountingEntity.getEndDateActive())) {
                enabledFlag = BaseConstants.YES;
            }
            // 3.0.2 更新
            BgtEntity bgtEntity = this.updateBgtEntity(request, bgtOrganization, enabledFlag, null, accountingEntity);
            // 3.0.3 回写管理公司预算实体
            FndCompanyRefAccEntity companyRefAccEntity = new FndCompanyRefAccEntity();
            companyRefAccEntity.setAccEntityId(bgtEntity.getEntityId());
            List<FndCompanyRefAccEntity> refAccEntityList =
                            refAccEntityService.select(request, companyRefAccEntity, 0, 0);
            for (FndCompanyRefAccEntity refAccEntity : refAccEntityList) {
                this.updateCompanyRefBgtEntity(request, enabledFlag, bgtEntity, null, refAccEntity);
            }
            // 3.0.4 回写核算主体与预算实体关联关系
            GldAccEntityRefBe gldAccEntityRefBe = new GldAccEntityRefBe();
            gldAccEntityRefBe.setAccEntityId(accountingEntity.getAccEntityId());
            gldAccEntityRefBe.setBgtEntityId(bgtEntity.getEntityId());
            gldAccEntityRefBe.setDefaultFlag(BaseConstants.NO);
            gldAccEntityRefBe.setEnabledFlag(BaseConstants.YES);

            accEntityRefBeService.insertSelective(request, gldAccEntityRefBe);
        }
        return true;
    }

    /**
     * 同步预算架构
     *
     * @param request
     * @param bgtOrganization
     * @author guiyuting 2019-03-04 15:09
     * @return
     */
    private boolean syncBgtOrganization(IRequest request, BgtOrganization bgtOrganization) {
        String enabledFlag = BaseConstants.NO;
        BgtEntity bgtEntity = new BgtEntity();
        bgtEntity.setSourceTypeCode(BgtEntity.SOURCE_TYPE_ACCOUNTING);
        bgtEntity.setEntityType(BgtEntity.ENTITY_TYPE_SUMMARY);
        bgtEntity.setEnabledFlag(BaseConstants.YES);
        List<BgtEntity> bgtEntityList = self().select(request, bgtEntity, 0, 0);
        for (BgtEntity bgtEntity1 : bgtEntityList) {
            BgtEntity queryEntity = new BgtEntity();
            queryEntity.setParentEntityId(bgtEntity1.getSourceId());
            queryEntity.setBgtOrgId(bgtOrganization.getBgtOrgId());
            List<Map<String, Object>> mapList = entityMapper.queryForSync(queryEntity);
            for (Map map : mapList) {
                // 启用标志
                String hierarchyEnabledFlag = map.get("enabledFlag").toString();
                if (checkActiveDate((Date) map.get("startDateActive"), (Date) map.get("endDateActive"))
                                && BaseConstants.YES.equalsIgnoreCase(hierarchyEnabledFlag)) {
                    enabledFlag = "Y";
                }
                BgtEntityHierarchy bgtEntityHierarchy = new BgtEntityHierarchy();
                bgtEntityHierarchy.setEntityId((Long) map.get("entityId"));
                bgtEntityHierarchy.setParentEntityId(bgtEntity1.getEntityId());

                List<BgtEntityHierarchy> bgtEntityHierarchyList =
                                entityHierarchyService.select(request, bgtEntityHierarchy, 0, 0);
                if (bgtEntityHierarchyList != null && !bgtEntityHierarchyList.isEmpty()) {
                    for (BgtEntityHierarchy hierarchy : bgtEntityHierarchyList) {
                        hierarchy.setEnabledFlag(enabledFlag);
                    }
                    entityHierarchyService.batchUpdate(request, bgtEntityHierarchyList);

                } else if (BaseConstants.YES.equalsIgnoreCase(enabledFlag)) {
                    bgtEntityHierarchy.setEnabledFlag(enabledFlag);
                    entityHierarchyService.insertSelective(request, bgtEntityHierarchy);

                }
            }
        }
        return true;
    }

    /**
     * 检查当前日期在不在活跃时间范围内
     *
     * @param startActiveDate 起始时间
     * @param endActiveDate 结束时间
     * @author guiyuting 2019-03-04 15:03
     * @return true表示在范围内，false表示不在范围内
     */
    private boolean checkActiveDate(Date startActiveDate, Date endActiveDate) {
        boolean flag = false;
        Date nowDate = new Date();
        if (startActiveDate.getTime() <= nowDate.getTime()
                        && (endActiveDate == null || endActiveDate.getTime() >= nowDate.getTime())) {
            flag = true;
        }
        return flag;
    }

    /**
     * 更新预算实体相关信息
     *
     * @param request
     * @param bgtOrganization 预算组织信息
     * @param enabledFlag 启用标志
     * @param company 公司信息
     * @param accountingEntity 核算实体信息
     * @author guiyuting 2019-03-04 15:35
     * @return
     */
    private BgtEntity updateBgtEntity(IRequest request, BgtOrganization bgtOrganization, String enabledFlag,
                    FndCompany company, GldAccountingEntity accountingEntity) {
        String sourceCode = null;
        Long sourceId = null;
        if (company != null) {
            sourceCode = company.getCompanyCode();
            sourceId = company.getCompanyId();
        } else if (accountingEntity != null) {
            sourceCode = accountingEntity.getAccEntityCode();
            sourceId = accountingEntity.getAccEntityId();
        }
        BgtEntity bgtEntity = new BgtEntity();
        bgtEntity.setBgtOrgId(bgtOrganization.getBgtOrgId());
        bgtEntity.setEntityCode(sourceCode);
        bgtEntity.setSourceTypeCode(BgtEntity.SOURCE_TYPE_MANAGEMENT);
        List<BgtEntity> entities = self().select(request, bgtEntity, 0, 0);
        if (entities != null && !entities.isEmpty()) {
            for (BgtEntity entity : entities) {
                entity.setEnabledFlag(enabledFlag);
            }
            entities = self().batchUpdate(request, entities);
            bgtEntity = entities.get(0);
        } else if (BaseConstants.YES.equalsIgnoreCase(enabledFlag)) {
            bgtEntity.setEntityType(BgtEntity.ENTITY_TYPE_DETAILS);
            bgtEntity.setSourceId(sourceId);
            bgtEntity.setEnabledFlag(enabledFlag);
            bgtEntity = self().insertSelective(request, bgtEntity);
        }
        return bgtEntity;
    }

    /**
     * 回写管理公司预算实体
     *
     * @param request
     * @param enabledFlag 启用标志
     * @param bgtEntity 预算实体信息
     * @param company 公司信息
     * @author guiyuting 2019-03-04 15:37
     * @return
     */
    private void updateCompanyRefBgtEntity(IRequest request, String enabledFlag, BgtEntity bgtEntity,
                    FndCompany company, FndCompanyRefAccEntity refAccEntity) {
        FndCompanyRefBgtEntity companyRefBgtEntity = new FndCompanyRefBgtEntity();
        Long companyId = null;
        if (company != null) {
            companyId = company.getCompanyId();
        } else if (refAccEntity != null) {
            companyId = refAccEntity.getCompanyId();
        }
        companyRefBgtEntity.setCompanyId(companyId);
        companyRefBgtEntity.setEntityId(bgtEntity.getEntityId());
        List<FndCompanyRefBgtEntity> refBgtEntityList = refBgtEntityService.select(request, companyRefBgtEntity, 0, 0);
        if (!refBgtEntityList.isEmpty()) {
            for (FndCompanyRefBgtEntity refBgtEntity : refBgtEntityList) {
                refBgtEntity.setEnabledFlag(enabledFlag);
            }
            refBgtEntityList = refBgtEntityService.batchUpdate(request, refBgtEntityList);
        } else if (BaseConstants.YES.equalsIgnoreCase(enabledFlag)) {
            if (refAccEntity != null) {
                String defaultFlag = BaseConstants.YES;
                FndCompanyRefBgtEntity refBgtEntity = new FndCompanyRefBgtEntity();
                refBgtEntity.setCompanyId(companyId);
                refBgtEntity.setEnabledFlag(enabledFlag);
                int num = companyRefBgtEntityMapper.selectCount(refBgtEntity);
                if (num == 0) {
                    defaultFlag = BaseConstants.NO;
                }
                companyRefBgtEntity.setDefaultFlag(defaultFlag);
            }
            companyRefBgtEntity.setEnabledFlag(enabledFlag);
            refBgtEntityService.insertSelective(request, companyRefBgtEntity);
        }
    }
}
