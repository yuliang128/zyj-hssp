package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageTagCombobox;
import com.hand.hec.bpm.dto.PageTagDatepicker;
import com.hand.hec.bpm.service.IPageTagComboboxService;
import com.hand.hec.bpm.service.IPageTagDatepickerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagDatepickerService extends BaseServiceImpl<PageTagDatepicker> implements IPageTagDatepickerService {
}
