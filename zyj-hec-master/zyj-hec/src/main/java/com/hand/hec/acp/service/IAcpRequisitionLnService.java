package com.hand.hec.acp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpRequisitionHd;
import com.hand.hec.acp.dto.AcpRequisitionLn;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 付款申请单行信息service
 * </p>
 *
 * @author guiyuting 2019/04/30 10:56
 */
public interface IAcpRequisitionLnService extends IBaseService<AcpRequisitionLn>, ProxySelf<IAcpRequisitionLnService> {

    /**
     * 我的付款申请单页面 - 根据付款申请单头ID查询
     *
     * @param requisitionHdsId 付款申请单头ID
     * @author guiyuting 2019-04-30 10:58
     * @return
     */
    List<AcpRequisitionLn> queryByHeader(IRequest request, Long requisitionHdsId, int page, int pageSize);

    List<AcpRequisitionLn> batchUpdate(IRequest request, List<AcpRequisitionLn> list,
                    AcpRequisitionHd acpRequisitionHd);

    /**
     * 根据付款申请单头ID更新行信息付款状态
     *
     * @param null
     * @author guiyuting 2019-05-06 14:58
     * @return
     */
    void updateStatusByHeaderId(IRequest request, AcpRequisitionLn acpRequisitionLn);

    /**
     * 根据付款申请单头ID 获取行金额总和
     *
     * @param requisitionHdsId 付款申请单头ID
     * @author guiyuting 2019-05-06 15:13
     * @return
     */
    BigDecimal getTotalAmount(Long requisitionHdsId);

    /**
     * 根据付款申请单头ID 删除行信息
     *
     * @param requisitionHdsId 付款申请单头ID
     * @author guiyuting 2019-05-06 18:43
     * @return
     */
    void deleteByHeaderId(Long requisitionHdsId);

    /**
     * 付款申请单创建凭证 - 根据头ID查询行信息
     *
     * @param requisitionHdsId
     * @author guiyuting 2019-05-08 16:45
     * @return
     */
    List<AcpRequisitionLn> queryForCreateAccount(Long requisitionHdsId);

}
