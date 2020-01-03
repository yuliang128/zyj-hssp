package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageLayoutTab;
import com.hand.hec.bpm.service.IPageLayoutTabService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageLayoutTabServiceImpl extends BaseServiceImpl<PageLayoutTab> implements IPageLayoutTabService {

}