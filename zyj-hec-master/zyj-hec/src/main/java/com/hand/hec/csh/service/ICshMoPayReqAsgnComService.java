package com.hand.hec.csh.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshMoPayReqAsgnCom;

public interface ICshMoPayReqAsgnComService extends IBaseService<CshMoPayReqAsgnCom>, ProxySelf<ICshMoPayReqAsgnComService>{
    /**
     *借款申请单类型查询已分配公司
     *
     * @param request 请求
     * @param condition 借款申请单类型分配公司实体类
     * @param pageNum 页码
     * @param pageSize 页数
     * @author dingwei.ma@hand-china.com 2019-02-20 10:16
     * @return List<CshMoPayReqAsgnCom>
     */
    List<CshMoPayReqAsgnCom> queryByTypeId(IRequest request, CshMoPayReqAsgnCom condition, int pageNum, int pageSize);

    /**
     *借款申请单类型查询未分配公司
     *
     * @param request 请求
     * @param moPaymentReqTypeId 借款单类型Id
     * @param magOrgId 组织Id
     * @param pageNum 页码
     * @param pageSize 页数
     * @author dingwei.ma@hand-china.com 2019-02-20 13:38
     * @return List<CshMoPayReqAsgnCom>
     */
    List<CshMoPayReqAsgnCom> queryComByTypeId(IRequest request, Long moPaymentReqTypeId,Long magOrgId, int pageNum, int pageSize);

    /**
     * 保存借款申请单类型批量分配公司
     *
     * @param request  请求
     * @param list  借款申请单类型批量分配公司List
     * @author dingwei.ma@hand-china.com 2019-02-20 16:32
     * @return List<CshMoPayReqAsgnCom>
     */
    List<CshMoPayReqAsgnCom> batchAssignCom(IRequest request, List<CshMoPayReqAsgnCom> list);

}