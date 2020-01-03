package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentRequisitionLn;
import com.hand.hec.csh.exception.CshPaymentRequisitionLnException;

import java.util.List;
import java.util.Map;

public interface ICshPaymentRequisitionLnService
                extends IBaseService<CshPaymentRequisitionLn>, ProxySelf<ICshPaymentRequisitionLnService> {
    /**
     * 借款单行保存
     *
     * @param request IRequest
     * @param list List<CshPaymentRequisitionLn>
     * @author user 2019-01-24 15:10
     * @return List<CshPaymentRequisitionLn>
     */
    List<CshPaymentRequisitionLn> batchSave(IRequest request, List<CshPaymentRequisitionLn> list)
                    throws CshPaymentRequisitionLnException;

    /**
     * 借款单行新增
     *
     * @param request IRequest
     * @param record CshPaymentRequisitionLn
     * @author user 2019-01-24 15:10
     * @return CshPaymentRequisitionLn
     */
    CshPaymentRequisitionLn insertPaymentReqLn(IRequest request, CshPaymentRequisitionLn record)
                    throws CshPaymentRequisitionLnException;

    /**
     * 借款单行更新
     *
     * @param request IRequest
     * @param record CshPaymentRequisitionLn
     * @author user 2019-01-24 15:10
     * @return CshPaymentRequisitionLn
     */
    CshPaymentRequisitionLn updatePaymentReqLn(IRequest request, CshPaymentRequisitionLn record)
                    throws CshPaymentRequisitionLnException;

    /**
     * 借款单行按头ID删除
     *
     * @param request IRequest
     * @param paymentRequisitionHeaderId Long
     * @author user 2019-01-24 15:10
     * @return CshPaymentRequisitionLn
     */
    void deletePaymentReqLn(IRequest request, Long paymentRequisitionHeaderId);

    /**
     * 借款申请单行查询
     *
     * @param paymentRequisitionHeaderId 借款申请单表头id
     * @param request IRequest
     * @param pageNum 页码
     * @param pageSize 页数
     * @author LJK 2019-02-22 18:21
     * @return List<Map>
     */
    List<Map> selectByHeaderId(IRequest request, Long paymentRequisitionHeaderId, int pageNum, int pageSize);

    /**
     * 校验借款申请行
     *
     * @param transactionHeaderId
     * @param transactionLineId
     * @author ngls.luhui 2019-03-11 16:19
     * @return 返回相应的错误代码
     */
    String checkPayReq(Long transactionHeaderId, Long transactionLineId);

}
