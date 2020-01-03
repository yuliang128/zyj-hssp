package com.hand.hec.acp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.acp.mapper.AcpMoPayReqTpRefDocMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.acp.dto.AcpMoPayReqTpRefDoc;
import com.hand.hec.acp.service.IAcpMoPayReqTpRefDocService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AcpMoPayReqTpRefDocServiceImpl extends BaseServiceImpl<AcpMoPayReqTpRefDoc>
                implements IAcpMoPayReqTpRefDocService {

    @Autowired
    private AcpMoPayReqTpRefDocMapper moPayReqTpRefDocMapper;

    @Override
    public List<AcpMoPayReqTpRefDoc> queryBymoPayReqType(IRequest request, AcpMoPayReqTpRefDoc acpMoPayReqTpRefDoc,
                    int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return moPayReqTpRefDocMapper.queryBymoPayReqType(acpMoPayReqTpRefDoc);
    }
}
