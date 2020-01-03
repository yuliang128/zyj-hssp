package com.hand.hec.acp.service;

/**
 * <p>
 * 付款申请单类型关联单据类型service
 * </p>
 *
 * @author guiyuting 2019/04/25 15:49
 */
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpMoPayReqTpRefDoc;

import java.util.List;

public interface IAcpMoPayReqTpRefDocService
                extends IBaseService<AcpMoPayReqTpRefDoc>, ProxySelf<IAcpMoPayReqTpRefDocService> {

    /**
     * 根据付款申请单类型查询关联信息
     *
     * @param acpMoPayReqTpRefDoc
     * @author guiyuting 2019-04-25 15:50
     * @return 符合条件的关联信息
     */
    List<AcpMoPayReqTpRefDoc> queryBymoPayReqType(IRequest request, AcpMoPayReqTpRefDoc acpMoPayReqTpRefDoc, int page,
                    int pageSize);

}
