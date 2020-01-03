package com.hand.hec.csh.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.mapper.CshMoPayReqRefEmpGpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshMoPayReqRefEmpGp;
import com.hand.hec.csh.service.ICshMoPayReqRefEmpGpService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshMoPayReqRefEmpGpServiceImpl extends BaseServiceImpl<CshMoPayReqRefEmpGp> implements ICshMoPayReqRefEmpGpService {
    @Autowired
    CshMoPayReqRefEmpGpMapper mapper;

    @Override
    public List<CshMoPayReqRefEmpGp> queryByTypeId(IRequest request, CshMoPayReqRefEmpGp condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return mapper.queryByTypeId(condition);
    }
}