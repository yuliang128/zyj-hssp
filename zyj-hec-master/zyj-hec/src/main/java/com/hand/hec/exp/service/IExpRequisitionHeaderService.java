package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpRequisitionHeader;

import java.util.List;
import java.util.Map;

public interface IExpRequisitionHeaderService extends IBaseService<ExpRequisitionHeader>, ProxySelf<IExpRequisitionHeaderService> {
    /**
     * 借款单关联申请单查询
     *
     * @param request  上下文
     * @param headerId 借款申请单头Id
     * @param pageNum  页码
     * @param pageSize 页数
     * @return List<Map>
     * @author dingwei.ma@hand-china.com 2019-02-27 15:00
     */
    List<Map> queryByPayReqHeaderId(IRequest request, Long headerId, int pageNum, int pageSize);

    List<Map> queryExp5360(IRequest request, ExpRequisitionHeader dto, int pageNum, int pageSize);

    List<Map> queryDetailHead(IRequest request, long headId);

    /**
     * 费用申请财务关闭  关闭逻辑
     *
     * @param iRequest  请求上下文
     * @param expRequisitionHeaders 费用申请单需关闭数据
     * @author weikun.wang2019-03-25 14:04
     * @return
     */
    void closeDetailHead(IRequest iRequest,List<ExpRequisitionHeader> expRequisitionHeaders);

    /**
     * 申请单查询
     *
     * @param request  上下文
     * @param dto      申请单信息
     * @param pageNum  页码
     * @param pageSize 页数
     * @return List<ExpRequisitionHeader>
     * @author jiangxz 2019-03-19 15:00
     */
    List<ExpRequisitionHeader> queryExpRequisitionMain(IRequest request, ExpRequisitionHeader dto, int pageNum, int pageSize);

    /**
     * 申请单维护查询
     *
     * @param request                上下文
     * @param employeeId             员工id
     * @param expRequisitionHeaderId 申请单头id
     * @param moExpReqTypeId         申请单类型id
     * @param accEntityId            核算主体id
     * @param paymentCurrencyCode    付款币种代码
     * @param pageNum                页码
     * @param pageSize               页数
     * @return List<ExpRequisitionHeader>
     * @author jiangxz 2019-03-26 15:00
     */
    List<ExpRequisitionHeader> expRequisitionHeaderQuery(IRequest request, Long expRequisitionHeaderId, Long moExpReqTypeId, Long accEntityId, String paymentCurrencyCode, Long employeeId, int pageNum, int pageSize);

    /**
     * <p>
     * 报销单头保存(页面保存)
     * <p/>
     *
     * @param request
     * @param headers 头信息
     * @return 返回保存后的头信息
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/7 14:38
     */
    List<ExpRequisitionHeader> saveReportHeader(IRequest request, List<ExpRequisitionHeader> headers);

    /**
     * <p>报销单关联申请单查询(头关联)</p>
     *
     * @param request
     * @param moExpReportTypeId
     * @param expRequisitionNumber
     * @param description
     * @return List<ExpRequisitionHeader>
     * @author yang.duan 2019/4/25 14:50
     **/
    List<ExpRequisitionHeader> expReportFromReqHeaderQuery(IRequest request, Long moExpReportTypeId, String expRequisitionNumber, String paymentCurrencyCode, String description);

}