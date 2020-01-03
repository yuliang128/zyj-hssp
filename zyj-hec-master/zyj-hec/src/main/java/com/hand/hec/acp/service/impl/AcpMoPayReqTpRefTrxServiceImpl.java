package com.hand.hec.acp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.acp.mapper.AcpMoPayReqTpRefTrxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.acp.dto.AcpMoPayReqTpRefTrx;
import com.hand.hec.acp.service.IAcpMoPayReqTpRefTrxService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AcpMoPayReqTpRefTrxServiceImpl extends BaseServiceImpl<AcpMoPayReqTpRefTrx>
                implements IAcpMoPayReqTpRefTrxService {

    @Autowired
    private AcpMoPayReqTpRefTrxMapper mapper;

    @Override
    public List<AcpMoPayReqTpRefTrx> queryByTypeId(IRequest request, AcpMoPayReqTpRefTrx acpMoPayReqTpRefTrx) {
        return mapper.queryByTypeId(acpMoPayReqTpRefTrx);
    }
}
