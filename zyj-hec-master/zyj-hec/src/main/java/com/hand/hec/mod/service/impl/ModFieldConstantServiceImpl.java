package com.hand.hec.mod.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.mod.dto.ModFieldConstant;
import com.hand.hec.mod.service.IModFieldConstantService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class ModFieldConstantServiceImpl extends BaseServiceImpl<ModFieldConstant> implements IModFieldConstantService{

    @Override
    public ModFieldConstant insertSelective(IRequest request, ModFieldConstant record) {
        record.setCreatedBy(request.getUserId());
        record.setCreationDate(new Date());
        return super.insertSelective(request, record);
    }
}