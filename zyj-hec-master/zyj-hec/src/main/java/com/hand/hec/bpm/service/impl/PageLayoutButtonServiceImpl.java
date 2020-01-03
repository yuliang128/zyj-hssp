package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageButton;
import com.hand.hec.bpm.dto.PageLayoutButton;
import com.hand.hec.bpm.service.IPageButtonService;
import com.hand.hec.bpm.service.IPageLayoutButtonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageLayoutButtonServiceImpl extends BaseServiceImpl<PageLayoutButton> implements IPageLayoutButtonService {

}