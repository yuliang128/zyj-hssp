package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageTagRadio;
import com.hand.hec.bpm.dto.PageTagTextfield;
import com.hand.hec.bpm.service.IPageTagRadioService;
import com.hand.hec.bpm.service.IPageTagTextfieldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagTextfieldService extends BaseServiceImpl<PageTagTextfield> implements IPageTagTextfieldService {
}
