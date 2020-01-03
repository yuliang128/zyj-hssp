package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReqType;
import com.hand.hec.exp.dto.ExpRequisitionDist;
import com.hand.hec.exp.dto.ExpRequisitionHeader;
import com.hand.hec.exp.dto.ExpRequisitionLine;

import java.util.List;

public interface IExpRequisitionDistService extends IBaseService<ExpRequisitionDist>, ProxySelf<IExpRequisitionDistService> {
    /**
     * 根据申请单头Id获取所有未关闭的申请单分配行数据
     *
     * @param iRequest               请求上下文
     * @param expRequisitionHeaderId 申请单头Id
     * @return List<ExpRequisitionDist>
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/29 15:11
     * @Version 1.0
     **/
    List<ExpRequisitionDist> getAllExpRequisitionDist(IRequest iRequest, Long expRequisitionHeaderId);

    /**
     * 根据申请单头Id,行Id,分配行Id获取所有未审核的报销单数据
     *
     * @param iRequest               请求上下文
     * @param expRequisitionHeaderId 申请单头Id
     * @param expRequisitionLineId   申请单行Id
     * @param expRequisitionDistId   申请单分配行Id
     * @return List<ExpRequisitionDist>
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/29 15:11
     * @Version 1.0
     **/
    int countUnAuditExpReport(IRequest iRequest, Long expRequisitionHeaderId, Long expRequisitionLineId, Long expRequisitionDistId);

    /**
     * <p>
     * 申请单分配行新增
     * <p/>
     *
     * @param request
     * @param expReqDist   需要保存的分配行信息
     * @param expMoReqType 申请单类型
     * @param expReqHeader 申请单头
     * @param expReqLine   申请单行
     * @return 返回新增后的分配行信息
     * @throws RuntimeException exception
     * @author jiangxz 2019/4/4 09:11
     */
    ExpRequisitionDist insertDistLine(IRequest request, ExpRequisitionDist expReqDist, ExpMoReqType expMoReqType, ExpRequisitionHeader expReqHeader, ExpRequisitionLine expReqLine);


    /**
     * <p>
     * 报销单分配行更新
     * <p/>
     *
     * @param request
     * @param expReqDist   申请单扩展行信息
     * @param expMoReqType 申请单类型信息
     * @param expReqHeader 申请单头信息
     * @param expReqLine   申请单行信息
     * @return 更新后的分配行
     * @throws RuntimeException exception
     * @author jiangx 2019/4/3 11:04
     */
    ExpRequisitionDist updateDistLine(IRequest request, ExpRequisitionDist expReqDist, ExpMoReqType expMoReqType, ExpRequisitionHeader expReqHeader, ExpRequisitionLine expReqLine);

    /**
     * <p>
     * 重算分配行
     * <p/>
     *
     * @param request
     * @param expReqLine   申请单行信息
     * @param expMoReqType 申请单类型信息
     * @param expReqHeader 申请单头信息
     * @return 重算后的分配行
     * @author jiangxz 2019/4/3 10:40
     */
    ExpRequisitionDist resetDistLine(IRequest request, ExpMoReqType expMoReqType, ExpRequisitionHeader expReqHeader, ExpRequisitionLine expReqLine);



    /**
     * <p>
     * 更新分配行的维度
     * <p/>
     *
     * @param request request请求
     * @param reqLine 标准行DTO
     * @return 返回更新完后的分配行List
     * @author jiangxz 2019/4/16 14:29
     */
     List<ExpRequisitionDist> updateDisLineDim(IRequest request, ExpRequisitionLine reqLine);


}