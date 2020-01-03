package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshBank;
import com.hand.hec.csh.dto.CshPaymentMethodAsgnCom;

import java.util.List;
/**
 * <p>
 * 付款方式分配公司 Service 接口
 * </p>
 *
 * @author yang.cai@hand-china.com 2019/02/19 15:21
 */
public interface ICshPaymentMethodAsgnComService extends IBaseService<CshPaymentMethodAsgnCom>, ProxySelf<ICshPaymentMethodAsgnComService>{
    /**
     *
     * @param request
     * @param cshPaymentMethodAsgnCom
     * @param page
     * @param pageSize
     * @return 返回符合条件的支付方法配分公司
     */
    List<CshPaymentMethodAsgnCom> queryLov(IRequest request, CshPaymentMethodAsgnCom cshPaymentMethodAsgnCom, int page, int pageSize);

    /**
     * 查询分配的公司
     * @param request
     * @param cshPaymentMethodAsgnCom
     * @param page
     * @param pageSize
     * @return 分配的公司
     */
    List<CshPaymentMethodAsgnCom> query(IRequest request, CshPaymentMethodAsgnCom cshPaymentMethodAsgnCom, int page, int pageSize);
}