package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bpm.dto.Tplt001Header;
import com.hand.hec.bpm.service.ITplt001HeaderService;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author xxx@hand-china.com
 * @date
 * Description
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class Tplt001HeaderServiceImpl extends BaseServiceImpl<Tplt001Header> implements ITplt001HeaderService{

}