package com.hand.hec.csh.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshRepaymentRegisterDist;
import com.hand.hec.csh.dto.CshTransactionHeader;
import com.hand.hec.csh.dto.CshTransactionLine;
import com.hand.hec.csh.mapper.CshRepaymentRegisterDistMapper;
import com.hand.hec.csh.service.ICshRepaymentRegisterDistService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshRepaymentRegisterDistServiceImpl extends BaseServiceImpl<CshRepaymentRegisterDist> implements ICshRepaymentRegisterDistService {

    @Autowired
    private CshRepaymentRegisterDistMapper distMapper;

    @Override
    public List<CshRepaymentRegisterDist> baseSelect(IRequest request, Long registerLnId) {
        return distMapper.baseSelect(registerLnId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map> queryTrxById(Long reqLnId, String paymentNum, String transactionNum) {
        return distMapper.queryTrxById(reqLnId, transactionNum, paymentNum).parallelStream().peek(map -> {
            if (map.get(CshTransactionHeader.FIELD_TRANSACTION_DATE) != null) {
                map.put(CshTransactionHeader.FIELD_TRANSACTION_DATE, DateUtils.date2Str((Date) map.get(CshTransactionHeader.FIELD_TRANSACTION_DATE), BaseConstants.DATE_FORMAT));
            }
            if (map.get(CshTransactionLine.FIELD_TRANSACTION_AMOUNT) != null || map.get(CshTransactionLine.FIELD_TRANSACTION_AMOUNT) != BigDecimal.ZERO) {
                String amount = map.get(CshTransactionLine.FIELD_TRANSACTION_AMOUNT).toString();
                map.put(CshTransactionLine.FIELD_TRANSACTION_AMOUNT, amount.substring(0, amount.indexOf('.') + 3));
            }
            if (map.get(CshRepaymentRegisterDist.FIELD_BALANCE) != null || map.get(CshRepaymentRegisterDist.FIELD_BALANCE) != BigDecimal.ZERO) {
                String amount = map.get(CshRepaymentRegisterDist.FIELD_BALANCE).toString();
                map.put(CshRepaymentRegisterDist.FIELD_BALANCE, amount.substring(0, amount.indexOf('.') + 3));
            }
        }).collect(Collectors.toList());
    }

    @Override
    public List<CshRepaymentRegisterDist> batchUpdate(IRequest request, List<CshRepaymentRegisterDist> list) {
        return super.batchUpdate(request, list.parallelStream().peek(record->{
            record.setRepaymentFlag(BaseConstants.NO);
            record.setReverseFlag(BaseConstants.NO);
        }).collect(Collectors.toList()));
    }
}