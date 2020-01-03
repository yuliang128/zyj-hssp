package com.hand.hec.fnd.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndMoClass;
import com.hand.hec.fnd.service.IFndMoClassService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndMoClassServiceImpl extends BaseServiceImpl<FndMoClass> implements IFndMoClassService{

}