package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageTagBasic;
import com.hand.hec.bpm.service.IPageTagBasicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagBasicService extends BaseServiceImpl<PageTagBasic> implements IPageTagBasicService {
}
