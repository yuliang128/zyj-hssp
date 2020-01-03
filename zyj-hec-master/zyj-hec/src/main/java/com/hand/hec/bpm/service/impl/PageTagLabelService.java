package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageTagDatetimepicker;
import com.hand.hec.bpm.dto.PageTagLabel;
import com.hand.hec.bpm.service.IPageTagDatetimepickerService;
import com.hand.hec.bpm.service.IPageTagLabelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagLabelService extends BaseServiceImpl<PageTagLabel> implements IPageTagLabelService {
}
