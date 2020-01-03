package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshRepaymentRegisterHd;
import com.hand.hec.csh.dto.CshRepaymentRegisterLn;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 还款登记单操作Mapper
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/26 16:25
 */
public interface CshRepaymentRegisterMapper {

    /**
     * 检查还款单与还款公司账户币种是否一致
     *
     * @param hd
     * @author mouse 2019-04-30 13:59
     * @return int
     */
    int validateCurrency(CshRepaymentRegisterHd hd);

    /**
     * 汇总剩余支付的可退款金额
     *
     *
     * @author mouse 2019-04-30 14:26
     * @return java.math.BigDecimal
     */
    BigDecimal sumLeftPaymentAmount(CshRepaymentRegisterLn ln);

    /**
     * 汇总还款金额
     *
     *
     * @author mouse 2019-04-30 14:26
     * @return java.math.BigDecimal
     */
    BigDecimal sumRepaymentAmount(@Param("dto") CshRepaymentRegisterLn ln);

    /**
     * 获取还款登记单行下根据付款事物group的数据
     *
     * @param ln
     * @author mouse 2019-04-30 15:09
     * @return java.util.List<java.util.Map>
     */
    List<Map> selectRepayTrxByLn(CshRepaymentRegisterLn ln);

    /**
     * 汇总当前付款事物的可退款金额
     *
     * @param repayTrxLineId
     * @author mouse 2019-04-30 15:25
     * @return java.math.BigDecimal
     */
    BigDecimal sumLeftTrxAmount(@Param(value = "repayTrxLineId") Long repayTrxLineId);

    /**
     * 获取还款登记单分配行，用于生产现金事物和凭证
     *
     * @param hd
     * @author mouse 2019-05-05 9:56
     * @return java.util.Map
     */
    List<Map> selectRepaymentDistForAcc(CshRepaymentRegisterHd hd);

    /**
     * 获取某个预付款的可还款金额
     *
     * @param prepayTrxLineId
     * @author mouse 2019-05-07 15:33
     * @return java.math.BigDecimal
     */
    BigDecimal getRepayableAmount(@Param("prepayTrxLineId") Long prepayTrxLineId);
}
