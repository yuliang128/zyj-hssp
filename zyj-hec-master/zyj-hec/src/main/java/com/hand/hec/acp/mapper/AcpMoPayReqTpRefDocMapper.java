package com.hand.hec.acp.mapper;

/**
 * <p>
 * 付款申请单类型关联单据类型mapper
 * </p>
 * 
 * @author guiyuting 2019/04/25 15:49
 */
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.acp.dto.AcpMoPayReqTpRefDoc;

import java.util.List;

public interface AcpMoPayReqTpRefDocMapper extends Mapper<AcpMoPayReqTpRefDoc>{

    /**
     * 根据付款申请单类型查询关联信息
     *
     * @param acpMoPayReqTpRefDoc
     * @author guiyuting 2019-04-25 15:50
     * @return 符合条件的关联信息
     */
    List<AcpMoPayReqTpRefDoc> queryBymoPayReqType(AcpMoPayReqTpRefDoc acpMoPayReqTpRefDoc);

}