package com.hand.hec.expm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.gld.mapper.GldCurrencyMapper;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBudgetReserve;
import com.hand.hec.bgt.dto.BgtEntity;
import com.hand.hec.bgt.mapper.BgtBudgetReserveMapper;
import com.hand.hec.bgt.service.IBgtBudgetItemMappingService;
import com.hand.hec.bgt.service.IBgtBudgetItemService;
import com.hand.hec.bgt.service.IBgtBudgetReserveService;
import com.hand.hec.bgt.service.IBgtEntityService;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.expm.dto.ExpReportDist;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportLine;
import com.hand.hec.expm.exception.ExpReportException;
import com.hand.hec.expm.mapper.ExpReportDistMapper;
import com.hand.hec.expm.mapper.ExpReportHeaderMapper;
import com.hand.hec.expm.mapper.ExpReportLineMapper;
import com.hand.hec.expm.service.IExpReportDistService;
import com.hand.hec.expm.service.IExpReportHeaderService;
import com.hand.hec.expm.service.IExpReportLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * ExpReportDistServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 15:03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpReportDistServiceImpl extends BaseServiceImpl<ExpReportDist> implements IExpReportDistService {

    @Autowired
    ExpReportDistMapper reportDistMapper;
    @Autowired
    GldCurrencyMapper gldCurrencyMapper;

    @Autowired
    BgtBudgetReserveMapper budgetReserveMapper;

    @Autowired
    ExpReportHeaderMapper expReportHeaderMapper;

    @Autowired
    ExpReportLineMapper reportLineMapper;

    @Autowired
    IExpReportLineService expReportLineService;

    @Autowired
    IExpReportHeaderService expReportHeaderService;

    @Autowired
    IBgtBudgetItemService bgtBudgetItemService;

    @Autowired
    IBgtBudgetItemMappingService budgetItemMappingService;

    @Autowired
    IBgtEntityService bgtEntityService;

    @Autowired
    IBgtBudgetReserveService bgtBudgetReserveService;


    /**
     * <p>
     * 更新分配行的维度
     * <p/>
     *
     * @param request request请求
     * @param reportLine 标准行DTO
     * @return 返回更新完后的分配行List
     * @author yang.duan 2019/3/7 14:29
     */
    @Override
    public List<ExpReportDist> updateDisLineDim(IRequest request, ExpReportLine reportLine) {
        List<ExpReportDist> distList = new ArrayList<>();
        if (reportLine != null) {
            ExpReportDist reportDist = new ExpReportDist();
            reportDist.setExpReportLineId(reportLine.getExpReportLineId());
            List<ExpReportDist> reportDistList = reportDistMapper.select(reportDist);
            if (!reportDistList.isEmpty()) {
                for (ExpReportDist dist : reportDistList) {
                    dist.setDimension1Id(reportLine.getDimension1Id() == null ? dist.getDimension1Id()
                                    : reportLine.getDimension1Id());
                    dist.setDimension2Id(reportLine.getDimension2Id() == null ? dist.getDimension2Id()
                                    : reportLine.getDimension2Id());
                    dist.setDimension3Id(reportLine.getDimension3Id() == null ? dist.getDimension3Id()
                                    : reportLine.getDimension3Id());
                    dist.setDimension4Id(reportLine.getDimension4Id() == null ? dist.getDimension4Id()
                                    : reportLine.getDimension4Id());
                    dist.setDimension5Id(reportLine.getDimension5Id() == null ? dist.getDimension5Id()
                                    : reportLine.getDimension5Id());
                    dist.setDimension6Id(reportLine.getDimension6Id() == null ? dist.getDimension6Id()
                                    : reportLine.getDimension6Id());
                    dist.setDimension7Id(reportLine.getDimension7Id() == null ? dist.getDimension7Id()
                                    : reportLine.getDimension7Id());
                    dist.setDimension8Id(reportLine.getDimension8Id() == null ? dist.getDimension8Id()
                                    : reportLine.getDimension8Id());
                    dist.setDimension9Id(reportLine.getDimension9Id() == null ? dist.getDimension9Id()
                                    : reportLine.getDimension9Id());
                    dist.setDimension10Id(reportLine.getDimension10Id() == null ? dist.getDimension10Id()
                                    : reportLine.getDimension10Id());
                    dist.setDimension11Id(reportLine.getDimension11Id() == null ? dist.getDimension11Id()
                                    : reportLine.getDimension11Id());
                    dist.setDimension12Id(reportLine.getDimension12Id() == null ? dist.getDimension12Id()
                                    : reportLine.getDimension12Id());
                    dist.setDimension13Id(reportLine.getDimension13Id() == null ? dist.getDimension13Id()
                                    : reportLine.getDimension13Id());
                    dist.setDimension14Id(reportLine.getDimension14Id() == null ? dist.getDimension14Id()
                                    : reportLine.getDimension14Id());
                    dist.setDimension15Id(reportLine.getDimension15Id() == null ? dist.getDimension15Id()
                                    : reportLine.getDimension15Id());
                    dist.setDimension16Id(reportLine.getDimension16Id() == null ? dist.getDimension16Id()
                                    : reportLine.getDimension16Id());
                    dist.setDimension17Id(reportLine.getDimension17Id() == null ? dist.getDimension17Id()
                                    : reportLine.getDimension17Id());
                    dist.setDimension18Id(reportLine.getDimension18Id() == null ? dist.getDimension18Id()
                                    : reportLine.getDimension18Id());
                    dist.setDimension19Id(reportLine.getDimension19Id() == null ? dist.getDimension19Id()
                                    : reportLine.getDimension19Id());
                    dist.setDimension20Id(reportLine.getDimension20Id() == null ? dist.getDimension20Id()
                                    : reportLine.getDimension20Id());
                    dist.setBudgetItemId(reportLine.getBudgetItemId());
                    dist.set__status(DTOStatus.UPDATE);
                    distList.add(dist);
                }
            }
        }
        return self().batchUpdate(request, distList);
    }

    /**
     * <p>
     * 报销单分配行新增
     * <p/>
     *
     * @param request
     * @param reportDist 需要保存的分配行信息
     * @param reportType 报销单类型
     * @param header 报销单头
     * @param line 报销单行
     * @return 返回新增后的分配行信息
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/7 15:11
     */
    public ExpReportDist insertDisLine(IRequest request, ExpReportDist reportDist, ExpMoReportType reportType,
                    ExpReportHeader header, ExpReportLine line) throws RuntimeException {
        if (expReportHeaderService.statusCheck(request,header.getExpReportHeaderId())) {
            // 校验非调整类的报销单金额/数量必须为正
            expReportLineService.signCheck(reportType, reportDist.getBusinessPrice());
            expReportLineService.signCheck(reportType, reportDist.getPrimaryQuantity());
            // 获取核算主体本位币
            String functionalCurrencyCode = gldCurrencyMapper.getAccEntityFunCurrCode(reportDist.getAccEntityId());
            // 获取各币种对应金额
            ExpReportLine amountLine = expReportLineService.calcAmount(reportDist.getBusinessPrice(),
                            reportDist.getPrimaryQuantity(), reportDist.getBusinessCurrencyCode(),
                            reportDist.getBiz2payExchangeRate(), reportDist.getPaymentCurrencyCode(),
                            reportDist.getPay2magExchangeRate(), reportDist.getManagementCurrencyCode(),
                            reportDist.getPay2funExchangeRate(), functionalCurrencyCode);
            // 根据预算项决定规则获取预算项目
            Long budgetItemId = budgetItemMappingService.getPrioRityItemId("EXP_REP", reportDist.getCompanyId(),
                            reportType.getMoExpReportTypeId(), reportDist.getMoExpenseTypeId(),
                            reportDist.getMoExpenseItemId(), null, reportDist.getUnitId(), reportDist.getPositionId(),
                            reportDist.getEmployeeId(), reportDist.getAccEntityId(), reportDist.getRespCenterId(),
                            reportDist.getPayeeCategory(), reportDist.getPayeeId(),
                            (line.getDimension1Id() == null ? reportDist.getDimension1Id() : line.getDimension1Id()),
                            (line.getDimension2Id() == null ? reportDist.getDimension2Id() : line.getDimension2Id()),
                            (line.getDimension3Id() == null ? reportDist.getDimension3Id() : line.getDimension3Id()),
                            (line.getDimension4Id() == null ? reportDist.getDimension4Id() : line.getDimension4Id()),
                            (line.getDimension5Id() == null ? reportDist.getDimension5Id() : line.getDimension5Id()),
                            (line.getDimension6Id() == null ? reportDist.getDimension6Id() : line.getDimension6Id()),
                            (line.getDimension7Id() == null ? reportDist.getDimension7Id() : line.getDimension7Id()),
                            (line.getDimension8Id() == null ? reportDist.getDimension8Id() : line.getDimension8Id()),
                            (line.getDimension9Id() == null ? reportDist.getDimension9Id() : line.getDimension9Id()),
                            (line.getDimension10Id() == null ? reportDist.getDimension10Id() : line.getDimension10Id()),
                            (line.getDimension11Id() == null ? reportDist.getDimension11Id() : line.getDimension11Id()),
                            (line.getDimension12Id() == null ? reportDist.getDimension12Id() : line.getDimension12Id()),
                            (line.getDimension13Id() == null ? reportDist.getDimension13Id() : line.getDimension13Id()),
                            (line.getDimension14Id() == null ? reportDist.getDimension14Id() : line.getDimension14Id()),
                            (line.getDimension15Id() == null ? reportDist.getDimension15Id() : line.getDimension15Id()),
                            (line.getDimension16Id() == null ? reportDist.getDimension16Id() : line.getDimension16Id()),
                            (line.getDimension17Id() == null ? reportDist.getDimension17Id() : line.getDimension17Id()),
                            (line.getDimension18Id() == null ? reportDist.getDimension18Id() : line.getDimension18Id()),
                            (line.getDimension19Id() == null ? reportDist.getDimension19Id() : line.getDimension19Id()),
                            (line.getDimension20Id() == null ? reportDist.getDimension20Id()
                                            : line.getDimension20Id()));
            // 如果根据预算项获取为空，则通过费用项目获取
            if (budgetItemId.longValue() == -1) {
                budgetItemId = bgtBudgetItemService.getExpneseBgtItemId(request, reportDist.getMoExpenseItemId());
            }

            reportDist.setBudgetItemId(budgetItemId);
            reportDist.setPaymentPrice(amountLine.getPaymentPrice());
            reportDist.setManagementPrice(amountLine.getManagementPrice());
            reportDist.setBusinessAmount(amountLine.getBusinessAmount());
            reportDist.setPaymentAmount(amountLine.getPaymentAmount());
            reportDist.setManagementAmount(amountLine.getManagementAmount());
            reportDist.setReportFunctionalAmount(amountLine.getReportFunctionalAmount());
            reportDist.setCloseFlag("N");
            reportDist.setCreationDate(new Date());
            reportDist.setCreatedBy(request.getUserId());
            reportDist.setLastUpdateDate(new Date());
            reportDist.setLastUpdatedBy(request.getUserId());

            // 分配行插入
            reportDist = self().insertSelective(request, reportDist);

            //新增报销单核销费用申请关联表(未)


            // 保存分配行,触发事件(EXP_REPORT_DISTS_POST_MODIFY未)

            // 插入预算保留表
            if (budgetItemId.longValue() != -1 && "Y".equals(reportType.getReserveBudget())) {
                BgtEntity bgtEntity = new BgtEntity();
                bgtEntity.setEntityId(reportDist.getBgtEntityId());
                bgtEntity = bgtEntityService.selectByPrimaryKey(request, bgtEntity);
                BgtBudgetReserve budgetReserve = new BgtBudgetReserve();
                budgetReserve.setCompanyId(reportDist.getCompanyId());
                budgetReserve.setReserveCompanyId(reportDist.getCompanyId());
                budgetReserve.setBgtOrgId(bgtEntity.getBgtOrgId());
                budgetReserve.setBgtEntityId(reportDist.getBgtEntityId());
                budgetReserve.setBgtCenterId(reportDist.getBgtCenterId());
                budgetReserve.setPeriodName(reportDist.getPeriodName());
                budgetReserve.setBusinessType(ExpReportHeader.EXP_REPORT);
                budgetReserve.setReserveFlag("U");
                budgetReserve.setStatus("N");
                budgetReserve.setManualFlag("N");
                budgetReserve.setDocumentId(header.getExpReportHeaderId());
                budgetReserve.setDocumentLineId(reportDist.getExpReportDistId());
                budgetReserve.setCurrency(reportDist.getPaymentCurrencyCode());
                budgetReserve.setBudgetItemId(budgetItemId);
                budgetReserve.setResponsibilityCenterId(reportDist.getRespCenterId());
                budgetReserve.setExchangeRateType(reportDist.getPay2funExchangeType());
                budgetReserve.setExchangeRate(reportDist.getPay2funExchangeRate());
                budgetReserve.setAmount(reportDist.getPaymentAmount());
                budgetReserve.setFunctionalAmount(reportDist.getReportFunctionalAmount());
                budgetReserve.setQuantity(reportDist.getPrimaryQuantity());
                budgetReserve.setUnitId(reportDist.getUnitId());
                budgetReserve.setPositionId(reportDist.getPositionId());
                budgetReserve.setEmployeeId(reportDist.getEmployeeId());
                budgetReserve.setDimension1Id(
                                line.getDimension1Id() == null ? reportDist.getDimension1Id() : line.getDimension1Id());
                budgetReserve.setDimension2Id(
                                line.getDimension2Id() == null ? reportDist.getDimension2Id() : line.getDimension2Id());
                budgetReserve.setDimension3Id(
                                line.getDimension3Id() == null ? reportDist.getDimension3Id() : line.getDimension3Id());
                budgetReserve.setDimension4Id(
                                line.getDimension4Id() == null ? reportDist.getDimension4Id() : line.getDimension4Id());
                budgetReserve.setDimension5Id(
                                line.getDimension5Id() == null ? reportDist.getDimension5Id() : line.getDimension5Id());
                budgetReserve.setDimension6Id(
                                line.getDimension6Id() == null ? reportDist.getDimension6Id() : line.getDimension6Id());
                budgetReserve.setDimension7Id(
                                line.getDimension7Id() == null ? reportDist.getDimension7Id() : line.getDimension7Id());
                budgetReserve.setDimension8Id(
                                line.getDimension8Id() == null ? reportDist.getDimension8Id() : line.getDimension8Id());
                budgetReserve.setDimension9Id(
                                line.getDimension9Id() == null ? reportDist.getDimension9Id() : line.getDimension9Id());
                budgetReserve.setDimension10Id(line.getDimension10Id() == null ? reportDist.getDimension10Id()
                                : line.getDimension10Id());
                budgetReserve.setDimension11Id(line.getDimension11Id() == null ? reportDist.getDimension11Id()
                                : line.getDimension11Id());
                budgetReserve.setDimension12Id(line.getDimension12Id() == null ? reportDist.getDimension12Id()
                                : line.getDimension12Id());
                budgetReserve.setDimension13Id(line.getDimension13Id() == null ? reportDist.getDimension13Id()
                                : line.getDimension13Id());
                budgetReserve.setDimension14Id(line.getDimension14Id() == null ? reportDist.getDimension14Id()
                                : line.getDimension14Id());
                budgetReserve.setDimension15Id(line.getDimension15Id() == null ? reportDist.getDimension15Id()
                                : line.getDimension15Id());
                budgetReserve.setDimension16Id(line.getDimension16Id() == null ? reportDist.getDimension16Id()
                                : line.getDimension16Id());
                budgetReserve.setDimension17Id(line.getDimension17Id() == null ? reportDist.getDimension17Id()
                                : line.getDimension17Id());
                budgetReserve.setDimension18Id(line.getDimension18Id() == null ? reportDist.getDimension18Id()
                                : line.getDimension18Id());
                budgetReserve.setDimension19Id(line.getDimension19Id() == null ? reportDist.getDimension19Id()
                                : line.getDimension19Id());
                budgetReserve.setDimension20Id(line.getDimension20Id() == null ? reportDist.getDimension20Id()
                                : line.getDimension20Id());
                budgetReserve = bgtBudgetReserveService.insertBgtReserve(request, budgetReserve);
            }

        }
        return reportDist;
    }

    /**
     * <p>
     * 报销单分配行更新
     * <p/>
     *
     * @param request
     * @param reportDist 需要保存的分配行信息
     * @param reportType 报销单类型
     * @param header 报销单头
     * @param line 报销单行
     * @return 更新后的分配行
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/8 11:04
     */
    public ExpReportDist updateDisLine(IRequest request, ExpReportDist reportDist, ExpMoReportType reportType,
                    ExpReportHeader header, ExpReportLine line) throws RuntimeException {
        // 非"调整"类型的报销单,金额和数量必须为正
        expReportLineService.signCheck(reportType, reportDist.getBusinessPrice());
        expReportLineService.signCheck(reportType, reportDist.getPrimaryQuantity());
        // 获取核算主体本位币
        String functionCurrencyCode = gldCurrencyMapper.getAccEntityFunCurrCode(reportDist.getAccEntityId());

        // 计算各币种对应金额
        ExpReportLine amountLine = expReportLineService.calcAmount(reportDist.getBusinessPrice(),
                        reportDist.getPrimaryQuantity(), reportDist.getBusinessCurrencyCode(),
                        reportDist.getBiz2payExchangeRate(), reportDist.getPaymentCurrencyCode(),
                        reportDist.getPay2magExchangeRate(), reportDist.getManagementCurrencyCode(),
                        reportDist.getPay2funExchangeRate(), functionCurrencyCode);
        if (expReportHeaderService.statusCheck(request,header.getExpReportHeaderId())) {
            Long budgetItemId = reportDist.getBudgetItemId();
            if (reportDist.getBudgetItemId() == null) {
                // 根据预算项决定规则获取预算项目
                budgetItemId = budgetItemMappingService.getPrioRityItemId("EXP_REP", reportDist.getCompanyId(),
                                reportType.getMoExpReportTypeId(), reportDist.getMoExpenseTypeId(),
                                reportDist.getMoExpenseItemId(), null, reportDist.getUnitId(),
                                reportDist.getPositionId(), reportDist.getEmployeeId(), reportDist.getAccEntityId(),
                                reportDist.getRespCenterId(), reportDist.getPayeeCategory(), reportDist.getPayeeId(),
                                (line.getDimension1Id() == null ? reportDist.getDimension1Id()
                                                : line.getDimension1Id()),
                                (line.getDimension2Id() == null ? reportDist.getDimension2Id()
                                                : line.getDimension2Id()),
                                (line.getDimension3Id() == null ? reportDist.getDimension3Id()
                                                : line.getDimension3Id()),
                                (line.getDimension4Id() == null ? reportDist.getDimension4Id()
                                                : line.getDimension4Id()),
                                (line.getDimension5Id() == null ? reportDist.getDimension5Id()
                                                : line.getDimension5Id()),
                                (line.getDimension6Id() == null ? reportDist.getDimension6Id()
                                                : line.getDimension6Id()),
                                (line.getDimension7Id() == null ? reportDist.getDimension7Id()
                                                : line.getDimension7Id()),
                                (line.getDimension8Id() == null ? reportDist.getDimension8Id()
                                                : line.getDimension8Id()),
                                (line.getDimension9Id() == null ? reportDist.getDimension9Id()
                                                : line.getDimension9Id()),
                                (line.getDimension10Id() == null ? reportDist.getDimension10Id()
                                                : line.getDimension10Id()),
                                (line.getDimension11Id() == null ? reportDist.getDimension11Id()
                                                : line.getDimension11Id()),
                                (line.getDimension12Id() == null ? reportDist.getDimension12Id()
                                                : line.getDimension12Id()),
                                (line.getDimension13Id() == null ? reportDist.getDimension13Id()
                                                : line.getDimension13Id()),
                                (line.getDimension14Id() == null ? reportDist.getDimension14Id()
                                                : line.getDimension14Id()),
                                (line.getDimension15Id() == null ? reportDist.getDimension15Id()
                                                : line.getDimension15Id()),
                                (line.getDimension16Id() == null ? reportDist.getDimension16Id()
                                                : line.getDimension16Id()),
                                (line.getDimension17Id() == null ? reportDist.getDimension17Id()
                                                : line.getDimension17Id()),
                                (line.getDimension18Id() == null ? reportDist.getDimension18Id()
                                                : line.getDimension18Id()),
                                (line.getDimension19Id() == null ? reportDist.getDimension19Id()
                                                : line.getDimension19Id()),
                                (line.getDimension20Id() == null ? reportDist.getDimension20Id()
                                                : line.getDimension20Id()));
                // 如果根据预算项获取为空，则通过费用项目获取
                if (budgetItemId.longValue() == -1) {
                    budgetItemId = bgtBudgetItemService.getExpneseBgtItemId(request, reportDist.getMoExpenseItemId());
                }

            }
            // 重新赋值
            reportDist.setBudgetItemId(budgetItemId);
            reportDist.setPaymentPrice(amountLine.getPaymentPrice());
            reportDist.setManagementPrice(amountLine.getManagementPrice());
            reportDist.setBusinessAmount(amountLine.getBusinessAmount());
            reportDist.setManagementAmount(amountLine.getManagementAmount());
            reportDist.setPaymentAmount(amountLine.getPaymentAmount());
            reportDist.setReportFunctionalAmount(amountLine.getReportFunctionalAmount());
            reportDist.setLastUpdatedBy(request.getUserId());
            reportDist.setLastUpdateDate(new Date());
            // 更新分配行
            reportDist = self().updateByPrimaryKeySelective(request, reportDist);
            // 保存分配行,触发事件(EXP_REPORT_DISTS_POST_MODIFY)(未)
            // 更新报销单核销费用申请关联表(未)

            if (budgetItemId.longValue() != -1
                            && (reportType.getReserveBudget() == null || "Y".equals(reportType.getReserveBudget()))) {
                BgtBudgetReserve dto = new BgtBudgetReserve();
                dto.setBusinessType(ExpReportHeader.EXP_REPORT);
                dto.setDocumentId(header.getExpReportHeaderId());
                dto.setDocumentLineId(reportDist.getExpReportDistId());
                int count = budgetReserveMapper.selectCount(dto);
                if (count != 0) {
                    BgtBudgetReserve budgetReserveUpdate = new BgtBudgetReserve();
                    BgtEntity bgtEntity = new BgtEntity();
                    bgtEntity.setEntityId(reportDist.getBgtEntityId());
                    bgtEntity = bgtEntityService.selectByPrimaryKey(request, bgtEntity);
                    budgetReserveUpdate.setCompanyId(reportDist.getCompanyId());
                    budgetReserveUpdate.setReserveCompanyId(reportDist.getCompanyId());
                    budgetReserveUpdate.setBgtOrgId(bgtEntity.getBgtOrgId());
                    budgetReserveUpdate.setBgtEntityId(reportDist.getBgtEntityId());
                    budgetReserveUpdate.setBgtCenterId(reportDist.getBgtCenterId());
                    budgetReserveUpdate.setPeriodName(reportDist.getPeriodName());
                    budgetReserveUpdate.setBusinessType(ExpReportHeader.EXP_REPORT);
                    budgetReserveUpdate.setDocumentId(header.getExpReportHeaderId());
                    budgetReserveUpdate.setDocumentLineId(reportDist.getExpReportDistId());
                    budgetReserveUpdate.setCurrency(reportDist.getPaymentCurrencyCode());
                    budgetReserveUpdate.setBudgetItemId(budgetItemId);
                    budgetReserveUpdate.setResponsibilityCenterId(reportDist.getRespCenterId());
                    budgetReserveUpdate.setExchangeRateType(reportDist.getPay2funExchangeType());
                    budgetReserveUpdate.setExchangeRate(reportDist.getPay2funExchangeRate());
                    budgetReserveUpdate.setAmount(reportDist.getPaymentAmount());
                    budgetReserveUpdate.setFunctionalAmount(reportDist.getReportFunctionalAmount());
                    budgetReserveUpdate.setQuantity(reportDist.getPrimaryQuantity());
                    budgetReserveUpdate.setUnitId(reportDist.getUnitId());
                    budgetReserveUpdate.setPositionId(reportDist.getPositionId());
                    budgetReserveUpdate.setEmployeeId(reportDist.getEmployeeId());
                    budgetReserveUpdate.setDimension1Id(reportDist.getDimension1Id() == null ? line.getDimension1Id()
                                    : reportDist.getDimension1Id());
                    budgetReserveUpdate.setDimension2Id(reportDist.getDimension2Id() == null ? line.getDimension2Id()
                                    : reportDist.getDimension2Id());
                    budgetReserveUpdate.setDimension3Id(reportDist.getDimension3Id() == null ? line.getDimension3Id()
                                    : reportDist.getDimension3Id());
                    budgetReserveUpdate.setDimension4Id(reportDist.getDimension4Id() == null ? line.getDimension4Id()
                                    : reportDist.getDimension4Id());
                    budgetReserveUpdate.setDimension5Id(reportDist.getDimension5Id() == null ? line.getDimension5Id()
                                    : reportDist.getDimension5Id());
                    budgetReserveUpdate.setDimension6Id(reportDist.getDimension6Id() == null ? line.getDimension6Id()
                                    : reportDist.getDimension6Id());
                    budgetReserveUpdate.setDimension7Id(reportDist.getDimension7Id() == null ? line.getDimension7Id()
                                    : reportDist.getDimension7Id());
                    budgetReserveUpdate.setDimension8Id(reportDist.getDimension8Id() == null ? line.getDimension8Id()
                                    : reportDist.getDimension8Id());
                    budgetReserveUpdate.setDimension9Id(reportDist.getDimension9Id() == null ? line.getDimension9Id()
                                    : reportDist.getDimension9Id());
                    budgetReserveUpdate.setDimension10Id(reportDist.getDimension10Id() == null ? line.getDimension10Id()
                                    : reportDist.getDimension10Id());
                    budgetReserveUpdate.setDimension11Id(reportDist.getDimension11Id() == null ? line.getDimension11Id()
                                    : reportDist.getDimension11Id());
                    budgetReserveUpdate.setDimension12Id(reportDist.getDimension12Id() == null ? line.getDimension12Id()
                                    : reportDist.getDimension12Id());
                    budgetReserveUpdate.setDimension13Id(reportDist.getDimension13Id() == null ? line.getDimension13Id()
                                    : reportDist.getDimension13Id());
                    budgetReserveUpdate.setDimension14Id(reportDist.getDimension14Id() == null ? line.getDimension14Id()
                                    : reportDist.getDimension14Id());
                    budgetReserveUpdate.setDimension15Id(reportDist.getDimension15Id() == null ? line.getDimension15Id()
                                    : reportDist.getDimension15Id());
                    budgetReserveUpdate.setDimension16Id(reportDist.getDimension16Id() == null ? line.getDimension16Id()
                                    : reportDist.getDimension16Id());
                    budgetReserveUpdate.setDimension17Id(reportDist.getDimension17Id() == null ? line.getDimension17Id()
                                    : reportDist.getDimension17Id());
                    budgetReserveUpdate.setDimension18Id(reportDist.getDimension18Id() == null ? line.getDimension18Id()
                                    : reportDist.getDimension18Id());
                    budgetReserveUpdate.setDimension19Id(reportDist.getDimension19Id() == null ? line.getDimension19Id()
                                    : reportDist.getDimension19Id());
                    budgetReserveUpdate.setDimension20Id(reportDist.getDimension20Id() == null ? line.getDimension20Id()
                                    : reportDist.getDimension20Id());
                    // 更新预算保留表
                    bgtBudgetReserveService.updateBgtReserve(request, budgetReserveUpdate);

                } else {
                    BgtBudgetReserve budgetReserveAdd = new BgtBudgetReserve();
                    BgtEntity bgtEntity = new BgtEntity();
                    bgtEntity.setEntityId(reportDist.getBgtEntityId());
                    bgtEntity = bgtEntityService.selectByPrimaryKey(request, bgtEntity);
                    budgetReserveAdd.setCompanyId(reportDist.getCompanyId());
                    budgetReserveAdd.setReserveCompanyId(reportDist.getCompanyId());
                    budgetReserveAdd.setBgtOrgId(bgtEntity.getBgtOrgId());
                    budgetReserveAdd.setBgtEntityId(reportDist.getBgtEntityId());
                    budgetReserveAdd.setBgtCenterId(reportDist.getBgtCenterId());
                    budgetReserveAdd.setPeriodName(reportDist.getPeriodName());
                    budgetReserveAdd.setBusinessType(ExpReportHeader.EXP_REPORT);
                    budgetReserveAdd.setReserveFlag("U");
                    budgetReserveAdd.setStatus("N");
                    budgetReserveAdd.setDocumentId(header.getExpReportHeaderId());
                    budgetReserveAdd.setDocumentLineId(reportDist.getExpReportDistId());
                    budgetReserveAdd.setCurrency(reportDist.getPaymentCurrencyCode());
                    budgetReserveAdd.setBudgetItemId(budgetItemId);
                    budgetReserveAdd.setResponsibilityCenterId(reportDist.getRespCenterId());
                    budgetReserveAdd.setExchangeRateType(reportDist.getPay2funExchangeType());
                    budgetReserveAdd.setExchangeRate(reportDist.getPay2funExchangeRate());
                    budgetReserveAdd.setAmount(reportDist.getPaymentAmount());
                    budgetReserveAdd.setFunctionalAmount(reportDist.getReportFunctionalAmount());
                    budgetReserveAdd.setQuantity(reportDist.getPrimaryQuantity());
                    budgetReserveAdd.setUnitId(reportDist.getUnitId());
                    budgetReserveAdd.setPositionId(reportDist.getPositionId());
                    budgetReserveAdd.setEmployeeId(reportDist.getEmployeeId());
                    budgetReserveAdd.setDimension1Id(line.getDimension1Id() == null ? reportDist.getDimension1Id()
                                    : line.getDimension1Id());
                    budgetReserveAdd.setDimension2Id(line.getDimension2Id() == null ? reportDist.getDimension2Id()
                                    : line.getDimension2Id());
                    budgetReserveAdd.setDimension3Id(line.getDimension3Id() == null ? reportDist.getDimension3Id()
                                    : line.getDimension3Id());
                    budgetReserveAdd.setDimension4Id(line.getDimension4Id() == null ? reportDist.getDimension4Id()
                                    : line.getDimension4Id());
                    budgetReserveAdd.setDimension5Id(line.getDimension5Id() == null ? reportDist.getDimension5Id()
                                    : line.getDimension5Id());
                    budgetReserveAdd.setDimension6Id(line.getDimension6Id() == null ? reportDist.getDimension6Id()
                                    : line.getDimension6Id());
                    budgetReserveAdd.setDimension7Id(line.getDimension7Id() == null ? reportDist.getDimension7Id()
                                    : line.getDimension7Id());
                    budgetReserveAdd.setDimension8Id(line.getDimension8Id() == null ? reportDist.getDimension8Id()
                                    : line.getDimension8Id());
                    budgetReserveAdd.setDimension9Id(line.getDimension9Id() == null ? reportDist.getDimension9Id()
                                    : line.getDimension9Id());
                    budgetReserveAdd.setDimension10Id(line.getDimension10Id() == null ? reportDist.getDimension10Id()
                                    : line.getDimension10Id());
                    budgetReserveAdd.setDimension11Id(line.getDimension11Id() == null ? reportDist.getDimension11Id()
                                    : line.getDimension11Id());
                    budgetReserveAdd.setDimension12Id(line.getDimension12Id() == null ? reportDist.getDimension12Id()
                                    : line.getDimension12Id());
                    budgetReserveAdd.setDimension13Id(line.getDimension13Id() == null ? reportDist.getDimension13Id()
                                    : line.getDimension13Id());
                    budgetReserveAdd.setDimension14Id(line.getDimension14Id() == null ? reportDist.getDimension14Id()
                                    : line.getDimension14Id());
                    budgetReserveAdd.setDimension15Id(line.getDimension15Id() == null ? reportDist.getDimension15Id()
                                    : line.getDimension15Id());
                    budgetReserveAdd.setDimension16Id(line.getDimension16Id() == null ? reportDist.getDimension16Id()
                                    : line.getDimension16Id());
                    budgetReserveAdd.setDimension17Id(line.getDimension17Id() == null ? reportDist.getDimension17Id()
                                    : line.getDimension17Id());
                    budgetReserveAdd.setDimension18Id(line.getDimension18Id() == null ? reportDist.getDimension18Id()
                                    : line.getDimension18Id());
                    budgetReserveAdd.setDimension19Id(line.getDimension19Id() == null ? reportDist.getDimension19Id()
                                    : line.getDimension19Id());
                    budgetReserveAdd.setDimension20Id(line.getDimension20Id() == null ? reportDist.getDimension20Id()
                                    : line.getDimension20Id());
                    // 插入预算保留表
                    bgtBudgetReserveService.insertBgtReserve(request, budgetReserveAdd);
                }
            } else {

            }
        }
        return reportDist;
    }



    /**
     * <p>
     * 重算分配行
     * <p/>
     *
     * @param request
     * @param line 报销单行信息
     * @param header 报销单头信息
     * @param reportType 报销单类型
     * @return 重算后的分配行
     * @author yang.duan 2019/3/8 10:40
     */
    public ExpReportDist resetDisLine(IRequest request, ExpReportLine line,ExpReportHeader header, ExpMoReportType reportType) {
        ExpReportDist dto = new ExpReportDist();
        // 查询行信息
        ExpReportLine reportLine = expReportLineService.selectByPrimaryKey(request, line);
        header.setExpReportHeaderId(line.getExpReportHeaderId());
        header = expReportHeaderService.selectByPrimaryKey(request,header);
        if (reportLine == null) {
            throw new ExpReportException("EXP",ExpReportException.EXP5110_EXP_REP_EXPENSE_LINE_DELETED_ERROR,null);
        }
        dto.setExpReportLineId(line.getExpReportLineId());
        // 查询分配行信息
        List<ExpReportDist> expReportDistList = reportDistMapper.select(dto);

        Long oldExenseTypeId =
                        reportLine.getMoExpenseTypeId() == null ? Long.MIN_VALUE : reportLine.getMoExpenseTypeId();
        Long newExenseTypeId = line.getMoExpenseTypeId() == null ? Long.MIN_VALUE : line.getMoExpenseTypeId();

        Long oldExpenseItemId =
                        reportLine.getMoExpenseItemId() == null ? Long.MIN_VALUE : reportLine.getMoExpenseItemId();
        Long newExpenseItemId = line.getMoExpenseItemId() == null ? Long.MIN_VALUE : line.getMoExpenseItemId();
        ExpReportDist reportDist = new ExpReportDist();
        // 如果只有一个分配行,则同步更新
        if (expReportDistList != null && expReportDistList.size() == 1) {
            // 校验行字段是否发生改变
            if (checkLineChange(reportLine, line)) {
                reportDist.setExpReportDistId(expReportDistList.get(0).getExpReportDistId());
                reportDist.setExpReportLineId(line.getExpReportLineId());
                reportDist.setDescription(line.getDescription());
                reportDist.setDateFrom(line.getDateFrom());
                reportDist.setDateTo(line.getDateTo());
                reportDist.setPeriodName(line.getPeriodName());
                reportDist.setBusinessCurrencyCode(line.getBusinessCurrencyCode());
                reportDist.setBiz2payExchangeType(line.getBiz2payExchangeType());
                reportDist.setBiz2payExchangeRate(line.getBiz2payExchangeRate());
                reportDist.setPaymentCurrencyCode(line.getPaymentCurrencyCode());
                reportDist.setPay2funExchangeType(line.getPay2funExchangeType());
                reportDist.setPay2funExchangeRate(line.getPay2funExchangeRate());
                reportDist.setManagementCurrencyCode(line.getManagementCurrencyCode());
                reportDist.setPay2magExchangeType(line.getPay2magExchangeType());
                reportDist.setPay2magExchangeRate(line.getPay2magExchangeRate());
                reportDist.setMoExpenseTypeId(line.getMoExpenseTypeId());
                reportDist.setMoExpenseItemId(line.getMoExpenseItemId());
                reportDist.setBusinessPrice(line.getBusinessPrice());
                reportDist.setPrimaryQuantity(line.getPrimaryQuantity());
                reportDist.setCompanyId(line.getCompanyId());
                reportDist.setOperationUnitId(line.getOperationUnitId());
                reportDist.setUnitId(line.getUnitId());
                reportDist.setPositionId(line.getPositionId());
                reportDist.setEmployeeId(line.getEmployeeId());
                reportDist.setAccEntityId(line.getAccEntityId());
                reportDist.setRespCenterId(line.getRespCenterId());
                reportDist.setBgtEntityId(line.getBgtEntityId());
                reportDist.setBgtCenterId(line.getBgtCenterId());
                reportDist.setAttachmentNum(line.getAttachmentNum());
                reportDist.setDimension1Id(line.getDimension1Id());
                reportDist.setDimension2Id(line.getDimension2Id());
                reportDist.setDimension3Id(line.getDimension3Id());
                reportDist.setDimension4Id(line.getDimension4Id());
                reportDist.setDimension5Id(line.getDimension5Id());
                reportDist.setDimension6Id(line.getDimension6Id());
                reportDist.setDimension7Id(line.getDimension7Id());
                reportDist.setDimension8Id(line.getDimension8Id());
                reportDist.setDimension9Id(line.getDimension9Id());
                reportDist.setDimension10Id(line.getDimension10Id());
                reportDist.setDimension11Id(line.getDimension11Id());
                reportDist.setDimension12Id(line.getDimension12Id());
                reportDist.setDimension13Id(line.getDimension13Id());
                reportDist.setDimension14Id(line.getDimension14Id());
                reportDist.setDimension15Id(line.getDimension15Id());
                reportDist.setDimension16Id(line.getDimension16Id());
                reportDist.setDimension17Id(line.getDimension17Id());
                reportDist.setDimension18Id(line.getDimension18Id());
                reportDist.setDimension19Id(line.getDimension19Id());
                reportDist.setDimension20Id(line.getDimension20Id());

                reportDist = this.updateDisLine(request,reportDist,reportType,header,line);
            } else if (!(oldExenseTypeId.equals(newExenseTypeId) && oldExpenseItemId.equals(newExpenseItemId))) {
                reportDist.setExpReportDistId(expReportDistList.get(0).getExpReportDistId());
                reportDist.setExpReportLineId(expReportDistList.get(0).getExpReportLineId());
                reportDist.setDescription(expReportDistList.get(0).getDescription());
                reportDist.setDateFrom(expReportDistList.get(0).getDateFrom());
                reportDist.setDateTo(expReportDistList.get(0).getDateTo());
                reportDist.setPeriodName(expReportDistList.get(0).getPeriodName());
                reportDist.setBusinessCurrencyCode(expReportDistList.get(0).getBusinessCurrencyCode());
                reportDist.setBiz2payExchangeType(expReportDistList.get(0).getBiz2payExchangeType());
                reportDist.setBiz2payExchangeRate(expReportDistList.get(0).getBiz2payExchangeRate());
                reportDist.setPaymentCurrencyCode(expReportDistList.get(0).getPaymentCurrencyCode());
                reportDist.setPay2funExchangeType(expReportDistList.get(0).getPay2funExchangeType());
                reportDist.setPay2funExchangeRate(expReportDistList.get(0).getPay2funExchangeRate());
                reportDist.setManagementCurrencyCode(expReportDistList.get(0).getManagementCurrencyCode());
                reportDist.setPay2magExchangeType(expReportDistList.get(0).getPay2magExchangeType());
                reportDist.setPay2magExchangeRate(expReportDistList.get(0).getPay2magExchangeRate());
                reportDist.setMoExpenseTypeId(line.getMoExpenseTypeId());
                reportDist.setMoExpenseItemId(line.getMoExpenseItemId());
                reportDist.setBusinessPrice(expReportDistList.get(0).getBusinessPrice());
                reportDist.setPrimaryQuantity(expReportDistList.get(0).getPrimaryQuantity());
                reportDist.setCompanyId(expReportDistList.get(0).getCompanyId());
                reportDist.setOperationUnitId(expReportDistList.get(0).getOperationUnitId());
                reportDist.setUnitId(expReportDistList.get(0).getUnitId());
                reportDist.setPositionId(expReportDistList.get(0).getPositionId());
                reportDist.setEmployeeId(expReportDistList.get(0).getEmployeeId());
                reportDist.setAccEntityId(expReportDistList.get(0).getAccEntityId());
                reportDist.setRespCenterId(expReportDistList.get(0).getRespCenterId());
                reportDist.setBgtEntityId(expReportDistList.get(0).getBgtEntityId());
                reportDist.setBgtCenterId(expReportDistList.get(0).getBgtCenterId());
                reportDist.setAttachmentNum(expReportDistList.get(0).getAttachmentNum());
                reportDist.setDimension1Id(expReportDistList.get(0).getDimension1Id());
                reportDist.setDimension2Id(expReportDistList.get(0).getDimension2Id());
                reportDist.setDimension3Id(expReportDistList.get(0).getDimension3Id());
                reportDist.setDimension4Id(expReportDistList.get(0).getDimension4Id());
                reportDist.setDimension5Id(expReportDistList.get(0).getDimension5Id());
                reportDist.setDimension6Id(expReportDistList.get(0).getDimension6Id());
                reportDist.setDimension7Id(expReportDistList.get(0).getDimension7Id());
                reportDist.setDimension8Id(expReportDistList.get(0).getDimension8Id());
                reportDist.setDimension9Id(expReportDistList.get(0).getDimension9Id());
                reportDist.setDimension10Id(expReportDistList.get(0).getDimension10Id());
                reportDist.setDimension11Id(expReportDistList.get(0).getDimension11Id());
                reportDist.setDimension12Id(expReportDistList.get(0).getDimension12Id());
                reportDist.setDimension13Id(expReportDistList.get(0).getDimension13Id());
                reportDist.setDimension14Id(expReportDistList.get(0).getDimension14Id());
                reportDist.setDimension15Id(expReportDistList.get(0).getDimension15Id());
                reportDist.setDimension16Id(expReportDistList.get(0).getDimension16Id());
                reportDist.setDimension17Id(expReportDistList.get(0).getDimension17Id());
                reportDist.setDimension18Id(expReportDistList.get(0).getDimension18Id());
                reportDist.setDimension19Id(expReportDistList.get(0).getDimension19Id());
                reportDist.setDimension20Id(expReportDistList.get(0).getDimension20Id());
                reportDist = this.updateDisLine(request, reportDist, reportType,header,line);
            }
        }
        // 存在多个分配行
        if (expReportDistList != null && expReportDistList.size() > 1) {
            if (!(oldExenseTypeId.equals(newExenseTypeId) && oldExpenseItemId.equals(newExpenseItemId))) {
                for (ExpReportDist dist : expReportDistList) {
                    reportDist.setExpReportDistId(dist.getExpReportDistId());
                    reportDist.setExpReportLineId(dist.getExpReportLineId());
                    reportDist.setDescription(dist.getDescription());
                    reportDist.setDateFrom(dist.getDateFrom());
                    reportDist.setDateTo(dist.getDateTo());
                    reportDist.setPeriodName(dist.getPeriodName());
                    reportDist.setBusinessCurrencyCode(dist.getBusinessCurrencyCode());
                    reportDist.setBiz2payExchangeType(dist.getBiz2payExchangeType());
                    reportDist.setBiz2payExchangeRate(dist.getBiz2payExchangeRate());
                    reportDist.setPaymentCurrencyCode(dist.getPaymentCurrencyCode());
                    reportDist.setPay2funExchangeType(dist.getPay2funExchangeType());
                    reportDist.setPay2funExchangeRate(dist.getPay2funExchangeRate());
                    reportDist.setManagementCurrencyCode(dist.getManagementCurrencyCode());
                    reportDist.setPay2magExchangeType(dist.getPay2magExchangeType());
                    reportDist.setPay2magExchangeRate(dist.getPay2magExchangeRate());
                    reportDist.setMoExpenseTypeId(line.getMoExpenseTypeId());
                    reportDist.setMoExpenseItemId(line.getMoExpenseItemId());
                    reportDist.setBusinessPrice(dist.getBusinessPrice());
                    reportDist.setPrimaryQuantity(dist.getPrimaryQuantity());
                    reportDist.setCompanyId(dist.getCompanyId());
                    reportDist.setOperationUnitId(dist.getOperationUnitId());
                    reportDist.setUnitId(dist.getUnitId());
                    reportDist.setPositionId(dist.getPositionId());
                    reportDist.setEmployeeId(dist.getEmployeeId());
                    reportDist.setAccEntityId(dist.getAccEntityId());
                    reportDist.setRespCenterId(dist.getRespCenterId());
                    reportDist.setBgtEntityId(dist.getBgtEntityId());
                    reportDist.setBgtCenterId(dist.getBgtCenterId());
                    reportDist.setAttachmentNum(dist.getAttachmentNum());
                    reportDist.setDimension1Id(dist.getDimension1Id());
                    reportDist.setDimension2Id(dist.getDimension2Id());
                    reportDist.setDimension3Id(dist.getDimension3Id());
                    reportDist.setDimension4Id(dist.getDimension4Id());
                    reportDist.setDimension5Id(dist.getDimension5Id());
                    reportDist.setDimension6Id(dist.getDimension6Id());
                    reportDist.setDimension7Id(dist.getDimension7Id());
                    reportDist.setDimension8Id(dist.getDimension8Id());
                    reportDist.setDimension9Id(dist.getDimension9Id());
                    reportDist.setDimension10Id(dist.getDimension10Id());
                    reportDist.setDimension11Id(dist.getDimension11Id());
                    reportDist.setDimension12Id(dist.getDimension12Id());
                    reportDist.setDimension13Id(dist.getDimension13Id());
                    reportDist.setDimension14Id(dist.getDimension14Id());
                    reportDist.setDimension15Id(dist.getDimension15Id());
                    reportDist.setDimension16Id(dist.getDimension16Id());
                    reportDist.setDimension17Id(dist.getDimension17Id());
                    reportDist.setDimension18Id(dist.getDimension18Id());
                    reportDist.setDimension19Id(dist.getDimension19Id());
                    reportDist.setDimension20Id(dist.getDimension20Id());
                    reportDist = this.updateDisLine(request, reportDist, reportType,header,line);
                }
            }
        }
        return reportDist;
    }

    /**
     * <p>
     * 校验行字段信息是否发生改变
     * <p/>
     *
     * @param oldLine 数据库中行信息
     * @param newLine 页面行信息
     * @return true(发生了改变) false(未发生改变)
     * @author yang.duan 2019/3/11 13:48
     */
    private boolean checkLineChange(ExpReportLine oldLine, ExpReportLine newLine) {
        boolean flag = false;
        BigDecimal oldBusinessPrice = oldLine.getBusinessPrice() == null ? BigDecimal.ZERO : oldLine.getBusinessPrice();
        BigDecimal newBusinessPrice = newLine.getBusinessPrice() == null ? BigDecimal.ZERO : newLine.getBusinessPrice();

        BigDecimal oldPaymentPrice = oldLine.getPaymentPrice() == null ? BigDecimal.ZERO : oldLine.getPaymentPrice();
        BigDecimal newPaymentPrice = newLine.getPaymentPrice() == null ? BigDecimal.ZERO : newLine.getPaymentPrice();

        BigDecimal oldManagementPrice =
                        oldLine.getManagementPrice() == null ? BigDecimal.ZERO : oldLine.getManagementPrice();
        BigDecimal newManagementPrice =
                        newLine.getManagementPrice() == null ? BigDecimal.ZERO : newLine.getManagementPrice();

        BigDecimal oldBusinessAmount =
                        oldLine.getBusinessAmount() == null ? BigDecimal.ZERO : oldLine.getBusinessAmount();
        BigDecimal newBusinessAmount =
                        newLine.getBusinessAmount() == null ? BigDecimal.ZERO : newLine.getBusinessAmount();

        BigDecimal oldPaymentAmount = oldLine.getPaymentAmount() == null ? BigDecimal.ZERO : oldLine.getPaymentAmount();
        BigDecimal newPaymentAmount = newLine.getPaymentAmount() == null ? BigDecimal.ZERO : newLine.getPaymentAmount();

        BigDecimal oldManagementAmount =
                        oldLine.getManagementAmount() == null ? BigDecimal.ZERO : oldLine.getManagementAmount();
        BigDecimal newManagementAmount =
                        newLine.getManagementAmount() == null ? BigDecimal.ZERO : newLine.getManagementAmount();

        BigDecimal oldFunctionalAmount = oldLine.getReportFunctionalAmount() == null ? BigDecimal.ZERO
                        : oldLine.getReportFunctionalAmount();
        BigDecimal newFunctionalAmount = newLine.getReportFunctionalAmount() == null ? BigDecimal.ZERO
                        : newLine.getReportFunctionalAmount();

        BigDecimal oldBiz2PayRate =
                        oldLine.getBiz2payExchangeRate() == null ? BigDecimal.ZERO : oldLine.getBiz2payExchangeRate();
        BigDecimal newBiz2PayRate =
                        newLine.getBiz2payExchangeRate() == null ? BigDecimal.ZERO : newLine.getBiz2payExchangeRate();

        BigDecimal oldPay2FunRate =
                        oldLine.getPay2funExchangeRate() == null ? BigDecimal.ZERO : oldLine.getPay2funExchangeRate();
        BigDecimal newPay2FunRate =
                        newLine.getPay2funExchangeRate() == null ? BigDecimal.ZERO : newLine.getPay2funExchangeRate();

        BigDecimal oldPay2MagRate =
                        oldLine.getPay2magExchangeRate() == null ? BigDecimal.ZERO : oldLine.getPay2magExchangeRate();
        BigDecimal newPay2MagRate =
                        newLine.getPay2magExchangeRate() == null ? BigDecimal.ZERO : newLine.getPay2magExchangeRate();

        BigDecimal oldPrimaryQuan =
                        oldLine.getPrimaryQuantity() == null ? BigDecimal.ZERO : oldLine.getPrimaryQuantity();
        BigDecimal newPrimaryQuan =
                        newLine.getPrimaryQuantity() == null ? BigDecimal.ZERO : newLine.getPrimaryQuantity();


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


        if (oldBusinessPrice.equals(newBusinessPrice) && oldPaymentPrice.equals(newPaymentPrice)
                        && oldManagementPrice.equals(newManagementPrice) && oldBusinessAmount.equals(newBusinessAmount)
                        && oldPaymentAmount.equals(newPaymentAmount) && oldManagementAmount.equals(newManagementAmount)
                        && oldFunctionalAmount.equals(newFunctionalAmount)
                        && oldLine.getBusinessCurrencyCode() == newLine.getBusinessCurrencyCode()
                        && oldLine.getPaymentCurrencyCode() == newLine.getBusinessCurrencyCode()
                        && oldLine.getManagementCurrencyCode() == newLine.getBusinessCurrencyCode()
                        && oldLine.getBiz2payExchangeType() == newLine.getBiz2payExchangeType()
                        && oldLine.getPay2funExchangeType() == newLine.getPay2funExchangeType()
                        && oldLine.getPay2magExchangeType() == newLine.getPay2magExchangeType()
                        && oldBiz2PayRate.equals(newBiz2PayRate) && oldPay2MagRate.equals(newPay2MagRate)
                        && oldPay2FunRate.equals(newPay2FunRate) && oldPrimaryQuan.equals(newPrimaryQuan)
                        && oldCompanyId.equals(newCompanyId) && oldRespCenterId.equals(newRespCenterId)
                        && oldPositionId.equals(newPositionId) && oldEmployeeId.equals(newEmployeeId)
                        && oldLine.getPeriodName() == newLine.getPeriodName() && oldUnitId.equals(newUnitId)
                        && oldExpenseTypeId.equals(newExpenseTypeId) && oldBudgetItemId.equals(newBudgetItemId)
                        && oldLine.getDescription() == newLine.getDescription()
                        && oldDimension1Id.equals(newDimension1Id) && oldDimension2Id.equals(newDimension2Id)
                        && oldDimension3Id.equals(newDimension3Id) && oldDimension4Id.equals(newDimension4Id)
                        && oldDimension5Id.equals(newDimension5Id) && oldDimension6Id.equals(newDimension6Id)
                        && oldDimension7Id.equals(newDimension7Id) && oldDimension8Id.equals(newDimension8Id)
                        && oldDimension9Id.equals(newDimension9Id) && oldDimension10Id.equals(newDimension10Id)
                        && oldDimension11Id.equals(newDimension11Id) && oldDimension12Id.equals(newDimension12Id)
                        && oldDimension13Id.equals(newDimension13Id) && oldDimension14Id.equals(newDimension14Id)
                        && oldDimension15Id.equals(newDimension15Id) && oldDimension16Id.equals(newDimension16Id)
                        && oldDimension17Id.equals(newDimension17Id) && oldDimension18Id.equals(newDimension18Id)
                        && oldDimension19Id.equals(newDimension19Id) && oldDimension20Id.equals(newDimension20Id)) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * <p>
     * 报销单分配行删除
     * <p/>
     *
     * @param request
     * @param dto
     * @return
     * @author yang.duan 2019/3/29 14:06
     */
    @Override
    public int deleteExpReportDist(IRequest request, ExpReportDist dto) {
        int deleteFlag = -1;
        if (dto != null) {
            // 查询报销单头数据
            ExpReportHeader header = new ExpReportHeader();
            ExpReportLine line = new ExpReportLine();
            line.setExpReportLineId(dto.getExpReportLineId());
            line = expReportLineService.selectByPrimaryKey(request, line);
            if (line != null) {
                header = expReportHeaderMapper.selectByPrimaryKey(line.getExpReportHeaderId());
            }
            if (header != null) {
                if (expReportHeaderService.statusCheck(request,header.getExpReportHeaderId())) {
                    // 删除预算保留行
                    BgtBudgetReserve budgetReserve = new BgtBudgetReserve();
                    budgetReserve.setDocumentId(header.getExpReportHeaderId());
                    budgetReserve.setDocumentLineId(dto.getExpReportDistId());
                    budgetReserve.setBusinessType(ExpReportHeader.EXP_REPORT);
                    List<BgtBudgetReserve> reserveList = bgtBudgetReserveService.select(request, budgetReserve, 0, 0);
                    bgtBudgetReserveService.batchDelete(reserveList);
                    // 删除报销单与申请单相关(未)

                    // 删除分配行
                    dto = self().selectByPrimaryKey(request, dto);
                    deleteFlag = self().deleteByPrimaryKey(dto);
                }
            }
        }
        return deleteFlag;
    }
}
