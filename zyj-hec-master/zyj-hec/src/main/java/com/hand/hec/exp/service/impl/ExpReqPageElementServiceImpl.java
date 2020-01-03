package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpReqPageElement;
import com.hand.hec.exp.service.IExpReqPageElementService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/1/30 11:03
 * Description:ExpReqPageElementServiceImpl
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpReqPageElementServiceImpl extends BaseServiceImpl<ExpReqPageElement> implements IExpReqPageElementService {

}