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
import com.hand.hap.fnd.service.IFndCompanyRefAccEntityService;
import com.hand.hap.fnd.service.IFndCompanyRefBgtEntityService;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.bgt.dto.BgtCenter;
import com.hand.hec.bgt.dto.BgtEntity;
import com.hand.hec.exp.dto.ExpOrgUnitRefAccOrg;
import com.hand.hec.exp.dto.ExpOrgUnitRefBgtOrg;
import com.hand.hec.exp.service.IExpOrgUnitRefAccOrgService;
import com.hand.hec.exp.service.IExpOrgUnitRefBgtOrgService;
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
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpOrgPosition;
import com.hand.hec.exp.mapper.ExpOrgPositionMapper;
import com.hand.hec.exp.service.IExpOrgPositionService;

/**
 * <p>
 * ExpOrgPositionServiceImpl
 * </p>
 *
 * @author guiyuting 2019/02/26 18:45
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpOrgPositionServiceImpl extends BaseServiceImpl<ExpOrgPosition> implements IExpOrgPositionService {

    @Autowired
    ExpOrgPositionMapper positionMapper;

    @Autowired
    IExpEmployeeAssignService assignService;

    @Autowired
    IExpOrgPositionService positionService;

    @Autowired
    IExpEmployeeAssignService employeeAssignService;

    @Autowired
    IGldAccountingEntityService accountingEntityService;

    @Autowired
    IGldResponsibilityCenterService responsibilityCenterService;


    @Autowired
    IFndCompanyRefAccBeService fndCompanyRefAccBeService;

    @Autowired
    IFndCompanyRefBgtEntityService fndCompanyRefBgtEntityService;

    @Autowired
    ExpEmployeeAssignMapper expEmployeeAssignMapper;

    @Autowired
    private IExpOrgUnitRefAccOrgService refAccOrgService;

    @Autowired
    private IExpOrgUnitRefBgtOrgService refBgtOrgService;

    @Autowired
    private IGldRespCenterRefBcService gldRespCenterRefBcService;

    @Override
    public List<ExpOrgPosition> checkExpPosition(String controlType, Long positionId, String filtrateMethod, String controlPositionCodeFrom, String controlPositionCodeTo) {
        return positionMapper.checkExpPosition(controlType, positionId, filtrateMethod, controlPositionCodeFrom, controlPositionCodeTo);
    }

    @Override
    public List<ExpOrgPosition> queryPosition(IRequest request, ExpOrgPosition expOrgPosition, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return positionMapper.queryPosition(expOrgPosition);
    }

    @Override
    public boolean checkParentLoop(IRequest request, ExpOrgPosition position, Long positionId) {
        ExpOrgPosition expOrgPosition = new ExpOrgPosition();
        expOrgPosition.setPositionId(position.getParentPositionId());
        ExpOrgPosition pos = self().selectByPrimaryKey(request, expOrgPosition);
        if (pos.getParentPositionId() == null) {
            return false;
        }

        if (pos.getParentPositionId().longValue() == positionId.longValue()) {
            return true;
        } else {
            return this.checkParentLoop(request, pos, positionId);
        }
    }

    @Override
    public ExpOrgPosition getDefaultPosition(IRequest request, Long employeeId, Long companyId) {
        ExpEmployeeAssign assign = new ExpEmployeeAssign();
        assign.setEmployeeId(employeeId);
        assign.setCompanyId(companyId);
        assign.setPrimaryPositionFlag("Y");
        assign.setEnabledFlag("Y");
        List<ExpEmployeeAssign> assignList = assignService.select(request, assign, 0, 0);

        if (assignList != null && assignList.size() != 0) {
            assign = assignList.get(0);
            ExpOrgPosition position = new ExpOrgPosition();
            position.setPositionId(assign.getPositionId());
            Criteria criteria = new Criteria(position);
            criteria.where(new WhereField(ExpOrgPosition.FIELD_POSITION_ID));
            List<ExpOrgPosition> positionList = positionService.selectOptions(request, position, criteria);
            if (positionList != null && positionList.size() != 0) {
                return positionList.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<ExpOrgPosition> queryPositionAndUnit(IRequest request, Long employeeId) {
        return positionMapper.queryPositionAndUnit(employeeId);
    }

    /**
     * <p>单据行岗位选择lov</p>
     *
     * @param request
     * @param employeeId   员工ID
     * @param companyId    公司ID
     * @param unitId       部门ID
     * @param positionCode 岗位代码
     * @param positionName 岗位描述
     * @return List<Map>
     * @author yang.duan 2019/5/16 10:03
     **/
    @Override
    public List<Map> getPositionForEmployeeAssign(IRequest request, Long employeeId, Long companyId, Long unitId, String positionCode, String positionName) {
        List<Map> positionMapList = new ArrayList<>();
        List<ExpOrgPosition> positionList = positionMapper.getPositionByUnitId(companyId, unitId, positionCode, positionName);
        if (positionList != null && !positionList.isEmpty()) {
            for (ExpOrgPosition position : positionList) {
                //设置核算架构
                setGldOrg(request, employeeId, companyId, position);

                //设置预算架构
                setBgtOrg(request, employeeId, companyId, position);

                positionMapList.add(BeanUtil.convert2Map(position));
            }
        }
        return positionMapList;
    }

    private void setGldOrg(IRequest request, Long employeeId, Long companyId, ExpOrgPosition position) {
        if (position != null) {
            //员工+岗位获取核算主体
            ExpEmployeeAssign accEntityDto = new ExpEmployeeAssign();
            accEntityDto.setEmployeeId(employeeId);
            accEntityDto.setCompanyId(companyId);
            accEntityDto.setPositionId(position.getPositionId());
            accEntityDto.setEnabledFlag("Y");
            List<ExpEmployeeAssign> accEntityDtoList = employeeAssignService.select(request, accEntityDto, 1, 0);
            if (accEntityDtoList != null && !accEntityDtoList.isEmpty()) {
                accEntityDto = accEntityDtoList.get(0);
            }
            if (accEntityDto != null && accEntityDto.getAccEntityId() != null) {
                GldAccountingEntity accountingEntity = new GldAccountingEntity();
                accountingEntity.setAccEntityId(accEntityDto.getAccEntityId());
                accountingEntity = accountingEntityService.selectByPrimaryKey(request, accountingEntity);
                position.setAccEntityId(accountingEntity.getAccEntityId());
                position.setAccEntityName(accountingEntity.getAccEntityName());
            } else {
                //获取公司默认的核算主体
                GldAccountingEntity accountingEntity = accountingEntityService.queryDefaultAccEntity(request, companyId);
                if (accountingEntity != null) {
                    position.setAccEntityId(accountingEntity.getAccEntityId());
                    position.setAccEntityName(accountingEntity.getAccEntityName());
                }
            }

            //获取成本中心
            ExpEmployeeAssign respCenterDto = new ExpEmployeeAssign();
            respCenterDto.setEmployeeId(employeeId);
            respCenterDto.setCompanyId(companyId);
            respCenterDto.setPositionId(position.getPositionId());
            respCenterDto.setAccEntityId(position.getAccEntityId());
            respCenterDto.setEnabledFlag("Y");
            List<ExpEmployeeAssign> respCenterDtoList = employeeAssignService.select(request, respCenterDto, 1, 0);
            if (respCenterDtoList != null && !respCenterDtoList.isEmpty()) {
                respCenterDto = respCenterDtoList.get(0);
            }
            if (respCenterDto != null && respCenterDto.getResponsibilityCenterId() != null) {
                GldResponsibilityCenter responsibilityCenter = new GldResponsibilityCenter();
                responsibilityCenter.setResponsibilityCenterId(respCenterDto.getResponsibilityCenterId());
                responsibilityCenter = responsibilityCenterService.selectByPrimaryKey(request, responsibilityCenter);
                position.setRespCenterId(responsibilityCenter.getResponsibilityCenterId());
                position.setRespCenterName(responsibilityCenter.getResponsibilityCenterName());
            } else {
                //获取部门默认的成本中心
                GldResponsibilityCenter responsibilityCenter = responsibilityCenterService.getDefaultRespCenter(position.getUnitId(), position.getAccEntityId());
                if (responsibilityCenter != null) {
                    position.setRespCenterId(responsibilityCenter.getParentRespCenterId());
                    position.setRespCenterName(responsibilityCenter.getResponsibilityCenterName());
                }
            }
        }
    }

    private void setBgtOrg(IRequest request, Long employeeId, Long companyId, ExpOrgPosition position) {
        if (position != null) {
            //获取员工分配的预算实体
            ExpEmployeeAssign employeeAssign = new ExpEmployeeAssign();
            employeeAssign.setEmployeeId(employeeId);
            employeeAssign.setCompanyId(companyId);
            employeeAssign.setPositionId(position.getPositionId());
            employeeAssign.setEnabledFlag("Y");
            Criteria empAssCri = new Criteria(employeeAssign);
            empAssCri.where(new WhereField(ExpEmployeeAssign.FIELD_EMPLOYEE_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_COMPANY_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_POSITION_ID, Comparison.EQUAL), new WhereField(ExpEmployeeAssign.FIELD_ENABLED_FLAG, Comparison.EQUAL));
            List<ExpEmployeeAssign> employeeAssignList = employeeAssignService.selectOptions(request, employeeAssign, empAssCri);
            if (employeeAssignList != null && !employeeAssignList.isEmpty()) {
                employeeAssign = employeeAssignList.get(0);
            }
            if (employeeAssign.getEntityId() != null) {
                position.setBgtEntityId(employeeAssign.getEntityId());
            } else {
                // 核算主体+公司获取预算实体
                FndCompanyRefAccBe companyRefAccBe = fndCompanyRefAccBeService.getBgtEntityByComAndAcc(request, companyId, position.getAccEntityId());
                if (companyRefAccBe != null && companyRefAccBe.getBgtEntityId() != null) {
                    position.setBgtEntityId(companyRefAccBe.getBgtEntityId());
                } else {
                    // 获取公司默认的预算实体
                    FndCompanyRefBgtEntity companyRefBgtEntity = fndCompanyRefBgtEntityService.getDftBgtEntity(request, companyId);
                    if (companyRefBgtEntity != null && companyRefBgtEntity.getEntityId() != null) {
                        position.setBgtEntityId(companyRefBgtEntity.getEntityId());
                    }
                }
            }

            // 设置默认预算中心
            // 获取员工分配的预算中心
            ExpEmployeeAssign bgtCenterAssDto = new ExpEmployeeAssign();
            BgtCenter bgtCenter = new BgtCenter();
            bgtCenterAssDto.setEmployeeId(employeeId);
            bgtCenterAssDto.setCompanyId(companyId);
            bgtCenterAssDto.setPositionId(position.getPositionId());
            bgtCenterAssDto.setEntityId(position.getBgtEntityId());
            bgtCenterAssDto.setEnabledFlag("Y");
            bgtCenterAssDto = expEmployeeAssignMapper.selectOne(bgtCenterAssDto);
            if (bgtCenterAssDto != null && bgtCenterAssDto.getCenterId() != null) {
                if (bgtCenter != null) {
                    position.setBgtCenterId(bgtCenterAssDto.getCenterId());
                }
            } else {
                // 部门ID+核算主体ID+成本中心ID+预算实体ID
                ExpOrgUnitRefAccOrg unitRefAccOrg = new ExpOrgUnitRefAccOrg();
                unitRefAccOrg.setUnitId(position.getUnitId());
                unitRefAccOrg.setAccEntityId(position.getAccEntityId());
                unitRefAccOrg.setRespCenterId(position.getRespCenterId());
                unitRefAccOrg.setBgtEntityId(position.getBgtEntityId());
                unitRefAccOrg.setEnabledFlag("Y");
                unitRefAccOrg.setDefaultFlag("Y");
                List<ExpOrgUnitRefAccOrg> unitRefAccOrgList = refAccOrgService.select(request, unitRefAccOrg, 1, 0);
                if (unitRefAccOrgList != null && !unitRefAccOrgList.isEmpty()) {
                    unitRefAccOrg = unitRefAccOrgList.get(0);
                }
                if (unitRefAccOrg != null && unitRefAccOrg.getBgtCenterId() != null) {
                    position.setBgtCenterId(unitRefAccOrg.getBgtCenterId());
                } else {
                    // 核算主体ID+预算实体ID
                    GldRespCenterRefBc gldRespCenterRefBc = new GldRespCenterRefBc();
                    gldRespCenterRefBc.setRespCenterId(position.getRespCenterId());
                    gldRespCenterRefBc.setBgtEntityId(position.getBgtEntityId());
                    gldRespCenterRefBc.setEnabledFlag("Y");
                    gldRespCenterRefBc.setDefaultFlag("Y");
                    List<GldRespCenterRefBc> gldRespCenterRefBcList = gldRespCenterRefBcService.select(request, gldRespCenterRefBc, 1, 0);
                    if (gldRespCenterRefBcList != null && !gldRespCenterRefBcList.isEmpty()) {
                        gldRespCenterRefBc = gldRespCenterRefBcList.get(0);
                    }
                    if (gldRespCenterRefBc != null && gldRespCenterRefBc.getBgtCenterId() != null) {
                        position.setBgtCenterId(gldRespCenterRefBc.getBgtCenterId());
                    } else {
                        // 部门ID+预算实体ID
                        ExpOrgUnitRefBgtOrg expOrgUnitRefBgtOrg = new ExpOrgUnitRefBgtOrg();
                        expOrgUnitRefBgtOrg.setUnitId(position.getUnitId());
                        expOrgUnitRefBgtOrg.setBgtEntityId(position.getBgtEntityId());
                        expOrgUnitRefBgtOrg.setEnabledFlag("Y");
                        expOrgUnitRefBgtOrg.setDefaultFlag("Y");
                        List<ExpOrgUnitRefBgtOrg> expOrgUnitRefBgtOrgList = refBgtOrgService.select(request, expOrgUnitRefBgtOrg, 1, 0);
                        if (expOrgUnitRefBgtOrgList != null && !expOrgUnitRefBgtOrgList.isEmpty()) {
                            expOrgUnitRefBgtOrg = expOrgUnitRefBgtOrgList.get(0);
                        }
                        if (expOrgUnitRefBgtOrg != null && expOrgUnitRefBgtOrg.getBgtCenterId() != null) {
                            position.setBgtCenterId(expOrgUnitRefBgtOrg.getBgtCenterId());
                        }
                    }
                }
            }
        }
    }
}
