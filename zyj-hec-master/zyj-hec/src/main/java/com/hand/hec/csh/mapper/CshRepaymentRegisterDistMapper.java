package com.hand.hec.csh.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshRepaymentRegisterDist;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/05/08 18:26
 */
public interface CshRepaymentRegisterDistMapper extends Mapper<CshRepaymentRegisterDist>{

    /**
     * 删除还款单关联的预付款
     *
     * @param registerHdId
     * @author jialin.xing@hand-china.com 2019-05-14 10:45
     * @return int
     */
    int deleteDist(@Param("registerHdId")Long registerHdId);

    /**
     * 还款申请单出纳-分配预付款-预付款查询
     *
     * @param registerLnId
     * @author jialin.xing@hand-china.com 2019-05-14 10:44
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterDist>
     */
    List<CshRepaymentRegisterDist> baseSelect(@Param("registerLnId")Long registerLnId);

    /**
     * 还款申请单出纳-分配预付款-预付款事务LOV
     *
     * @param reqLnId
     * @author jialin.xing@hand-china.com 2019-05-14 14:18
     * @return java.util.List<java.util.Map>
     */
    List<Map> queryTrxById(@Param("reqLnId")Long reqLnId,
                           @Param("paymentNum")String paymentNum,
                           @Param("transactionNum")String transactionNum);
}