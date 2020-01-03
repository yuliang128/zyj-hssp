package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtCheckScheduleLog;
import com.hand.hec.bgt.service.IBgtCheckScheduleLogService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算执行率日志ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtCheckScheduleLogServiceImpl extends BaseServiceImpl<BgtCheckScheduleLog> implements IBgtCheckScheduleLogService{

}