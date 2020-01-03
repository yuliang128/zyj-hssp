package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpRequisitionLn;
import com.hand.hec.csh.dto.CshTransactionHeader;
import com.hand.hec.csh.dto.CshTransactionLine;
import com.hand.hec.csh.dto.CshWriteOff;
import com.hand.hec.csh.dto.CshWriteOffAccount;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 核销凭证表Service
 * </p>
 *
 * @author Tagin 2019/01/22 10:24
 */
public interface ICshWriteOffAccountService
                extends IBaseService<CshWriteOffAccount>, ProxySelf<ICshWriteOffAccountService> {

    /**
     * 生成核销凭证-报销单核销借款
     * 
     * @param iRequest 请求
     * @param cshWriteOff 核销对象
     * @Author Tagin
     * @Date 2019-02-21 13:51
     * @Return void
     * @Version 1.0
     **/
    void generateAccount(IRequest iRequest, CshWriteOff cshWriteOff);

    /**
     * 生成核销凭证-付款申请单核销报销、合同
     *
     * @author Tagin
     * @date 2019-05-08 10:18
     * @param iRequest 请求
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销
     * @param acpRequisitionLn 付款申请单行对象
     * @return void
     * @version 1.0
     **/
    void generateAcpWriteAccount(IRequest iRequest, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, CshWriteOff cshWriteOff, AcpRequisitionLn acpRequisitionLn);


    /**
     * <p>
     * 付款反冲查询凭证信息
     * </p>
     *
     * @param request
     * @param transactionHeaderId 现金事务头ID
     * @param pageNum
     * @param pageSize
     * @return List<Map>
     * @author yang.duan 2019/5/24 11:37
     **/
    List<Map> cshPaymentFinanceInfo(IRequest request,Long transactionHeaderId,int pageNum,int pageSize);

	/**
	 * <p>预付款核销反冲查询凭证信息</p>
	 *
	 * @param request
	 * @param writeOffId 凭证ID
	 * @param pageNum
	 * @param pageSize
	 * @return List<Map>
	 * @author LJK 2019/6/04 11:37
	 **/
	List<Map> cshPrepaymentFinanceInfo(IRequest request, Long writeOffId,int pageNum,int pageSize);


    /**
     * 查询核销凭证信息
     *
     * @author Tagin
     * @date 2019-06-05 19:04
     * @param writeOffId 核销 ID
     * @param usageCode 用途代码
     * @param drFlag 借方标志
     * @param crFlag 贷方标志
     * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
     * @version 1.0
     **/
    List<CshWriteOffAccount> queryAccount(Long writeOffId, String usageCode, String drFlag, String crFlag);

}
