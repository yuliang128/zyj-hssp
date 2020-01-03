package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshMoPayUsdAsgnCom;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 付款用途分配公司Service接口
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */
public interface ICshMoPayUsdAsgnComService extends IBaseService<CshMoPayUsdAsgnCom>, ProxySelf<ICshMoPayUsdAsgnComService>{
    /**
     *  查询符合条件的付款用途分配公司
     * @param request
     * @param magOrgId
     * @param paymentUsedeId
     * @param pageNum
     * @param pageSize
     * @return 符合条件的付款用途分配公司
     */
    List<CshMoPayUsdAsgnCom> queryLov(IRequest request,Long magOrgId,Long paymentUsedeId, int pageNum,
                                      int pageSize);


    List<CshMoPayUsdAsgnCom> query(IRequest request,CshMoPayUsdAsgnCom cshMoPayUsdAsgnCom, int pageNum,
                                   int pageSize);
}