package com.hand.hec.acp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.acp.dto.AcpRequisitionLn;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 付款申请单行信息mapper
 * </p>
 * 
 * @author guiyuting 2019/04/30 10:56
 */
public interface AcpRequisitionLnMapper extends Mapper<AcpRequisitionLn>{

    /**
     * 我的付款申请单页面 - 根据付款申请单头ID查询
     *
     * @param requisitionHdsId 付款申请单头ID
     * @author guiyuting 2019-04-30 10:58
     * @return 
     */
    List<AcpRequisitionLn> queryByHeader(@Param("requisitionHdsId") Long requisitionHdsId);
    
    /**
     * 根据付款申请单头ID更新行信息付款状态
     *
     * @param null
     * @author guiyuting 2019-05-06 14:59
     * @return 
     */
    void updateStatusByHeaderId(AcpRequisitionLn acpRequisitionLn);

    /**
     * 根据付款申请单头ID 获取行金额总和
     *
     * @param requisitionHdsId 付款申请单头ID
     * @author guiyuting 2019-05-06 15:13
     * @return 
     */
    BigDecimal getTotalAmount(@Param("requisitionHdsId") Long requisitionHdsId);

    /**
     * 根据付款申请单头ID 删除行信息
     *
     * @param requisitionHdsId 付款申请单头ID
     * @author guiyuting 2019-05-06 18:43
     * @return 
     */
    void deleteByHeaderId(@Param("requisitionHdsId") Long requisitionHdsId);

    /**
     * 付款申请单创建凭证 - 根据头ID查询行信息
     *
     * @param requisitionHdsId
     * @author guiyuting 2019-05-08 16:45
     * @return 
     */
    List<AcpRequisitionLn> queryForCreateAccount(@Param("requisitionHdsId") Long requisitionHdsId);

    /**
     * <p>获取单据ID(付款反冲)</p>
     *
     * @param transactionHeaderId 现金事务头ID
     * @return Long
     * @author yang.duan 2019/5/23 15:51
    **/
    Long getDocId(@Param("transactionHeaderId") Long transactionHeaderId);

}