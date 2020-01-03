package com.hand.hec.csh.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshMoPayReqRefEmpGp;

public interface ICshMoPayReqRefEmpGpService extends IBaseService<CshMoPayReqRefEmpGp>, ProxySelf<ICshMoPayReqRefEmpGpService>{
    /**
     *查询借款申请单类型分配工作组
     *
     * @param request 请求
     * @param condition 借款申请单类型分配工作组实体类
     * @param pageNum 页码
     * @param pageSize 页数
     * @author user 2019-02-19 17:34
     * @return
     */
    List<CshMoPayReqRefEmpGp> queryByTypeId(IRequest request, CshMoPayReqRefEmpGp condition, int pageNum, int pageSize);
}