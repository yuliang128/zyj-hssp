package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageTagCheckbox;
import com.hand.hec.bpm.dto.PageTagCombobox;
import com.hand.hec.bpm.service.IPageTagCheckboxService;
import com.hand.hec.bpm.service.IPageTagComboboxService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagComboboxService extends BaseServiceImpl<PageTagCombobox> implements IPageTagComboboxService {
}
