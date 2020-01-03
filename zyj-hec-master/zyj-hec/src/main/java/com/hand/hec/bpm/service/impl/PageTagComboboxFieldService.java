package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageTagComboboxField;
import com.hand.hec.bpm.dto.PageTagDataGuide;
import com.hand.hec.bpm.service.IPageTagComboboxFieldService;
import com.hand.hec.bpm.service.IPageTagDataGuideService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagComboboxFieldService extends BaseServiceImpl<PageTagComboboxField> implements IPageTagComboboxFieldService {
}
