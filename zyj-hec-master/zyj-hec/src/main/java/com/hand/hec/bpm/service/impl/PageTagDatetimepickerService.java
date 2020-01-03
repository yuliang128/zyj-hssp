package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageTagDatepicker;
import com.hand.hec.bpm.dto.PageTagDatetimepicker;
import com.hand.hec.bpm.service.IPageTagDatepickerService;
import com.hand.hec.bpm.service.IPageTagDatetimepickerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagDatetimepickerService extends BaseServiceImpl<PageTagDatetimepicker> implements IPageTagDatetimepickerService {
}
