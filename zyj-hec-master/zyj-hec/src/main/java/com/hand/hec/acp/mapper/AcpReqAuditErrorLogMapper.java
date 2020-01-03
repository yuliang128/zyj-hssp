package com.hand.hec.acp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.acp.dto.AcpReqAuditErrorLog;

import java.util.List;

public interface AcpReqAuditErrorLogMapper extends Mapper<AcpReqAuditErrorLog>{

    List<AcpReqAuditErrorLog> queryBySessionId();

}