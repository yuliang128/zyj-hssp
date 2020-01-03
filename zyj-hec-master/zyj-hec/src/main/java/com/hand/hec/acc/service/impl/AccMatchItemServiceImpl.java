package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccMatchItem;
import com.hand.hec.acc.service.IAccMatchItemService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccMatchItemServiceImpl extends BaseServiceImpl<AccMatchItem> implements IAccMatchItemService{

}