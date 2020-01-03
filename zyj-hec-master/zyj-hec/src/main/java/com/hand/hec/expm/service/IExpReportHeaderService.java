package com.hand.hec.expm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportLine;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * IExpReportHeaderService
 * </p>
 *
 * @author yang.duan 2019/01/10 15:05
 */
public interface IExpReportHeaderService extends IBaseService<ExpReportHeader>, ProxySelf<IExpReportHeaderService> {
    /**
     * <p>
     * 我的费用报销主查询
     * <p/>
     *
     * @param dto 查询DTO
     * @param request
     * @param page 分页大小
     * @param pageSize 页面大小
     * @return 报销单主页面信息list
     * @author yang.duan 2019/3/12 14:04
     */
    List<ExpReportHeader> queryExpReportMain(ExpReportHeader dto, IRequest request, int page, int pageSize);



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
    List<ExpReportHeader> saveReportHeader(IRequest request, List<ExpReportHeader> headers) throws RuntimeException;


    /**
     * <p>
     * 根据头维度设置行维度
     * <p/>
     *
     * @param header 报销单头信息
     * @param line 报销单行信息
     * @return 设置维度后的行DTO
     * @author yang.duan 2019/3/13 15:05
     */

    ExpReportLine setDimFromHeaderToLine(ExpReportHeader header, ExpReportLine line);

    /**
     * <p>
     * 报销单整单删除
     * <p/>
     *
     * @param request
     * @param header
     * @return
     * @author yang.duan 2019/3/29 10:30
     */
    int deleteHeader(IRequest request, ExpReportHeader header);

    /**
     * 报销单创建凭证
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/6 10:53
     * @param iRequest 请求上下文
     * @param expReportHeaders 需要创建凭证数据
     * @param journalDate 凭证日期
     * @return
     * @Version 1.0
     **/
    void createAccount(IRequest iRequest, List<ExpReportHeader> expReportHeaders, Date journalDate);

    /**
     * 报销单审核
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/7 10:14
     * @param iRequest 请求上下文
     * @param expReportHeaders 待审核的数据
     * @return
     * @Version 1.0
     **/
    void audit(IRequest iRequest, List<ExpReportHeader> expReportHeaders);

    /**
     * 报销单审核拒绝
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/7 10:14
     * @param iRequest 请求上下文
     * @param expReportHeaders 待审核拒绝的数据
     * @return
     * @Version 1.0
     **/
    void auditReject(IRequest iRequest, List<ExpReportHeader> expReportHeaders);

    /**
     * 报销单审核查询
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/7 10:14
     * @param iRequest 请求上下文
     * @param expReportHeader 查询条件
     * @param page
     * @param pageSize
     * @return List<ExpReportHeader>
     * @Version 1.0
     **/
    List<ExpReportHeader> auditQuery(IRequest iRequest, ExpReportHeader expReportHeader, int page, int pageSize);

    /**
     * 报销单反冲查询
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/7 10:14
     * @param iRequest 请求上下文
     * @param expReportHeader 查询条件
     * @param page
     * @param pageSize
     * @return List<ExpReportHeader>
     * @Version 1.0
     **/
    List<ExpReportHeader> reverseQuery(IRequest iRequest, ExpReportHeader expReportHeader, int page, int pageSize);

    /**
     * 修改汇率时修改对应单据的凭证信息
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/12 15:24
     * @param iRequest 请求上下文
     * @param list 需要修改汇率的单据信息
     * @return
     * @Version 1.0
     **/
    void updateAccount(IRequest iRequest, List<HashMap<String, Object>> list);

    /**
     * 报销单反冲
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/12 15:24
     * @param iRequest 请求上下文
     * @param journalDate 反冲日期
     * @param periodName 期间
     * @param expReportHeaders 需要反冲的单据信息
     * @return
     * @Version 1.0
     **/
    void reverse(IRequest iRequest, Date journalDate, String periodName, List<ExpReportHeader> expReportHeaders);

    /**
     * <p>
     * 报销单头查询
     * <p/>
     * 
     * @param request
     * @param expReportHeaderId
     * @param moExpReportTypeId
     * @param accEntityId
     * @param employeeId
     * @param paymentCurrencyCode
     * @return 报销单头list
     * @author yang.duan 2019/3/20 15:09
     */
    List<ExpReportHeader> expReportHeaderQuery(IRequest request, Long expReportHeaderId, Long moExpReportTypeId,
                    Long accEntityId, Long employeeId, String paymentCurrencyCode);


    /**
     * <p>
     * 报销单提交
     * <p/>
     *
     * @param request
     * @param expReportHeaderId 报销单头ID
     * @param bgtIgnoreWarningFlag
     * @param vatIgnoreAccEntityFlag
     * @param vatIgnoreDateFlag
     * @return map
     * @author yang.duan 2019/4/2 13:41
     */
    Map submitExpReport(IRequest request, Long expReportHeaderId, String bgtIgnoreWarningFlag,
                    String vatIgnoreAccEntityFlag, String vatIgnoreDateFlag);


    /**
     * <p>根据状态条件锁表</p>
     *
     * @param request
     * @param expReportHeaderId 报销单头ID
     * @return
     * @author yang.duan 2019/5/8 10:10
     **/
    boolean statusCheck(IRequest request,Long expReportHeaderId);


    /**
     * <p>单据行员工选择lov</p>
     *
     * @param request
     * @param companyId 公司ID
     * @param positionId  岗位ID
     * @param primaryPositionFlag  主岗标志
     * @param employeeCode 员工代码
     * @param employeeName 员工名称
     * @return List<Map>
     * @author yang.duan 2019/5/16 16:28
    **/
    List<Map> getEmployeeByCompanyId(IRequest request,Long companyId,Long positionId,String primaryPositionFlag,String employeeCode,String employeeName);
}
