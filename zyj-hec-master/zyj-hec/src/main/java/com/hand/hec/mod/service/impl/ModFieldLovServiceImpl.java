package com.hand.hec.mod.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.mod.dto.ModFieldLov;
import com.hand.hec.mod.service.IModFieldLovService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class ModFieldLovServiceImpl extends BaseServiceImpl<ModFieldLov> implements IModFieldLovService {


    @Override
    public ModFieldLov insertSelective(IRequest request, ModFieldLov record) {
        record.setCreatedBy(request.getUserId());
        record.setCreationDate(new Date());
        return super.insertSelective(request, record);
    }
}
