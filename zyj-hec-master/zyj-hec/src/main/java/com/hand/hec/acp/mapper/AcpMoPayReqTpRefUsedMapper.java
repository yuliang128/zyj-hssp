package com.hand.hec.acp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.acp.dto.AcpMoPayReqTpRefUsed;

import java.util.List;

/**
 * <p>
 * 付款申请单类型关联付款用途mapper
 * </p>
 * 
 * @author guiyuting 2019/04/29 16:47
 */
public interface AcpMoPayReqTpRefUsedMapper extends Mapper<AcpMoPayReqTpRefUsed>{

    /**
     * 我的付款申请单维护页面 - 查询根据付款申请单类型查询付款用途信息
     *
     * @param acpMoPayReqTpRefUsed
     * @author guiyuting 2019-04-29 16:47
     * @return 
     */
    List<AcpMoPayReqTpRefUsed> queryByTypeId(AcpMoPayReqTpRefUsed acpMoPayReqTpRefUsed);

}