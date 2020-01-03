package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.gld.mapper.GldCurrencyMapper;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBudgetReserve;
import com.hand.hec.bgt.dto.BgtEntity;
import com.hand.hec.bgt.mapper.BgtBudgetReserveMapper;
import com.hand.hec.bgt.service.IBgtBudgetItemMappingService;
import com.hand.hec.bgt.service.IBgtBudgetItemService;
import com.hand.hec.bgt.service.IBgtBudgetReserveService;
import com.hand.hec.bgt.service.IBgtEntityService;
import com.hand.hec.exp.dto.*;
import com.hand.hec.exp.mapper.ExpMoReqItemMapper;
import com.hand.hec.exp.mapper.ExpRequisitionDistMapper;
import com.hand.hec.exp.service.IExpRequisitionDistService;
import com.hand.hec.exp.service.IExpRequisitionLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpRequisitionDistServiceImpl extends BaseServiceImpl<ExpRequisitionDist> implements IExpRequisitionDistService {
    @Autowired
    private ExpRequisitionDistMapper expRequisitionDistMapper;

    @Autowired
    private IExpRequisitionLineService expReqLineService;

    @Autowired
    GldCurrencyMapper gldCurrencyMapper;

    @Autowired
    BgtBudgetReserveMapper budgetReserveMapper;

    @Autowired
    IBgtBudgetItemService bgtBudgetItemService;

    @Autowired
    IBgtBudgetItemMappingService budgetItemMappingService;

    @Autowired
    IBgtEntityService bgtEntityService;

    @Autowired
    IBgtBudgetReserveService bgtBudgetReserveService;

    @Autowired
    ExpMoReqItemMapper expMoReqItemMapper;

    @Override
    public List<ExpRequisitionDist> getAllExpRequisitionDist(IRequest iRequest, Long expRequisitionHeaderId) {
        return expRequisitionDistMapper.getAllExpRequisitionDist(expRequisitionHeaderId);
    }

    @Override
    public int countUnAuditExpReport(IRequest iRequest, Long expRequisitionHeaderId, Long expRequisitionLineId, Long expRequisitionDistId) {
        return expRequisitionDistMapper.countUnAuditExpReport(expRequisitionHeaderId, expRequisitionLineId, expRequisitionDistId);
    }

    /**
     * <p>
     * 申请单分配行新增
     * <p/>
     *
     * @param request
     * @param expReqDist   需要保存的分配行信息
     * @param expMoReqType 申请单类型
     * @param expReqHeader 申请单头
     * @param expReqLine   申请单行
     * @return 返回新增后的分配行信息
     * @throws RuntimeException exception
     * @author jiangxz 2019/4/4 09:11
     */
    @Override
    public ExpRequisitionDist insertDistLine(IRequest request, ExpRequisitionDist expReqDist, ExpMoReqType expMoReqType, ExpRequisitionHeader expReqHeader, ExpRequisitionLine expReqLine) {
        if (ExpRequisitionHeader.FIELD_STATUS_GENERATE.equals(expReqHeader.getRequisitionStatus()) || ExpRequisitionHeader.FIELD_STATUS_REJECTED.equals(expReqHeader.getRequisitionStatus()) || ExpRequisitionHeader.FIELD_STATUS_TAKEN_BACK.equals(expReqHeader.getRequisitionStatus())) {
            //检验单价、金额必须为正数
            expReqLineService.signCheck(expReqDist.getBusinessPrice());
            expReqLineService.signCheck(new BigDecimal(expReqDist.getPrimaryQuantity()));
            Long budgetItemId = expReqDist.getBudgetItemId();
            // 获取核算主体本位币
            String functionalCurrencyCode = gldCurrencyMapper.getAccEntityFunCurrCode(expReqDist.getAccEntityId());
            //计算金额
            ExpRequisitionLine line = expReqLineService.calcAmount(expReqDist.getBusinessPrice(), new BigDecimal(expReqDist.getPrimaryQuantity()), expReqDist.getBusinessCurrencyCode(), expReqDist.getBiz2payExchangeRate(), expReqDist.getPaymentCurrencyCode(), expReqDist.getPay2magExchangeRate(), expReqDist.getManagementCurrencyCode(), expReqDist.getPay2funExchangeRate(), functionalCurrencyCode);
            if (budgetItemId.longValue() == -1) {
                // 根据预算项决定规则获取预算项目ID
                budgetItemId = budgetItemMappingService.getPrioRityItemId(ExpRequisitionHeader.FIELD_EXP_REQ, expReqDist.getCompanyId(), expMoReqType.getMoExpReqTypeId(), expReqDist.getMoExpenseTypeId(), expReqDist.getMoReqItemId(), null, expReqDist.getUnitId(), expReqDist.getPositionId(), expReqDist.getEmployeeId(), expReqDist.getAccEntityId(), expReqDist.getRespCenterId(), expReqHeader.getPayeeCategory(), expReqHeader.getPayeeId(), (line.getDimension1Id() == null ? expReqDist.getDimension1Id() : line.getDimension1Id()), (line.getDimension2Id() == null ? expReqDist.getDimension2Id() : line.getDimension2Id()), (line.getDimension3Id() == null ? expReqDist.getDimension3Id() : line.getDimension3Id()), (line.getDimension4Id() == null ? expReqDist.getDimension4Id() : line.getDimension4Id()), (line.getDimension5Id() == null ? expReqDist.getDimension5Id() : line.getDimension5Id()), (line.getDimension6Id() == null ? expReqDist.getDimension6Id() : line.getDimension6Id()), (line.getDimension7Id() == null ? expReqDist.getDimension7Id() : line.getDimension7Id()), (line.getDimension8Id() == null ? expReqDist.getDimension8Id() : line.getDimension8Id()), (line.getDimension9Id() == null ? expReqDist.getDimension9Id() : line.getDimension9Id()), (line.getDimension10Id() == null ? expReqDist.getDimension10Id() : line.getDimension10Id()), (line.getDimension11Id() == null ? expReqDist.getDimension11Id() : line.getDimension11Id()), (line.getDimension12Id() == null ? expReqDist.getDimension12Id() : line.getDimension12Id()), (line.getDimension13Id() == null ? expReqDist.getDimension13Id() : line.getDimension13Id()), (line.getDimension14Id() == null ? expReqDist.getDimension14Id() : line.getDimension14Id()), (line.getDimension15Id() == null ? expReqDist.getDimension15Id() : line.getDimension16Id()), (line.getDimension16Id() == null ? expReqDist.getDimension16Id() : line.getDimension16Id()), (line.getDimension17Id() == null ? expReqDist.getDimension17Id() : line.getDimension17Id()), (line.getDimension18Id() == null ? expReqDist.getDimension18Id() : line.getDimension18Id()), (line.getDimension19Id() == null ? expReqDist.getDimension19Id() : line.getDimension19Id()), (line.getDimension20Id() == null ? expReqDist.getDimension20Id() : line.getDimension20Id()));
            }
            if (budgetItemId.longValue() == -1) {
                ExpMoReqItem expMoReqItem = expMoReqItemMapper.selectByPrimaryKey(expReqLine.getMoReqItemId());
                budgetItemId = expMoReqItem.getBudgetItemId();
            }
            expReqDist.setExpRequisitionLineId(expReqLine.getExpRequisitionLineId());
            expReqDist.setBudgetItemId(budgetItemId);
            expReqDist.setPaymentPrice(line.getPaymentPrice());
            expReqDist.setManagementPrice(line.getManagementPrice());
            expReqDist.setBusinessAmount(line.getBusinessAmount());
            expReqDist.setPaymentAmount(line.getPaymentAmount());
            expReqDist.setManagementAmount(line.getManagementAmount());
            expReqDist.setRequisitionFunctionalAmount(line.getRequisitionFunctionalAmount());
            expReqDist.setPeriodName(expReqHeader.getPeriodName());
            expReqDist.setPay2funExchangeRate(expReqHeader.getPay2funExchangeRate());
            expReqDist.setPayeeCategory(expReqHeader.getPayeeCategory());
            expReqDist.setPayeeId(expReqHeader.getPayeeId());
            expReqDist.setPaymentFlag("N");
            expReqDist.setRequisitionStatus(expReqHeader.getRequisitionStatus());
            expReqDist.setAuditFlag(expReqHeader.getAuditFlag());
            expReqDist.setReleasedQuantity(0L);
            expReqDist.setReleasedStatus("N");
            expReqDist.setCreatedBy(request.getUserId());
            expReqDist.setCreationDate(new Date());
            expReqDist.setLastUpdatedBy(request.getUserId());
            expReqDist.setLastUpdateDate(new Date());
            // 分配行插入
            expReqDist = self().insertSelective(request, expReqDist);
            // 插入预算保留表
            if (budgetItemId.longValue() != -1 && "Y".equals(expMoReqType.getReserveBudget())) {
                BgtEntity bgtEntity = new BgtEntity();
                bgtEntity.setEntityId(expReqDist.getBgtEntityId());
                bgtEntity = bgtEntityService.selectByPrimaryKey(request, bgtEntity);
                BgtBudgetReserve budgetReserve = new BgtBudgetReserve();
                budgetReserve.setCompanyId(expReqDist.getCompanyId());
                budgetReserve.setBgtOrgId(bgtEntity.getBgtOrgId());
                budgetReserve.setBgtEntityId(expReqDist.getBgtEntityId());
                budgetReserve.setBgtCenterId(expReqDist.getBgtCenterId());
                budgetReserve.setPeriodName(expReqDist.getPeriodName());
                budgetReserve.setBusinessType(ExpRequisitionHeader.FIELD_EXP_REQ);
                budgetReserve.setReserveFlag("U");
                budgetReserve.setStatus("N");
                budgetReserve.setDocumentId(expReqHeader.getExpRequisitionHeaderId());
                budgetReserve.setDocumentLineId(expReqDist.getExpRequisitionDistId());
                budgetReserve.setCurrency(expReqDist.getPaymentCurrencyCode());
                budgetReserve.setBudgetItemId(budgetItemId);
                budgetReserve.setResponsibilityCenterId(expReqDist.getRespCenterId());
                budgetReserve.setExchangeRateType(expReqDist.getPay2funExchangeType());
                budgetReserve.setExchangeRate(expReqDist.getPay2funExchangeRate());
                budgetReserve.setAmount(expReqDist.getPaymentAmount());
                budgetReserve.setFunctionalAmount(expReqDist.getRequisitionFunctionalAmount());
                budgetReserve.setQuantity(new BigDecimal(expReqDist.getPrimaryQuantity()));
                budgetReserve.setUnitId(expReqDist.getUnitId());
                budgetReserve.setPositionId(expReqDist.getPositionId());
                budgetReserve.setEmployeeId(expReqDist.getEmployeeId());
                budgetReserve.setDimension1Id(expReqLine.getDimension1Id() == null ? expReqDist.getDimension1Id() : expReqLine.getDimension1Id());
                budgetReserve.setDimension2Id(expReqLine.getDimension2Id() == null ? expReqDist.getDimension2Id() : expReqLine.getDimension2Id());
                budgetReserve.setDimension3Id(expReqLine.getDimension3Id() == null ? expReqDist.getDimension3Id() : expReqLine.getDimension3Id());
                budgetReserve.setDimension4Id(expReqLine.getDimension4Id() == null ? expReqDist.getDimension4Id() : expReqLine.getDimension4Id());
                budgetReserve.setDimension5Id(expReqLine.getDimension5Id() == null ? expReqDist.getDimension5Id() : expReqLine.getDimension5Id());
                budgetReserve.setDimension6Id(expReqLine.getDimension6Id() == null ? expReqDist.getDimension6Id() : expReqLine.getDimension6Id());
                budgetReserve.setDimension7Id(expReqLine.getDimension7Id() == null ? expReqDist.getDimension7Id() : expReqLine.getDimension7Id());
                budgetReserve.setDimension8Id(expReqLine.getDimension8Id() == null ? expReqDist.getDimension8Id() : expReqLine.getDimension8Id());
                budgetReserve.setDimension9Id(expReqLine.getDimension9Id() == null ? expReqDist.getDimension9Id() : expReqLine.getDimension9Id());
                budgetReserve.setDimension10Id(expReqLine.getDimension10Id() == null ? expReqDist.getDimension10Id() : expReqLine.getDimension10Id());
                budgetReserve.setDimension11Id(expReqLine.getDimension11Id() == null ? expReqDist.getDimension11Id() : expReqLine.getDimension11Id());
                budgetReserve.setDimension12Id(expReqLine.getDimension12Id() == null ? expReqDist.getDimension12Id() : expReqLine.getDimension12Id());
                budgetReserve.setDimension13Id(expReqLine.getDimension13Id() == null ? expReqDist.getDimension13Id() : expReqLine.getDimension13Id());
                budgetReserve.setDimension14Id(expReqLine.getDimension14Id() == null ? expReqDist.getDimension14Id() : expReqLine.getDimension14Id());
                budgetReserve.setDimension15Id(expReqLine.getDimension15Id() == null ? expReqDist.getDimension15Id() : expReqLine.getDimension15Id());
                budgetReserve.setDimension16Id(expReqLine.getDimension16Id() == null ? expReqDist.getDimension16Id() : expReqLine.getDimension16Id());
                budgetReserve.setDimension17Id(expReqLine.getDimension17Id() == null ? expReqDist.getDimension17Id() : expReqLine.getDimension17Id());
                budgetReserve.setDimension18Id(expReqLine.getDimension18Id() == null ? expReqDist.getDimension18Id() : expReqLine.getDimension18Id());
                budgetReserve.setDimension19Id(expReqLine.getDimension19Id() == null ? expReqDist.getDimension19Id() : expReqLine.getDimension19Id());
                budgetReserve.setDimension20Id(expReqLine.getDimension20Id() == null ? expReqDist.getDimension20Id() : expReqLine.getDimension20Id());
                budgetReserve = bgtBudgetReserveService.insertBgtReserve(request, budgetReserve);
            }


        }
        return expReqDist;
    }


    /**
     * <p>
     * 报销单分配行更新
     * <p/>
     *
     * @param request
     * @param expReqDist   申请单扩展行信息
     * @param expMoReqType 申请单类型信息
     * @param expReqHeader 申请单头信息
     * @param expReqLine   申请单行信息
     * @return 更新后的分配行
     * @throws RuntimeException exception
     * @author jiangx 2019/4/3 11:04
     */
    @Override
    public ExpRequisitionDist updateDistLine(IRequest request, ExpRequisitionDist expReqDist, ExpMoReqType expMoReqType, ExpRequisitionHeader expReqHeader, ExpRequisitionLine expReqLine) {
        if (ExpRequisitionHeader.FIELD_STATUS_GENERATE.equals(expReqHeader.getRequisitionStatus()) || ExpRequisitionHeader.FIELD_STATUS_REJECTED.equals(expReqHeader.getRequisitionStatus()) || ExpRequisitionHeader.FIELD_STATUS_TAKEN_BACK.equals(expReqHeader.getRequisitionStatus())) {
            //检验单价、金额必须为正数
            expReqLineService.signCheck(expReqDist.getBusinessPrice());
            expReqLineService.signCheck(new BigDecimal(expReqDist.getPrimaryQuantity()));
            Long budgetItemId = expReqDist.getBudgetItemId();
            // 获取核算主体本位币
            String functionalCurrencyCode = gldCurrencyMapper.getAccEntityFunCurrCode(expReqDist.getAccEntityId());
            //计算金额
            ExpRequisitionLine line = expReqLineService.calcAmount(expReqDist.getBusinessPrice(), new BigDecimal(expReqDist.getPrimaryQuantity()), expReqDist.getBusinessCurrencyCode(), expReqDist.getBiz2payExchangeRate(), expReqDist.getPaymentCurrencyCode(), expReqDist.getPay2magExchangeRate(), expReqDist.getManagementCurrencyCode(), expReqDist.getPay2funExchangeRate(), functionalCurrencyCode);
            if (budgetItemId.longValue() == -1) {
                // 根据预算项决定规则获取预算项目ID
                budgetItemId = budgetItemMappingService.getPrioRityItemId(ExpRequisitionHeader.FIELD_EXP_REQ, expReqDist.getCompanyId(), expMoReqType.getMoExpReqTypeId(), expReqDist.getMoExpenseTypeId(), expReqDist.getMoReqItemId(), null, expReqDist.getUnitId(), expReqDist.getPositionId(), expReqDist.getEmployeeId(), expReqDist.getAccEntityId(), expReqDist.getRespCenterId(), expReqHeader.getPayeeCategory(), expReqHeader.getPayeeId(), (line.getDimension1Id() == null ? expReqDist.getDimension1Id() : line.getDimension1Id()), (line.getDimension2Id() == null ? expReqDist.getDimension2Id() : line.getDimension2Id()), (line.getDimension3Id() == null ? expReqDist.getDimension3Id() : line.getDimension3Id()), (line.getDimension4Id() == null ? expReqDist.getDimension4Id() : line.getDimension4Id()), (line.getDimension5Id() == null ? expReqDist.getDimension5Id() : line.getDimension5Id()), (line.getDimension6Id() == null ? expReqDist.getDimension6Id() : line.getDimension6Id()), (line.getDimension7Id() == null ? expReqDist.getDimension7Id() : line.getDimension7Id()), (line.getDimension8Id() == null ? expReqDist.getDimension8Id() : line.getDimension8Id()), (line.getDimension9Id() == null ? expReqDist.getDimension9Id() : line.getDimension9Id()), (line.getDimension10Id() == null ? expReqDist.getDimension10Id() : line.getDimension10Id()), (line.getDimension11Id() == null ? expReqDist.getDimension11Id() : line.getDimension11Id()), (line.getDimension12Id() == null ? expReqDist.getDimension12Id() : line.getDimension12Id()), (line.getDimension13Id() == null ? expReqDist.getDimension13Id() : line.getDimension13Id()), (line.getDimension14Id() == null ? expReqDist.getDimension14Id() : line.getDimension14Id()), (line.getDimension15Id() == null ? expReqDist.getDimension15Id() : line.getDimension16Id()), (line.getDimension16Id() == null ? expReqDist.getDimension16Id() : line.getDimension16Id()), (line.getDimension17Id() == null ? expReqDist.getDimension17Id() : line.getDimension17Id()), (line.getDimension18Id() == null ? expReqDist.getDimension18Id() : line.getDimension18Id()), (line.getDimension19Id() == null ? expReqDist.getDimension19Id() : line.getDimension19Id()), (line.getDimension20Id() == null ? expReqDist.getDimension20Id() : line.getDimension20Id()));
            }
            if (budgetItemId.longValue() == -1) {
                ExpMoReqItem expMoReqItem = expMoReqItemMapper.selectByPrimaryKey(expReqLine.getMoReqItemId());
                budgetItemId = expMoReqItem.getBudgetItemId();
            }
            expReqDist.setBudgetItemId(budgetItemId);
            expReqDist.setPaymentPrice(line.getPaymentPrice());
            expReqDist.setManagementPrice(line.getManagementPrice());
            expReqDist.setBusinessAmount(line.getBusinessAmount());
            expReqDist.setPaymentAmount(line.getPaymentAmount());
            expReqDist.setManagementAmount(line.getManagementAmount());
            expReqDist.setRequisitionFunctionalAmount(line.getRequisitionFunctionalAmount());
            expReqDist.setLastUpdatedBy(request.getUserId());
            expReqDist.setLastUpdateDate(new Date());
            //更新分配
            expReqDist = self().updateByPrimaryKey(request, expReqDist);

            // 插入预算保留表
            if (budgetItemId.longValue() != -1 && "Y".equals(expMoReqType.getReserveBudget())) {
                BgtEntity bgtEntity = new BgtEntity();
                bgtEntity.setEntityId(expReqDist.getBgtEntityId());
                bgtEntity = bgtEntityService.selectByPrimaryKey(request, bgtEntity);
                BgtBudgetReserve dto = new BgtBudgetReserve();
                dto.setBusinessType(ExpRequisitionHeader.FIELD_EXP_REQ);
                dto.setDocumentId(expReqHeader.getExpRequisitionHeaderId());
                dto.setDocumentLineId(expReqDist.getExpRequisitionDistId());
                int count = budgetReserveMapper.selectCount(dto);
                if (count != 0) {
                    BgtBudgetReserve budgetReserveUpdate = new BgtBudgetReserve();
                    budgetReserveUpdate.setCompanyId(expReqDist.getCompanyId());
                    budgetReserveUpdate.setBgtOrgId(bgtEntity.getBgtOrgId());
                    budgetReserveUpdate.setBgtEntityId(expReqDist.getBgtEntityId());
                    budgetReserveUpdate.setBgtCenterId(expReqDist.getBgtCenterId());
                    budgetReserveUpdate.setPeriodName(expReqDist.getPeriodName());
                    budgetReserveUpdate.setBusinessType(ExpRequisitionHeader.FIELD_EXP_REQ);
                    budgetReserveUpdate.setDocumentId(expReqHeader.getExpRequisitionHeaderId());
                    budgetReserveUpdate.setDocumentLineId(expReqDist.getExpRequisitionDistId());
                    budgetReserveUpdate.setCurrency(expReqDist.getPaymentCurrencyCode());
                    budgetReserveUpdate.setBudgetItemId(budgetItemId);
                    budgetReserveUpdate.setResponsibilityCenterId(expReqDist.getRespCenterId());
                    budgetReserveUpdate.setExchangeRateType(expReqDist.getPay2funExchangeType());
                    budgetReserveUpdate.setExchangeRate(expReqDist.getPay2funExchangeRate());
                    budgetReserveUpdate.setAmount(expReqDist.getPaymentAmount());
                    budgetReserveUpdate.setFunctionalAmount(expReqDist.getRequisitionFunctionalAmount());
                    budgetReserveUpdate.setQuantity(new BigDecimal(expReqDist.getPrimaryQuantity()));
                    budgetReserveUpdate.setUnitId(expReqDist.getUnitId());
                    budgetReserveUpdate.setPositionId(expReqDist.getPositionId());
                    budgetReserveUpdate.setEmployeeId(expReqDist.getEmployeeId());
                    budgetReserveUpdate.setDimension1Id(expReqLine.getDimension1Id() == null ? expReqDist.getDimension1Id() : expReqLine.getDimension1Id());
                    budgetReserveUpdate.setDimension2Id(expReqLine.getDimension2Id() == null ? expReqDist.getDimension2Id() : expReqLine.getDimension2Id());
                    budgetReserveUpdate.setDimension3Id(expReqLine.getDimension3Id() == null ? expReqDist.getDimension3Id() : expReqLine.getDimension3Id());
                    budgetReserveUpdate.setDimension4Id(expReqLine.getDimension4Id() == null ? expReqDist.getDimension4Id() : expReqLine.getDimension4Id());
                    budgetReserveUpdate.setDimension5Id(expReqLine.getDimension5Id() == null ? expReqDist.getDimension5Id() : expReqLine.getDimension5Id());
                    budgetReserveUpdate.setDimension6Id(expReqLine.getDimension6Id() == null ? expReqDist.getDimension6Id() : expReqLine.getDimension6Id());
                    budgetReserveUpdate.setDimension7Id(expReqLine.getDimension7Id() == null ? expReqDist.getDimension7Id() : expReqLine.getDimension7Id());
                    budgetReserveUpdate.setDimension8Id(expReqLine.getDimension8Id() == null ? expReqDist.getDimension8Id() : expReqLine.getDimension8Id());
                    budgetReserveUpdate.setDimension9Id(expReqLine.getDimension9Id() == null ? expReqDist.getDimension9Id() : expReqLine.getDimension9Id());
                    budgetReserveUpdate.setDimension10Id(expReqLine.getDimension10Id() == null ? expReqDist.getDimension10Id() : expReqLine.getDimension10Id());
                    budgetReserveUpdate.setDimension11Id(expReqLine.getDimension11Id() == null ? expReqDist.getDimension11Id() : expReqLine.getDimension11Id());
                    budgetReserveUpdate.setDimension12Id(expReqLine.getDimension12Id() == null ? expReqDist.getDimension12Id() : expReqLine.getDimension12Id());
                    budgetReserveUpdate.setDimension13Id(expReqLine.getDimension13Id() == null ? expReqDist.getDimension13Id() : expReqLine.getDimension13Id());
                    budgetReserveUpdate.setDimension14Id(expReqLine.getDimension14Id() == null ? expReqDist.getDimension14Id() : expReqLine.getDimension14Id());
                    budgetReserveUpdate.setDimension15Id(expReqLine.getDimension15Id() == null ? expReqDist.getDimension15Id() : expReqLine.getDimension15Id());
                    budgetReserveUpdate.setDimension16Id(expReqLine.getDimension16Id() == null ? expReqDist.getDimension16Id() : expReqLine.getDimension16Id());
                    budgetReserveUpdate.setDimension17Id(expReqLine.getDimension17Id() == null ? expReqDist.getDimension17Id() : expReqLine.getDimension17Id());
                    budgetReserveUpdate.setDimension18Id(expReqLine.getDimension18Id() == null ? expReqDist.getDimension18Id() : expReqLine.getDimension18Id());
                    budgetReserveUpdate.setDimension19Id(expReqLine.getDimension19Id() == null ? expReqDist.getDimension19Id() : expReqLine.getDimension19Id());
                    budgetReserveUpdate.setDimension20Id(expReqLine.getDimension20Id() == null ? expReqDist.getDimension20Id() : expReqLine.getDimension20Id());
                    // 更新预算保留表
                    bgtBudgetReserveService.updateBgtReserve(request, budgetReserveUpdate);
                } else {
                    BgtBudgetReserve budgetReserve = new BgtBudgetReserve();
                    budgetReserve.setCompanyId(expReqDist.getCompanyId());
                    budgetReserve.setBgtOrgId(bgtEntity.getBgtOrgId());
                    budgetReserve.setBgtEntityId(expReqDist.getBgtEntityId());
                    budgetReserve.setBgtCenterId(expReqDist.getBgtCenterId());
                    budgetReserve.setPeriodName(expReqDist.getPeriodName());
                    budgetReserve.setBusinessType(ExpRequisitionHeader.FIELD_EXP_REQ);
                    budgetReserve.setReserveFlag("U");
                    budgetReserve.setStatus("N");
                    budgetReserve.setDocumentId(expReqHeader.getExpRequisitionHeaderId());
                    budgetReserve.setDocumentLineId(expReqDist.getExpRequisitionDistId());
                    budgetReserve.setCurrency(expReqDist.getPaymentCurrencyCode());
                    budgetReserve.setBudgetItemId(budgetItemId);
                    budgetReserve.setResponsibilityCenterId(expReqDist.getRespCenterId());
                    budgetReserve.setExchangeRateType(expReqDist.getPay2funExchangeType());
                    budgetReserve.setExchangeRate(expReqDist.getPay2funExchangeRate());
                    budgetReserve.setAmount(expReqDist.getPaymentAmount());
                    budgetReserve.setFunctionalAmount(expReqDist.getRequisitionFunctionalAmount());
                    budgetReserve.setQuantity(new BigDecimal(expReqDist.getPrimaryQuantity()));
                    budgetReserve.setUnitId(expReqDist.getUnitId());
                    budgetReserve.setPositionId(expReqDist.getPositionId());
                    budgetReserve.setEmployeeId(expReqDist.getEmployeeId());
                    budgetReserve.setDimension1Id(expReqLine.getDimension1Id() == null ? expReqDist.getDimension1Id() : expReqLine.getDimension1Id());
                    budgetReserve.setDimension2Id(expReqLine.getDimension2Id() == null ? expReqDist.getDimension2Id() : expReqLine.getDimension2Id());
                    budgetReserve.setDimension3Id(expReqLine.getDimension3Id() == null ? expReqDist.getDimension3Id() : expReqLine.getDimension3Id());
                    budgetReserve.setDimension4Id(expReqLine.getDimension4Id() == null ? expReqDist.getDimension4Id() : expReqLine.getDimension4Id());
                    budgetReserve.setDimension5Id(expReqLine.getDimension5Id() == null ? expReqDist.getDimension5Id() : expReqLine.getDimension5Id());
                    budgetReserve.setDimension6Id(expReqLine.getDimension6Id() == null ? expReqDist.getDimension6Id() : expReqLine.getDimension6Id());
                    budgetReserve.setDimension7Id(expReqLine.getDimension7Id() == null ? expReqDist.getDimension7Id() : expReqLine.getDimension7Id());
                    budgetReserve.setDimension8Id(expReqLine.getDimension8Id() == null ? expReqDist.getDimension8Id() : expReqLine.getDimension8Id());
                    budgetReserve.setDimension9Id(expReqLine.getDimension9Id() == null ? expReqDist.getDimension9Id() : expReqLine.getDimension9Id());
                    budgetReserve.setDimension10Id(expReqLine.getDimension10Id() == null ? expReqDist.getDimension10Id() : expReqLine.getDimension10Id());
                    budgetReserve.setDimension11Id(expReqLine.getDimension11Id() == null ? expReqDist.getDimension11Id() : expReqLine.getDimension11Id());
                    budgetReserve.setDimension12Id(expReqLine.getDimension12Id() == null ? expReqDist.getDimension12Id() : expReqLine.getDimension12Id());
                    budgetReserve.setDimension13Id(expReqLine.getDimension13Id() == null ? expReqDist.getDimension13Id() : expReqLine.getDimension13Id());
                    budgetReserve.setDimension14Id(expReqLine.getDimension14Id() == null ? expReqDist.getDimension14Id() : expReqLine.getDimension14Id());
                    budgetReserve.setDimension15Id(expReqLine.getDimension15Id() == null ? expReqDist.getDimension15Id() : expReqLine.getDimension15Id());
                    budgetReserve.setDimension16Id(expReqLine.getDimension16Id() == null ? expReqDist.getDimension16Id() : expReqLine.getDimension16Id());
                    budgetReserve.setDimension17Id(expReqLine.getDimension17Id() == null ? expReqDist.getDimension17Id() : expReqLine.getDimension17Id());
                    budgetReserve.setDimension18Id(expReqLine.getDimension18Id() == null ? expReqDist.getDimension18Id() : expReqLine.getDimension18Id());
                    budgetReserve.setDimension19Id(expReqLine.getDimension19Id() == null ? expReqDist.getDimension19Id() : expReqLine.getDimension19Id());
                    budgetReserve.setDimension20Id(expReqLine.getDimension20Id() == null ? expReqDist.getDimension20Id() : expReqLine.getDimension20Id());
                    bgtBudgetReserveService.insertBgtReserve(request, budgetReserve);
                }
            }
        }
        return null;
    }

    /**
     * <p>
     * 重算分配行
     * <p/>
     *
     * @param request
     * @param expReqLine   申请单行信息
     * @param expMoReqType 申请单类型信息
     * @param expReqHeader 申请单头信息
     * @return 重算后的分配行
     * @author jiangxz 2019/4/3 10:40
     */
    @Override
    public ExpRequisitionDist resetDistLine(IRequest request, ExpMoReqType expMoReqType, ExpRequisitionHeader expReqHeader, ExpRequisitionLine expReqLine) {
        ExpRequisitionDist expReqDist = new ExpRequisitionDist();
        //查询行信息
        ExpRequisitionLine expRequisitionLine = expReqLineService.selectByPrimaryKey(request, expReqLine);
        if (expRequisitionLine == null) {
            throw new RuntimeException("此申请单行已经被删除!");
        }
        expReqDist.setExpRequisitionLineId(expReqLine.getExpRequisitionLineId());
        //查询原分配行
        expReqDist = self().selectByPrimaryKey(request, expReqDist);
        if (checkLineChange(expRequisitionLine, expReqLine)) {
            expReqDist.setExpRequisitionLineId(expReqLine.getExpRequisitionLineId());
            expReqDist.setDescription(expReqLine.getDescription());
            expReqDist.setDateFrom(expReqLine.getDateFrom());
            expReqDist.setDateTo(expReqLine.getDateTo());
            expReqDist.setPeriodName(expReqLine.getPeriodName());
            expReqDist.setBusinessCurrencyCode(expReqLine.getBusinessCurrencyCode());
            expReqDist.setBiz2payExchangeRate(expReqLine.getBiz2payExchangeRate());
            expReqDist.setBiz2payExchangeType(expReqLine.getBiz2payExchangeType());
            expReqDist.setPaymentCurrencyCode(expReqLine.getPaymentCurrencyCode());
            expReqDist.setPay2funExchangeType(expReqLine.getPay2funExchangeType());
            expReqDist.setPay2funExchangeRate(expReqLine.getPay2funExchangeRate());
            expReqDist.setManagementCurrencyCode(expReqLine.getManagementCurrencyCode());
            expReqDist.setPay2funExchangeType(expReqLine.getPay2funExchangeType());
            expReqDist.setPay2funExchangeRate(expReqLine.getPay2funExchangeRate());
            expReqDist.setMoExpenseTypeId(expReqLine.getMoExpenseTypeId());
            expReqDist.setMoReqItemId(expReqLine.getMoReqItemId());
            expReqDist.setBudgetItemId(expReqLine.getBudgetItemId());
            expReqDist.setBusinessPrice(expReqLine.getBusinessPrice());
            expReqDist.setPaymentPrice(expReqLine.getPaymentPrice());
            expReqDist.setPrimaryQuantity(expReqLine.getPrimaryQuantity());
            expReqDist.setPrimaryUom(expReqLine.getPrimaryUom());
            expReqDist.setBusinessAmount(expReqLine.getBusinessAmount());
            expReqDist.setPaymentAmount(expReqLine.getPaymentAmount());
            expReqDist.setManagementAmount(expReqLine.getManagementAmount());
            expReqDist.setRequisitionFunctionalAmount(expReqLine.getRequisitionFunctionalAmount());
            expReqDist.setCompanyId(expReqLine.getCompanyId());
            expReqDist.setUnitId(expReqLine.getUnitId());
            expReqDist.setPositionId(expReqLine.getPositionId());
            expReqDist.setEmployeeId(expReqLine.getEmployeeId());
            expReqDist.setAccEntityId(expReqLine.getAccEntityId());
            expReqDist.setRespCenterId(expReqLine.getRespCenterId());
            expReqDist.setBgtEntityId(expReqLine.getBgtEntityId());
            expReqDist.setBgtCenterId(expReqLine.getBgtCenterId());
            expReqDist.setCloseFlag("N");
            expReqDist.setCloseDate(null);
            expReqDist.setAttachmentNum(expReqLine.getAttachmentNum());
            expReqDist.setExceedBudgetStrategy(null);
            expReqDist.setReleasedAmount(new BigDecimal(0));
            expReqDist.setDimension1Id(expReqLine.getDimension1Id());
            expReqDist.setDimension2Id(expReqLine.getDimension2Id());
            expReqDist.setDimension3Id(expReqLine.getDimension3Id());
            expReqDist.setDimension4Id(expReqLine.getDimension4Id());
            expReqDist.setDimension5Id(expReqLine.getDimension5Id());
            expReqDist.setDimension6Id(expReqLine.getDimension6Id());
            expReqDist.setDimension7Id(expReqLine.getDimension7Id());
            expReqDist.setDimension8Id(expReqLine.getDimension8Id());
            expReqDist.setDimension9Id(expReqLine.getDimension9Id());
            expReqDist.setDimension10Id(expReqLine.getDimension10Id());
            expReqDist.setDimension11Id(expReqLine.getDimension11Id());
            expReqDist.setDimension12Id(expReqLine.getDimension12Id());
            expReqDist.setDimension13Id(expReqLine.getDimension13Id());
            expReqDist.setDimension14Id(expReqLine.getDimension14Id());
            expReqDist.setDimension15Id(expReqLine.getDimension15Id());
            expReqDist.setDimension16Id(expReqLine.getDimension16Id());
            expReqDist.setDimension17Id(expReqLine.getDimension17Id());
            expReqDist.setDimension18Id(expReqLine.getDimension18Id());
            expReqDist.setDimension19Id(expReqLine.getDimension19Id());
            expReqDist.setDimension20Id(expReqLine.getDimension20Id());
            expReqDist = this.updateDistLine(request, expReqDist, expMoReqType, expReqHeader, expReqLine);
        }
        return expReqDist;
    }

    /**
     * <p>
     * 校验行字段信息是否发生改变
     * <p/>
     *
     * @param oldLine 数据库中行信息
     * @param newLine 页面行信息
     * @return true(发生了改变) false(未发生改变)
     * @author jiangxz 2019/4/3 11:48
     */
    private boolean checkLineChange(ExpRequisitionLine oldLine, ExpRequisitionLine newLine) {
        boolean flag = false;
        String oldDescription = oldLine.getDescription() == null ? "-0.999" : oldLine.getDescription();
        String newDescription = newLine.getDescription() == null ? "-0.999" : newLine.getDescription();

        Date oldDateFrom = oldLine.getDateFrom() == null ? new Date() : oldLine.getDateFrom();
        Date newDateFrom = newLine.getDateFrom() == null ? new Date() : newLine.getDateFrom();

        Date oldDateTo = oldLine.getDateTo() == null ? new Date() : oldLine.getDateTo();
        Date newDateTo = newLine.getDateTo() == null ? new Date() : newLine.getDateTo();

        String oldPeriodName = oldLine.getPeriodName() == null ? "-0.999" : oldLine.getPeriodName();
        String newPeriodName = newLine.getPeriodName() == null ? "-0.999" : newLine.getPeriodName();

        String oldBusinessCurrencyCode = oldLine.getBusinessCurrencyCode() == null ? "-0.999" : oldLine.getBusinessCurrencyCode();
        String newBusinessCurrencyCode = newLine.getBusinessCurrencyCode() == null ? "-0.999" : newLine.getBusinessCurrencyCode();

        String oldManagementCurrencyCode = oldLine.getManagementCurrencyCode() == null ? "-0.999" : oldLine.getManagementCurrencyCode();
        String newManagementCurrencyCode = newLine.getManagementCurrencyCode() == null ? "-0.999" : newLine.getManagementCurrencyCode();

        String oldPaymentCurrencyCode = oldLine.getPaymentCurrencyCode() == null ? "-0.999" : oldLine.getPaymentCurrencyCode();
        String newPaymentCurrencyCode = newLine.getPaymentCurrencyCode() == null ? "-0.999" : newLine.getPaymentCurrencyCode();


        String oldBiz2payExchangeType = oldLine.getBiz2payExchangeType() == null ? "-0.999" : oldLine.getBiz2payExchangeType();
        String newBiz2payExchangeType = newLine.getBiz2payExchangeType() == null ? "-0.999" : newLine.getBiz2payExchangeType();

        String oldPay2magExchangeType = oldLine.getPay2magExchangeType() == null ? "-0.999" : oldLine.getPay2magExchangeType();
        String newPay2magExchangeType = newLine.getPay2magExchangeType() == null ? "-0.999" : newLine.getPay2magExchangeType();

        String oldPay2funExchangeType = oldLine.getPay2funExchangeType() == null ? "-0.999" : oldLine.getPay2funExchangeType();
        String newPay2funExchangeType = newLine.getPay2funExchangeType() == null ? "-0.999" : newLine.getPay2funExchangeType();


        BigDecimal oldBusinessPrice = oldLine.getBusinessPrice() == null ? BigDecimal.ZERO : oldLine.getBusinessPrice();
        BigDecimal newBusinessPrice = newLine.getBusinessPrice() == null ? BigDecimal.ZERO : newLine.getBusinessPrice();

        BigDecimal oldPaymentPrice = oldLine.getPaymentPrice() == null ? BigDecimal.ZERO : oldLine.getPaymentPrice();
        BigDecimal newPaymentPrice = newLine.getPaymentPrice() == null ? BigDecimal.ZERO : newLine.getPaymentPrice();

        BigDecimal oldManagementPrice = oldLine.getManagementPrice() == null ? BigDecimal.ZERO : oldLine.getManagementPrice();
        BigDecimal newManagementPrice = newLine.getManagementPrice() == null ? BigDecimal.ZERO : newLine.getManagementPrice();

        BigDecimal oldBusinessAmount = oldLine.getBusinessAmount() == null ? BigDecimal.ZERO : oldLine.getBusinessAmount();
        BigDecimal newBusinessAmount = newLine.getBusinessAmount() == null ? BigDecimal.ZERO : newLine.getBusinessAmount();

        BigDecimal oldPaymentAmount = oldLine.getPaymentAmount() == null ? BigDecimal.ZERO : oldLine.getPaymentAmount();
        BigDecimal newPaymentAmount = newLine.getPaymentAmount() == null ? BigDecimal.ZERO : newLine.getPaymentAmount();

        BigDecimal oldManagementAmount = oldLine.getManagementAmount() == null ? BigDecimal.ZERO : oldLine.getManagementAmount();
        BigDecimal newManagementAmount = newLine.getManagementAmount() == null ? BigDecimal.ZERO : newLine.getManagementAmount();

        BigDecimal oldFunctionalAmount = oldLine.getRequisitionFunctionalAmount() == null ? BigDecimal.ZERO : oldLine.getRequisitionFunctionalAmount();
        BigDecimal newFunctionalAmount = newLine.getRequisitionFunctionalAmount() == null ? BigDecimal.ZERO : newLine.getRequisitionFunctionalAmount();

        BigDecimal oldBiz2PayRate = oldLine.getBiz2payExchangeRate() == null ? BigDecimal.ZERO : oldLine.getBiz2payExchangeRate();
        BigDecimal newBiz2PayRate = newLine.getBiz2payExchangeRate() == null ? BigDecimal.ZERO : newLine.getBiz2payExchangeRate();

        BigDecimal oldPay2FunRate = oldLine.getPay2funExchangeRate() == null ? BigDecimal.ZERO : oldLine.getPay2funExchangeRate();
        BigDecimal newPay2FunRate = newLine.getPay2funExchangeRate() == null ? BigDecimal.ZERO : newLine.getPay2funExchangeRate();

        BigDecimal oldPay2MagRate = oldLine.getPay2magExchangeRate() == null ? BigDecimal.ZERO : oldLine.getPay2magExchangeRate();
        BigDecimal newPay2MagRate = newLine.getPay2magExchangeRate() == null ? BigDecimal.ZERO : newLine.getPay2magExchangeRate();

        Long oldPrimaryQuan = oldLine.getPrimaryQuantity() == null ? Long.MIN_VALUE : oldLine.getPrimaryQuantity();
        Long newPrimaryQuan = newLine.getPrimaryQuantity() == null ? Long.MIN_VALUE : newLine.getPrimaryQuantity();


        Long oldCompanyId = oldLine.getCompanyId() == null ? Long.MIN_VALUE : oldLine.getCompanyId();
        Long newCompanyId = newLine.getCompanyId() == null ? Long.MIN_VALUE : newLine.getCompanyId();

        Long oldPositionId = oldLine.getPositionId() == null ? Long.MIN_VALUE : oldLine.getPositionId();
        Long newPositionId = newLine.getPositionId() == null ? Long.MIN_VALUE : newLine.getPositionId();

        Long oldRespCenterId = oldLine.getRespCenterId() == null ? Long.MIN_VALUE : oldLine.getRespCenterId();
        Long newRespCenterId = newLine.getRespCenterId() == null ? Long.MIN_VALUE : newLine.getRespCenterId();

        Long oldEmployeeId = oldLine.getEmployeeId() == null ? Long.MIN_VALUE : oldLine.getEmployeeId();
        Long newEmployeeId = newLine.getEmployeeId() == null ? Long.MIN_VALUE : newLine.getEmployeeId();

        Long oldUnitId = oldLine.getUnitId() == null ? Long.MIN_VALUE : oldLine.getUnitId();
        Long newUnitId = newLine.getUnitId() == null ? Long.MIN_VALUE : newLine.getUnitId();

        Long oldExpenseTypeId = oldLine.getMoExpenseTypeId() == null ? Long.MIN_VALUE : oldLine.getMoExpenseTypeId();
        Long newExpenseTypeId = newLine.getMoExpenseTypeId() == null ? Long.MIN_VALUE : newLine.getMoExpenseTypeId();

        Long oldBudgetItemId = oldLine.getBudgetItemId() == null ? Long.MIN_VALUE : oldLine.getBudgetItemId();
        Long newBudgetItemId = newLine.getBudgetItemId() == null ? Long.MIN_VALUE : newLine.getBudgetItemId();

        Long oldDimension1Id = oldLine.getDimension1Id() == null ? Long.MIN_VALUE : oldLine.getDimension1Id();
        Long newDimension1Id = newLine.getDimension1Id() == null ? Long.MIN_VALUE : newLine.getDimension1Id();

        Long oldDimension2Id = oldLine.getDimension2Id() == null ? Long.MIN_VALUE : oldLine.getDimension2Id();
        Long newDimension2Id = newLine.getDimension2Id() == null ? Long.MIN_VALUE : newLine.getDimension2Id();

        Long oldDimension3Id = oldLine.getDimension3Id() == null ? Long.MIN_VALUE : oldLine.getDimension3Id();
        Long newDimension3Id = newLine.getDimension3Id() == null ? Long.MIN_VALUE : newLine.getDimension3Id();

        Long oldDimension4Id = oldLine.getDimension4Id() == null ? Long.MIN_VALUE : oldLine.getDimension4Id();
        Long newDimension4Id = newLine.getDimension4Id() == null ? Long.MIN_VALUE : newLine.getDimension4Id();

        Long oldDimension5Id = oldLine.getDimension5Id() == null ? Long.MIN_VALUE : oldLine.getDimension5Id();
        Long newDimension5Id = newLine.getDimension5Id() == null ? Long.MIN_VALUE : newLine.getDimension5Id();

        Long oldDimension6Id = oldLine.getDimension6Id() == null ? Long.MIN_VALUE : oldLine.getDimension6Id();
        Long newDimension6Id = newLine.getDimension6Id() == null ? Long.MIN_VALUE : newLine.getDimension6Id();

        Long oldDimension7Id = oldLine.getDimension7Id() == null ? Long.MIN_VALUE : oldLine.getDimension7Id();
        Long newDimension7Id = newLine.getDimension7Id() == null ? Long.MIN_VALUE : newLine.getDimension7Id();

        Long oldDimension8Id = oldLine.getDimension8Id() == null ? Long.MIN_VALUE : oldLine.getDimension8Id();
        Long newDimension8Id = newLine.getDimension8Id() == null ? Long.MIN_VALUE : newLine.getDimension8Id();

        Long oldDimension9Id = oldLine.getDimension9Id() == null ? Long.MIN_VALUE : oldLine.getDimension9Id();
        Long newDimension9Id = newLine.getDimension9Id() == null ? Long.MIN_VALUE : newLine.getDimension9Id();

        Long oldDimension10Id = oldLine.getDimension10Id() == null ? Long.MIN_VALUE : oldLine.getDimension10Id();
        Long newDimension10Id = newLine.getDimension10Id() == null ? Long.MIN_VALUE : newLine.getDimension10Id();

        Long oldDimension11Id = oldLine.getDimension11Id() == null ? Long.MIN_VALUE : oldLine.getDimension11Id();
        Long newDimension11Id = newLine.getDimension11Id() == null ? Long.MIN_VALUE : newLine.getDimension11Id();

        Long oldDimension12Id = oldLine.getDimension12Id() == null ? Long.MIN_VALUE : oldLine.getDimension12Id();
        Long newDimension12Id = newLine.getDimension12Id() == null ? Long.MIN_VALUE : newLine.getDimension12Id();

        Long oldDimension13Id = oldLine.getDimension13Id() == null ? Long.MIN_VALUE : oldLine.getDimension13Id();
        Long newDimension13Id = newLine.getDimension13Id() == null ? Long.MIN_VALUE : newLine.getDimension13Id();

        Long oldDimension14Id = oldLine.getDimension14Id() == null ? Long.MIN_VALUE : oldLine.getDimension14Id();
        Long newDimension14Id = newLine.getDimension14Id() == null ? Long.MIN_VALUE : newLine.getDimension14Id();

        Long oldDimension15Id = oldLine.getDimension15Id() == null ? Long.MIN_VALUE : oldLine.getDimension15Id();
        Long newDimension15Id = newLine.getDimension15Id() == null ? Long.MIN_VALUE : newLine.getDimension15Id();

        Long oldDimension16Id = oldLine.getDimension16Id() == null ? Long.MIN_VALUE : oldLine.getDimension16Id();
        Long newDimension16Id = newLine.getDimension16Id() == null ? Long.MIN_VALUE : newLine.getDimension16Id();

        Long oldDimension17Id = oldLine.getDimension17Id() == null ? Long.MIN_VALUE : oldLine.getDimension17Id();
        Long newDimension17Id = newLine.getDimension17Id() == null ? Long.MIN_VALUE : newLine.getDimension17Id();

        Long oldDimension18Id = oldLine.getDimension18Id() == null ? Long.MIN_VALUE : oldLine.getDimension18Id();
        Long newDimension18Id = newLine.getDimension18Id() == null ? Long.MIN_VALUE : newLine.getDimension18Id();

        Long oldDimension19Id = oldLine.getDimension19Id() == null ? Long.MIN_VALUE : oldLine.getDimension19Id();
        Long newDimension19Id = newLine.getDimension19Id() == null ? Long.MIN_VALUE : newLine.getDimension19Id();

        Long oldDimension20Id = oldLine.getDimension20Id() == null ? Long.MIN_VALUE : oldLine.getDimension20Id();
        Long newDimension20Id = newLine.getDimension20Id() == null ? Long.MIN_VALUE : newLine.getDimension20Id();


        if (oldDescription.equals(newDescription) && oldDateFrom.equals(newDateFrom) && oldDateTo.equals(newDateTo) && oldPeriodName.equals(newPeriodName) && oldBusinessCurrencyCode.equals(newBusinessCurrencyCode) && oldManagementCurrencyCode.equals(newManagementCurrencyCode) && oldPaymentCurrencyCode.equals(newPaymentCurrencyCode) && oldBiz2payExchangeType.equals(newBiz2payExchangeType) && oldPay2magExchangeType.equals(newPay2magExchangeType) && oldPay2funExchangeType.equals(newPay2funExchangeType) && oldBusinessPrice.equals(newBusinessPrice) && oldPaymentPrice.equals(newPaymentPrice) && oldManagementPrice.equals(newManagementPrice) && oldBusinessAmount.equals(newBusinessAmount) && oldPaymentAmount.equals(newPaymentAmount) && oldManagementAmount.equals(newManagementAmount) && oldFunctionalAmount.equals(newFunctionalAmount) && oldBiz2PayRate.equals(newBiz2PayRate) && oldPay2MagRate.equals(newPay2MagRate) && oldPay2FunRate.equals(newPay2FunRate) && oldPrimaryQuan.equals(newPrimaryQuan) && oldCompanyId.equals(newCompanyId) && oldRespCenterId.equals(newRespCenterId) && oldPositionId.equals(newPositionId) && oldEmployeeId.equals(newEmployeeId) && oldUnitId.equals(newUnitId) && oldExpenseTypeId.equals(newExpenseTypeId) && oldBudgetItemId.equals(newBudgetItemId) && oldDimension1Id.equals(newDimension1Id) && oldDimension2Id.equals(newDimension2Id) && oldDimension3Id.equals(newDimension3Id) && oldDimension4Id.equals(newDimension4Id) && oldDimension5Id.equals(newDimension5Id) && oldDimension6Id.equals(newDimension6Id) && oldDimension7Id.equals(newDimension7Id) && oldDimension8Id.equals(newDimension8Id) && oldDimension9Id.equals(newDimension9Id) && oldDimension10Id.equals(newDimension10Id) && oldDimension11Id.equals(newDimension11Id) && oldDimension12Id.equals(newDimension12Id) && oldDimension13Id.equals(newDimension13Id) && oldDimension14Id.equals(newDimension14Id) && oldDimension15Id.equals(newDimension15Id) && oldDimension16Id.equals(newDimension16Id) && oldDimension17Id.equals(newDimension17Id) && oldDimension18Id.equals(newDimension18Id) && oldDimension19Id.equals(newDimension19Id) && oldDimension20Id.equals(newDimension20Id)) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * <p>
     * 更新分配行的维度
     * <p/>
     *
     * @param request request请求
     * @param reqLine 标准行DTO
     * @return 返回更新完后的分配行List
     * @author jiangxz 2019/4/16 14:29
     */
    @Override
    public List<ExpRequisitionDist> updateDisLineDim(IRequest request, ExpRequisitionLine reqLine) {
        List<ExpRequisitionDist> reqDistList = new ArrayList<>();
        if (reqLine != null) {
            ExpRequisitionDist reqDist = new ExpRequisitionDist();
            reqDist.setExpRequisitionLineId(reqLine.getExpRequisitionLineId());
            reqDistList = expRequisitionDistMapper.select(reqDist);
            if (!reqDistList.isEmpty()) {
                for (ExpRequisitionDist dist : reqDistList) {
                    dist.setDimension1Id(reqLine.getDimension1Id() == null ? dist.getDimension1Id() : reqLine.getDimension1Id());
                    dist.setDimension2Id(reqLine.getDimension2Id() == null ? dist.getDimension2Id() : reqLine.getDimension2Id());
                    dist.setDimension3Id(reqLine.getDimension3Id() == null ? dist.getDimension3Id() : reqLine.getDimension3Id());
                    dist.setDimension4Id(reqLine.getDimension4Id() == null ? dist.getDimension4Id() : reqLine.getDimension4Id());
                    dist.setDimension5Id(reqLine.getDimension5Id() == null ? dist.getDimension5Id() : reqLine.getDimension5Id());
                    dist.setDimension6Id(reqLine.getDimension6Id() == null ? dist.getDimension6Id() : reqLine.getDimension6Id());
                    dist.setDimension7Id(reqLine.getDimension7Id() == null ? dist.getDimension7Id() : reqLine.getDimension7Id());
                    dist.setDimension8Id(reqLine.getDimension8Id() == null ? dist.getDimension8Id() : reqLine.getDimension8Id());
                    dist.setDimension9Id(reqLine.getDimension9Id() == null ? dist.getDimension9Id() : reqLine.getDimension9Id());
                    dist.setDimension10Id(reqLine.getDimension10Id() == null ? dist.getDimension10Id() : reqLine.getDimension10Id());
                    dist.setDimension11Id(reqLine.getDimension11Id() == null ? dist.getDimension11Id() : reqLine.getDimension11Id());
                    dist.setDimension12Id(reqLine.getDimension12Id() == null ? dist.getDimension12Id() : reqLine.getDimension12Id());
                    dist.setDimension13Id(reqLine.getDimension13Id() == null ? dist.getDimension13Id() : reqLine.getDimension13Id());
                    dist.setDimension14Id(reqLine.getDimension14Id() == null ? dist.getDimension14Id() : reqLine.getDimension14Id());
                    dist.setDimension15Id(reqLine.getDimension15Id() == null ? dist.getDimension15Id() : reqLine.getDimension15Id());
                    dist.setDimension16Id(reqLine.getDimension16Id() == null ? dist.getDimension16Id() : reqLine.getDimension16Id());
                    dist.setDimension17Id(reqLine.getDimension17Id() == null ? dist.getDimension17Id() : reqLine.getDimension17Id());
                    dist.setDimension18Id(reqLine.getDimension18Id() == null ? dist.getDimension18Id() : reqLine.getDimension18Id());
                    dist.setDimension19Id(reqLine.getDimension19Id() == null ? dist.getDimension19Id() : reqLine.getDimension19Id());
                    dist.setDimension20Id(reqLine.getDimension20Id() == null ? dist.getDimension20Id() : reqLine.getDimension20Id());
                    dist.setLastUpdatedBy(request.getUserId());
                    dist.setLastUpdateDate(new Date());
                    self().updateByPrimaryKey(request, dist);
                }
            }
            return expRequisitionDistMapper.select(reqDist);
        }
        return null;
    }
}