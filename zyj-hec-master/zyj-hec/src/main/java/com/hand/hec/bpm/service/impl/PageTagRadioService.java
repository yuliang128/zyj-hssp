package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageTagNumberfield;
import com.hand.hec.bpm.dto.PageTagRadio;
import com.hand.hec.bpm.service.IPageTagNumberfieldService;
import com.hand.hec.bpm.service.IPageTagRadioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagRadioService extends BaseServiceImpl<PageTagRadio> implements IPageTagRadioService {
}
