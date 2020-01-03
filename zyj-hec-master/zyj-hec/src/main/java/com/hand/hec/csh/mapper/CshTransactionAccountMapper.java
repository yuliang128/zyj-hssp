package com.hand.hec.csh.mapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshTransactionAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 现金事务凭证Mapper
 * </p>
 * 
 * @author Tagin 2019/01/22 20:05
 */
public interface CshTransactionAccountMapper extends Mapper<CshTransactionAccount> {

    /**
     * 更新现金事务凭证接口标识
     *
     * @author Tagin
     * @date 2019-03-29 11:06
     * @param gldInterfaceFlag 接口标识
     * @param transactionLineId 现金事务行ID
     * @return
     * @version 1.0
     **/
    void updateInterfaceFlag(@Param("gldInterfaceFlag") String gldInterfaceFlag,
                    @Param("transactionLineId") Long transactionLineId);

    /**
     * 获取现金事务凭证
     *
     * @author Tagin
     * @date 2019-04-01 15:17
     * @param transactionLineId 现金事务行ID
     * @param usageCode 用途代码
     * @param drFlag 借方标识
     * @param crFlag 贷方标识
     * @param writeOffId 核销 ID
     * @return List<CshTransactionAccount>
     * @version 1.0
     **/
    List<CshTransactionAccount> queryAccount(@Param("transactionLineId") Long transactionLineId,
                    @Param("usageCode") String usageCode, @Param("drFlag") String drFlag,
                    @Param("crFlag") String crFlag, @Param("writeOffId") Long writeOffId);

    List<CshTransactionAccount> queryByLineId(@Param("transactionLineId") Long transactionLineId);

    /**
     * 根据还款登记单头ID查询凭证信息
     * 
     * @param registerHdsId
     * @author zhongyu 2019-05-17
     * @return
     */
    List<CshTransactionAccount> queryAccountForFinance(@Param("registerHdsId") Long registerHdsId);

    /**
     * 凭证查询
     * 
     * @param request
     * @param transactionHeaderId
     * @return
     */
    List<CshTransactionAccount> queryByHeaderId(@Param("request") IRequest request,
                    @Param("transactionHeaderId") Long transactionHeaderId);

}
