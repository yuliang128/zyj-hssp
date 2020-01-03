package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bpm.dto.DynamicDataLine;
import com.hand.hec.bpm.service.IDynamicDataLineService;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author xxx@hand-china.com
 * @date
 * Description
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DynamicDataLineServiceImpl extends BaseServiceImpl<DynamicDataLine> implements IDynamicDataLineService{

}