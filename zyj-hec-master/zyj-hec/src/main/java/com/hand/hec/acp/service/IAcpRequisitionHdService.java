package com.hand.hec.acp.service;

/**
 * <p>
 * 付款申请单头信息service
 * </p>
 * 
 * @author guiyuting 2019/04/28 15:30
 */

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpRequisitionHd;

import java.util.Date;
import java.util.List;

public interface IAcpRequisitionHdService extends IBaseService<AcpRequisitionHd>, ProxySelf<IAcpRequisitionHdService> {

    /**
     * 根据当前登陆用户信息查询付款申请单头信息
     *
     * @param acpRequisitionHd
     * @author guiyuting 2019-04-28 15:31
     * @return
     */
    List<AcpRequisitionHd> queryMain(IRequest request, AcpRequisitionHd acpRequisitionHd, int page, int pageSize);

    /**
     * 我的付款申请维护页面-根据headerId查询
     *
     * @param acpRequisitionHd
     * @author guiyuting 2019-04-29 16:06
     * @return
     */
    AcpRequisitionHd selectByHeaderId(IRequest request, AcpRequisitionHd acpRequisitionHd);

    @Override
    List<AcpRequisitionHd> batchUpdate(IRequest request, List<AcpRequisitionHd> list);

    /**
     * 检查该单据是否可修改（新建，拒绝，收回状态）
     *
     * @param requisitionHdsId
     * @author guiyuting 2019-05-05 19:14
     * @return
     */
    String updateStatusCheck(IRequest request, Long requisitionHdsId);

    /**
     * 付款申请单提交
     *
     * @param requisitionHdsId
     * @author guiyuting 2019-05-06 14:18
     * @return
     */
    boolean submitAcpRequisition(IRequest request, Long requisitionHdsId);

    /**
     * 根据付款申请单头ID删除付款申请单
     *
     * @param requisitionHdsId
     * @author guiyuting 2019-05-06 18:32
     * @return
     */
    boolean deleteAcpRequisition(IRequest request, Long requisitionHdsId);

    /**
     * 付款申请单审核查询单据
     *
     * @param request
     * @param dto
     * @param pageNum
     * @param pageSize
     * @return
     * @author zhongyu 2019-5-7
     */
    List<AcpRequisitionHd> queryAuditResult(IRequest request, AcpRequisitionHd dto, int pageNum, int pageSize);

    /**
     * 获取付款申请单头记录-根据付款申请单头 ID
     *
     * @author Tagin
     * @date 2019-05-07 21:12
     * @param iRequest 请求
     * @param requisitionHdsId 付款申请单头 ID
     * @return com.hand.hec.acp.dto.AcpRequisitionHd
     * @version 1.0
     **/
    AcpRequisitionHd getAcpRequisitionHeader(IRequest iRequest, Long requisitionHdsId);


    /**
     * 付款申请单审核 - 创建凭证
     *
     * @param accEntityId 核算主体ID
     * @param journalDate 期间
     * @param details
     * @author guiyuting 2019-05-08 19:15
     * @return 
     */
    boolean createAcpRequisitionAccount(IRequest request, Long accEntityId, Date journalDate,
                    List<AcpRequisitionHd> details);

    /**
     * 付款申请单财务查询
     * @param request
     * @param dto
     * @oaram allCompanyFlag
     * @param page
     * @param pageSize
     * @author zhongyu 2019-05-29
     * @return
     */
    List<AcpRequisitionHd> queryRequisition(IRequest request, AcpRequisitionHd dto, String allCompanyFlag,int page, int pageSize);


    /**
     * 付款申请单财务查询 包括子公司
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @author zhongyu 2019-05-29
     * @return
     */
    List<AcpRequisitionHd> queryComRequisition(IRequest request, AcpRequisitionHd dto,int page, int pageSize);


}
