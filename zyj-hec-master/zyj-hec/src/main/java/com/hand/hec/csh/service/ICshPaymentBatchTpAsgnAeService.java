package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentBatchTpAsgnAe;

import java.util.List;

public interface ICshPaymentBatchTpAsgnAeService
                extends IBaseService<CshPaymentBatchTpAsgnAe>, ProxySelf<ICshPaymentBatchTpAsgnAeService> {
    /**
     * 查询未分配的核算主体
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/4 17:21
     * @param iRequest 请求上下文
     * @param typeId 付款批类型Id
     * @param magOrgId 管理组织ID
     * @param page
     * @param pageSize
     * @return List<CshPaymentBatchTpAsgnAe>
     * @Version 1.0
     **/
    List<CshPaymentBatchTpAsgnAe> queryCanAsgEntity(IRequest iRequest, Long typeId, Long magOrgId, int page,
                    int pageSize);
}
