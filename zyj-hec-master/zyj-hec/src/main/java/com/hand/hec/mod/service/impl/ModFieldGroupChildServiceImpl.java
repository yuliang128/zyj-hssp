package com.hand.hec.mod.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.mod.dto.ModFieldGroupChild;
import com.hand.hec.mod.service.IModFieldGroupChildService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class ModFieldGroupChildServiceImpl extends BaseServiceImpl<ModFieldGroupChild> implements IModFieldGroupChildService{

    @Override
    public ModFieldGroupChild insertSelective(IRequest request, ModFieldGroupChild record) {
        record.setCreatedBy(request.getUserId());
        record.setCreationDate(new Date());
        return super.insertSelective(request, record);
    }
}