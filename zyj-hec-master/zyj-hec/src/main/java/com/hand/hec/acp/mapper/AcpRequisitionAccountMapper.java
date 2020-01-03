package com.hand.hec.acp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.acp.dto.AcpRequisitionAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AcpRequisitionAccountMapper extends Mapper<AcpRequisitionAccount> {


    /**
     * 获取借款审核凭证
     *
     * @author Tagin
     * @date 2019-04-30 15:51
     * @param transactionLineId 现金事务行ID
     * @param usageCode 用途代码
     * @param requisitionLnsId 付款申请单行ID
     * @param drFlag 借方标识
     * @param crFlag 贷方标识
     * @return java.util.List<com.hand.hec.acp.dto.AcpRequisitionAccount>
     * @version 1.0
     **/
    List<AcpRequisitionAccount> queryAccount(@Param("transactionLineId") Long transactionLineId,
                    @Param("usageCode") String usageCode, @Param("requisitionLnsId") Long requisitionLnsId,
                    @Param("drFlag") String drFlag, @Param("crFlag") String crFlag);


    /**
     * 按行ID和核算主体汇总数据
     * @param requisitionHdsId
     * @author zhongyu 2019-5-9
     * @return
     */
    List<Map> selectGroupByLine(@Param("requisitionHdsId")Long requisitionHdsId);

    /**
     * 查询借贷方金额差值
     * @param requisitionLineId
     * @param accEntityId
     * @author zhongyu 2019-5-9
     * @return
     */
    List<Map> selectForEnterAmount(@Param("requisitionLineId")Long requisitionLineId,@Param("accEntityId")Long accEntityId);

    /**
     * 更改单据状态
     * @param requisitionHdsId
     * @param interfaceFlag
     */
    void updateByLineId(@Param("requisitionHdsId") Long requisitionHdsId,@Param("interfaceFlag")String interfaceFlag);

    /**
     * 根据付款申请单行ID删除凭证
     *
     * @param requisitionLnsId 付款申请单行ID
     * @author guiyuting 2019-05-08 17:13
     * @return 
     */
    void deleteByAcpLineId(@Param("requisitionLnsId") Long requisitionLnsId);

    /**
     * 删除相关数据
     * @param requisitionHdsId
     */
    void rejectAcpDelete(@Param("requisitionHdsId")Long requisitionHdsId);
}
