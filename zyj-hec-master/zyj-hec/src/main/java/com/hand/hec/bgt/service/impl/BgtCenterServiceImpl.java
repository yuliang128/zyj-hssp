package com.hand.hec.bgt.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.bgt.service.IBgtCenterRefBgtEntityService;
import com.hand.hec.exp.service.IExpOrgUnitRefBgtOrgService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.*;
import com.hand.hec.bgt.mapper.BgtCenterMapper;
import com.hand.hec.bgt.mapper.BgtCenterRefBgtEntityMapper;
import com.hand.hec.bgt.mapper.BgtOrgRefDetailMapper;
import com.hand.hec.bgt.service.IBgtCenterHierarchyService;
import com.hand.hec.bgt.service.IBgtCenterService;
import com.hand.hec.bgt.service.IBgtEntityService;
import com.hand.hec.exp.dto.ExpOrgUnit;
import com.hand.hec.exp.dto.ExpOrgUnitRefAccOrg;
import com.hand.hec.exp.dto.ExpOrgUnitRefBgtOrg;
import com.hand.hec.exp.mapper.ExpOrgUnitMapper;
import com.hand.hec.exp.mapper.ExpOrgUnitRefBgtOrgMapper;
import com.hand.hec.exp.service.IExpOrgUnitRefAccOrgService;

import com.hand.hec.gld.service.IGldAccountingEntityService;
import com.hand.hec.gld.service.IGldResponsibilityCenterService;

import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.dto.FndCompanyRefBgtEntity;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.mapper.FndCompanyMapper;
import com.hand.hap.fnd.mapper.FndCompanyRefBgtEntityMapper;
import com.hand.hap.fnd.mapper.FndManagingOrganizationMapper;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hec.gld.dto.GldAccEntityRefBe;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.dto.GldRespCenterRefBc;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.mapper.GldAccEntityRefBeMapper;
import com.hand.hec.gld.mapper.GldAccEntityRefSobMapper;
import com.hand.hec.gld.mapper.GldAccountingEntityMapper;
import com.hand.hec.gld.mapper.GldRespCenterRefBcMapper;
import com.hand.hec.gld.mapper.GldResponsibilityCenterMapper;
import com.hand.hec.gld.mapper.GldSetOfBookMapper;



