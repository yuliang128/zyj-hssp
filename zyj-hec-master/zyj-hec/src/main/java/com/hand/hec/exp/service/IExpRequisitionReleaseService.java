package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpRequisitionRelease;

import java.util.List;

public interface IExpRequisitionReleaseService extends IBaseService<ExpRequisitionRelease>, ProxySelf<IExpRequisitionReleaseService>{
    /**
     *获取报销单关联的申请单信息
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/14 11:16
     *@param expReportHeaderId 报销单头Id
     *@param expReportLineId 报销单行Id
     *@param expReportDistId 报销单分配行Id
     *@return List<ExpRequisitionRelease>
     *@Version 1.0
     **/
    List<ExpRequisitionRelease> selectExpRequisitionReleaseInfo(Long expReportHeaderId,Long expReportLineId,Long expReportDistId);

    /**
     *获取对应的申请单已报销金额、已报销数量
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/18 10:53
     *@param expRequisitionDistId 申请单分配行Id
     *@return ExpRequisitionRelease
     *@Version 1.0
     **/
    ExpRequisitionRelease getRequisitionDistsRelease(Long expRequisitionDistId);


    void releasedBgtReserveBalance(IRequest request,Long releaseId);

    void openOneOffExpRequisition(IRequest request,Long expReqHeaderId);

    /**
     * <p>报销单关联申请单新增</p>
     *
     * @param request
     * @param dto
     * @return ExpRequisitionRelease
     * @throws RuntimeException exception
     * @author yang.duan 2019/4/29 14:32
    **/
    ExpRequisitionRelease insertExpRequisitionRelease(IRequest request,ExpRequisitionRelease dto) throws RuntimeException;
}