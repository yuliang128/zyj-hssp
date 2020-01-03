package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageTagComboboxMap;
import com.hand.hec.bpm.dto.PageTagLov;
import com.hand.hec.bpm.dto.PageTagLovMap;
import com.hand.hec.bpm.service.IPageTagComboboxMapService;
import com.hand.hec.bpm.service.IPageTagLovMapService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagLovMapService extends BaseServiceImpl<PageTagLovMap> implements IPageTagLovMapService {
}
