package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hec.csh.dto.CshRepaymentRegisterHd;
import com.hand.hec.csh.dto.CshRepaymentRegisterLn;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 还款登记单操作核心类
 *
 * @author mouse 2019/04/30 10:33
 */
public interface ICshRepaymentRegisterService extends ProxySelf<ICshRepaymentRegisterService> {

    /**
     * 还款登记单金额检查
     *
     *
     * @author mouse 2019-04-30 11:05
     * @return void
     */
    void amountCheck(IRequest request, CshRepaymentRegisterHd registerHd);

    /**
     * 还款登记单提交检查
     *
     *
     * @author mouse 2019-04-30 11:05
     * @return void
     */
    void submitCheck(IRequest request, CshRepaymentRegisterHd registerHd);

    /**
     * 还款登记单出纳确认检查
     *
     *
     * @author mouse 2019-04-30 11:05
     * @return void
     */
    void cashierConfirmCheck(IRequest request, CshRepaymentRegisterHd registerHd);

    /**
     * 还款登记单出纳拒绝检查
     *
     *
     * @author mouse 2019-04-30 11:05
     * @return void
     */
    void cashierRejectCheck(IRequest request, CshRepaymentRegisterHd registerHd);

    /**
     * 还款登记单会计确认检查
     *
     *
     * @author mouse 2019-04-30 11:05
     * @return void
     */
    void accountingConfirmCheck(IRequest request, CshRepaymentRegisterHd registerHd);

    /**
     * 还款登记单会计拒绝检查
     *
     *
     * @author mouse 2019-04-30 11:05
     * @return void
     */
    void accountingRejectCheck(IRequest request, CshRepaymentRegisterHd registerHd);


    /**
     * 汇总剩余支付的可退款金额
     *
     *
     * @author mouse 2019-04-30 14:26
     * @return java.math.BigDecimal
     */
    BigDecimal sumLeftPaymentAmount(IRequest request, CshRepaymentRegisterLn ln);


    /**
     * 获取还款登记单行下根据付款事物group的数据
     *
     * @param ln
     * @author mouse 2019-04-30 15:09
     * @return java.util.List<java.util.Map>
     */
    List<Map> selectRepayTrxByLn(IRequest request, CshRepaymentRegisterLn ln);

    /**
     * 汇总当前付款事物的可退款金额
     *
     * @param repayTrxLineId
     * @author mouse 2019-04-30 15:25
     * @return java.math.BigDecimal
     */
    BigDecimal sumLeftTrxAmount(IRequest request, Long repayTrxLineId);

    /**
     * 出纳同意
     *
     * @param registerHd
     * @author mouse 2019-04-30 16:10
     * @return void
     */
    void cashierConfirm(IRequest request, CshRepaymentRegisterHd registerHd);

    /**
     * 出纳拒绝
     *
     * @param registerHd
     * @author mouse 2019-04-30 16:13
     * @return void
     */
    void cashierReject(IRequest request, CshRepaymentRegisterHd registerHd);

    /**
     * 出纳同意
     *
     * @param registerHd
     * @author mouse 2019-04-30 16:10
     * @return void
     */
    void accountingConfirm(IRequest request, CshRepaymentRegisterHd registerHd);

    /**
     * 出纳拒绝
     *
     * @param registerHd
     * @author mouse 2019-04-30 16:13
     * @return void
     */
    void accountingReject(IRequest request, CshRepaymentRegisterHd registerHd);

    /**
     * 获取还款登记单分配行，用于生产现金事物和凭证
     *
     * @param request
     * @param hd
     * @author mouse 2019-05-05 9:56
     * @return java.util.List<java.util.Map>
     */
    List<Map> selectRepaymentDistForAcc(IRequest request, CshRepaymentRegisterHd hd);

    void lockRegister(Long registerHdsId);
}
