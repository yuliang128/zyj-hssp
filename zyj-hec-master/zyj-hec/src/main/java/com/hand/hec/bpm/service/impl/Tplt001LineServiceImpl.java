package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.mapper.Tplt001LineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bpm.dto.Tplt001Line;
import com.hand.hec.bpm.service.ITplt001LineService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xxx@hand-china.com
 * @date Description
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class Tplt001LineServiceImpl extends BaseServiceImpl<Tplt001Line> implements ITplt001LineService {
    @Autowired
    Tplt001LineMapper lineMapper;

    @Override
    public Long queryMaxLineNum(Long headerId) {
        return lineMapper.queryMaxLineNum(headerId);
    }
}