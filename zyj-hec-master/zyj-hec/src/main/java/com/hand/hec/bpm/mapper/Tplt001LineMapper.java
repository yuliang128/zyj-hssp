package com.hand.hec.bpm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bpm.dto.Tplt001Line;

/**
 * @author xxx@hand-china.com
 * @date Description
 */
public interface Tplt001LineMapper extends Mapper<Tplt001Line> {
    Long queryMaxLineNum(Long headerId);
}