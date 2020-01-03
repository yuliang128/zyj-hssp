package com.hand.hec.csh.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.gld.service.IGldCurrencyService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand.hec.csh.dto.CshBankAccount;
import com.hand.hec.csh.mapper.CshBankAccountMapper;
import com.hand.hec.csh.service.ICshBankAccountService;
import com.hand.hec.fnd.mapper.GldPeriodMapper;
import com.hand.hec.fnd.service.IGldExchangeRateService;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.service.ISysParameterService;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公司付款账户分配账户ServiceImpl
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshBankAccountServiceImpl extends BaseServiceImpl<CshBankAccount> implements ICshBankAccountService {

    @Autowired
    CshBankAccountMapper cshBankAccountMapper;

    @Autowired
    IGldExchangeRateService gldExchangeRateService;

    @Autowired
    ISysParameterService sysParameterService;

    @Autowired
    IGldCurrencyService gldCurrencyService;

    @Autowired
    GldPeriodMapper gldPeriodMapper;

    @Override
    public List<CshBankAccount> queryBankAccounts(Long accEntityId, String bankAccountName, String bankAccountNum, String currencyCode, IRequest request) {
        List<CshBankAccount> cshBankAccountList = cshBankAccountMapper.queryBankAccounts(accEntityId, bankAccountName, bankAccountNum, currencyCode);
        //1.0 获取汇率通用参数

        //1.1 获取汇率类型
        String exchangeRateType = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_DEFAULT_EXCHANGE_RATE_TYPE, null, null, null, accEntityId, null, null, null);
        //1.2 获取转换的币种
        String toCurrency = gldCurrencyService.getAccEntityFunCurrCode(accEntityId);
        //1.3 获取汇率转换日期
        Date exchangeDate = new Date();
        //1.4 获取汇率转换期间
        String exchangePeriodName = gldPeriodMapper.getPeriodName(exchangeDate, accEntityId, null);

        //2.0 注入汇率
        for (CshBankAccount cshBankAccount : cshBankAccountList) {

            //2.1 获取汇率
            BigDecimal exchangeRate = gldExchangeRateService.getExchangeRate(accEntityId, cshBankAccount.getCurrencyCode(), toCurrency, exchangeRateType, exchangeDate, exchangePeriodName, "N");
            cshBankAccount.setExchangeRate(exchangeRate);
        }
        return cshBankAccountList;
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }


    /**
     * <p>获取核算主体下银行信息</p>
     *
     * @param request
     * @param accEntityId 核算主体ID
     * @return List<Map>
     * @author yang.duan 2019/5/30 10:00
     **/
    @Override
    public List<Map> queryBankAccountByAe(IRequest request, Long accEntityId) {
        return cshBankAccountMapper.queryBankAccountByAe(accEntityId);
    }
}