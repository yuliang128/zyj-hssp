package com.hand.hec.acp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpRequisitionDtl;
import com.hand.hec.acp.dto.AcpRequisitionLn;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 付款申请单明细service
 * </p>
 *
 * @author guiyuting 2019/04/30 11:09
 */
public interface IAcpRequisitionDtlService
                extends IBaseService<AcpRequisitionDtl>, ProxySelf<IAcpRequisitionDtlService> {

    /**
     * 我的付款申请单维护页面 - 根据申请单行ID 查询
     *
     * @param acpRequisitionDtl 付款申请单行ID
     * @author guiyuting 2019-04-30 11:13
     * @return
     */
    List<AcpRequisitionDtl> queryByLineId(IRequest request, AcpRequisitionDtl acpRequisitionDtl);

    List<AcpRequisitionDtl> batchUpdate(IRequest request, List<AcpRequisitionDtl> list, AcpRequisitionLn line,
                    String refType);

    /**
     * 根据付款申请单行ID 获取行金额总和
     *
     * @param requisitionLnsId 付款申请单行ID
     * @author guiyuting 2019-05-06 15:13
     * @return
     */
    BigDecimal getTotalAmount(Long requisitionLnsId);

    /**
     * 付款申请单提交 - 根据付款申请单头ID 查询
     *
     * @param requisitionHdsId 付款申请单头ID
     * @author guiyuting 2019-05-06 15:53
     * @return
     */
    List<AcpRequisitionDtl> queryByHeaderId(Long requisitionHdsId);

    /**
     * 付款申请单提交校验- 根据单据行ID和单据类型，付款明细ID查询总金额
     *
     * @param acpRequisitionDtl
     * @author guiyuting 2019-05-06 16:32
     * @return
     */
    BigDecimal getOtherAmount(AcpRequisitionDtl acpRequisitionDtl);

    /**
     * 付款申请单维护页面- 根据报销单查询明细
     *
     * @param acpRequisitionDtl
     * @author guiyuting 2019-05-07 10:04
     * @return
     */
    List<AcpRequisitionDtl> queryFromReport(IRequest request, AcpRequisitionDtl acpRequisitionDtl, int page,
                    int pageSize);

    /**
     * 获取付款申请单明细-根据付款申请单行 ID
     *
     * @author Tagin
     * @date 2019-05-06 15:58
     * @param requisitionLnsId 付款申请单行 ID
     * @return java.util.List<com.hand.hec.acp.dto.AcpRequisitionDtl>
     * @version 1.0
     **/
    List<AcpRequisitionDtl> queryAllByLnsId(Long requisitionLnsId);

}
