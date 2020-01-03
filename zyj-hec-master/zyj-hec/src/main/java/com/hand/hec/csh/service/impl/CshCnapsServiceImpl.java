package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshCnaps;
import com.hand.hec.csh.service.ICshCnapsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * CshCnapsServiceImpl
 * </p>
 *
 * @author guiyu 2019/01/29 14:22
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class CshCnapsServiceImpl extends BaseServiceImpl<CshCnaps> implements ICshCnapsService {

}