package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshRepaymentRegisterHd;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/26 16:25
 */
public interface CshRepaymentRegisterHdMapper extends Mapper<CshRepaymentRegisterHd> {

    /**
     * 我的还款申请-基础查询
     *
     * @param para 条件集合
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterHd>
     * @author jialin.xing@hand-china.com 2019-04-26 16:27
     */
    List<CshRepaymentRegisterHd> baseSelect(Map<String, Object> para);

    /**
     * 打开已创建的还款申请单页面-头查询
     *
     * @param registerHdsId 头ID
     * @return com.hand.hec.csh.dto.CshRepaymentRegisterHd
     * @author jialin.xing@hand-china.com 2019-04-28 19:24
     */
    CshRepaymentRegisterHd queryRepaymentHd(@Param("registerHdsId") Long registerHdsId);


    /**
     * 创建还款申请单
     *
     * @param positionId
     * @param unitId
     * @param employeeId
     * @param accEntityId
     * @param moRepaymentRegTypeId
     * @return com.hand.hec.csh.dto.CshRepaymentRegisterHd
     * @author jialin.xing@hand-china.com 2019-04-29 14:18
     */
    CshRepaymentRegisterHd queryHdWithIdIsNull(@Param("positionId") Long positionId,
                                               @Param("unitId") Long unitId,
                                               @Param("employeeId") Long employeeId,
                                               @Param("accEntityId") Long accEntityId,
                                               @Param("moRepaymentRegTypeId") Long moRepaymentRegTypeId);


    /**
     * 还款单关联借款单查询
     *
     * @param registerHdsId 还款单头 ID
     * @author jialin.xing@hand-china.com 2019-05-10 10:15
     * @return java.util.List<java.util.Map>
     */
    List<Map> selectRelatedPaymentRequisition(@Param("registerHdsId")Long registerHdsId);

    /**
     * 查询单据编号与单据类型
     *
     * @param registerHdsId
     * @author jialin.xing@hand-china.com 2019-05-10 15:08
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String, String> queryNumberAndType(@Param("registerHdsId")Long registerHdsId);


    /**
     * 还款单出纳确认页面基础查询
     *
     * @param condition
     * @author jialin.xing@hand-china.com 2019-05-10 18:06
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterHd>
     */
    List<CshRepaymentRegisterHd> queryRepaymentCashier(Map condition);

    /**
     * 还款单会计确认页面基础查询
     *
     * @param params
     * @author jialin.xing@hand-china.com 2019-05-14 17:10
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterHd>
     */
    List<CshRepaymentRegisterHd> queryRepaymentAccounting(Map<String, Object> params);


    List<CshRepaymentRegisterHd> queryForFinance(CshRepaymentRegisterHd dto);

}