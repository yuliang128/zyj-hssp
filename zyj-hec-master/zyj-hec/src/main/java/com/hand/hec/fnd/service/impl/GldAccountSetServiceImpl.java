package com.hand.hec.fnd.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.GldAccountSet;
import com.hand.hec.fnd.service.IGldAccountSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * GldAccountSetServiceImpl
 * </p>
 * 
 * @author guiyu 2019/01/08 15:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldAccountSetServiceImpl extends BaseServiceImpl<GldAccountSet> implements IGldAccountSetService {

}
