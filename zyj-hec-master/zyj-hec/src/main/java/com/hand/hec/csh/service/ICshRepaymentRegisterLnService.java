package com.hand.hec.csh.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshRepaymentRegisterLn;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/05/06 14:41
 */
public interface ICshRepaymentRegisterLnService extends IBaseService<CshRepaymentRegisterLn>, ProxySelf<ICshRepaymentRegisterLnService> {

    /**
     * 还款登记单行创建-借款申请单LOV查询
     *
     * @param request      上下文
     * @param currencyCode 币种代码
     * @param employeeId   员工ID
     * @param requisitionNumber 查询条件:单据编号
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterLn>
     * @author jialin.xing@hand-china.com 2019-05-06 14:40
     */
    List<CshRepaymentRegisterLn> queryPaymentRequisition(IRequest request, String currencyCode, Long employeeId,String requisitionNumber);

    /**
     * 还款登记单行查询
     *
     * @param request       上下文
     * @param registerHdsId 头 ID
     * @param page          页数
     * @param pageSize      页大小
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterLn>
     * @author jialin.xing@hand-china.com 2019-05-07 15:30
     */
    List<CshRepaymentRegisterLn> queryLinesByHeaderId(IRequest request, Long registerHdsId, int page, int pageSize);

    @Override
    List<CshRepaymentRegisterLn> batchUpdate(IRequest request,@StdWho List<CshRepaymentRegisterLn> list);
}