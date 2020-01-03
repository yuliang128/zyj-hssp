package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtCheckLog;
import com.hand.hec.bgt.service.IBgtCheckLogService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算检查日志ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtCheckLogServiceImpl extends BaseServiceImpl<BgtCheckLog> implements IBgtCheckLogService{

}