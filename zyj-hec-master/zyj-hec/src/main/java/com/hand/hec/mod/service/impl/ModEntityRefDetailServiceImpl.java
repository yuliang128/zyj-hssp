package com.hand.hec.mod.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.mod.dto.ModEntityRefDetail;
import com.hand.hec.mod.service.IModEntityRefDetailService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ModEntityRefDetailServiceImpl extends BaseServiceImpl<ModEntityRefDetail> implements IModEntityRefDetailService{

}