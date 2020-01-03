package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageLayoutGrid;
import com.hand.hec.bpm.service.IPageLayoutGridService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageLayoutGridServiceImpl extends BaseServiceImpl<PageLayoutGrid> implements IPageLayoutGridService {

}