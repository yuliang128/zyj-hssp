package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentMethodAsgnMo;

import java.util.List;
/**
 * <p>
 * 付款方式分配管理组织 Service 接口
 * </p>
 *
 * @author yang.cai@hand-china.com 2019/02/19 15:21
 */
public interface ICshPaymentMethodAsgnMoService extends IBaseService<CshPaymentMethodAsgnMo>, ProxySelf<ICshPaymentMethodAsgnMoService>{
    /**
     * 查询分配的管理组织
     * @param request
     * @param cshPaymentMethodAsgnMo
     * @param page
     * @param pageSize
     * @return 分配的管理组织
     */
    List<CshPaymentMethodAsgnMo> query(IRequest request,CshPaymentMethodAsgnMo cshPaymentMethodAsgnMo, int page, int pageSize);
}