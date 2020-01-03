package com.hand.hec.mod.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.mod.dto.ModFieldProgram;
import com.hand.hec.mod.service.IModFieldProgramService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class ModFieldProgramServiceImpl extends BaseServiceImpl<ModFieldProgram> implements IModFieldProgramService{

    @Override
    public ModFieldProgram insertSelective(IRequest request, ModFieldProgram record) {
        record.setCreatedBy(request.getUserId());
        record.setCreationDate(new Date());
        return super.insertSelective(request, record);
    }
}