package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.gld.service.IGldCurrencyService;
import com.hand.hap.mybatis.entity.EntityField;
import com.hand.hap.system.dto.DTOClassInfo;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.*;
import com.hand.hec.bgt.exception.BgtJournalHeaderException;
import com.hand.hec.bgt.service.*;
import com.hand.hec.fnd.service.IGldExchangeRateService;
import com.hand.hec.fnd.service.IGldExchangerateTypeService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 预算余额币种扩展ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtJournalBalanceExtendServiceImpl extends BaseServiceImpl<BgtJournalBalanceExtend>
                implements IBgtJournalBalanceExtendService {
    @Autowired
    private IBgtJournalBalanceService journalBalanceService;

    @Autowired
    private IBgtPeriodService periodService;

    @Autowired
    private IBgtJournalBalanceExtendBakService balanceExtendBakService;

    @Autowired
    private IGldExchangerateTypeService exchangerateTypeService;

    @Autowired
    private IBgtCenterService bgtCenterService;

    @Autowired
    private IGldExchangeRateService exchangeRateService;

    @Autowired
    private IBgtEntityService bgtEntityService;

    @Autowired
    private IBgtOrganizationService bgtOrganizationService;

    @Autowired
    private IGldCurrencyService gldCurrencyService;

    private List<BgtBudgetCurrencyTmp> budgetCurrencyTmpList = new ArrayList<>();

    @Override
    public void calculateBalanceExtend(IRequest request, Long balanceId) {
        BigDecimal vExchangePeriodAmount = null;
        BigDecimal vExchangeQuarterAmount = null;
        BigDecimal vExchangeYearAmount = null;
        String vCurrentCurrencyCode = "";
        // 新版本计算之前，先归档老版本数据
        this.archiveBalanceExtend(request, balanceId);

        BgtJournalBalance bgtJournalBalance = journalBalanceService.selectByPrimaryKey(request,
                        BgtJournalBalance.builder().balanceId(balanceId).build());
        Map<String, Object> exchangeMap = this.getBalanceExchangeDate(bgtJournalBalance);
        // 初始化关联的币种汇率
        this.initCurrencyExchange(request, bgtJournalBalance, (Date) exchangeMap.get("exchangeDate"),
                        (String) exchangeMap.get("exchangePeriodName"));
        for (BgtBudgetCurrencyTmp bgtBudgetCurrencyTmp : budgetCurrencyTmpList) {
            vCurrentCurrencyCode = bgtBudgetCurrencyTmp.getToCurrencyCode();
            // 获取期间汇兑金额
            if (bgtJournalBalance.getPeriodAmount() != null) {
                vExchangePeriodAmount = this.getExchangeAmount(request, bgtJournalBalance.getPeriodAmount(),
                                bgtBudgetCurrencyTmp.getExchangeRate(), bgtBudgetCurrencyTmp.getToCurrencyCode());
            }
            // 获取季度汇兑金额
            if (bgtJournalBalance.getQuarterAmount() != null) {
                vExchangeQuarterAmount = this.getExchangeAmount(request, bgtJournalBalance.getQuarterAmount(),
                                bgtBudgetCurrencyTmp.getExchangeRate(), bgtBudgetCurrencyTmp.getToCurrencyCode());
            }
            // 获取年度汇兑金额
            if (bgtJournalBalance.getYearAmount() != null) {
                vExchangeYearAmount = this.getExchangeAmount(request, bgtJournalBalance.getYearAmount(),
                                bgtBudgetCurrencyTmp.getExchangeRate(), bgtBudgetCurrencyTmp.getToCurrencyCode());
            }
            if (vExchangePeriodAmount == null && vExchangeQuarterAmount == null && vExchangeYearAmount == null) {
                throw new BgtJournalHeaderException(BgtJournalHeaderException.EXCHANGE_AMOUNT_NULL,null);
            }
            BgtJournalBalanceExtend journalBalanceExtend = new BgtJournalBalanceExtend();
            journalBalanceExtend.setBalanceId(bgtJournalBalance.getBalanceId());
            journalBalanceExtend.setPeriodAmount(vExchangePeriodAmount);
            journalBalanceExtend.setQuarterAmount(vExchangeQuarterAmount);
            journalBalanceExtend.setYearAmount(vExchangeYearAmount);
            journalBalanceExtend.setCurrencyCode(bgtBudgetCurrencyTmp.getToCurrencyCode());
            journalBalanceExtend.setExchangeRateType(bgtBudgetCurrencyTmp.getExchangeRateType());
            journalBalanceExtend.setExchangeRate(bgtBudgetCurrencyTmp.getExchangeRate());
            journalBalanceExtend.setExchangeDate(bgtBudgetCurrencyTmp.getExchangeDate());
            journalBalanceExtend.setExchangePeriodName(bgtBudgetCurrencyTmp.getExchangePeriodName());
            self().insertSelective(request, journalBalanceExtend);
        }
    }

    private Map<String, Object> getBalanceExchangeDate(BgtJournalBalance bgtJournalBalance) {
        Map<String, Object> map = new HashMap<>();
        Date exchangeDate = DateUtils.getCurrentDate();
        String exchangePeriodName = bgtJournalBalance.getPeriodName();
        BgtPeriod bgtPeriod = BgtPeriod.builder().bgtOrgId(bgtJournalBalance.getBgtOrgId())
                        .periodYear(bgtJournalBalance.getPeriodYear()).build();
        if (bgtJournalBalance.getPeriodName() != null) {
            bgtPeriod.setQuarterNum(bgtJournalBalance.getPeriodQuarter());
            exchangePeriodName = periodService.queryQuarterPeriodName(bgtPeriod);
        } else if (bgtJournalBalance.getPeriodQuarter() != null && bgtJournalBalance.getPeriodYear() != null) {
            exchangePeriodName = periodService.queryYearPeriodName(bgtPeriod);
        }
        map.put("exchangeDate", exchangeDate);
        map.put("exchangePeriodName", exchangePeriodName);
        return map;
    }

    /**
     * 初始化关联的币种汇率
     *
     * @param bgtJournalBalance 预算余额信息
     * @param exchangeDate 汇率日期
     * @param exchangePeriodName 汇率期间
     * @author guiyuting 2019-03-29 10:44
     * @return
     */
    private void initCurrencyExchange(IRequest request, BgtJournalBalance bgtJournalBalance, Date exchangeDate,
                    String exchangePeriodName) {
        // 清除历史数据
        this.budgetCurrencyTmpList = new ArrayList<>();
        // 获取预算组织的汇率类型
        String exchangeRateType = exchangerateTypeService.getRateTypeByBgtOrg(bgtJournalBalance.getBgtOrgId());
        // 当前币种对应汇率，汇率类型和标价方法为空
        this.tmpCurrencyExchange(bgtJournalBalance.getCurrencyCode(), bgtJournalBalance.getCurrencyCode(), null,
                        exchangeDate, exchangePeriodName, new BigDecimal(1));
        // 获取当前明细预算中心的预算币种
        if (bgtJournalBalance.getBgtCenterId() != null) {
            BgtCenter bgtCenter = bgtCenterService.selectByPrimaryKey(request,
                            BgtCenter.builder().centerId(bgtJournalBalance.getBgtCenterId()).build());
            if (bgtCenter.getCurrencyCode() != null) {
                this.initTmpCurrencyExchange(bgtJournalBalance, bgtCenter.getCurrencyCode(), exchangeRateType,
                                exchangeDate, exchangePeriodName);
            }
        }
        // 获取当前明细预算实体的预算币种
        if (bgtJournalBalance.getBgtEntityId() != null) {
            BgtEntity bgtEntity = bgtEntityService.selectByPrimaryKey(request,
                            BgtEntity.builder().entityId(bgtJournalBalance.getBgtEntityId()).build());
            if (bgtEntity.getCurrencyCode() != null) {
                this.initTmpCurrencyExchange(bgtJournalBalance, bgtEntity.getCurrencyCode(), exchangeRateType,
                                exchangeDate, exchangePeriodName);
            }

        }

        // 获取当前预算组织的预算币种
        BgtOrganization bgtOrganization = bgtOrganizationService.selectByPrimaryKey(request,
                        BgtOrganization.builder().bgtOrgId(bgtJournalBalance.getBgtOrgId()).build());
        if (bgtOrganization.getCurrencyCode() != null) {
            this.initTmpCurrencyExchange(bgtJournalBalance, bgtOrganization.getCurrencyCode(), exchangeRateType,
                            exchangeDate, exchangePeriodName);
        }

        // 循环当前明细中心上的所有版块中心并找到对应的预算币种
        List<BgtCenter> centerList = bgtCenterService.queryDetailCenter(bgtJournalBalance.getBgtCenterId());
        for (BgtCenter bgtCenter : centerList) {
            this.initTmpCurrencyExchange(bgtJournalBalance, bgtCenter.getCurrencyCode(), exchangeRateType, exchangeDate,
                            exchangePeriodName);
        }
        // 循环当前预算实体上的所有版块并找到对应的预算币种
        List<BgtEntity> entityList = bgtEntityService.queryDetailEntity(bgtJournalBalance.getBgtEntityId());
        for (BgtEntity bgtEntity : entityList) {
            this.initTmpCurrencyExchange(bgtJournalBalance, bgtEntity.getCurrencyCode(), exchangeRateType, exchangeDate,
                            exchangePeriodName);
        }
    }

    /**
     * 初始化关联的币种汇率时插入临时表
     *
     * @param bgtJournalBalance
     * @author guiyuting 2019-03-29 16:31
     * @return
     */
    private void initTmpCurrencyExchange(BgtJournalBalance bgtJournalBalance, String currencyCode,
                    String exchangeRateType, Date exchangeDate, String exchangePeriodName) {
        boolean existsFlag = this.checkIfExistsInTmp(currencyCode);
        if (!existsFlag && !bgtJournalBalance.getCurrencyCode().equals(currencyCode)) {
            BigDecimal vExchangeRate = exchangeRateService.getExchangeRate(null, bgtJournalBalance.getCurrencyCode(),
                            currencyCode, exchangeRateType, exchangeDate, exchangePeriodName, BaseConstants.NO);
            this.tmpCurrencyExchange(bgtJournalBalance.getCurrencyCode(), currencyCode, exchangeRateType, exchangeDate,
                            exchangePeriodName, vExchangeRate);
        }
    }

    private boolean checkIfExistsInTmp(String currencyCode) {
        boolean existsFlag = false;
        for (BgtBudgetCurrencyTmp bgtBudgetCurrencyTmp : this.budgetCurrencyTmpList) {
            if (bgtBudgetCurrencyTmp.getToCurrencyCode().equals(currencyCode)) {
                existsFlag = true;
                break;
            }
        }
        return existsFlag;
    }

    /**
     * 归档预算余额扩展表
     *
     * @param balanceId 预算余额扩展ID
     * @author guiyuting 2019-03-29 10:27
     * @return
     */
    private void archiveBalanceExtend(IRequest request, Long balanceId) {
        List<BgtJournalBalanceExtend> journalBalanceExtendList =
                        self().select(request, BgtJournalBalanceExtend.builder().balanceId(balanceId).build(), 0, 0);
        List<BgtJournalBalanceExtendBak> bakList = new ArrayList<>();
        journalBalanceExtendList.forEach(bgtJournalBalanceExtend -> {
            BgtJournalBalanceExtendBak balanceExtendBak = new BgtJournalBalanceExtendBak();
            BeanUtils.copyProperties(bgtJournalBalanceExtend, balanceExtendBak);
            bgtJournalBalanceExtend.set__status(DTOStatus.ADD);

        });
        balanceExtendBakService.batchUpdate(request, bakList);
        self().batchDelete(journalBalanceExtendList);
    }

    private BgtBudgetCurrencyTmp tmpCurrencyExchange(String fromCurrencyCode, String toCurrencyCode,
                    String exchangeRateType, Date exchangeDate, String exchangePeriodName, BigDecimal exchangeRate) {
        BgtBudgetCurrencyTmp budgetCurrencyTmp = new BgtBudgetCurrencyTmp();
        boolean existsFlag = false;
        for (BgtBudgetCurrencyTmp bgtBudgetCurrencyTmp : this.budgetCurrencyTmpList) {
            if (bgtBudgetCurrencyTmp.getFromCurrencyCode().equals(fromCurrencyCode)
                            && bgtBudgetCurrencyTmp.getToCurrencyCode().equals(toCurrencyCode)
                            && bgtBudgetCurrencyTmp.getExchangeDate().getTime() == exchangeDate.getTime()) {
                budgetCurrencyTmp = bgtBudgetCurrencyTmp;
                existsFlag = true;
                break;
            }
        }
        if (!existsFlag) {
            budgetCurrencyTmp.setFromCurrencyCode(fromCurrencyCode);
            budgetCurrencyTmp.setToCurrencyCode(toCurrencyCode);
            budgetCurrencyTmp.setExchangeRateType(exchangeRateType);
            budgetCurrencyTmp.setExchangeRate(exchangeRate);
            budgetCurrencyTmp.setExchangeDate(exchangeDate);
            budgetCurrencyTmp.setExchangePeriodName(exchangePeriodName);
            this.budgetCurrencyTmpList.add(budgetCurrencyTmp);
        }
        return budgetCurrencyTmp;
    }

    /**
     * 获取兑换金额
     *
     * @param amount 金额
     * @param exchangeRate 汇率
     * @param currencyCode 币种
     * @author guiyuting 2019-03-29 16:32
     * @return
     */
    private BigDecimal getExchangeAmount(IRequest request, BigDecimal amount, BigDecimal exchangeRate,
                    String currencyCode) {
        BigDecimal exchangeAmount = new BigDecimal(0);
        if (exchangeRate.intValue() == 1) {
            exchangeAmount = amount;
        } else {
            exchangeAmount = amount.multiply(exchangeRate);
            List<GldCurrency> gldCurrencyList = gldCurrencyService.select(request,
                            GldCurrency.builder().currencyCode(currencyCode).build(), 0, 0);

            Long precision = gldCurrencyList.get(0).getTransactionPrecision();
            exchangeAmount = exchangeAmount.setScale(precision.intValue(), BigDecimal.ROUND_HALF_UP);
        }
        return exchangeAmount;
    }

}
