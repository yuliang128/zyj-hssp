package com.hand.hec.csh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshRepaymentRegisterLn;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/05/06 14:45
 */
public interface CshRepaymentRegisterLnMapper extends Mapper<CshRepaymentRegisterLn> {

    /**
     * 还款登记单行创建-借款申请单LOV查询
     *
     * @param currencyCode 币种代码
     * @param employeeId 员工ID
     * @param requisitionNumber 查询条件: 单据编号
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterLn>
     * @author jialin.xing@hand-china.com 2019-05-06 14:40
     */
    List<CshRepaymentRegisterLn> queryPaymentRequisition(@Param("currencyCode") String currencyCode,
                                                         @Param("employeeId") Long employeeId,
                                                         @Param("requisitionNumber")String requisitionNumber);
    /**
     * 还款登记单行查询
     *
     * @param registerHdsId 登记单头ID
     * @author jialin.xing@hand-china.com 2019-05-07 15:30
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterLn>
     */
    List<CshRepaymentRegisterLn> queryLinesByHeaderId(@Param("registerHdsId")Long registerHdsId);
}