package com.hand.hec.cont.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.cont.dto.ConCashTrxPmtScheduleLn;
import com.hand.hec.cont.dto.ConContractHeader;
import com.hand.hec.cont.mapper.ConCashTrxPmtScheduleLnMapper;
import com.hand.hec.cont.mapper.ConContractHeaderMapper;
import com.hand.hec.cont.service.IConCashTrxPmtScheduleLnService;
import com.hand.hec.csh.exception.CshTransactionException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConCashTrxPmtScheduleLnServiceImpl extends BaseServiceImpl<ConCashTrxPmtScheduleLn>
                implements IConCashTrxPmtScheduleLnService {

    @Autowired
    private ConCashTrxPmtScheduleLnMapper conCashTrxPmtScheduleLnMapper;

    @Autowired
    private ConContractHeaderMapper conContractHeaderMapper;

    /**
     * 校验合同资金计划
     *
     * @author Tagin
     * @date 2019-04-03 19:13
     * @param transactionLineId 现金事务行ID
     * @return java.lang.String
     * @version 1.0
     **/
    @Override
    public String validateConSchedule(Long transactionLineId) {
        // 获取资金计划 [合同资金计划收款方对象与现金事务收款方对象是否一致]
        List<ConCashTrxPmtScheduleLn> conCashTrxPmtScheduleLnList =
                        conCashTrxPmtScheduleLnMapper.queryScheduleByTrx(transactionLineId);
        if (CollectionUtils.isNotEmpty(conCashTrxPmtScheduleLnList)) {
            return CshTransactionException.CON_PAYEE_ERROR;
        }
        // 获取合同头 [合同币种与现金事务币种是否一致]
        List<ConContractHeader> conContractHeaderList =
                        conContractHeaderMapper.queryContractHeaderByTrx(transactionLineId);
        if (CollectionUtils.isNotEmpty(conContractHeaderList)) {
            return CshTransactionException.CON_CURRENCY_ERROR;
        }
        return null;
    }

}
