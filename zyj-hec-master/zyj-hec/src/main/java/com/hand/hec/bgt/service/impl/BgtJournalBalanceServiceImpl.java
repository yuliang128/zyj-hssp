package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtJournalHeader;
import com.hand.hec.bgt.dto.BgtJournalLine;
import com.hand.hec.bgt.mapper.BgtJournalBalanceMapper;
import com.hand.hec.bgt.service.IBgtJournalBalanceExtendService;
import com.hand.hec.bgt.service.IBgtJournalHeaderService;
import com.hand.hec.bgt.service.IBgtJournalLineService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtJournalBalance;
import com.hand.hec.bgt.service.IBgtJournalBalanceService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 预算余额ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtJournalBalanceServiceImpl extends BaseServiceImpl<BgtJournalBalance>
                implements IBgtJournalBalanceService {
    @Autowired
    private BgtJournalBalanceMapper balanceMapper;

    @Autowired
    private IBgtJournalHeaderService journalHeaderService;

    @Autowired
    private IBgtJournalLineService journalLineService;

    @Autowired
    private IBgtJournalBalanceExtendService journalBalanceExtendService;

    private BigDecimal gCurrentAmount = new BigDecimal(0);

    private BigDecimal gCurrentFuncAmount = new BigDecimal(0);

    private BigDecimal gCurrentQuantity = new BigDecimal(0);

    @Override
    public BgtJournalBalance createJournalBalance(IRequest request, Long journalHeaderId) {
        BgtJournalBalance bgtJournalBalance = new BgtJournalBalance();
        List<BgtJournalLine> bgtJournalLineList = journalLineService.queryInfoByHeaderId(request, journalHeaderId);
        bgtJournalLineList.forEach(bgtJournalLine -> {
            gCurrentAmount = bgtJournalLine.getAmount();
            gCurrentFuncAmount = bgtJournalLine.getFunctionalAmount();
            gCurrentQuantity = bgtJournalLine.getQuantity();
            BeanUtils.copyProperties(bgtJournalLine, bgtJournalBalance);
            bgtJournalBalance.setCurrencyCode(bgtJournalLine.getCurrency());
            bgtJournalBalance.setPeriodAmount(new BigDecimal(0));
            bgtJournalBalance.setPeriodFunctionalAmount(new BigDecimal(0));
            bgtJournalBalance.setPeriodQuantity(new BigDecimal(0));
            bgtJournalBalance.setQuarterAmount(new BigDecimal(0));
            bgtJournalBalance.setQuarterFunctionalAmount(new BigDecimal(0));
            bgtJournalBalance.setQuarterQuantity(new BigDecimal(0));
            bgtJournalBalance.setYearAmount(new BigDecimal(0));
            bgtJournalBalance.setYearFunctionalAmount(new BigDecimal(0));
            bgtJournalBalance.setYearQuantity(new BigDecimal(0));
            this.executeBalance(request, bgtJournalBalance);
        });
        return bgtJournalBalance;
    }

    private BgtJournalBalance executeBalance(IRequest request, BgtJournalBalance bgtJournalBalance) {
        boolean ifExists = this.checkBalanceExists(bgtJournalBalance);
        if (bgtJournalBalance.getPeriodName() != null) {
            // 更新其他记录的年度值
            this.updateYearBalanceMonth(request, bgtJournalBalance);
            // 更新其他记录的季度值
            this.updateQuarterBalanceMonth(request, bgtJournalBalance);

            bgtJournalBalance.setPeriodAmount(gCurrentAmount);
            bgtJournalBalance.setPeriodFunctionalAmount(gCurrentFuncAmount);
            bgtJournalBalance.setPeriodQuantity(gCurrentQuantity);

            if (!ifExists) {
                // 取累计季度值
                Map<String, BigDecimal> quarterMap = this.getQuarterBalanceMonth(bgtJournalBalance);
                // 取累计年度值
                Map<String, BigDecimal> yearMap = this.getYearBalanceMonth(bgtJournalBalance);
                bgtJournalBalance.setQuarterAmount(quarterMap.get("totalPeriodAmount"));
                bgtJournalBalance.setQuarterFunctionalAmount(quarterMap.get("totalPeriodFunctionalAmount"));
                bgtJournalBalance.setQuarterQuantity(quarterMap.get("totalPeriodQuantity"));
                bgtJournalBalance.setYearAmount(yearMap.get("totalPeriodAmount"));
                bgtJournalBalance.setYearFunctionalAmount(yearMap.get("totalPeriodFunctionalAmount"));
                bgtJournalBalance.setYearQuantity(yearMap.get("totalPeriodQuantity"));
            } else {
                bgtJournalBalance.setQuarterAmount(gCurrentAmount);
                bgtJournalBalance.setQuarterFunctionalAmount(gCurrentFuncAmount);
                bgtJournalBalance.setQuarterQuantity(gCurrentQuantity);
                bgtJournalBalance.setYearAmount(gCurrentAmount);
                bgtJournalBalance.setYearFunctionalAmount(gCurrentFuncAmount);
                bgtJournalBalance.setYearQuantity(gCurrentQuantity);
            }

        } else if (bgtJournalBalance.getPeriodQuarter() != null) {
            // 更新其他记录的年度值
            this.updateYearBalanceQuarter(request, bgtJournalBalance);
            if (!ifExists) {
                // 取累计年度值
                Map<String, BigDecimal> yearMap = this.getYearBalanceQuarter(bgtJournalBalance);
                bgtJournalBalance.setQuarterAmount(gCurrentAmount);
                bgtJournalBalance.setQuarterFunctionalAmount(gCurrentFuncAmount);
                bgtJournalBalance.setQuarterQuantity(gCurrentQuantity);
                bgtJournalBalance.setYearAmount(yearMap.get("totalPeriodAmount"));
                bgtJournalBalance.setYearFunctionalAmount(yearMap.get("totalPeriodFunctionalAmount"));
                bgtJournalBalance.setYearQuantity(yearMap.get("totalPeriodQuantity"));
            } else {
                bgtJournalBalance.setQuarterAmount(gCurrentAmount);
                bgtJournalBalance.setQuarterFunctionalAmount(gCurrentFuncAmount);
                bgtJournalBalance.setQuarterQuantity(gCurrentQuantity);
                bgtJournalBalance.setYearAmount(gCurrentAmount);
                bgtJournalBalance.setYearFunctionalAmount(gCurrentFuncAmount);
                bgtJournalBalance.setYearQuantity(gCurrentQuantity);
            }
        } else if (bgtJournalBalance.getPeriodYear() != null) {
            if (!ifExists) {
                // 取已存在的年度值
                Map<String, BigDecimal> yearMap = this.getYearBalanceYear(bgtJournalBalance);
                bgtJournalBalance.setQuarterAmount(gCurrentAmount);
                bgtJournalBalance.setQuarterFunctionalAmount(gCurrentFuncAmount);
                bgtJournalBalance.setQuarterQuantity(gCurrentQuantity);
                bgtJournalBalance.setYearAmount(yearMap.get("totalPeriodAmount"));
                bgtJournalBalance.setYearFunctionalAmount(yearMap.get("totalPeriodFunctionalAmount"));
                bgtJournalBalance.setYearQuantity(yearMap.get("totalPeriodQuantity"));
            } else {
                bgtJournalBalance.setQuarterAmount(gCurrentAmount);
                bgtJournalBalance.setQuarterFunctionalAmount(gCurrentFuncAmount);
                bgtJournalBalance.setQuarterQuantity(gCurrentQuantity);
                bgtJournalBalance.setYearAmount(gCurrentAmount);
                bgtJournalBalance.setYearFunctionalAmount(gCurrentFuncAmount);
                bgtJournalBalance.setYearQuantity(gCurrentQuantity);
            }
        }

        if (!ifExists) {
            bgtJournalBalance = self().insertSelective(request, bgtJournalBalance);
            journalBalanceExtendService.calculateBalanceExtend(request, bgtJournalBalance.getBalanceId());
        } else {
            this.updateOwnBalance(request, bgtJournalBalance);
        }
        return bgtJournalBalance;
    }

    private boolean checkBalanceExists(BgtJournalBalance bgtJournalBalance) {
        int num = balanceMapper.checkBalanceExists(bgtJournalBalance);
        return num == 0 ? false : true;
    }

    /**
     * 更新其他记录的年度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 15:41
     * @return
     */
    private void updateYearBalanceMonth(IRequest request, BgtJournalBalance bgtJournalBalance) {
            bgtJournalBalance.setYearAmount(gCurrentAmount);
            bgtJournalBalance.setYearFunctionalAmount(gCurrentFuncAmount);
            bgtJournalBalance.setYearQuantity(gCurrentQuantity);
            balanceMapper.updateYearBalanceMonth(bgtJournalBalance);
        this.updateBalanceByOtherCurrency(request, bgtJournalBalance, "MONTH");
    }

    /**
     * 更新其他记录的年度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 15:41
     * @return
     */
    private void updateYearBalanceQuarter(IRequest request, BgtJournalBalance bgtJournalBalance) {
            bgtJournalBalance.setYearAmount(gCurrentAmount);
            bgtJournalBalance.setYearFunctionalAmount(gCurrentFuncAmount);
            bgtJournalBalance.setYearQuantity(gCurrentQuantity);
            balanceMapper.updateYearBalanceQuarter(bgtJournalBalance);
        this.updateBalanceByOtherCurrency(request, bgtJournalBalance,"QUARTER");
    }

    /**
     * 更新其他记录的季度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 15:41
     * @return
     */
    private void updateQuarterBalanceMonth(IRequest request, BgtJournalBalance bgtJournalBalance) {
            bgtJournalBalance.setYearAmount(gCurrentAmount);
            bgtJournalBalance.setYearFunctionalAmount(gCurrentFuncAmount);
            bgtJournalBalance.setYearQuantity(gCurrentQuantity);
            balanceMapper.updateYearBalanceMonth(bgtJournalBalance);
        this.updateBalanceByOtherCurrency(request, bgtJournalBalance,"QUARTER_MONTH");
    }


    /**
     * 按月控制，取季度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 16:29
     * @return Map<String, BigDecimal>
     */
    private Map<String, BigDecimal> getQuarterBalanceMonth(BgtJournalBalance bgtJournalBalance) {
        Map<String, BigDecimal> map = balanceMapper.queryTotalQuarterBalanceMonth(bgtJournalBalance);
        return this.getTotalAmountMap(map);
    }

    /**
     * 按月控制，取年度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 16:29
     * @return Map<String, BigDecimal>
     */
    private Map<String, BigDecimal> getYearBalanceMonth(BgtJournalBalance bgtJournalBalance) {
        Map<String, BigDecimal> map = balanceMapper.queryTotalYearBalanceMonth(bgtJournalBalance);
        return this.getTotalAmountMap(map);
    }

    /**
     * 按季度控制，取年度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-05-17 10:52
     * @return
     */
    private Map<String, BigDecimal> getYearBalanceQuarter(BgtJournalBalance bgtJournalBalance) {
        Map<String, BigDecimal> map = balanceMapper.getYearBalanceQuarter(bgtJournalBalance);
        return this.getTotalAmountMap(map);
    }

    /**
     * 按季年控制，取年度值
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-05-17 10:52
     * @return
     */
    private Map<String, BigDecimal> getYearBalanceYear(BgtJournalBalance bgtJournalBalance) {
        Map<String, BigDecimal> map = balanceMapper.getYearBalanceYear(bgtJournalBalance);
        return this.getTotalAmountMap(map);
    }

    private Map<String, BigDecimal> getTotalAmountMap(Map<String, BigDecimal> map) {
        Map<String, BigDecimal> result = new HashMap<>();
        BigDecimal totalPeriodAmount = gCurrentAmount;
        BigDecimal totalPeriodFunctionalAmount = gCurrentFuncAmount;
        BigDecimal totalPeriodQuantity = gCurrentQuantity;
        if (map != null) {
            totalPeriodAmount = map.get("totalPeriodAmount") == null ? totalPeriodAmount
                            : totalPeriodAmount.add(map.get("totalPeriodAmount"));
            totalPeriodFunctionalAmount = map.get("totalPeriodFunctionalAmount") == null ? totalPeriodFunctionalAmount
                            : totalPeriodFunctionalAmount.add(map.get("totalPeriodFunctionalAmount"));
            totalPeriodQuantity = map.get("totalPeriodQuantity") == null ? totalPeriodQuantity
                            : totalPeriodQuantity.add(map.get("totalPeriodQuantity"));
        }
        result.put("totalPeriodAmount", totalPeriodAmount);
        result.put("totalPeriodFunctionalAmount", totalPeriodFunctionalAmount);
        result.put("totalPeriodQuantity", totalPeriodQuantity);
        return result;
    }

    /**
     * 计算其他币种的预算余额的扩展金额
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-28 17:05
     * @return
     */
    private void updateBalanceByOtherCurrency(IRequest request, BgtJournalBalance bgtJournalBalance, String bgtType) {
        List<BgtJournalBalance> bgtJournalBalanceList = balanceMapper.queryByOption(bgtJournalBalance);
        bgtJournalBalanceList =
                bgtJournalBalanceList.stream().filter(balance -> this.filterBgtBalance(bgtJournalBalance, balance, bgtType))
                                        .collect(Collectors.toList());
        bgtJournalBalanceList.forEach(journalBalance -> {
            journalBalanceExtendService.calculateBalanceExtend(request, journalBalance.getBalanceId());
        });
    }

    private boolean filterBgtBalance(BgtJournalBalance bgtJournalBalance, BgtJournalBalance result, String bgtType) {
        boolean flag = false;
        if (bgtJournalBalance.getPeriodName().equals(result.getPeriodName())) {
            flag = true;
            switch (bgtType) {
                case "MONTH":
                    if (bgtJournalBalance.getBgtPeriodNum().compareTo(result.getBgtPeriodNum()) == -1) {
                        flag = true;
                    }
                    break;
                case "QUARTER":
                    if (bgtJournalBalance.getPeriodQuarter().compareTo(result.getPeriodQuarter()) == -1) {
                        flag = true;
                    }
                    break;
                case "QUARTER_MONTH":
                    if (bgtJournalBalance.getPeriodQuarter().compareTo(result.getPeriodQuarter()) == 0
                                    && bgtJournalBalance.getBgtPeriodNum().compareTo(result.getBgtPeriodNum()) == -1) {
                        flag = true;
                    }
                    break;
                default:
                    break;
            }
        }
        return flag;
    }

    /**
     * 更新已存在的数据
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-05-17 14:43
     * @return
     */
    private BgtJournalBalance updateOwnBalance(IRequest request, BgtJournalBalance bgtJournalBalance) {
        balanceMapper.updateBgtJournalBalance(bgtJournalBalance);
        List<BgtJournalBalance> list = balanceMapper.queryByOption(bgtJournalBalance);
        list.forEach(bgtJournalBalance1 -> {
            journalBalanceExtendService.calculateBalanceExtend(request, bgtJournalBalance1.getBalanceId());
        });
        return bgtJournalBalance;
    }
}
