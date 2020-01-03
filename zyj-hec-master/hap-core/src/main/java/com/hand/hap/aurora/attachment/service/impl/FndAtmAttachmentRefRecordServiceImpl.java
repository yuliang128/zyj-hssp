package com.hand.hap.aurora.attachment.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hap.aurora.attachment.dto.FndAtmAttachmentRefRecord;
import com.hand.hap.aurora.attachment.service.IFndAtmAttachmentRefRecordService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndAtmAttachmentRefRecordServiceImpl extends BaseServiceImpl<FndAtmAttachmentRefRecord> implements IFndAtmAttachmentRefRecordService{

}