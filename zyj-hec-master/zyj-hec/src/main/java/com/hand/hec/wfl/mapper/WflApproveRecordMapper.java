package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflApproveRecord;
import org.apache.ibatis.annotations.Param;

public interface WflApproveRecordMapper extends Mapper<WflApproveRecord> {

    Long getLastApproveRecordId(@Param(value = "instanceId") Long instanceId);
}
