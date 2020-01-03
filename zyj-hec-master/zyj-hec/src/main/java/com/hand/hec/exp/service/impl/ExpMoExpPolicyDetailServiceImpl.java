package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.exp.dto.ExpEmployeeLevel;
import com.hand.hap.exp.mapper.ExpEmployeeLevelMapper;
import com.hand.hap.extensible.components.ServiceListenerChainFactory;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.mapper.FndCompanyMapper;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.service.ICodeService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.*;
import com.hand.hec.exp.mapper.*;
import com.hand.hec.exp.service.IExpMoExpPolicyConditionService;
import com.hand.hec.exp.service.IExpMoExpPolicyDetailService;
import com.hand.hec.expm.dto.ExpPolicyPlace;
import com.hand.hec.expm.dto.ExpPolicyPlaceType;
import com.hand.hec.expm.mapper.ExpPolicyPlaceMapper;
import com.hand.hec.expm.mapper.ExpPolicyPlaceTypeMapper;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.mapper.GldAccountingEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 政策标准明细定义ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/19 25:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpPolicyDetailServiceImpl extends BaseServiceImpl<ExpMoExpPolicyDetail>
                implements IExpMoExpPolicyDetailService {

    @Autowired
    ExpMoExpPolicyDetailMapper expMoExpPolicyDetailMapper;

    @Autowired
    private ServiceListenerChainFactory chainFactory;
    @Autowired
    ExpMoExpPolicyConditionMapper expMoExpPolicyConditionMapper;
    @Autowired
    IExpMoExpPolicyConditionService expMoExpPolicyConditionService;

    // 系统代码
    @Autowired
    ICodeService codeService;
    // 核算主体
    @Autowired
    GldAccountingEntityMapper gldAccountingEntityMapper;
    // 管理公司
    @Autowired
    FndCompanyMapper fndCompanyMapper;
    // 员工职务
    @Autowired
    ExpEmployeeJobMapper expEmployeeJobMapper;
    // 员工级别
    @Autowired
    ExpEmployeeLevelMapper expEmployeeLevelMapper;
    // 地点
    @Autowired
    ExpPolicyPlaceMapper expPolicyPlaceMapper;
    // 地点类型
    @Autowired
    ExpPolicyPlaceTypeMapper expPolicyPlaceTypeMapper;
    // 岗位
    @Autowired
    ExpOrgPositionMapper expOrgPositionMapper;
    // 部门
    @Autowired
    ExpOrgUnitMapper expOrgUnitMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(ExpMoExpPolicyDetail record) {
        record = (ExpMoExpPolicyDetail) chainFactory.getChain(this).beforeDelete(null, record);
        ExpMoExpPolicyCondition data = new ExpMoExpPolicyCondition();
        data.setDetailId(record.getDetailId());
        List<ExpMoExpPolicyCondition> datas = expMoExpPolicyConditionMapper.select(data);
        for (ExpMoExpPolicyCondition d : datas) {
            expMoExpPolicyConditionService.deleteByPrimaryKey(d);
        }
        int ret = mapper.deleteByPrimaryKey(record);
        checkOvn(ret, record);
        record = (ExpMoExpPolicyDetail) chainFactory.getChain(this).afterDelete(null, record);
        return ret;
    }

    @Override
    public List<CodeValue> querySeatClass(String transportation, IRequest request) {
        if (transportation != null && !"".equals(transportation)) {
            return expMoExpPolicyDetailMapper.querySeatClass(transportation);
        } else {
            return expMoExpPolicyDetailMapper.queryAllSeatClass();
        }
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }


    /**
     * <p>
     * 获取费用政策定义信息(对应原exp_mo_expense_policy_pkg.get_policy_info)
     * <p/>
     * 
     * @param request
     * @param magOrgId 管理组织ID
     * @param companyId 管理公司ID
     * @param vehicleCode 交通工具CODE
     * @param docCategory 单据类别
     * @param docTypeId 单据类型ID
     * @param expenseTypeId 报销类型ID
     * @param expenseItemId 费用项目ID
     * @param reqItemId 申请项目ID
     * @return 符合条件的政策标准明细DTO
     * @author yang.duan 2019/3/12 20:00
     */
    @Override
    public List<ExpMoExpPolicyDetail> getPolicyInfo(IRequest request, Long magOrgId, Long companyId, Long accEntityId,
                    Long employeeJobId, Long employeeLevelId, Long placeId, Long placeTypeId, Long positionId,
                    Long unitId, String vehicleCode, String docCategory, Long docTypeId, Long expenseTypeId,
                    Long expenseItemId, Long reqItemId) {
        // 获取核算主体CODE
        GldAccountingEntity accountingEntity = gldAccountingEntityMapper.selectByPrimaryKey(accEntityId);
        String accEntityCode = (accountingEntity == null ? null : accountingEntity.getAccEntityCode());
        // 获取管理公司
        FndCompany company = fndCompanyMapper.selectByPrimaryKey(companyId);
        String companyCode = (company == null ? null : company.getCompanyCode());
        // 获取员工职务
        ExpEmployeeJob employeeJob = expEmployeeJobMapper.selectByPrimaryKey(employeeJobId);
        String employeeJobCode = (employeeJob == null ? null : employeeJob.getEmployeeJobCode());

        // 获取员工级别
        ExpEmployeeLevel employeeLevel = expEmployeeLevelMapper.selectByPrimaryKey(employeeLevelId);
        String employeeLevelCode = (employeeLevel == null ? null : employeeLevel.getEmployeeLevelsCode());

        // 获取地点
        ExpPolicyPlace expPolicyPlace = expPolicyPlaceMapper.selectByPrimaryKey(placeId);
        String countryCode = (expPolicyPlace == null ? null : expPolicyPlace.getCountryCode());
        String placeCode = (expPolicyPlace == null ? null : expPolicyPlace.getPlaceCode());

        // 获取地点类型
        ExpPolicyPlaceType expPolicyPlaceType = expPolicyPlaceTypeMapper.selectByPrimaryKey(placeTypeId);
        String placeTypeCode = (expPolicyPlaceType == null ? null : expPolicyPlaceType.getPlaceTypeCode());

        // 获取岗位
        ExpOrgPosition expOrgPosition = expOrgPositionMapper.selectByPrimaryKey(positionId);
        String positionCode = (expOrgPosition == null ? null : expOrgPosition.getPositionCode());
        // 获取部门
        ExpOrgUnit expOrgUnit = expOrgUnitMapper.selectByPrimaryKey(unitId);
        String unitCode = (expOrgUnit == null ? null : expOrgUnit.getUnitCode());

        Date currentDate = DateUtils.getCurrentDate();

        List<ExpMoExpPolicyDetail> expPolicyDetails = expMoExpPolicyDetailMapper.getPolicyInfo(magOrgId, currentDate,
                        companyId, accEntityCode, companyCode, countryCode, employeeJobCode, employeeLevelCode,
                        placeCode, placeTypeCode, positionCode, unitCode, vehicleCode, docCategory, docTypeId,
                        expenseTypeId, expenseItemId, reqItemId);
        if (expPolicyDetails != null && !expPolicyDetails.isEmpty()) {
            for (ExpMoExpPolicyDetail detail : expPolicyDetails) {
                detail.setTransportationName(codeService.getCodeMeaningByValue(request, "TRANSPORTATION",
                                detail.getTransportation()));
                detail.setSeatClassName(codeService.getCodeMeaningByValue(request, detail.getTransportation(),
                                detail.getSeatClass()));
                detail.setRoomTypeName(codeService.getCodeMeaningByValue(request, "ROOM_TYPE", detail.getRoomType()));
            }
        }
        return expPolicyDetails;
    }
}
