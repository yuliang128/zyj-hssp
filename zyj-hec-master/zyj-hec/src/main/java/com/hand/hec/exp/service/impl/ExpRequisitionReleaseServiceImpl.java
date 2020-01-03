package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.*;
import com.hand.hec.bgt.mapper.BgtBudgetReserveMapper;
import com.hand.hec.bgt.mapper.BgtPeriodMapper;
import com.hand.hec.bgt.service.IBgtBudgetReserveExtendService;
import com.hand.hec.bgt.service.IBgtBudgetReserveService;
import com.hand.hec.bgt.service.IBgtEntityService;
import com.hand.hec.bgt.service.IBgtOrganizationService;
import com.hand.hec.exp.dto.*;
import com.hand.hec.exp.mapper.ExpRequisitionDistMapper;
import com.hand.hec.exp.mapper.ExpRequisitionReleaseMapper;
import com.hand.hec.exp.service.*;
import com.hand.hec.expm.dto.ExpReportDist;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportLine;
import com.hand.hec.expm.service.IExpReportDistService;
import com.hand.hec.expm.service.IExpReportHeaderService;
import com.hand.hec.expm.service.IExpReportLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpRequisitionReleaseServiceImpl extends BaseServiceImpl<ExpRequisitionRelease> implements IExpRequisitionReleaseService {
    @Autowired
    private ExpRequisitionReleaseMapper expRequisitionReleaseMapper;

    @Autowired
    private IExpRequisitionHeaderService requisitionHeaderService;

    @Autowired
    private IExpRequisitionDistService requisitionDistService;

    @Autowired
    private IExpRequisitionLineService requisitionLineService;

    @Autowired
    private IBgtBudgetReserveService budgetReserveService;

    @Autowired
    private BgtBudgetReserveMapper budgetReserveMapper;

    @Autowired
    private BgtPeriodMapper bgtPeriodMapper;

    @Autowired
    private IExpReportDistService reportDistService;

    @Autowired
    private IExpReportLineService reportLineService;

    @Autowired
    private IExpReportHeaderService reportHeaderService;

    @Autowired
    private IBgtEntityService entityService;

    @Autowired
    private IBgtOrganizationService bgtOrganizationService;

    @Autowired
    private IBgtBudgetReserveExtendService bgtBudgetReserveExtendService;

    @Autowired
    private IBgtEntityService bgtEntityService;

    @Autowired
    private IBgtBudgetReserveService bgtBudgetReserveService;

    @Autowired
    private IExpMoReqTypeService reqTypeService;

    @Autowired
    private ExpRequisitionDistMapper requisitionDistMapper;

    @Override
    public List<ExpRequisitionRelease> selectExpRequisitionReleaseInfo(Long expReportHeaderId, Long expReportLineId, Long expReportDistId) {
        return expRequisitionReleaseMapper.selectExpRequisitionReleaseInfo(expReportHeaderId, expReportLineId, expReportDistId);
    }

    @Override
    public ExpRequisitionRelease getRequisitionDistsRelease(Long expRequisitionDistId) {
        return expRequisitionReleaseMapper.getRequisitionDistsRelease(expRequisitionDistId);
    }

    @Override
    public void releasedBgtReserveBalance(IRequest request, Long releaseId) {
        // 获取关联信息
        ExpRequisitionRelease release = new ExpRequisitionRelease();
        BigDecimal exchangeRate = BigDecimal.ONE;
        release.setReleaseId(releaseId);
        release = self().selectByPrimaryKey(request, release);

        if (release != null) {
            if (release.getExpRequisitionHeaderId() != null) {
                // 取申请单头中的汇率
                ExpRequisitionHeader requisitionHeader = new ExpRequisitionHeader();
                requisitionHeader.setExpRequisitionHeaderId(release.getExpRequisitionHeaderId());
                requisitionHeader = requisitionHeaderService.selectByPrimaryKey(request, requisitionHeader);
                if (requisitionHeader != null) {
                    exchangeRate = requisitionHeader.getPay2funExchangeRate();
                }
            }
            // 获取关联的总数量与总金额
            BigDecimal totalQuantity = BigDecimal.ZERO;
            BigDecimal totalAmount = BigDecimal.ZERO;
            ExpRequisitionRelease quantityAmtDto = new ExpRequisitionRelease();
            quantityAmtDto.setExpRequisitionHeaderId(release.getExpRequisitionHeaderId());
            quantityAmtDto.setExpRequisitionDistId(release.getExpRequisitionDistId());
            List<ExpRequisitionRelease> quantityAmtDtos = self().select(request, quantityAmtDto, 1, 0);
            if (quantityAmtDtos != null && !quantityAmtDtos.isEmpty()) {
                for (ExpRequisitionRelease r : quantityAmtDtos) {
                    totalQuantity = totalQuantity.add((r.getReqReleaseQuantity() == null ? BigDecimal.ZERO : r.getReqReleaseQuantity()));
                    totalAmount = totalAmount.add((r.getReqReleaseAmount() == null ? BigDecimal.ZERO : r.getReqReleaseAmount()));
                }
            }
            // 取分配行原始金额及数量
            BigDecimal reqOriginAmt = BigDecimal.ZERO;
            Long reqOriginQty = Long.valueOf(0);
            ExpRequisitionDist requisitionDist = new ExpRequisitionDist();
            requisitionDist.setExpRequisitionDistId(release.getExpRequisitionDistId());
            requisitionDist = requisitionDistService.selectByPrimaryKey(request, requisitionDist);
            if (requisitionDist != null) {
                reqOriginAmt = requisitionDist.getPaymentAmount();
                reqOriginQty = requisitionDist.getPrimaryQuantity();
            }
            // 获取已被下达的非当前release的总金额
            BigDecimal releaseAmt = BigDecimal.ZERO;
            BigDecimal releaseQty = BigDecimal.ZERO;
            BgtBudgetReserve budgetReserve = new BgtBudgetReserve();
            budgetReserve.setDocumentId(release.getExpRequisitionHeaderId());
            budgetReserve.setDocumentLineId(release.getExpRequisitionDistId());
            budgetReserve.setBusinessType("EXP_REQUISITION");
            List<BgtBudgetReserve> budgetReserveList = budgetReserveService.select(request, budgetReserve, 1, 0);
            if (budgetReserveList != null && !budgetReserveList.isEmpty()) {
                for (BgtBudgetReserve reserve : budgetReserveList) {
                    if (reserve.getReleaseId() != null && reserve.getReleaseId() != releaseId) {
                        releaseAmt = releaseAmt.add(reserve.getAmount());
                        releaseQty = releaseQty.add(reserve.getQuantity());
                    }
                }
            }

            // 取保留表中，当前申请单分配行的金额及数量
            BgtBudgetReserve reserve = new BgtBudgetReserve();
            reserve.setDocumentId(release.getExpRequisitionHeaderId());
            reserve.setDocumentLineId(release.getExpRequisitionDistId());
            reserve.setBusinessType("EXP_REQUISITION");
            reserve.setReleaseId(release.getReleaseId());
            List<BgtBudgetReserve> reserveList = budgetReserveService.select(request, reserve, 1, 0);
            if (reserveList != null && !reserveList.isEmpty()) {
                // 如果申请单总金额小于等于已下达总金额，本次下达占用预算设置为0
                if (reqOriginAmt.compareTo(releaseAmt.multiply(BigDecimal.ONE.negate())) == -1 || reqOriginAmt.compareTo(releaseAmt.multiply(BigDecimal.ONE.negate())) == 0) {
                    budgetReserveMapper.updateReqAmount(BigDecimal.ZERO, BigDecimal.ZERO, request.getUserId(), release.getExpRequisitionHeaderId(), release.getExpRequisitionDistId(), release.getReleaseId());
                } else {
                    BigDecimal amount = (reqOriginAmt.add(releaseAmt).compareTo(release.getReqReleaseAmount()) == -1 || reqOriginAmt.add(releaseAmt).compareTo(release.getReqReleaseAmount()) == 0 ? reqOriginAmt.add(releaseAmt) : release.getReqReleaseAmount()).multiply(BigDecimal.ONE.negate());
                    BigDecimal functionAmount = (reqOriginAmt.add(releaseAmt).compareTo(release.getReqReleaseAmount()) == -1 || reqOriginAmt.add(releaseAmt).compareTo(release.getReqReleaseAmount()) == 0 ? reqOriginAmt.add(releaseAmt) : release.getReqReleaseAmount()).multiply(exchangeRate).multiply(BigDecimal.ONE.negate());
                    budgetReserveMapper.updateReqAmount(amount, functionAmount, request.getUserId(), release.getExpRequisitionHeaderId(), release.getExpRequisitionDistId(), release.getReleaseId());
                }
                // 如果申请单总数量小于等于已下达总数量，本次下达占用预算数量设置为0
                if (reqOriginQty.longValue() <= releaseQty.longValue() * -1) {
                    budgetReserveMapper.updateReqQty(BigDecimal.ZERO, request.getUserId(), release.getExpRequisitionHeaderId(), release.getExpRequisitionDistId(), release.getReleaseId());
                } else {
                    BigDecimal quantiy = (reqOriginQty.longValue() + releaseQty.longValue() <= release.getReqReleaseAmount().longValue() ? BigDecimal.valueOf(reqOriginQty.longValue() + releaseQty.longValue()) : release.getReqReleaseAmount()).multiply(BigDecimal.ONE.negate());
                    budgetReserveMapper.updateReqQty(quantiy, request.getUserId(), release.getExpRequisitionHeaderId(), release.getExpRequisitionDistId(), release.getReleaseId());
                }
            } else {
                // 不存在预算保留，则新建
                // 取保留表中，关联过报销单的、申请单分配行的总金额及数量
                BigDecimal bgtReserveTotalAmt = BigDecimal.ZERO;
                BigDecimal bgtReserveTotalQty = BigDecimal.ZERO;
                BigDecimal currentAmt = BigDecimal.ZERO;
                BigDecimal currentQty = BigDecimal.ZERO;
                BigDecimal balanceAmt = BigDecimal.ZERO;
                BigDecimal balanceQty = BigDecimal.ZERO;
                BgtBudgetReserve reserve1 = new BgtBudgetReserve();
                reserve1.setBusinessType("EXP_REQUISITION");
                reserve1.setDocumentId(release.getExpRequisitionHeaderId());
                reserve1.setDocumentLineId(release.getExpRequisitionDistId());
                reserve1.setStatus("P");
                List<BgtBudgetReserve> budgetReserves = budgetReserveService.select(request, reserve1, 1, 0);
                if (budgetReserves != null && !budgetReserves.isEmpty()) {
                    for (BgtBudgetReserve reserve2 : budgetReserves) {
                        if (reserve2.getReleaseId() != null) {
                            bgtReserveTotalAmt = bgtReserveTotalAmt.add(reserve2.getAmount().abs());
                            bgtReserveTotalQty = bgtReserveTotalQty.add(reserve2.getQuantity().abs());
                        }
                    }
                }
                currentAmt = (totalAmount.compareTo(reqOriginAmt) == -1 ? totalAmount : reqOriginAmt);
                currentQty = (totalQuantity.longValue() < reqOriginQty.longValue() ? totalQuantity : BigDecimal.valueOf(reqOriginQty));
                // 计算保留差额
                balanceAmt = currentAmt.subtract(bgtReserveTotalAmt);
                balanceQty = currentQty.subtract(bgtReserveTotalQty);

                if (balanceAmt.compareTo(BigDecimal.ZERO) == 0 && balanceQty.compareTo(BigDecimal.ZERO) == 0) {
                    return;
                }
                currentAmt = (release.getDocReleaseAmount().compareTo(balanceAmt) == -1 ? release.getDocReleaseAmount() : balanceAmt);
                currentQty = (release.getDocReleaseQuantity().compareTo(balanceQty) == -1 ? release.getDocReleaseQuantity() : balanceQty);
                ExpRequisitionDist dist = new ExpRequisitionDist();
                dist.setExpRequisitionDistId(release.getExpRequisitionDistId());
                dist = requisitionDistService.selectByPrimaryKey(request, dist);
                if (dist != null && dist.getBudgetItemId() != null) {
                    // 取对应报销单的期间
                    String periodName = getRepPeriod(request, release.getReleaseId());
                    BgtEntity entity = new BgtEntity();
                    entity.setEntityId(dist.getBgtEntityId());
                    entity = entityService.selectByPrimaryKey(request, entity);

                    BgtBudgetReserve bgtBudgetReserve = new BgtBudgetReserve();
                    bgtBudgetReserve.setReserveCompanyId(dist.getCompanyId());
                    bgtBudgetReserve.setReserveOperationUnitId(null);
                    bgtBudgetReserve.setBgtOrgId(entity.getBgtOrgId());
                    bgtBudgetReserve.setBgtEntityId(dist.getBgtEntityId());
                    bgtBudgetReserve.setBgtCenterId(dist.getBgtCenterId());
                    bgtBudgetReserve.setPeriodName(periodName);
                    bgtBudgetReserve.setBusinessType("EXP_REQUISITION");
                    bgtBudgetReserve.setReserveFlag("R");
                    bgtBudgetReserve.setStatus("N");
                    bgtBudgetReserve.setDocumentId(release.getExpRequisitionHeaderId());
                    bgtBudgetReserve.setDocumentLineId(release.getExpRequisitionDistId());
                    bgtBudgetReserve.setCurrency(dist.getPaymentCurrencyCode());
                    bgtBudgetReserve.setBudgetItemId(dist.getBudgetItemId());
                    bgtBudgetReserve.setResponsibilityCenterId(dist.getRespCenterId());
                    bgtBudgetReserve.setExchangeRateType(dist.getPay2funExchangeType());
                    bgtBudgetReserve.setExchangeRate(dist.getPay2funExchangeRate());
                    bgtBudgetReserve.setAmount(currentAmt);
                    bgtBudgetReserve.setFunctionalAmount(currentAmt.multiply(dist.getPay2funExchangeRate()));
                    bgtBudgetReserve.setQuantity(currentQty);
                    bgtBudgetReserve.setUom(dist.getPrimaryUom());
                    bgtBudgetReserve.setCompanyId(dist.getCompanyId());
                    bgtBudgetReserve.setOperationUnitId(dist.getOperationUnitId());
                    bgtBudgetReserve.setUnitId(dist.getUnitId());
                    bgtBudgetReserve.setPositionId(dist.getPositionId());
                    bgtBudgetReserve.setEmployeeId(dist.getEmployeeId());
                    bgtBudgetReserve.setDimension1Id(dist.getDimension1Id());
                    bgtBudgetReserve.setDimension2Id(dist.getDimension2Id());
                    bgtBudgetReserve.setDimension3Id(dist.getDimension3Id());
                    bgtBudgetReserve.setDimension4Id(dist.getDimension4Id());
                    bgtBudgetReserve.setDimension5Id(dist.getDimension5Id());
                    bgtBudgetReserve.setDimension6Id(dist.getDimension6Id());
                    bgtBudgetReserve.setDimension7Id(dist.getDimension7Id());
                    bgtBudgetReserve.setDimension8Id(dist.getDimension8Id());
                    bgtBudgetReserve.setDimension9Id(dist.getDimension9Id());
                    bgtBudgetReserve.setDimension10Id(dist.getDimension10Id());
                    bgtBudgetReserve.setDimension11Id(dist.getDimension11Id());
                    bgtBudgetReserve.setDimension12Id(dist.getDimension12Id());
                    bgtBudgetReserve.setDimension13Id(dist.getDimension13Id());
                    bgtBudgetReserve.setDimension14Id(dist.getDimension14Id());
                    bgtBudgetReserve.setDimension15Id(dist.getDimension15Id());
                    bgtBudgetReserve.setDimension16Id(dist.getDimension16Id());
                    bgtBudgetReserve.setDimension17Id(dist.getDimension17Id());
                    bgtBudgetReserve.setDimension18Id(dist.getDimension18Id());
                    bgtBudgetReserve.setDimension19Id(dist.getDimension19Id());
                    bgtBudgetReserve.setDimension20Id(dist.getDimension20Id());
                    bgtBudgetReserve.setReleaseId(releaseId);
                    bgtBudgetReserve.setSourceType("");
                    budgetReserveService.insertBgtReserve(request, bgtBudgetReserve);
                }
            }
        }
    }

    private String getRepPeriod(IRequest request, Long releaseId) {
        ExpRequisitionRelease release = expRequisitionReleaseMapper.selectByPrimaryKey(releaseId);
        String periodName = "";
        if (release != null) {
            ExpRequisitionDist dist = new ExpRequisitionDist();
            dist.setExpRequisitionDistId(release.getExpRequisitionDistId());
            dist = requisitionDistService.selectByPrimaryKey(request, dist);
            if (dist != null) {
                if (release.getDocumentDistId() == null) {
                    // 如果没有找到对应的报销单分配行ID,即此数据为手工释放,取当前系统时间对应的期间
                    BgtEntity entity = new BgtEntity();
                    entity.setEntityId(dist.getBgtEntityId());
                    entity = entityService.selectByPrimaryKey(request, entity);
                    if (entity != null) {
                        BgtOrganization organization = new BgtOrganization();
                        organization.setBgtOrgId(entity.getBgtOrgId());
                        organization = bgtOrganizationService.selectByPrimaryKey(request, organization);
                        if (organization != null) {
                            BgtPeriod period = new BgtPeriod();
                            period.setPeriodSetId(organization.getPeriodSetId());
                            List<BgtPeriod> periodList = bgtPeriodMapper.select(period);
                            if (periodList != null && !periodList.isEmpty()) {
                                for (BgtPeriod period1 : periodList) {
                                    if (DateUtils.getCurrentDate().after(period1.getStartDate()) && DateUtils.getCurrentDate().before(period1.getEndDate())) {
                                        periodName = period1.getPeriodName();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    ExpReportDist reportDist = new ExpReportDist();
                    reportDist.setExpReportDistId(release.getDocumentDistId());
                    reportDist = reportDistService.selectByPrimaryKey(request, reportDist);
                    if (reportDist != null) {
                        periodName = reportDist.getPeriodName();
                    }
                }
            }
        }
        return periodName;
    }


    @Override
    public void openOneOffExpRequisition(IRequest request, Long expReqHeaderId) {
        // 根据申请单头ID获取申请单所有分配行信息
        List<ExpRequisitionDist> requisitionDistList = requisitionDistService.getAllExpRequisitionDist(request, expReqHeaderId);
        if (requisitionDistList != null && !requisitionDistList.isEmpty()) {
            for (ExpRequisitionDist dist : requisitionDistList) {
                dist.setCloseFlag("N");
                dist.setCloseDate(null);
                dist = requisitionDistService.updateByPrimaryKey(request, dist);
                BgtBudgetReserve reserve = new BgtBudgetReserve();
                reserve.setDocumentId(expReqHeaderId);
                reserve.setDocumentLineId(dist.getExpRequisitionDistId());
                reserve.setBusinessType("EXP_REQUISITION");
                List<BgtBudgetReserve> reserveList = budgetReserveService.select(request, reserve, 1, 0);
                if (reserveList != null && !reserveList.isEmpty()) {
                    for (BgtBudgetReserve reserve1 : reserveList) {
                        if ("REQ_CLOSE".equals(reserve1.getSourceType()) || "ADJUSTMENT".equals(reserve1.getSourceType())) {
                            budgetReserveService.deleteByPrimaryKey(reserve1);
                            BgtBudgetReserveExtend budgetReserveExtend = new BgtBudgetReserveExtend();
                            budgetReserveExtend.setBudgetReserveLineId(reserve1.getBudgetReserveLineId());
                            budgetReserveExtend = bgtBudgetReserveExtendService.selectByPrimaryKey(request, budgetReserveExtend);
                            bgtBudgetReserveExtendService.deleteByPrimaryKey(budgetReserveExtend);
                        }
                    }
                }
            }
        }
    }

    /**
     * <p>报销单关联申请单新增</p>
     *
     * @param request
     * @param dto
     * @return ExpRequisitionRelease
     * @throws RuntimeException exception
     * @author yang.duan 2019/4/29 14:32
     **/
    @Override
    public ExpRequisitionRelease insertExpRequisitionRelease(IRequest request, ExpRequisitionRelease dto) throws RuntimeException {
        Long documentId = null;
        Long documentLineId = null;
        ExpRequisitionHeader requisitionHeader = new ExpRequisitionHeader();
        ExpRequisitionLine requisitionLine = new ExpRequisitionLine();
        ExpReportDist reportDist = new ExpReportDist();
        ExpReportLine reportLine = new ExpReportLine();
        ExpReportHeader reportHeader = new ExpReportHeader();
        ExpRequisitionRelease release = new ExpRequisitionRelease();
        //获取申请单信息
        ExpRequisitionDist requisitionDist = new ExpRequisitionDist();
        requisitionDist.setExpRequisitionDistId(dto.getExpRequisitionDistId());
        requisitionDist = requisitionDistService.selectByPrimaryKey(request, requisitionDist);

        if (requisitionDist != null) {
            requisitionLine.setExpRequisitionLineId(requisitionDist.getExpRequisitionLineId());
            requisitionLine = requisitionLineService.selectByPrimaryKey(request, requisitionLine);
            if (requisitionLine != null) {
                requisitionHeader.setExpRequisitionHeaderId(requisitionLine.getExpRequisitionHeaderId());
                requisitionHeader = requisitionHeaderService.selectByPrimaryKey(request, requisitionHeader);
            }
        }
        if (ExpReportHeader.EXP_REPORT.equals(dto.getDocumentType())) {
            //获取报销单信息
            reportDist.setExpReportDistId(dto.getDocumentDistId());
            reportDist = reportDistService.selectByPrimaryKey(request, reportDist);
            if (reportDist != null) {
                reportLine.setExpReportLineId(reportDist.getExpReportLineId());
                reportLine = reportLineService.selectByPrimaryKey(request, reportLine);
                if (reportLine != null) {
                    reportHeader.setExpReportHeaderId(reportLine.getExpReportHeaderId());
                    reportHeader = reportHeaderService.selectByPrimaryKey(request, reportHeader);
                    documentId = reportLine.getExpReportHeaderId();
                    documentLineId = reportLine.getExpReportLineId();
                }
            }
        }

        if (!ExpReportHeader.EXP_REPORT.equals(dto.getDocumentType()) || (reportHeader != null && (ExpReportHeader.GENERATE.equals(reportHeader.getReportStatus()) || ExpReportHeader.TAKEN_BACK.equals(reportHeader.getReportStatus()) || ExpReportHeader.REJECTED.equals(reportHeader.getReportStatus())))) {
            //新增release表
            release.setExpRequisitionHeaderId(requisitionHeader != null ? requisitionHeader.getExpRequisitionHeaderId() : null);
            release.setExpRequisitionLineId(requisitionLine != null ? requisitionLine.getExpRequisitionLineId() : null);
            release.setExpRequisitionDistId(dto.getExpRequisitionDistId());
            release.setCompanyId(requisitionHeader != null ? requisitionHeader.getCompanyId() : null);
            release.setOperationUnitId(requisitionHeader != null ? requisitionHeader.getOperationUnitId() : null);
            release.setDocumentType(ExpReportHeader.EXP_REPORT);
            release.setDocumentId(documentId);
            release.setDocumentLineId(documentLineId);
            release.setDocumentDistId(dto.getDocumentDistId());
            release.setReqReleaseAmount(dto.getReqReleaseAmount());
            release.setDocReleaseAmount(dto.getReqReleaseAmount());
            release.setReqReleaseQuantity(dto.getReqReleaseQuantity());
            release.setReqReleaseUom(dto.getReqReleaseUom());
            release.setDocReleaseQuantity(dto.getReqReleaseQuantity());
            release.setDocReleaseUom(dto.getReqReleaseUom());
            release.setCreatedBy(request.getUserId());
            release.setCreationDate(new Date());
            release.setLastUpdatedBy(request.getUserId());
            release.setLastUpdateDate(new Date());
            release = self().insertSelective(request, release);

            //创建申请单分配行预算保留
            createReqBgtBudgetReserves(request, release);

            //更新分配行已下达金额
            setRequisitionDistsRelease(request, release.getExpRequisitionDistId(), (requisitionHeader != null ? requisitionHeader.getExpRequisitionHeaderId() : null));

            //一次性申请单 关联校验
            oneOffReqReleasedCheck(request, release);

            //报销单关联申请单 容限控制校验
            toleranceCheck(request, (requisitionHeader != null ? requisitionHeader.getExpRequisitionHeaderId() : null), release.getExpRequisitionDistId(), (requisitionHeader != null ? requisitionHeader.getMoExpReqTypeId() : null), release.getDocumentId());
        }
        return release;
    }

    private void createReqBgtBudgetReserves(IRequest request, ExpRequisitionRelease release) {
        Long distQty = Long.valueOf(0);
        BigDecimal distAmt = BigDecimal.ZERO;
        BigDecimal bgtReverseQty = BigDecimal.ZERO;
        BigDecimal bgtReverseAmt = BigDecimal.ZERO;
        BigDecimal totalReleaseQty = BigDecimal.ZERO;
        BigDecimal totalReleaseAmt = BigDecimal.ZERO;
        BigDecimal currentReleaseAmt = BigDecimal.ZERO;
        BigDecimal currentReleaseQty = BigDecimal.ZERO;
        ExpRequisitionDist requisitionDist = new ExpRequisitionDist();
        requisitionDist.setExpRequisitionDistId(release.getExpRequisitionDistId());
        requisitionDist = requisitionDistService.selectByPrimaryKey(request, requisitionDist);
        //累计下达金额、数量
        ExpRequisitionRelease amtQtyRelease = expRequisitionReleaseMapper.getTotalReleaseAmtQty(release.getExpRequisitionHeaderId(), release.getExpRequisitionDistId());
        if (amtQtyRelease != null) {
            totalReleaseQty = amtQtyRelease.getReqReleaseQuantity();
            totalReleaseAmt = amtQtyRelease.getReqReleaseAmount();
        }

        //当前分配行金额、数量
        distQty = (requisitionDist != null && requisitionDist.getPrimaryQuantity() != null ? requisitionDist.getPrimaryQuantity() : Long.valueOf(0));
        distAmt = (requisitionDist != null && requisitionDist.getPaymentAmount() != null ? requisitionDist.getPaymentAmount() : BigDecimal.ZERO);

        //累计保留表中金额、数量
        BgtBudgetReserve reserve = budgetReserveMapper.getBudgetReservesBalance(ExpRequisitionHeader.FIELD_EXP_REQUISITION, release.getExpRequisitionDistId());
        if (reserve != null) {
            bgtReverseQty = reserve.getQuantity();
            bgtReverseAmt = reserve.getAmount();
        }


        if (totalReleaseAmt.compareTo(distAmt) == -1 || totalReleaseAmt.compareTo(distAmt) == 0) {
            currentReleaseAmt = release.getReqReleaseAmount().multiply(BigDecimal.valueOf(-1));
        } else {
            currentReleaseAmt = bgtReverseAmt.multiply(BigDecimal.valueOf(-1));
        }

        if (totalReleaseQty.compareTo(BigDecimal.valueOf(distQty)) == -1 || totalReleaseQty.compareTo(BigDecimal.valueOf(distQty)) == 0) {
            currentReleaseQty = release.getReqReleaseQuantity().multiply(BigDecimal.valueOf(-1));
        } else {
            currentReleaseQty = bgtReverseQty.multiply(BigDecimal.valueOf(-1));
        }

        if (requisitionDist != null && requisitionDist.getBudgetItemId() != null) {
            //取对应报销单的期间
            String periodName = getRepPeriod(request, release.getReleaseId());
            //根据预算实体获取预算组织ID
            BgtEntity entity = new BgtEntity();
            entity.setEntityId(requisitionDist.getBgtEntityId());
            entity = bgtEntityService.selectByPrimaryKey(request, entity);
            //插入reserves表
            BgtBudgetReserve budgetReserve = new BgtBudgetReserve();
            budgetReserve.setReserveCompanyId(requisitionDist.getCompanyId());
            budgetReserve.setReserveOperationUnitId(null);
            budgetReserve.setBgtOrgId(entity.getBgtOrgId());
            budgetReserve.setBgtEntityId(requisitionDist.getBgtEntityId());
            budgetReserve.setBgtCenterId(requisitionDist.getBgtCenterId());
            budgetReserve.setPeriodName(periodName);
            budgetReserve.setBusinessType(ExpRequisitionHeader.FIELD_EXP_REQUISITION);
            budgetReserve.setReserveFlag("R");
            budgetReserve.setStatus("N");
            budgetReserve.setDocumentId(release.getExpRequisitionHeaderId());
            budgetReserve.setDocumentLineId(release.getExpRequisitionDistId());
            budgetReserve.setCurrency(requisitionDist.getPaymentCurrencyCode());
            budgetReserve.setBudgetItemId(requisitionDist.getBudgetItemId());
            budgetReserve.setResponsibilityCenterId(requisitionDist.getRespCenterId());
            budgetReserve.setExchangeRateType(requisitionDist.getPay2funExchangeType());
            budgetReserve.setExchangeRate(requisitionDist.getPay2funExchangeRate());
            budgetReserve.setAmount(currentReleaseAmt);
            budgetReserve.setQuantity(currentReleaseQty);
            budgetReserve.setFunctionalAmount(currentReleaseAmt.multiply(requisitionDist.getPay2funExchangeRate()));
            budgetReserve.setQuantity(currentReleaseAmt);
            budgetReserve.setUom(requisitionDist.getPrimaryUom());
            budgetReserve.setCompanyId(requisitionDist.getCompanyId());
            budgetReserve.setOperationUnitId(requisitionDist.getOperationUnitId());
            budgetReserve.setUnitId(requisitionDist.getUnitId());
            budgetReserve.setPositionId(requisitionDist.getPositionId());
            budgetReserve.setEmployeeId(requisitionDist.getEmployeeId());
            budgetReserve.setDimension1Id(requisitionDist.getDimension1Id());
            budgetReserve.setDimension2Id(requisitionDist.getDimension2Id());
            budgetReserve.setDimension3Id(requisitionDist.getDimension3Id());
            budgetReserve.setDimension4Id(requisitionDist.getDimension4Id());
            budgetReserve.setDimension5Id(requisitionDist.getDimension5Id());
            budgetReserve.setDimension6Id(requisitionDist.getDimension6Id());
            budgetReserve.setDimension7Id(requisitionDist.getDimension7Id());
            budgetReserve.setDimension8Id(requisitionDist.getDimension8Id());
            budgetReserve.setDimension9Id(requisitionDist.getDimension9Id());
            budgetReserve.setDimension10Id(requisitionDist.getDimension10Id());
            budgetReserve.setDimension11Id(requisitionDist.getDimension11Id());
            budgetReserve.setDimension12Id(requisitionDist.getDimension12Id());
            budgetReserve.setDimension13Id(requisitionDist.getDimension13Id());
            budgetReserve.setDimension14Id(requisitionDist.getDimension14Id());
            budgetReserve.setDimension15Id(requisitionDist.getDimension15Id());
            budgetReserve.setDimension16Id(requisitionDist.getDimension16Id());
            budgetReserve.setDimension17Id(requisitionDist.getDimension17Id());
            budgetReserve.setDimension18Id(requisitionDist.getDimension18Id());
            budgetReserve.setDimension19Id(requisitionDist.getDimension19Id());
            budgetReserve.setDimension20Id(requisitionDist.getDimension20Id());
            budgetReserve.setReleaseId(release.getReleaseId());
            budgetReserve = bgtBudgetReserveService.insertBgtReserve(request, budgetReserve);
        }

    }


    private void setRequisitionDistsRelease(IRequest request, Long expRequisitionDistId, Long expRequisitionHeaderId) {
        String releaseStatus = "";
        BigDecimal paymentAmt = BigDecimal.ZERO;
        ExpRequisitionRelease release = expRequisitionReleaseMapper.getTotalReleaseAmtQty(expRequisitionHeaderId, expRequisitionDistId);
        ExpRequisitionDist dist = new ExpRequisitionDist();
        dist.setExpRequisitionDistId(expRequisitionDistId);
        dist = requisitionDistService.selectByPrimaryKey(request, dist);
        paymentAmt = (dist == null && dist.getPaymentAmount() == null ? BigDecimal.ZERO : dist.getPaymentAmount());
        if (release != null) {
            if (release.getReqReleaseAmount().compareTo(BigDecimal.ZERO) == 0) {
                releaseStatus = "N";
            } else if (release.getReqReleaseAmount().compareTo(paymentAmt) == -1) {
                releaseStatus = "Y";
            } else {
                releaseStatus = "C";
            }
            //更新申请单分配行的关联状态
            dist.setReleasedAmount(release.getReqReleaseAmount());
            dist.setReleasedQuantity(release.getReqReleaseQuantity().longValue());
            dist.setReleasedStatus(releaseStatus);
            dist.setLastUpdateDate(new Date());
            dist.setLastUpdatedBy(request.getUserId());
            requisitionDistService.updateByPrimaryKeySelective(request, dist);
        }
    }

    private void oneOffReqReleasedCheck(IRequest request, ExpRequisitionRelease release) {
        //获取申请单信息
        ExpRequisitionDist dist = new ExpRequisitionDist();
        dist.setExpRequisitionDistId(release.getExpRequisitionDistId());
        dist = requisitionDistService.selectByPrimaryKey(request, dist);

        ExpRequisitionLine requisitionLine = new ExpRequisitionLine();
        requisitionLine.setExpRequisitionLineId(dist.getExpRequisitionLineId());
        requisitionLine = requisitionLineService.selectByPrimaryKey(request, requisitionLine);

        ExpRequisitionHeader requisitionHeader = new ExpRequisitionHeader();
        requisitionHeader.setExpRequisitionHeaderId(requisitionLine.getExpRequisitionHeaderId());
        requisitionHeader = requisitionHeaderService.selectByPrimaryKey(request, requisitionHeader);

        ExpMoReqType reqType = new ExpMoReqType();
        reqType.setMoExpReqTypeId(requisitionHeader.getMoExpReqTypeId());
        reqType = reqTypeService.selectByPrimaryKey(request, reqType);

        if (reqType != null && "Y".equals(reqType.getOneOffFlag())) {
            ExpRequisitionRelease requisitionRelease = new ExpRequisitionRelease();
            requisitionRelease.setDocumentType(ExpReportHeader.EXP_REPORT);
            requisitionRelease.setExpRequisitionDistId(dist.getExpRequisitionDistId());
            requisitionRelease.setExpRequisitionLineId(requisitionLine.getExpRequisitionLineId());
            requisitionRelease.setExpRequisitionHeaderId(requisitionHeader.getExpRequisitionHeaderId());

            List<ExpRequisitionRelease> releaseList = self().select(request, requisitionRelease, 1, 0);
            if (releaseList != null && releaseList.size() > 1) {
                throw new RuntimeException("此一次性申请单已被下达报销单!");
            }
        }
    }


    private void toleranceCheck(IRequest request, Long expReqHeaderId, Long expReqDistId, Long expReqTypeId, Long expDocumentId) {
        String oneOffFlag = "";
        String toleranceFlag = "";
        String toleranceRange = "";
        Long toleranceRatio = Long.valueOf(0);
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal releaseAmount = BigDecimal.ZERO;
        BigDecimal beforeReleaseAmount = BigDecimal.ZERO;
        BigDecimal infoAmount = BigDecimal.ZERO;
        String businessCurrencyCode = "";
        ExpMoReqType reqType = new ExpMoReqType();
        reqType.setMoExpReqTypeId(expReqTypeId);
        reqType = reqTypeService.selectByPrimaryKey(request, reqType);
        if (reqType != null) {
            oneOffFlag = reqType.getOneOffFlag();
            toleranceFlag = reqType.getToleranceFlag();
            toleranceRange = reqType.getToleranceRange();
            toleranceRatio = reqType.getToleranceRatio() / 100;
        }
        if ("Y".equals(toleranceFlag)) {
            if ("HEAD".equals(toleranceRange)) {
                //头容限控制
                amount = requisitionDistMapper.getTotalPaymentAmount(expReqHeaderId, null);
                releaseAmount = expRequisitionReleaseMapper.getDocReleaseAmount(expReqHeaderId, null, null);
                beforeReleaseAmount = expRequisitionReleaseMapper.getDocBeforeReleaseAmt(expReqHeaderId, expDocumentId, null);
                infoAmount = amount.multiply(BigDecimal.valueOf(toleranceRatio)).subtract(beforeReleaseAmount);

                if (releaseAmount.compareTo(amount.multiply(BigDecimal.valueOf(toleranceRatio))) == 1) {
                    ExpRequisitionDist dist = new ExpRequisitionDist();
                    dist.setExpRequisitionDistId(expReqDistId);
                    dist = requisitionDistService.selectByPrimaryKey(request, dist);
                    businessCurrencyCode = (dist != null && dist.getBusinessCurrencyCode() != null ? dist.getBusinessCurrencyCode() : "CNY");
                    throw new RuntimeException("下达金额不能超过" + infoAmount + businessCurrencyCode + ",请检查下达金额!");
                }
            } else if ("LINE".equals(toleranceRange)) {
                ExpRequisitionDist dist = new ExpRequisitionDist();
                dist.setExpRequisitionDistId(expReqDistId);
                dist = requisitionDistService.selectByPrimaryKey(request, dist);
                //行容限控制
                amount = dist.getPaymentAmount();
                releaseAmount = expRequisitionReleaseMapper.getDocReleaseAmount(expReqHeaderId, expReqDistId, null);
                beforeReleaseAmount = expRequisitionReleaseMapper.getDocBeforeReleaseAmt(expReqHeaderId, expDocumentId, expReqDistId);
                infoAmount = amount.multiply(BigDecimal.valueOf(toleranceRatio)).subtract(beforeReleaseAmount);
                if (releaseAmount.compareTo(amount.multiply(BigDecimal.valueOf(toleranceRatio))) == 1) {
                    businessCurrencyCode = (dist != null && dist.getBusinessCurrencyCode() != null ? dist.getBusinessCurrencyCode() : "CNY");
                    throw new RuntimeException("下达金额不能超过" + infoAmount + businessCurrencyCode + ",请检查下达金额!");
                }
            } else if ("EXPENSE_TYPE".equals(toleranceRange)) {
                ExpRequisitionDist dist = new ExpRequisitionDist();
                dist.setExpRequisitionDistId(expReqDistId);
                dist = requisitionDistService.selectByPrimaryKey(request, dist);
                amount = requisitionDistMapper.getTotalPaymentAmount(expReqHeaderId, expReqDistId);
                releaseAmount = expRequisitionReleaseMapper.getDocReleaseAmount(expReqHeaderId, null, expReqDistId);
                if (releaseAmount.compareTo(amount.multiply(BigDecimal.valueOf(expReqDistId))) == 1) {
                    businessCurrencyCode = (dist != null && dist.getBusinessCurrencyCode() != null ? dist.getBusinessCurrencyCode() : "CNY");
                    throw new RuntimeException("下达金额不能超过" + infoAmount + businessCurrencyCode + ",请检查下达金额!");
                }
            }
        }
    }

}
