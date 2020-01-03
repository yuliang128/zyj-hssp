package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpEmployeeAttachment;
import com.hand.hec.exp.service.IExpEmployeeAttachmentService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpEmployeeAttachmentServiceImpl extends BaseServiceImpl<ExpEmployeeAttachment> implements IExpEmployeeAttachmentService{

}