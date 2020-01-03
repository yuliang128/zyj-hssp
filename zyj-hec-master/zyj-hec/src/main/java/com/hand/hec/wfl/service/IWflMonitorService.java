package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hec.wfl.mapper.WflMonitorMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mouse 2019/03/07 14:25
 */
public interface IWflMonitorService {

    List<Map<String, Object>> selectWflInstance(IRequest context, Map param, int page, int pagesize);

    List<Map<String, Object>> selectWflApproveInfo(IRequest context, Long instanceId, int page, int pagesize);
}