/**
 * <p>
 * 预算中心ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 16:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtCenterServiceImpl extends BaseServiceImpl<BgtCenter> implements IBgtCenterService {

    @Autowired
    BgtCenterMapper centerMapper;

    @Autowired
    BgtOrgRefDetailMapper bgtOrgRefDetailMapper;

    @Autowired
    BgtCenterRefBgtEntityMapper bgtCenterRefBgtEntityMapper;

    @Autowired
    FndManagingOrganizationMapper fndManagingOrganizationMapper;

    @Autowired
    FndCompanyMapper fndCompanyMapper;

    @Autowired
    IFndCompanyService fndCompanyService;

    @Autowired
    FndCompanyRefBgtEntityMapper fndCompanyRefBgtEntityMapper;

    @Autowired
    ExpOrgUnitMapper expOrgUnitMapper;

    @Autowired
    ExpOrgUnitRefBgtOrgMapper expOrgUnitRefBgtOrgMapper;

    @Autowired
    GldSetOfBookMapper gldSetOfBookMapper;

    @Autowired
    GldAccountingEntityMapper gldAccountingEntityMapper;

    @Autowired
    GldAccEntityRefSobMapper gldAccEntityRefSobMapper;

    @Autowired
    GldResponsibilityCenterMapper gldResponsibilityCenterMapper;

    @Autowired
    IGldAccountingEntityService gldAccountingEntityService;

    @Autowired
    IGldResponsibilityCenterService gldResponsibilityCenterService;

    @Autowired
    GldAccEntityRefBeMapper gldAccEntityRefBeMapper;

    @Autowired
    GldRespCenterRefBcMapper gldRespCenterRefBcMapper;

    @Autowired
    IBgtEntityService entityService;

    @Autowired
    IExpOrgUnitRefAccOrgService expOrgUnitRefAccOrgService;

    @Autowired
    IBgtCenterHierarchyService bgtCenterHierarchyService;

    @Autowired
    IExpOrgUnitRefBgtOrgService unitRefBgtOrgService;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<BgtCenter> checkBgtCenter(String controlType, Long bgtOrgId, String filtrateMethod, Long centerId,
                    String controlCenterCodeFrom, String controlCenterCodeTo) {
        return centerMapper.checkBgtCenter(controlType, bgtOrgId, filtrateMethod, centerId, controlCenterCodeFrom,
                        controlCenterCodeTo);
    }

    @Override
    public List<BgtCenter> querytMain(String sourceTypeCode, Long bgtOrgId, String centerCode, String description,
                    IRequest request, Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return centerMapper.selectMain(sourceTypeCode, bgtOrgId, centerCode, description);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean manual(Long bgtOrgId, String sourceTypeCode, IRequest request) {
        IBaseService<BgtCenter> self = ((IBaseService<BgtCenter>) AopContext.currentProxy());
        IBaseService<BgtCenterRefBgtEntity> bgtCenterRefBgtEntityIBaseService =
                        ((IBaseService<BgtCenterRefBgtEntity>) AopContext.currentProxy());
        IBaseService<ExpOrgUnitRefBgtOrg> expOrgUnitRefBgtOrgIBaseService =
                        ((IBaseService<ExpOrgUnitRefBgtOrg>) AopContext.currentProxy());
        IBaseService<GldRespCenterRefBc> gldRespCenterRefBcIBaseService =
                        ((IBaseService<GldRespCenterRefBc>) AopContext.currentProxy());

        // 思路：根据sourceTypeCode判断同步源-1.MANAGEMENT 管理架构 2.ACCOUNTING 核算架构
        // 1.管理架构-查询出预算组织来源的所有管理组织-查询出所有管理组织ID为当前id的公司-查询出每个公司iD为当前公司id的部门-将部门信息插入到预算中心信息中,将部门所属公司的预算主体信息同步到预算中心和预算实体，
        // 将预算实体和预算中心的关系回写到部门-预算中心-预算实体
        List<BgtOrgRefDetail> bgtOrgRefDetailList =
                        bgtOrgRefDetailMapper.select(BgtOrgRefDetail.builder().bgtOrgId(bgtOrgId).build());
        if ("MANAGEMENT".equals(sourceTypeCode)) {
            // 管理架构
            for (BgtOrgRefDetail bgtOrgRefDetail : bgtOrgRefDetailList) {
                // 判断该管理组织是否启用
                FndManagingOrganization fndManagingOrganization =
                                fndManagingOrganizationMapper
                                                .select(FndManagingOrganization.builder()
                                                                .magOrgId(bgtOrgRefDetail.getSourceId()).build())
                                                .get(0);
                if ("N".equals(fndManagingOrganization.getEnabledFlag())) {
                    continue;
                }
                // 每个管理组织对应的所有公司
                List<FndCompany> companyList = fndCompanyMapper
                                .select(FndCompany.builder().magOrgId(bgtOrgRefDetail.getSourceId()).build());
                // 判断该管理组织旗下是否有公司
                if (companyList.isEmpty()) {
                    continue;
                }
                for (FndCompany fndCompany : companyList) {
                    // 判断当前公司是否在有效期内
                    if (!fndCompanyService.checkInTime(new Date(), fndCompany.getCompanyId())) {
                        continue;
                    }
                    // 每个公司对应的所有部门
                    List<ExpOrgUnit> unitList = expOrgUnitMapper
                                    .select(ExpOrgUnit.builder().companyId(fndCompany.getCompanyId()).build());
                    // 判断该公司旗下是否有部门
                    if (unitList.isEmpty()) {
                        continue;
                    }
                    for (ExpOrgUnit expOrgUnit : unitList) {
                        // 判断当前部门是否启用
                        if ("N".equals(expOrgUnit.getEnabledFlag())) {
                            continue;
                        }
                        // 将每个部门信息同步到预算中心--封装BgtCenter对象
                        BgtCenter bgtCenter = new BgtCenter();
                        bgtCenter.setBgtOrgId(bgtOrgId);
                        bgtCenter.setCenterCode(expOrgUnit.getUnitCode());
                        bgtCenter.setDescription(expOrgUnit.getDescription());
                        // 默认为空
                        bgtCenter.setCurrencyCode("");
                        // 默认为DETAILS
                        bgtCenter.setCenterType("DETAILS");
                        bgtCenter.setSourceTypeCode(sourceTypeCode);
                        bgtCenter.setSourceId(bgtOrgRefDetail.getSourceId());
                        bgtCenter.setEnabledFlag(expOrgUnit.getEnabledFlag());

                        // 录入之前检查该部门是否同步过,查询条件为-bgtOrgId,sourceTypeCode,centerCode,不能多不能少
                        List<BgtCenter> list1 = centerMapper.select(BgtCenter.builder().bgtOrgId(bgtOrgId)
                                        .sourceTypeCode(sourceTypeCode).centerCode(bgtCenter.getCenterCode()).build());
                        if (!list1.isEmpty()) {
                            // 同步过，做更新
                            Long centerId = list1.get(0).getCenterId();
                            bgtCenter.setCenterId(centerId);
                            self.updateByPrimaryKeySelective(request, bgtCenter);
                        }

                        // 未同步过，则同步录入预算中心
                        self.insertSelective(request, bgtCenter);

                        // 检查公司是否分配了预算实体
                        List<FndCompanyRefBgtEntity> fndCompanyRefBgtEntityList = fndCompanyRefBgtEntityMapper.select(
                                        FndCompanyRefBgtEntity.builder().companyId(fndCompany.getCompanyId()).build());
                        if (!fndCompanyRefBgtEntityList.isEmpty()) {
                            // 如果分配了，则同步过来的预算中心也要分配
                            for (FndCompanyRefBgtEntity fndCompanyRefBgtEntity : fndCompanyRefBgtEntityList) {
                                if ("N".equals(fndCompanyRefBgtEntity.getEnabledFlag())) {
                                    // 如果当前公司-预算实体同步关系未启用，则不同步
                                    continue;
                                }

                                // 封装预算中心-预算实体关联关系
                                BgtCenterRefBgtEntity bgtCenterRefBgtEntity = new BgtCenterRefBgtEntity();
                                bgtCenterRefBgtEntity.setCenterId(centerMapper.select(bgtCenter).get(0).getCenterId());
                                bgtCenterRefBgtEntity.setEntityId(fndCompanyRefBgtEntity.getEntityId());
                                bgtCenterRefBgtEntity.setEnabledFlag(fndCompanyRefBgtEntity.getEnabledFlag());

                                // 封装预算中心-预算实体-部门关联关系
                                ExpOrgUnitRefBgtOrg expOrgUnitRefBgtOrg = new ExpOrgUnitRefBgtOrg();
                                expOrgUnitRefBgtOrg.setUnitId(expOrgUnit.getUnitId());
                                expOrgUnitRefBgtOrg.setBgtEntityId(bgtCenterRefBgtEntity.getEntityId());
                                expOrgUnitRefBgtOrg.setBgtCenterId(bgtCenterRefBgtEntity.getCenterId());
                                List<ExpOrgUnitRefBgtOrg> list3 = expOrgUnitRefBgtOrgMapper.select(expOrgUnitRefBgtOrg);

                                // 检查该关联关系是否录入过
                                List<BgtCenterRefBgtEntity> list2 =
                                                bgtCenterRefBgtEntityMapper.select(BgtCenterRefBgtEntity.builder()
                                                                .centerId(bgtCenterRefBgtEntity.getCenterId())
                                                                .entityId(bgtCenterRefBgtEntity.getEntityId()).build());
                                if (!list2.isEmpty()) {
                                    // 同步过，对预算中心-预算实体关系做更新(主要是启用标志)
                                    Long refId = list2.get(0).getRefId();
                                    bgtCenterRefBgtEntity.setRefId(refId);
                                    bgtCenterRefBgtEntityIBaseService.updateByPrimaryKeySelective(request,
                                                    bgtCenterRefBgtEntity);

                                    // 回写更新：同时对预算中心-部门-预算实体关联关系做更新(启用标志，默认标志)-如果预算中心-预算实体关联禁用，则预算中心-部门-预算实体关联禁用，默认标志禁用，如果启用则默认标志不做变化
                                    Long refId2 = list3.get(0).getRefId();
                                    expOrgUnitRefBgtOrg.setRefId(refId2);
                                    expOrgUnitRefBgtOrg.setEnabledFlag(bgtCenterRefBgtEntity.getEnabledFlag());
                                    if ("N".equals(bgtCenterRefBgtEntity.getEnabledFlag())) {
                                        expOrgUnitRefBgtOrg.setDefaultFlag("N");
                                    }
                                    expOrgUnitRefBgtOrgIBaseService.updateByPrimaryKeySelective(request,
                                                    expOrgUnitRefBgtOrg);
                                }

                                // 未同步过，录入与预算实体的分配关系
                                bgtCenterRefBgtEntityIBaseService.insertSelective(request, bgtCenterRefBgtEntity);

                                // 回写部门与预算中心的关联关系
                                expOrgUnitRefBgtOrg.setEnabledFlag(bgtCenterRefBgtEntity.getEnabledFlag());
                                if (expOrgUnitRefBgtOrgMapper.select(ExpOrgUnitRefBgtOrg.builder()
                                                .unitId(expOrgUnit.getUnitId()).defaultFlag("Y").build()).size() != 0) {
                                    // 该部门已经有了默认的预算中心
                                    expOrgUnitRefBgtOrg.setDefaultFlag("N");
                                } else {
                                    expOrgUnitRefBgtOrg.setDefaultFlag("Y");
                                }
                                expOrgUnitRefBgtOrgIBaseService.insertSelective(request, expOrgUnitRefBgtOrg);
                            }
                        }
                    }
                }
            }
        }
        // 2.核算架构-查询出预算组织来源的所有账套-查询出所有账套ID为当前id的核算主体-查询出每个核算主体iD为当前核算主体id的成本中心-将成本中心信息插入到预算中心信息中,将成本中心所属核算主体的预算主体信息同步到预算中心和预算实体，
        // 将预算实体和预算中心的关系回写到成本中心-预算中心-预算实体
        if ("ACCOUNTING".equals(sourceTypeCode)) {
            // 管理架构
            for (BgtOrgRefDetail bgtOrgRefDetail : bgtOrgRefDetailList) {
                // 判断该账套是否启用
                GldSetOfBook gldSetOfBook = gldSetOfBookMapper
                                .select(GldSetOfBook.builder().setOfBooksId(bgtOrgRefDetail.getSourceId()).build())
                                .get(0);
                if ("N".equals(gldSetOfBook.getEnabledFlag())) {
                    continue;
                }
                // 每个账套对应的核算主体(默认)
                List<GldAccountingEntity> accountingEntityList =
                                gldAccountingEntityMapper.queryAccEntityBySob(bgtOrgRefDetail.getSourceId());
                // 判断该账套下是否有核算主体
                if (accountingEntityList.isEmpty()) {
                    continue;
                }
                for (GldAccountingEntity gldAccountingEntity : accountingEntityList) {
                    // 判断当前核算主体是否在有效期内
                    if (!gldAccountingEntityService.checkInTime(new Date(), gldAccountingEntity.getAccEntityId())) {
                        continue;
                    }
                    // 每个核算主体对应的所有成本中心
                    List<GldResponsibilityCenter> gldResponsibilityCenterList =
                                    gldResponsibilityCenterMapper.select(GldResponsibilityCenter.builder()
                                                    .accEntityId(gldAccountingEntity.getAccEntityId()).build());
                    // 判断该核算主体底下是否有成本中心
                    if (gldResponsibilityCenterList.isEmpty()) {
                        continue;
                    }
                    for (GldResponsibilityCenter gldResponsibilityCenter : gldResponsibilityCenterList) {
                        // 判断当前成本中心是否在有效期内
                        if (!gldResponsibilityCenterService.checkInTime(new Date(),
                                        gldResponsibilityCenter.getResponsibilityCenterId())) {
                            continue;
                        }
                        // 将每个成本中心信息同步到预算中心--封装BgtCenter对象
                        BgtCenter bgtCenter = new BgtCenter();
                        bgtCenter.setBgtOrgId(bgtOrgId);
                        bgtCenter.setCenterCode(gldResponsibilityCenter.getResponsibilityCenterCode());
                        bgtCenter.setDescription(gldResponsibilityCenter.getResponsibilityCenterName());
                        // 默认为空
                        bgtCenter.setCurrencyCode("");
                        // 默认为DETAILS
                        bgtCenter.setCenterType("DETAILS");
                        bgtCenter.setSourceTypeCode(sourceTypeCode);
                        bgtCenter.setSourceId(bgtOrgRefDetail.getSourceId());
                        bgtCenter.setEnabledFlag("Y");

                        // 录入之前检查该成本中心是否同步过,查询条件为-bgtOrgId,sourceTypeCode,centerCode,不能多不能少
                        List<BgtCenter> list1 = centerMapper.select(BgtCenter.builder().bgtOrgId(bgtOrgId)
                                        .sourceTypeCode(sourceTypeCode).centerCode(bgtCenter.getCenterCode()).build());
                        if (!list1.isEmpty()) {
                            // 同步过，做更新
                            Long centerId = list1.get(0).getCenterId();
                            bgtCenter.setCenterId(centerId);
                            self.updateByPrimaryKeySelective(request, bgtCenter);
                        }

                        // 未同步过，则同步录入预算中心
                        self.insertSelective(request, bgtCenter);

                        // 检查核算主体是否分配了预算实体
                        List<GldAccEntityRefBe> gldAccEntityRefBeList = gldAccEntityRefBeMapper.select(GldAccEntityRefBe
                                        .builder().accEntityId(gldAccountingEntity.getAccEntityId()).build());
                        if (gldAccEntityRefBeList.size() != 0) {
                            // 如果分配了，则同步过来的预算中心也要分配
                            for (GldAccEntityRefBe GldAccEntityRefBe : gldAccEntityRefBeList) {
                                if ("N".equals(GldAccEntityRefBe.getEnabledFlag())) {
                                    // 如果当前核算主体-预算实体同步关系未启用，则不同步
                                    continue;
                                }

                                // 封装预算中心-预算实体关联关系
                                BgtCenterRefBgtEntity bgtCenterRefBgtEntity = new BgtCenterRefBgtEntity();
                                bgtCenterRefBgtEntity.setCenterId(centerMapper.select(bgtCenter).get(0).getCenterId());
                                bgtCenterRefBgtEntity.setEntityId(GldAccEntityRefBe.getBgtEntityId());
                                bgtCenterRefBgtEntity.setEnabledFlag(GldAccEntityRefBe.getEnabledFlag());

                                // 封装预算中心-预算实体-成本中心关联关系

                                GldRespCenterRefBc gldRespCenterRefBc = new GldRespCenterRefBc();
                                gldRespCenterRefBc.setRespCenterId(gldResponsibilityCenter.getResponsibilityCenterId());
                                gldRespCenterRefBc.setBgtEntityId(bgtCenterRefBgtEntity.getEntityId());
                                gldRespCenterRefBc.setBgtCenterId(bgtCenterRefBgtEntity.getCenterId());
                                List<GldRespCenterRefBc> list3 = gldRespCenterRefBcMapper.select(gldRespCenterRefBc);

                                // 检查该关联关系是否录入过
                                List<BgtCenterRefBgtEntity> list2 =
                                                bgtCenterRefBgtEntityMapper.select(BgtCenterRefBgtEntity.builder()
                                                                .centerId(bgtCenterRefBgtEntity.getCenterId())
                                                                .entityId(bgtCenterRefBgtEntity.getEntityId()).build());
                                if (!list2.isEmpty()) {
                                    // 同步过，对预算中心-预算实体关系做更新(主要是启用标志)
                                    Long refId = list2.get(0).getRefId();
                                    bgtCenterRefBgtEntity.setRefId(refId);
                                    bgtCenterRefBgtEntityIBaseService.updateByPrimaryKeySelective(request,
                                                    bgtCenterRefBgtEntity);

                                    // 回写更新：同时对预算中心-成本中心-预算实体关联关系做更新(启用标志，默认标志)-如果预算中心-预算实体关联禁用，则预算中心-成本中心-预算实体关联禁用，默认标志禁用，如果启用则默认标志不做变化
                                    Long refId2 = list3.get(0).getRefId();

                                    gldRespCenterRefBc.setRefId(refId2);
                                    gldRespCenterRefBc.setEnabledFlag(bgtCenterRefBgtEntity.getEnabledFlag());
                                    if ("N".equals(bgtCenterRefBgtEntity.getEnabledFlag())) {
                                        gldRespCenterRefBc.setDefaultFlag("N");
                                    }
                                    gldRespCenterRefBcIBaseService.updateByPrimaryKeySelective(request,
                                                    gldRespCenterRefBc);
                                }

                                // 未同步过，录入与预算实体的分配关系
                                bgtCenterRefBgtEntityIBaseService.insertSelective(request, bgtCenterRefBgtEntity);

                                // 回写成本中心与预算中心的关联关系
                                gldRespCenterRefBc.setEnabledFlag(bgtCenterRefBgtEntity.getEnabledFlag());
                                if (expOrgUnitRefBgtOrgMapper.select(ExpOrgUnitRefBgtOrg.builder()
                                                .unitId(gldResponsibilityCenter.getResponsibilityCenterId())
                                                .defaultFlag("Y").build()).size() != 0) {
                                    // 该成本中心已经有了默认的预算中心
                                    gldRespCenterRefBc.setDefaultFlag("N");
                                } else {
                                    gldRespCenterRefBc.setDefaultFlag("Y");
                                }
                                gldRespCenterRefBcIBaseService.insertSelective(request, gldRespCenterRefBc);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Boolean centerSync(IRequest request, Long bgtOrgId, List<Long> entityIds) {
        for (Long entityId : entityIds) {
            // 1.0 获取预算实体信息
            BgtEntity bgtEntity = new BgtEntity();
            bgtEntity.setEntityId(entityId);
            bgtEntity = entityService.selectByPrimaryKey(request, bgtEntity);
            // 2.0 同步管理架构
            if (BgtEntity.SOURCE_TYPE_MANAGEMENT.equalsIgnoreCase(bgtEntity.getSourceTypeCode())) {
                syncMagOrganization(request, bgtEntity);
                // 3.0 同步核算架构
                syncAccOrganization(request, bgtEntity);
            }
            // 4.0 预算中心层级同步【核算架构】
            syncBgtMagOrganization(request, bgtEntity);
        }
        return true;
    }

    @Override
    public BgtCenter getDefaultCenterByUnit(IRequest request, Long unitId, Long entityId) {
        ExpOrgUnitRefBgtOrg ref = unitRefBgtOrgService.getDefaultRef(request, unitId, entityId);
        if (ref == null) {
            return null;
        }

        BgtCenter center = new BgtCenter();
        center.setCenterId(ref.getBgtCenterId());
        Criteria criteria = new Criteria(center);
        criteria.where(new WhereField(BgtCenter.FIELD_CENTER_ID));
        List<BgtCenter> centerList = self().selectOptions(request, center, criteria);

        if (centerList != null && centerList.size() != 0) {
            return centerList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<BgtCenter> bgtJournalBatch(IRequest request, Long bgtOrgId, Long rangeEtsId) {
        return centerMapper.bgtJournalBatch(bgtOrgId, rangeEtsId);
    }

    @Override
    public List<BgtCenter> queryDetailCenter(Long centerId) {
        return centerMapper.queryDetailCenter(centerId);
    }


    /**
     * 同步管理架构
     *
     * @param request
     * @param bgtEntity 预算实体信息
     * @author guiyuting 2019-03-04 15:05
     * @return
     */
    private void syncMagOrganization(IRequest request, BgtEntity bgtEntity) {
        String vEnabledFlag = BaseConstants.NO;
        String vDefaultFlag = BaseConstants.NO;
        List<Map<String, Object>> expOrgUnitList = expOrgUnitMapper.queryWithCompany(bgtEntity.getSourceId());
        for (Map<String, Object> map : expOrgUnitList) {
            // 2.0.1 判断启用标志
            String unitEnabledFlag = map.get("unitEnabledFlag") == null ? BaseConstants.NO
                            : map.get("unitEnabledFlag").toString();
            if (checkActiveDate((Date) map.get("startDateActive"), (Date) map.get("endDateActive"))) {
                vEnabledFlag = BaseConstants.YES;
            }
            // 2.0.2 更新
            BgtCenter bgtCenter = updateBgtCenter(request, bgtEntity, vEnabledFlag, map, null);

            // 2.0.3 预算中心分配预算实体
            updateBgtCenterRefBgtEntity(bgtCenter, bgtEntity, vEnabledFlag);

            // 2.0.4 回写管理部门分配预算组织
            updateMagRefBgt(bgtCenter, bgtEntity, vEnabledFlag, vDefaultFlag, map);
        }
    }

    /**
     * 同步核算架构
     *
     * @param request
     * @param bgtEntity 预算实体信息
     * @author guiyuting 2019-03-04 15:09
     * @return
     */
    private void syncAccOrganization(IRequest request, BgtEntity bgtEntity) {
        String vEnabledFlag = BaseConstants.NO;
        String vDefaultFlag = BaseConstants.NO;
        List<GldResponsibilityCenter> gldResponsibilityCenterList =
                        gldResponsibilityCenterMapper.queryByAccEntityId(bgtEntity.getSourceId());
        for (GldResponsibilityCenter gldResponsibilityCenter : gldResponsibilityCenterList) {
            Long vCenterId = null;
            // 3.0.1 判断启用标志
            if (checkActiveDate(gldResponsibilityCenter.getStartDateActive(),
                            gldResponsibilityCenter.getEndDateActive())) {
                vEnabledFlag = BaseConstants.YES;
            }
            // 3.0.2 更新
            BgtCenter bgtCenter = updateBgtCenter(request, bgtEntity, vEnabledFlag, null, gldResponsibilityCenter);

            // 3.0.3 预算中心分配预算实体
            updateBgtCenterRefBgtEntity(bgtCenter, bgtEntity, vEnabledFlag);

            // 3.0.4 成本中心关联预算实体
            updateBgtCenterRefGld(bgtCenter, bgtEntity, vEnabledFlag, vDefaultFlag, gldResponsibilityCenter);

            // 3.0.5 部门关联预算组织
            updateUnitRefBgtMag(request, bgtCenter, vDefaultFlag, gldResponsibilityCenter);

        }
    }


    /**
     * 同步预算架构
     *
     * @param request
     * @param bgtEntity 预算实体信息
     * @author guiyuting 2019-03-04 15:09
     * @return
     */
    private void syncBgtMagOrganization(IRequest request, BgtEntity bgtEntity) {
        String vEnabledFlag = BaseConstants.NO;
        String vDefaultFlag = BaseConstants.NO;

        BgtCenter queryBgtCenter = new BgtCenter();
        queryBgtCenter.setBgtOrgId(bgtEntity.getBgtOrgId());
        queryBgtCenter.setSourceTypeCode(BgtCenter.SOURCE_TYPE_ACCOUNTING);
        queryBgtCenter.setCenterType(BgtCenter.CENTER_TYPE_SUMMARY);
        queryBgtCenter.setEnabledFlag(BaseConstants.YES);
        List<BgtCenter> bgtCenterList = self().select(request, queryBgtCenter, 0, 0);
        for (BgtCenter bgtCenter : bgtCenterList) {
            List<BgtCenter> bgtCenterHierarchyList =
                            centerMapper.queryRefByRespCenter(bgtCenter.getSourceId(), bgtEntity.getBgtOrgId());
            for (BgtCenter hierarchy : bgtCenterHierarchyList) {
                // -- 启用标志
                if (checkActiveDate(hierarchy.getStartDateActive(), hierarchy.getEndDateActive())
                                && BaseConstants.YES.equalsIgnoreCase(hierarchy.getEnabledFlag())) {
                    vEnabledFlag = BaseConstants.YES;
                }
                BgtCenterHierarchy queryBgtCenterHierarchy = new BgtCenterHierarchy();
                queryBgtCenterHierarchy.setParentCenterId(bgtCenter.getCenterId());
                queryBgtCenterHierarchy.setCenterId(hierarchy.getCenterId());
                List<BgtCenterHierarchy> bgtCenterHierarchyList1 =
                                bgtCenterHierarchyService.select(request, queryBgtCenterHierarchy, 0, 0);
                if (bgtCenterHierarchyList1 != null && !bgtCenterHierarchyList1.isEmpty()) {
                    for (BgtCenterHierarchy bgtCenterHierarchy : bgtCenterHierarchyList1) {
                        bgtCenterHierarchy.setEnabledFlag(vEnabledFlag);
                    }
                    bgtCenterHierarchyService.batchUpdate(request, bgtCenterHierarchyList1);
                } else if (BaseConstants.YES.equalsIgnoreCase(hierarchy.getEnabledFlag())) {
                    queryBgtCenterHierarchy.setEnabledFlag(vEnabledFlag);
                    bgtCenterHierarchyService.insertSelective(request, queryBgtCenterHierarchy);
                }
            }
        }
    }

    /**
     * 更新预算中心相关信息
     *
     * @param request
     * @param bgtEntity 预算实体信息
     * @param vEnabledFlag 启用标志
     * @param map 部门信息
     * @param gldResponsibilityCenter 成本中心信息
     * @author guiyuting 2019-03-04 15:35
     * @return
     */
    private BgtCenter updateBgtCenter(IRequest request, BgtEntity bgtEntity, String vEnabledFlag,
                    Map<String, Object> map, GldResponsibilityCenter gldResponsibilityCenter) {
        String sourceCode = null, sourceTypeCode = null, centerType = null;
        Long sourceId = null;
        if (map != null) {
            sourceCode = map.get("unitCode").toString();
            sourceId = (Long) map.get("unitId");
            sourceTypeCode = BgtCenter.SOURCE_TYPE_MANAGEMENT;
            centerType = BgtCenter.CENTER_TYPE_DETAILS;
        } else if (gldResponsibilityCenter != null) {
            sourceCode = gldResponsibilityCenter.getResponsibilityCenterCode();
            sourceId = gldResponsibilityCenter.getResponsibilityCenterId();
            sourceTypeCode = BgtCenter.SOURCE_TYPE_ACCOUNTING;
            centerType = gldResponsibilityCenter.getSummaryType();
        }
        BgtCenter queryBgtCenter = new BgtCenter();
        queryBgtCenter.setBgtOrgId(bgtEntity.getBgtOrgId());
        queryBgtCenter.setCenterCode(sourceCode);
        queryBgtCenter.setSourceTypeCode(sourceTypeCode);

        List<BgtCenter> updateBgtCenters = self().select(request, queryBgtCenter, 0, 0);
        if (updateBgtCenters != null && !updateBgtCenters.isEmpty()) {
            for (BgtCenter bgtCenter : updateBgtCenters) {
                bgtCenter.setEnabledFlag(vEnabledFlag);
            }
            self().batchUpdate(request, updateBgtCenters);
            queryBgtCenter = updateBgtCenters.get(0);
        } else if (BaseConstants.YES.equalsIgnoreCase(vEnabledFlag)) {
            // 2.0.3 新增录入
            queryBgtCenter.setEnabledFlag(vEnabledFlag);
            queryBgtCenter.setCenterType(centerType);
            queryBgtCenter.setSourceId(sourceId);
            queryBgtCenter.setCurrencyCode("");
            queryBgtCenter = self().insertSelective(request, queryBgtCenter);
        }

        return queryBgtCenter;
    }

    /**
     * 预算中心分配预算实体
     *
     * @param bgtCenter 预算中心信息
     * @param bgtEntity 预算实体信息
     * @param vEnabledFlag 启用标志
     * @author guiyuting 2019-03-04 19:33
     * @return
     */
    private void updateBgtCenterRefBgtEntity(BgtCenter bgtCenter, BgtEntity bgtEntity, String vEnabledFlag) {
        BgtCenterRefBgtEntity queryBgtCenterRefBgtEntity = new BgtCenterRefBgtEntity();
        queryBgtCenterRefBgtEntity.setCenterId(bgtCenter.getCenterId());
        queryBgtCenterRefBgtEntity.setEntityId(bgtEntity.getEntityId());

        List<BgtCenterRefBgtEntity> updateBgtCenterRefBgtEntities =
                        bgtCenterRefBgtEntityMapper.select(queryBgtCenterRefBgtEntity);
        if (updateBgtCenterRefBgtEntities != null && !updateBgtCenterRefBgtEntities.isEmpty()) {
            for (BgtCenterRefBgtEntity bgtCenterRefBgtEntity : updateBgtCenterRefBgtEntities) {
                bgtCenterRefBgtEntity.setEnabledFlag(vEnabledFlag);
                bgtCenterRefBgtEntityMapper.updateByPrimaryKey(bgtCenterRefBgtEntity);
            }
        } else if (BaseConstants.YES.equals(vEnabledFlag)) {
            queryBgtCenterRefBgtEntity.setEnabledFlag(vEnabledFlag);
            bgtCenterRefBgtEntityMapper.insertSelective(queryBgtCenterRefBgtEntity);
        }
    }

    /**
     * 回写管理部门分配预算组织
     *
     * @param bgtCenter 预算中心信息
     * @param bgtEntity 预算实体信息
     * @param vEnabledFlag 启用标志
     * @param vDefaultFlag 默认标志
     * @param map 部门信息
     * @author guiyuting 2019-03-04 19:39
     * @return
     */
    private void updateMagRefBgt(BgtCenter bgtCenter, BgtEntity bgtEntity, String vEnabledFlag, String vDefaultFlag,
                    Map<String, Object> map) {
        // 获取默认标记
        int num = gldRespCenterRefBcMapper.queryRefRalationNum((Long) map.get("unitId"), bgtEntity.getEntityId(),
                        bgtCenter.getCenterId());
        if (num == 0) {
            vDefaultFlag = BaseConstants.YES;
        }
        // 更新
        ExpOrgUnitRefBgtOrg queryExpOrgUnitRefBgtOrg = new ExpOrgUnitRefBgtOrg();
        queryExpOrgUnitRefBgtOrg.setUnitId((Long) map.get("unitId"));
        queryExpOrgUnitRefBgtOrg.setBgtEntityId(bgtEntity.getEntityId());
        queryExpOrgUnitRefBgtOrg.setBgtCenterId(bgtCenter.getCenterId());

        List<ExpOrgUnitRefBgtOrg> expOrgUnitRefBgtOrgList = expOrgUnitRefBgtOrgMapper.select(queryExpOrgUnitRefBgtOrg);
        if (expOrgUnitRefBgtOrgList != null && !expOrgUnitRefBgtOrgList.isEmpty()) {
            for (ExpOrgUnitRefBgtOrg expOrgUnitRefBgtOrg : expOrgUnitRefBgtOrgList) {
                expOrgUnitRefBgtOrg.setDefaultFlag(vDefaultFlag);
                expOrgUnitRefBgtOrg.setEnabledFlag(BaseConstants.YES);
                expOrgUnitRefBgtOrgMapper.updateByPrimaryKey(expOrgUnitRefBgtOrg);
            }
        } else {
            queryExpOrgUnitRefBgtOrg.setDefaultFlag(vDefaultFlag);
            queryExpOrgUnitRefBgtOrg.setEnabledFlag(vEnabledFlag);
            expOrgUnitRefBgtOrgMapper.insertSelective(queryExpOrgUnitRefBgtOrg);
        }
    }

    /**
     * 成本中心分配预算组织
     *
     * @param bgtCenter 预算中心信息
     * @param bgtEntity 预算实体信息
     * @param vEnabledFlag 启用标志
     * @param vDefaultFlag 默认标志
     * @param gldResponsibilityCenter 成本中心信息
     * @author guiyuting 2019-03-04 19:39
     * @return
     */
    private void updateBgtCenterRefGld(BgtCenter bgtCenter, BgtEntity bgtEntity, String vEnabledFlag,
                    String vDefaultFlag, GldResponsibilityCenter gldResponsibilityCenter) {
        // 获取默认标记
        int num = gldRespCenterRefBcMapper.queryRefRalationNum(gldResponsibilityCenter.getResponsibilityCenterId(),
                        bgtEntity.getEntityId(), bgtCenter.getCenterId());

        if (num == 0) {
            vDefaultFlag = BaseConstants.YES;
        }
        // 更新
        GldRespCenterRefBc queryGldRespCenterRefBc = new GldRespCenterRefBc();
        queryGldRespCenterRefBc.setRespCenterId(gldResponsibilityCenter.getResponsibilityCenterId());
        queryGldRespCenterRefBc.setBgtEntityId(bgtEntity.getEntityId());
        queryGldRespCenterRefBc.setBgtCenterId(bgtCenter.getCenterId());

        List<GldRespCenterRefBc> gldRespCenterRefBcList = gldRespCenterRefBcMapper.select(queryGldRespCenterRefBc);
        if (gldRespCenterRefBcList != null && !gldRespCenterRefBcList.isEmpty()) {
            for (GldRespCenterRefBc gldRespCenterRefBc : gldRespCenterRefBcList) {
                gldRespCenterRefBc.setDefaultFlag(vDefaultFlag);
                gldRespCenterRefBc.setEnabledFlag(vEnabledFlag);
                gldRespCenterRefBcMapper.updateByPrimaryKey(gldRespCenterRefBc);

            }
        } else if (BaseConstants.YES.equals(vEnabledFlag)) {
            queryGldRespCenterRefBc.setDefaultFlag(vDefaultFlag);
            queryGldRespCenterRefBc.setEnabledFlag(vEnabledFlag);
            gldRespCenterRefBcMapper.insertSelective(queryGldRespCenterRefBc);
        }
    }

    /**
     * 部门关联预算组织
     *
     * @param request
     * @param bgtCenter 预算中心信息
     * @param vDefaultFlag 默认标志
     * @param gldResponsibilityCenter 成本中心信息
     * @author guiyuting 2019-03-04 19:53
     * @return
     */
    private void updateUnitRefBgtMag(IRequest request, BgtCenter bgtCenter, String vDefaultFlag,
                    GldResponsibilityCenter gldResponsibilityCenter) {
        ExpOrgUnitRefAccOrg queryExpOrgUnitRefAccOrg = new ExpOrgUnitRefAccOrg();
        queryExpOrgUnitRefAccOrg.setAccEntityId(gldResponsibilityCenter.getAccEntityId());
        queryExpOrgUnitRefAccOrg.setRespCenterId(gldResponsibilityCenter.getParentRespCenterId());

        List<ExpOrgUnitRefAccOrg> expOrgUnitRefAccOrgList =
                        expOrgUnitRefAccOrgService.select(request, queryExpOrgUnitRefAccOrg, 0, 0);
        for (ExpOrgUnitRefAccOrg expOrgUnitRefAccOrg : expOrgUnitRefAccOrgList) {
            BgtCenterRefBgtEntity bgtCenterRefBgtEntity = new BgtCenterRefBgtEntity();
            bgtCenterRefBgtEntity.setCenterId(bgtCenter.getCenterId());
            List<BgtCenterRefBgtEntity> bgtCenterRefBgtEntityList =
                            bgtCenterRefBgtEntityMapper.select(bgtCenterRefBgtEntity);
            for (BgtCenterRefBgtEntity centerRefBgtEntity : bgtCenterRefBgtEntityList) {
                // 获取默认标记
                int n = expOrgUnitRefBgtOrgMapper.queryRefRalationNum(expOrgUnitRefAccOrg.getUnitId(),
                                centerRefBgtEntity.getEntityId(), centerRefBgtEntity.getCenterId());

                if (n > 0 || BaseConstants.NO.equals(centerRefBgtEntity.getEnabledFlag())) {
                    vDefaultFlag = BaseConstants.NO;
                } else {
                    vDefaultFlag = expOrgUnitRefAccOrg.getDefaultFlag();
                }
                // 更新
                ExpOrgUnitRefBgtOrg queryExpOrgUnitRefBgtOrg = new ExpOrgUnitRefBgtOrg();
                queryExpOrgUnitRefBgtOrg.setUnitId(expOrgUnitRefAccOrg.getUnitId());
                queryExpOrgUnitRefBgtOrg.setBgtEntityId(centerRefBgtEntity.getEntityId());
                queryExpOrgUnitRefBgtOrg.setBgtCenterId(centerRefBgtEntity.getCenterId());

                List<ExpOrgUnitRefBgtOrg> expOrgUnitRefBgtOrgList =
                                expOrgUnitRefBgtOrgMapper.select(queryExpOrgUnitRefBgtOrg);
                if (expOrgUnitRefBgtOrgList != null && !expOrgUnitRefBgtOrgList.isEmpty()) {
                    for (ExpOrgUnitRefBgtOrg expOrgUnitRefBgtOrg : expOrgUnitRefBgtOrgList) {
                        expOrgUnitRefBgtOrg.setDefaultFlag(vDefaultFlag);
                        expOrgUnitRefBgtOrg.setEnabledFlag(centerRefBgtEntity.getEnabledFlag());
                        expOrgUnitRefBgtOrgMapper.updateByPrimaryKey(expOrgUnitRefBgtOrg);
                    }
                } else if (BaseConstants.YES.equals(centerRefBgtEntity.getEnabledFlag())) {
                    ExpOrgUnitRefBgtOrg newQueryExpOrgUnitRefBgtOrg = new ExpOrgUnitRefBgtOrg();
                    newQueryExpOrgUnitRefBgtOrg.setUnitId(expOrgUnitRefAccOrg.getUnitId());
                    newQueryExpOrgUnitRefBgtOrg.setDefaultFlag(BaseConstants.YES);
                    int ifDefault = expOrgUnitRefBgtOrgMapper.selectCount(newQueryExpOrgUnitRefBgtOrg);
                    if (ifDefault == 0) {
                        vDefaultFlag = expOrgUnitRefAccOrg.getDefaultFlag();
                    } else {
                        vDefaultFlag = BaseConstants.NO;
                    }
                    queryExpOrgUnitRefBgtOrg.setDefaultFlag(vDefaultFlag);
                    queryExpOrgUnitRefBgtOrg.setEnabledFlag(centerRefBgtEntity.getEnabledFlag());
                    expOrgUnitRefBgtOrgMapper.insertSelective(queryExpOrgUnitRefBgtOrg);
                }


            }
        }
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
}
