package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageTagComboboxMap;
import com.hand.hec.bpm.service.IPageTagComboboxMapService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagComboboxMapService extends BaseServiceImpl<PageTagComboboxMap> implements IPageTagComboboxMapService {
}
