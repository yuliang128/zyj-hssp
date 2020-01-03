package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshRepaymentRegisterHd;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/26 16:45
 */
public interface ICshRepaymentRegisterHdService extends IBaseService<CshRepaymentRegisterHd>, ProxySelf<ICshRepaymentRegisterHdService> {

    /**
     * 我的还款申请基础查询
     *
     * @param request 上下文
     * @param params 查询条件
     * @param page 页数
     * @param pageSize 页大小
     * @author jialin.xing@hand-china.com 2019-04-26 16:46
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterHd>
     */
    List<CshRepaymentRegisterHd> baseSelect(IRequest request, Map<String,Object> params,int page,int pageSize);

    /**
     * 创建还款申请单-头查询
     *
     * @param request 上下文
     * @param registerHdsId 头ID
     * @author jialin.xing@hand-china.com 2019-04-28 19:24
     * @return com.hand.hec.csh.dto.CshRepaymentRegisterHd
     */
    CshRepaymentRegisterHd queryRepaymentHd(IRequest request,Long registerHdsId);

    /**
     * 创建还款申请单
     *
     * @param requestCxt
     * @param moRepaymentRegTypeId
     * @param employeeId
     * @param accEntityId
     * @author jialin.xing@hand-china.com 2019-04-29 14:27
     * @return com.hand.hec.csh.dto.CshRepaymentRegisterHd
     */
    CshRepaymentRegisterHd queryHdWithIdIsNull(IRequest requestCxt,Long moRepaymentRegTypeId, Long employeeId, Long accEntityId);

    /**
     * 还款单出纳确认页面基础查询
     *
     * @param request
     * @param params
     * @param page
     * @param pageSize
     * @author jialin.xing@hand-china.com 2019-05-10 18:13
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterHd>
     */
    List<CshRepaymentRegisterHd> queryRepaymentCashier(IRequest request,Map<String,Object> params,int page,int pageSize);

    /**
     * 还款单会计确认页面基础查询
     *
     * @param requestContext
     * @param params
     * @param page
     * @param pageSize
     * @author jialin.xing@hand-china.com 2019-05-14 17:09
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterHd>
     */
    List<CshRepaymentRegisterHd> queryRepaymentAccounting(IRequest requestContext, Map<String, Object> params, int page, int pageSize);

    /**
     * 查询单据编号与单据类型
     *
     * @param request
     * @param registerHdsId
     * @author jialin.xing@hand-china.com 2019-05-10 15:07
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String,String> queryNumberAndType(IRequest request,Long registerHdsId);

    /**
     * 单据保存
     *
     * @param request
     * @param list
     * @author jialin.xing@hand-china.com 2019-05-10 10:16
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterHd>
     */
    @Override
    List<CshRepaymentRegisterHd> batchUpdate(IRequest request,@StdWho List<CshRepaymentRegisterHd> list);

    /**
     * 单据提交审批
     *
     * @param request
     * @param registerHdsId
     * @author jialin.xing@hand-china.com 2019-05-10 10:17
     * @return java.util.List<com.hand.hec.csh.dto.CshRepaymentRegisterHd>
     */
    List<CshRepaymentRegisterHd> batchSubmit(IRequest request,Long registerHdsId);

    /**
     * 删除单据草稿
     *
     * @param request
     * @param header
     * @author jialin.xing@hand-china.com 2019-05-10 10:17
     * @return int
     */
    int delete(IRequest request,CshRepaymentRegisterHd header);

    /**
     * 还款单关联借款单查询
     *
     * @param request 上下文
     * @param registerHdsId 还款单头 ID
     * @author jialin.xing@hand-china.com 2019-05-10 10:15
     * @return java.util.List<java.util.Map>
     */
    List<Map> selectRelatedPaymentRequisition(IRequest request,Long registerHdsId);

    /**
     * 还款登记单财务查询
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @author zhongyu 2019-05-16
     * @return
     */
    List<CshRepaymentRegisterHd> queryForFinance(IRequest request,CshRepaymentRegisterHd dto,int page,int pageSize);
}