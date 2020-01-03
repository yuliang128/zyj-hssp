package com.hand.hec.acp.service;

/**
 * <p>
 * 付款申请单类型关联公司service
 * </p>
 * 
 * @author guiyuting 2019/04/26 10:35
 */
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpMoPayReqTpAsgnCom;

import java.util.List;

public interface IAcpMoPayReqTpAsgnComService
                extends IBaseService<AcpMoPayReqTpAsgnCom>, ProxySelf<IAcpMoPayReqTpAsgnComService> {
    /**
     * 付款申请单类型 批量分配公司
     *
     * @param list
     * @author guiyuting 2019-05-23 15:54
     * @return 
     */
    List<AcpMoPayReqTpAsgnCom> batchAssign(IRequest request,@StdWho List<AcpMoPayReqTpAsgnCom> list);

}
