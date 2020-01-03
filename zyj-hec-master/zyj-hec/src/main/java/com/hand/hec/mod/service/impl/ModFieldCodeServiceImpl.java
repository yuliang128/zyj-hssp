package com.hand.hec.mod.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.mod.dto.ModFieldCode;
import com.hand.hec.mod.service.IModFieldCodeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class ModFieldCodeServiceImpl extends BaseServiceImpl<ModFieldCode> implements IModFieldCodeService{

    @Override
    public ModFieldCode insertSelective(IRequest request, ModFieldCode record) {
        record.setCreatedBy(request.getUserId());
        record.setCreationDate(new Date());
        return super.insertSelective(request, record);
    }
}