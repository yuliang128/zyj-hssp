package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageLayoutForm;
import com.hand.hec.bpm.mapper.PageLayoutFormMapper;
import com.hand.hec.bpm.service.IPageLayoutFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageLayoutFormServiceImpl extends BaseServiceImpl<PageLayoutForm> implements IPageLayoutFormService {
    @Autowired
    PageLayoutFormMapper bpmPageLayoutFormMapper;

}