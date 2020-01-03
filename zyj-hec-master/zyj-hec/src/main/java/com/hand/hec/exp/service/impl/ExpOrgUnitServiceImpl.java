package com.hand.hec.exp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.util.BeanUtil;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.mapper.ExpEmployeeAssignMapper;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hap.fnd.dto.FndCompanyRefAccBe;
import com.hand.hap.fnd.dto.FndCompanyRefBgtEntity;
import com.hand.hap.fnd.service.IFndCompanyRefAccBeService;
import com.hand.hap.fnd.service.IFndCompanyRefBgtEntityService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.bgt.dto.BgtCenter;
import com.hand.hec.bgt.dto.BgtEntity;
import com.hand.hec.bgt.service.IBgtCenterService;
import com.hand.hec.bgt.service.IBgtEntityService;
import com.hand.hec.exp.dto.ExpOrgPosition;
import com.hand.hec.exp.service.IExpOrgPositionService;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.dto.GldRespCenterRefBc;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import com.hand.hec.gld.service.IGldRespCenterRefBcService;
import com.hand.hec.gld.service.IGldResponsibilityCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpOrgUnit;
import com.hand.hec.exp.dto.ExpOrgUnitRefAccOrg;
import com.hand.hec.exp.dto.ExpOrgUnitRefBgtOrg;
import com.hand.hec.exp.exception.UnitAccOrBgtDfMultiException;
import com.hand.hec.exp.mapper.ExpOrgUnitMapper;
import com.hand.hec.exp.service.IExpOrgUnitRefAccOrgService;
import com.hand.hec.exp.service.IExpOrgUnitRefBgtOrgService;
import com.hand.hec.exp.service.IExpOrgUnitService;

