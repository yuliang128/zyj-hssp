package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.mapper.CshPaymentMethodAsgnMoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentMethodAsgnMo;
import com.hand.hec.csh.service.ICshPaymentMethodAsgnMoService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentMethodAsgnMoServiceImpl extends BaseServiceImpl<CshPaymentMethodAsgnMo> implements ICshPaymentMethodAsgnMoService {
    @Autowired
    CshPaymentMethodAsgnMoMapper mapper;
    @Override
    public List<CshPaymentMethodAsgnMo> query(IRequest request, CshPaymentMethodAsgnMo cshPaymentMethodAsgnMo, int page, int pageSize) {
        return mapper.query(cshPaymentMethodAsgnMo);
    }
}