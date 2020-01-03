package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageTagLov;
import com.hand.hec.bpm.dto.PageTagNumberfield;
import com.hand.hec.bpm.service.IPageTagLovService;
import com.hand.hec.bpm.service.IPageTagNumberfieldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagNumberfieldService extends BaseServiceImpl<PageTagNumberfield> implements IPageTagNumberfieldService {
}
