package com.hand.hap.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.dto.FndCompanyRefAccEntity;
import com.hand.hap.fnd.dto.FndCompanyRefBgtEntity;
import com.hand.hap.fnd.exception.FndCompanyException;
import com.hand.hap.fnd.mapper.FndCompanyMapper;
import com.hand.hap.fnd.service.IFndCompanyRefAccEntityService;
import com.hand.hap.fnd.service.IFndCompanyRefBgtEntityService;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import org.apache.ibatis.annotations.Param;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理公司ServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 11:08
 * @author xiuxian.wu 2019/01/25 15:08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndCompanyServiceImpl extends BaseServiceImpl<FndCompany> implements IFndCompanyService {

    @Autowired
    private FndCompanyMapper companyMapper;
    @Autowired
    private IFndCompanyRefBgtEntityService companyRefBgtEntityService;
    @Autowired
    private IFndCompanyRefAccEntityService companyRefAccEntityService;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<FndCompany> checkFndCompany(String filtrateMethod, String controlType, Long companyId,
                    String controlCompanyCodeFrom, String controlCompanyCodeTo) {
        return companyMapper.checkFndCompany(filtrateMethod, controlType, companyId, controlCompanyCodeFrom,
                        controlCompanyCodeTo);
    }


    @Override
    public List<FndCompany> queryFndCompany(IRequest requestContext, FndCompany dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return companyMapper.queryFndCompany(dto);
    }

    @Override
    public List<FndCompany> submitFndCompany(IRequest requestCtx, List<FndCompany> dto) {
        List<FndCompanyRefAccEntity> accEntities = new ArrayList<>();
        List<FndCompanyRefBgtEntity> bgtEntities = new ArrayList<>();
        List<FndCompany> companies = batchUpdate(requestCtx, dto);
        for (FndCompany company : companies) {
            if (company.getAccEntityId() != null) {
                FndCompanyRefAccEntity accEntity =
                                new FndCompanyRefAccEntity(company.getCompanyId(), company.getAccEntityId(), "Y", "Y");
                accEntity.set__id(company.get__id());
                accEntity.set_token(company.get_token());
                accEntity.set__status(DTOStatus.ADD);
                if (DTOStatus.UPDATE.equals(company.get__status())) {
                    FndCompanyRefAccEntity entity = companyRefAccEntityService.queryFndCompanyRefAccEntity(accEntity);
                    if (entity != null) {
                        accEntity.setRefId(entity.getRefId());
                        accEntity.set__status(company.get__status());
                    }
                    companyRefAccEntityService.setDefaultFlagN(company.getCompanyId());
                }
                accEntities.add(accEntity);
            }
            if (company.getBgtEntityId() != null) {
                FndCompanyRefBgtEntity bgtEntity =
                                new FndCompanyRefBgtEntity(company.getCompanyId(), company.getBgtEntityId(), "Y", "Y");
                bgtEntity.set__id(company.get__id());
                bgtEntity.set_token(company.get_token());
                bgtEntity.set__status(DTOStatus.ADD);
                if (DTOStatus.UPDATE.equals(company.get__status())) {
                    FndCompanyRefBgtEntity entity = companyRefBgtEntityService.queryFndCompanyRefBgtEntity(bgtEntity);
                    if (entity != null) {
                        bgtEntity.setRefId(entity.getRefId());
                        bgtEntity.set__status(company.get__status());
                    }
                    companyRefBgtEntityService.setDefaultFlagN(company.getCompanyId());
                }
                bgtEntities.add(bgtEntity);
            }
        }
        companyRefAccEntityService.batchUpdate(requestCtx, accEntities);
        companyRefBgtEntityService.batchUpdate(requestCtx, bgtEntities);
        return companies;
    }

    @Override
    public List<FndCompany> queryFndCompanyBox(FndCompany dto, IRequest requestContext) {
        return companyMapper.queryFndCompanyBox(dto);
    }

    @Override
    public Boolean checkInTime(Date date, Long companyId) {
        Date startDateActive = companyMapper.select(FndCompany.builder().companyId(companyId).build()).get(0)
                        .getStartDateActive();
        Date endDateActive = companyMapper.select(FndCompany.builder().companyId(companyId).build()).get(0)
                        .getEndDateActive();
        if (date.after(startDateActive) && date.before(endDateActive)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void checkCompanyExists(Long employeeId, Long companyId) throws FndCompanyException {
        if (companyMapper.selectEmployeeCompanyCount(employeeId, companyId) != 1) {
            throw new FndCompanyException(FndCompanyException.MSG_INVALID_EMPLOYEE_COMPANY,
                            FndCompanyException.MSG_INVALID_EMPLOYEE_COMPANY, null);
        }
    }

    @Override
    public List<FndCompany> queryByBgtOrgId(IRequest request, Long bgtOrgId) {
        return companyMapper.queryByBgtOrgId(bgtOrgId);
    }

    @Override
    public Map<String, Object> queryDefaultCompany(IRequest request, FndCompany company) {
        return companyMapper.queryDefaultCompany(company);
    }

    @Override
    public List<FndCompany> queryForBgtStructureAssign(IRequest request, Long magOrgId, Long assignMoId) {
        return companyMapper.queryForBgtStructureAssign(magOrgId, assignMoId);
    }

    @Override
    public List<FndCompany> bgtItemAssignCompany(IRequest request, Long magOrgId, Long assignMoId) {
        return companyMapper.bgtItemAssignCompany(magOrgId, assignMoId);
    }

    @Override
    public List<FndCompany> unitTypeAssignCompany(IRequest request, Long magOrgId, Long unitTypeId) {
        return companyMapper.unitTypeAssignCompany(magOrgId, unitTypeId);
    }

    @Override
    public FndCompany getCompany(IRequest request, Long companyId) {
        FndCompany company = new FndCompany();
        company.setCompanyId(companyId);
        Criteria criteria = new Criteria(company);
        criteria.where(new WhereField(FndCompany.FIELD_COMPANY_ID));
        List<FndCompany> companyList = selectOptions(request, company, criteria);
        if (companyList != null && companyList.size() != 0) {
            return companyList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<FndCompany> workCenterDocTypeFndCompany(IRequest iRequest, @Param("magOrgId") Long magOrgId,
                    @Param("scopeId") Long scopeId) {
        return companyMapper.workCenterDocTypeFndCompany(magOrgId, scopeId);
    }

    @Override
    public List<FndCompany> bgtJournalBatchAssign(IRequest iRequest, Long magOrgId, Long assignMoId, int page,
                    int pageSize) {
        PageHelper.startPage(page,pageSize);
        return companyMapper.bgtJournalBatchAssign(magOrgId,assignMoId);
    }

	@Override
	public List<FndCompany> queryCurrentCompany(IRequest iRequest) {
		return companyMapper.queryCurrentCompany();
	}
	@Override
    public List<FndCompany> queryCompanyForUoms(IRequest iRequest,FndCompany dto, int page,int pageSize){
        PageHelper.startPage(page,pageSize);
        return companyMapper.queryCompanyForUoms(dto);
    }

    @Override
    public List<FndCompany> queryBatchCompany(IRequest request, FndCompany dto, int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        return companyMapper.queryBatchCompany(dto);
    }
    @Override
    public List<FndCompany> queryNoAssignedCompanyForUoms(IRequest iRequest,Long assignMoId,Long magOrgId,int page ,int pageSize){
        PageHelper.startPage(page,pageSize);
        return  companyMapper.queryNoAssignedCompanyForUoms(assignMoId,magOrgId);
    }

}
