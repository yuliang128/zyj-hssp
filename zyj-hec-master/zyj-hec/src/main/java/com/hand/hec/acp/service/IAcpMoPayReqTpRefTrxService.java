package com.hand.hec.acp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpMoPayReqTpRefTrx;

import java.util.List;
/**
 * <p>
 * 付款申请单类型关联现金事务类型service
 * </p>
 *
 * @author guiyuting 2019/04/29 18:36
 */

public interface IAcpMoPayReqTpRefTrxService
                extends IBaseService<AcpMoPayReqTpRefTrx>, ProxySelf<IAcpMoPayReqTpRefTrxService> {

    /**
     * 我的付款申请单维护页面 - 根据付款申请单类型查询现金事务类型信息
     *
     * @param acpMoPayReqTpRefTrx
     * @author guiyuting 2019-04-29 18:37
     * @return
     */
    List<AcpMoPayReqTpRefTrx> queryByTypeId(IRequest request, AcpMoPayReqTpRefTrx acpMoPayReqTpRefTrx);

}
