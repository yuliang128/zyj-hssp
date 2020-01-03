package com.hand.hap.sys.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hap.sys.dto.SystemHelp;
import com.hand.hap.sys.service.ISystemHelpService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SystemHelpServiceImpl extends BaseServiceImpl<SystemHelp> implements ISystemHelpService{

}