/**
 * <p>
 * ExpOrgUnitServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 11:19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpOrgUnitServiceImpl extends BaseServiceImpl<ExpOrgUnit> implements IExpOrgUnitService {

    @Autowired
    ExpOrgUnitMapper unitMapper;

    @Autowired
    private IExpOrgUnitRefAccOrgService refAccOrgService;

    @Autowired
    private IExpOrgUnitRefBgtOrgService refBgtOrgService;

    @Autowired
    private IExpOrgPositionService positionService;

    @Autowired
    private IGldAccountingEntityService accountingEntityService;

    @Autowired
    private IExpEmployeeAssignService employeeAssignService;


    @Autowired
    private IFndCompanyRefAccBeService fndCompanyRefAccBeService;

    @Autowired
    private IFndCompanyRefBgtEntityService fndCompanyRefBgtEntityService;


    @Autowired
    private ExpEmployeeAssignMapper expEmployeeAssignMapper;


    @Autowired
    private IBgtCenterService bgtCenterService;

    @Autowired
    private IGldRespCenterRefBcService gldRespCenterRefBcService;

    @Autowired
    private IGldResponsibilityCenterService responsibilityCenterService;

    @Autowired
    private IBgtEntityService bgtEntityService;


    @Override
    public List<ExpOrgUnit> checkExpOrgUnit(String controlType, Long unitId, String filtrateMethod, String controlUnitCodeFrom, String controlUnitCodeTo) {
        return unitMapper.checkExpOrgUnit(controlType, unitId, filtrateMethod, controlUnitCodeFrom, controlUnitCodeTo);
    }

    @Override
    public List<ExpOrgUnit> queryAssignUnit(IRequest request, ExpOrgUnit expOrgUnit) {
        return unitMapper.queryAssignUnit(expOrgUnit);
    }

    @Override
    public List<ExpOrgUnit> queryByCompany(IRequest request, ExpOrgUnit expOrgUnit, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return unitMapper.queryByCompany(expOrgUnit);
    }

    @Override
    public List<ExpOrgUnit> submitExpOrgUnit(IRequest request, List<ExpOrgUnit> expOrgUnits) throws UnitAccOrBgtDfMultiException {
        List<ExpOrgUnit> list = new ArrayList<>();
        for (ExpOrgUnit unit : expOrgUnits) {
            ExpOrgUnit u = new ExpOrgUnit();
            switch (unit.get__status()) {
                case DTOStatus.ADD:
                    u = self().insertSelective(request, unit);
                    assignDefaultAccEntity(request, u);
                    assignDefaultBgtEntity(request, u);
                    break;
                case DTOStatus.UPDATE:
                    if (unit.getParentUnitId() != null && self().checkParentLoop(request, unit, unit.getUnitId())) {
                        throw new UnitAccOrBgtDfMultiException("EXP", UnitAccOrBgtDfMultiException.ERROR_PARENT_LOOP);
                    }
                    u = self().updateByPrimaryKey(request, unit);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(unit);
                    break;
                default:
                    break;

            }
            list.add(u);
        }
        return list;
    }

    @Override
    public ExpOrgUnit getDefaultUnit(IRequest request, Long employeeId, Long companyId) {
        ExpOrgPosition position = positionService.getDefaultPosition(request, employeeId, companyId);
        if (position == null) {
            return null;
        }

        ExpOrgUnit unit = new ExpOrgUnit();
        unit.setUnitId(position.getUnitId());
        Criteria criteria = new Criteria(unit);
        criteria.where(new WhereField(ExpOrgUnit.FIELD_UNIT_ID));
        List<ExpOrgUnit> unitList = selectOptions(request, unit, criteria);
        if (unitList != null && unitList.size() != 0) {
            return unitList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean checkParentLoop(IRequest request, ExpOrgUnit unit, Long unitId) {
        ExpOrgUnit expOrgUnit = new ExpOrgUnit();
        expOrgUnit.setUnitId(unit.getParentUnitId());
        ExpOrgUnit pos = self().selectByPrimaryKey(request, expOrgUnit);
        if (pos.getParentUnitId() == null) {
            return false;
        }

        if (pos.getParentUnitId().longValue() == unitId.longValue()) {
            return true;
        } else {
            return this.checkParentLoop(request, pos, unitId);
        }
    }

    private ExpOrgUnitRefAccOrg assignDefaultAccEntity(IRequest request, ExpOrgUnit expOrgUnit) throws UnitAccOrBgtDfMultiException {
        ExpOrgUnitRefAccOrg refAccOrg = new ExpOrgUnitRefAccOrg();
        if (expOrgUnit.getDefaultAccEntityId() != null && expOrgUnit.getDefaultResCenterId() != null) {
            refAccOrg.setUnitId(expOrgUnit.getUnitId());
            refAccOrg.setAccEntityId(expOrgUnit.getDefaultAccEntityId());
            refAccOrg.setRespCenterId(expOrgUnit.getDefaultResCenterId());
            refAccOrg.setDefaultFlag("Y");
            refAccOrg.setEnabledFlag("Y");
            if (refAccOrgService.checkUnitAccDefault(refAccOrg) >= 1) {
                throw new UnitAccOrBgtDfMultiException("EXP", UnitAccOrBgtDfMultiException.ERROR_UNIT_ACC_DEFAULT_MULTI);
            }
            refAccOrg = refAccOrgService.insertSelective(request, refAccOrg);
        }
        return refAccOrg;
    }

    private ExpOrgUnitRefBgtOrg assignDefaultBgtEntity(IRequest request, ExpOrgUnit expOrgUnit) throws UnitAccOrBgtDfMultiException {
        ExpOrgUnitRefBgtOrg refBgtOrg = new ExpOrgUnitRefBgtOrg();
        if (expOrgUnit.getDefaultAccEntityId() != null && expOrgUnit.getDefaultBgtCenterId() != null) {
            refBgtOrg.setUnitId(expOrgUnit.getUnitId());
            refBgtOrg.setBgtEntityId(expOrgUnit.getDefaultBgtEntityId());
            refBgtOrg.setBgtCenterId(expOrgUnit.getDefaultBgtCenterId());
            refBgtOrg.setDefaultFlag("Y");
            refBgtOrg.setEnabledFlag("Y");
            if (refBgtOrgService.checkUnitBgtDefault(refBgtOrg) >= 1) {
                throw new UnitAccOrBgtDfMultiException("EXP", UnitAccOrBgtDfMultiException.ERROR_UNIT_BGT_DEFAULT_MULTI);
            }
            refBgtOrg = refBgtOrgService.insertSelective(request, refBgtOrg);
        }
        return refBgtOrg;
    }


    /**
     * <p>
     * 单据行部门选择lov
     * </p>
     *
     * @param request
     * @param employeeId 员工ID
     * @param companyId  公司ID
     * @param unitCode   部门代码
     * @param unitName   部门名称
     * @return List<Map>
     * @author yang.duan 2019/5/9 10:35
     **/
    @Override
    public List<Map> getUnitForEmployeeAssign(IRequest request, Long employeeId, Long companyId, String unitCode, String unitName) {
        List<Map> unitList = new ArrayList<>();
        //获取员工分配信息
        ExpEmployeeAssign employeeAssign = new ExpEmployeeAssign();
        employeeAssign.setEmployeeId(employeeId);
        employeeAssign.setCompanyId(companyId);
        employeeAssign.setEnabledFlag("Y");
        Criteria empAssignCri = new Criteria(employeeAssign);
        empAssignCri.where(new WhereField(ExpEmployeeAssign.FIELD_EMPLOYEE_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_COMPANY_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_ENABLED_FLAG, Comparison.EQUAL));
        List<ExpEmployeeAssign> employeeAssignList = employeeAssignService.selectOptions(request, employeeAssign, empAssignCri);
        if (employeeAssignList != null && !employeeAssignList.isEmpty()) {
            for (ExpEmployeeAssign assign : employeeAssignList) {
                //根据岗位ID获取部门ID
                ExpOrgPosition position = new ExpOrgPosition();
                position.setPositionId(assign.getPositionId());
                position = positionService.selectByPrimaryKey(request, position);
                //设置核算相关参数
                setGldOrg(request, companyId, employeeId, assign, position.getUnitId());
                //设置预算相关参数
                setBgtOrg(request, companyId, employeeId, assign, position.getUnitId());
                ExpOrgUnit unit = new ExpOrgUnit();
                unit.setUnitId(position.getUnitId());
                unit = self().selectByPrimaryKey(request, unit);
                unit.setUnitName(unit.getDescription());
                unit.setPositionId(position.getPositionId());
                unit.setPositionCode(position.getPositionCode());
                unit.setPositionName(position.getDescription());
                unit.setDefaultAccEntityId(assign.getAccEntityId());
                unit.setDefaultAccEntityDisplay(assign.getAccEntityName());
                unit.setDefaultResCenterId(assign.getResponsibilityCenterId());
                unit.setDefaultResCenterDisplay(assign.getResponsibilityCenterName());
                unit.setDefaultBgtEntityId(assign.getEntityId());
                unit.setDefaultBgtEntityDisplay(assign.getEntityName());
                unit.setDefaultBgtCenterId(assign.getCenterId());
                unit.setDefaultBgtCenterDisplay(assign.getCenterName());
                if ((unitCode == null || "".equals(unitCode)) && (unitName == null || "".equals(unitName))) {
                    unitList.add(BeanUtil.convert2Map(unit));
                } else {
                    if (unitCode != null && unitCode.equals(unit.getUnitCode())) {
                        unitList.add(BeanUtil.convert2Map(unit));
                    } else if (unitName != null && unitName.equals(unit.getDescription())) {
                        unitList.add(BeanUtil.convert2Map(unit));
                    }
                }
            }
        }
        return unitList;
    }


    private void setGldOrg(IRequest request, Long companyId, Long employeeId, ExpEmployeeAssign assign, Long unitId) {
        if (assign != null) {
            //设置核算主体ID
            if (assign.getAccEntityId() == null) {
                GldAccountingEntity accountingEntity = accountingEntityService.queryDefaultAccEntity(request, companyId);
                if (accountingEntity != null) {
                    assign.setAccEntityId(accountingEntity.getAccEntityId());
                }
            }
            //设置成本中心ID
            ExpEmployeeAssign employeeAssign = new ExpEmployeeAssign();
            employeeAssign.setEmployeeId(employeeId);
            employeeAssign.setCompanyId(companyId);
            employeeAssign.setPositionId(assign.getPositionId());
            employeeAssign.setAccEntityId(assign.getAccEntityId());
            employeeAssign.setEnabledFlag("Y");
            Criteria empAssCri = new Criteria(employeeAssign);
            empAssCri.where(new WhereField(ExpEmployeeAssign.FIELD_EMPLOYEE_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_COMPANY_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_POSITION_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_ACC_ENTITY_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_ENABLED_FLAG, Comparison.EQUAL));
            List<ExpEmployeeAssign> employeeAssignList = employeeAssignService.selectOptions(request, employeeAssign, empAssCri);
            if (employeeAssignList != null && !employeeAssignList.isEmpty()) {
                employeeAssign = employeeAssignList.get(0);
            }
            if (employeeAssign.getResponsibilityCenterId() != null) {
                assign.setResponsibilityCenterId(employeeAssign.getResponsibilityCenterId());
            } else {
                //获取部门默认的成本中心
                ExpOrgUnitRefAccOrg orgUnitRefAccOrg = new ExpOrgUnitRefAccOrg();
                orgUnitRefAccOrg.setAccEntityId(assign.getAccEntityId());
                orgUnitRefAccOrg.setUnitId(unitId);
                orgUnitRefAccOrg.setDefaultFlag("Y");
                orgUnitRefAccOrg.setEnabledFlag("Y");
                Criteria unitRefAoCr = new Criteria(orgUnitRefAccOrg);
                unitRefAoCr.where(new WhereField(ExpOrgUnitRefAccOrg.FIELD_ACC_ENTITY_ID, Comparison.EQUAL), new WhereField(ExpOrgUnitRefAccOrg.FIELD_UNIT_ID, Comparison.EQUAL), new WhereField(ExpOrgUnitRefAccOrg.FIELD_DEFAULT_FLAG, Comparison.EQUAL), new WhereField(ExpOrgUnitRefAccOrg.FIELD_DEFAULT_FLAG, Comparison.EQUAL));
                List<ExpOrgUnitRefAccOrg> unitRefAccOrgList = refAccOrgService.selectOptions(request, orgUnitRefAccOrg, unitRefAoCr);
                if (unitRefAccOrgList != null && !unitRefAccOrgList.isEmpty()) {
                    assign.setResponsibilityCenterId(unitRefAccOrgList.get(0).getRespCenterId());
                }
            }
            GldAccountingEntity accountingEntity = new GldAccountingEntity();
            accountingEntity.setAccEntityId(assign.getAccEntityId());
            accountingEntity = accountingEntityService.selectByPrimaryKey(request, accountingEntity);
            if (accountingEntity != null) {
                assign.setAccEntityName(accountingEntity.getAccEntityName());
            }
            GldResponsibilityCenter responsibilityCenter = new GldResponsibilityCenter();
            responsibilityCenter.setResponsibilityCenterId(assign.getResponsibilityCenterId());
            responsibilityCenter = responsibilityCenterService.selectByPrimaryKey(request, responsibilityCenter);
            if (responsibilityCenter != null) {
                assign.setResponsibilityCenterName(responsibilityCenter.getResponsibilityCenterName());
            }
        }
    }

    private void setBgtOrg(IRequest request, Long companyId, Long employeeId, ExpEmployeeAssign assign, Long unitId) {
        if (assign != null) {
            //设置预算实体ID
            ExpEmployeeAssign employeeAssign = new ExpEmployeeAssign();
            employeeAssign.setEmployeeId(employeeId);
            employeeAssign.setCompanyId(companyId);
            employeeAssign.setPositionId(assign.getPositionId());
            employeeAssign.setEnabledFlag("Y");
            Criteria empAssCri = new Criteria(employeeAssign);
            empAssCri.where(new WhereField(ExpEmployeeAssign.FIELD_EMPLOYEE_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_COMPANY_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_POSITION_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_ENABLED_FLAG, Comparison.EQUAL));
            List<ExpEmployeeAssign> employeeAssignList = employeeAssignService.selectOptions(request, employeeAssign, empAssCri);
            if (employeeAssignList != null && !employeeAssignList.isEmpty()) {
                employeeAssign = employeeAssignList.get(0);
            }
            if (employeeAssign.getEntityId() != null) {
                assign.setEntityId(employeeAssign.getEntityId());
            } else {
                // 核算主体+公司获取预算实体
                FndCompanyRefAccBe companyRefAccBe = fndCompanyRefAccBeService.getBgtEntityByComAndAcc(request, companyId, assign.getAccEntityId());
                if (companyRefAccBe != null && companyRefAccBe.getBgtEntityId() != null) {
                    assign.setEntityId(companyRefAccBe.getBgtEntityId());
                } else {
                    // 获取公司默认的预算实体
                    FndCompanyRefBgtEntity companyRefBgtEntity = fndCompanyRefBgtEntityService.getDftBgtEntity(request, companyId);
                    if (companyRefBgtEntity != null && companyRefBgtEntity.getEntityId() != null) {
                        assign.setEntityId(companyRefBgtEntity.getEntityId());
                    }
                }
            }

            // 设置默认预算中心
            // 获取员工分配的预算中心
            ExpEmployeeAssign bgtCenterAssDto = new ExpEmployeeAssign();
            BgtCenter bgtCenter = new BgtCenter();
            bgtCenterAssDto.setEmployeeId(employeeId);
            bgtCenterAssDto.setCompanyId(companyId);
            bgtCenterAssDto.setPositionId(assign.getPositionId());
            bgtCenterAssDto.setEntityId(assign.getEntityId());
            bgtCenterAssDto.setEnabledFlag("Y");
            bgtCenterAssDto = expEmployeeAssignMapper.selectOne(bgtCenterAssDto);
            if (bgtCenterAssDto != null && bgtCenterAssDto.getCenterId() != null) {
                assign.setCenterId(bgtCenterAssDto.getCenterId());
            } else {
                // 部门ID+核算主体ID+成本中心ID+预算实体ID
                ExpOrgUnitRefAccOrg unitRefAccOrg = new ExpOrgUnitRefAccOrg();
                unitRefAccOrg.setUnitId(unitId);
                unitRefAccOrg.setAccEntityId(assign.getAccEntityId());
                unitRefAccOrg.setRespCenterId(assign.getResponsibilityCenterId());
                unitRefAccOrg.setBgtEntityId(assign.getEntityId());
                unitRefAccOrg.setEnabledFlag("Y");
                unitRefAccOrg.setDefaultFlag("Y");
                List<ExpOrgUnitRefAccOrg> unitRefAccOrgList = refAccOrgService.select(request, unitRefAccOrg, 1, 0);
                if (unitRefAccOrgList != null && !unitRefAccOrgList.isEmpty()) {
                    unitRefAccOrg = unitRefAccOrgList.get(0);
                }
                if (unitRefAccOrg != null && unitRefAccOrg.getBgtCenterId() != null) {
                    assign.setCenterId(unitRefAccOrg.getBgtCenterId());
                } else {
                    // 核算主体ID+预算实体ID
                    GldRespCenterRefBc gldRespCenterRefBc = new GldRespCenterRefBc();
                    gldRespCenterRefBc.setRespCenterId(assign.getResponsibilityCenterId());
                    gldRespCenterRefBc.setBgtEntityId(assign.getEntityId());
                    gldRespCenterRefBc.setEnabledFlag("Y");
                    gldRespCenterRefBc.setDefaultFlag("Y");
                    List<GldRespCenterRefBc> gldRespCenterRefBcList = gldRespCenterRefBcService.select(request, gldRespCenterRefBc, 1, 0);
                    if (gldRespCenterRefBcList != null && !gldRespCenterRefBcList.isEmpty()) {
                        gldRespCenterRefBc = gldRespCenterRefBcList.get(0);
                    }
                    if (gldRespCenterRefBc != null && gldRespCenterRefBc.getBgtCenterId() != null) {
                        assign.setCenterId(gldRespCenterRefBc.getBgtCenterId());
                    } else {
                        // 部门ID+预算实体ID
                        ExpOrgUnitRefBgtOrg expOrgUnitRefBgtOrg = new ExpOrgUnitRefBgtOrg();
                        expOrgUnitRefBgtOrg.setUnitId(unitId);
                        expOrgUnitRefBgtOrg.setBgtEntityId(assign.getEntityId());
                        expOrgUnitRefBgtOrg.setEnabledFlag("Y");
                        expOrgUnitRefBgtOrg.setDefaultFlag("Y");
                        List<ExpOrgUnitRefBgtOrg> expOrgUnitRefBgtOrgList = refBgtOrgService.select(request, expOrgUnitRefBgtOrg, 1, 0);
                        if (expOrgUnitRefBgtOrgList != null && !expOrgUnitRefBgtOrgList.isEmpty()) {
                            expOrgUnitRefBgtOrg = expOrgUnitRefBgtOrgList.get(0);
                        }
                        if (expOrgUnitRefBgtOrg != null && expOrgUnitRefBgtOrg.getBgtCenterId() != null) {
                            assign.setCenterId(expOrgUnitRefBgtOrg.getBgtCenterId());
                        }
                    }
                }
            }
            BgtEntity bgtEntity = new BgtEntity();
            bgtEntity.setEntityId(assign.getEntityId());
            bgtEntity = bgtEntityService.selectByPrimaryKey(request, bgtEntity);
            if (bgtEntity != null) {
                assign.setEntityName(bgtEntity.getDescription());
            }

            BgtCenter center = new BgtCenter();
            center.setCenterId(assign.getCenterId());
            center = bgtCenterService.selectByPrimaryKey(request, center);
            if (center != null) {
                assign.setCenterName(center.getDescription());
            }
        }
    }


}
