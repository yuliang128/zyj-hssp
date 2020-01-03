package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReqType;
import com.hand.hec.exp.dto.ExpRequisitionHeader;
import com.hand.hec.exp.dto.ExpRequisitionLine;
import com.hand.hec.exp.dto.ExpRequisitionObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IExpRequisitionLineService extends IBaseService<ExpRequisitionLine>, ProxySelf<IExpRequisitionLineService> {

    List<Map> queryDetailLine(IRequest request, long headId);

    /**
     * 申请单行信息查询
     *
     * @param expRequisitionHeaderId 申请单头id
     * @param request
     * @return List<ExpRequisitionLine>
     * @author jiangxuzheng@hand-china.com 2019-03-28 10:00
     */
    List<ExpRequisitionLine> expReqLineQuery(IRequest request, long expRequisitionHeaderId);

    /**
     * <p>
     * 校验非申请单数量和金额
     * <p/>
     *
     * @param aBigDecimal 行金额/数量
     * @return void
     * @throws RuntimeException exception
     * @author jiangxz 2019/4/3 10:57
     */
     void signCheck(BigDecimal aBigDecimal);
    /**
     * <p>
     * 计算金额
     * <p/>
     *
     * @param businessPrice          业务单价
     * @param primaryQuantity        业务数量
     * @param businessCurrencyCode   业务币种
     * @param biz2payExchangeRate    业务币种->支付币种汇率
     * @param paymentCurrencyCode    支付币种
     * @param pay2magExchangeRate    支付币种->管理币种汇率
     * @param managementCurrencyCode 管理币种
     * @param pay2funExchangeRate    支付币种->本位币汇率
     * @param funCurrencyCode        本位币币种
     * @return 返回带有各个金额属性的申请单行DTO
     * @author jiangxz 2019/4/3 10:00
     */
     ExpRequisitionLine calcAmount(BigDecimal businessPrice, BigDecimal primaryQuantity, String businessCurrencyCode, BigDecimal biz2payExchangeRate, String paymentCurrencyCode, BigDecimal pay2magExchangeRate, String managementCurrencyCode, BigDecimal pay2funExchangeRate, String funCurrencyCode);
    /**
     * <p>
     * 申请单行新增
     * <p/>
     *
     * @param request
     * @param expReqLine   需要新增的申请单行信息
     * @param expReqHeader 申请单头信息
     * @param expMoReqType 申请单类型
     * @return 新增后的行信息
     * @throws RuntimeException exception
     * @author jiangxz 2019/4/3 9:58
     */
    ExpRequisitionLine insertExpRequisitionLine(IRequest request, ExpRequisitionLine expReqLine, ExpRequisitionHeader expReqHeader, ExpMoReqType expMoReqType) throws RuntimeException;

    /**
     * <p>
     * 申请单行更新
     * <p/>
     *
     * @param request
     * @param expReqLine   需要更新的申请单行信息
     * @param expReqHeader 申请单头信息
     * @param expMoReqType 申请单类型
     * @return 更新后的行信息
     * @throws RuntimeException exception
     * @author jiangxz 2019/4/3 9:58
     */
    ExpRequisitionLine updateExpRequisitionLine(IRequest request, ExpRequisitionLine expReqLine, ExpRequisitionHeader expReqHeader, ExpMoReqType expMoReqType) throws RuntimeException;


    /**
     * <p>
     * 申请单行费用对象保存
     * <p/>
     *
     * @param request 请求
     * @param expReqLine 申请单行信息
     * @return 保存后的行费用对象list
     * @throws RuntimeException exception
     * @author jiangxz 2019/3/13 10:41
     */
     List<ExpRequisitionObject> saveLineObject(IRequest request, ExpRequisitionLine expReqLine) throws RuntimeException;

    /**
     * <p>
     * 更新行维度
     * <p/>
     *
     * @param request
     * @param header  头信息
     * @return 更新完后的行list
     * @author jiangxz 2019/4/16 9:54
     */

     List<ExpRequisitionLine> updateLineDim(IRequest request, ExpRequisitionHeader header);
    /**
     * <p>
     * 标准行保存(包括费用对象保存)
     * <p/>
     *
     * @param request 请求
     * @param header 申请头信息
     * @param reqType 申请单类型
     * @return 保存后的行list
     * @throws RuntimeException exception
     * @author jiangxz 2019/4/16 15:17
     */
     List<ExpRequisitionLine> saveStandardLines(IRequest request, ExpRequisitionHeader header, ExpMoReqType reqType);
    /**
     * <p>
     * 申请单行删除
     * <p/>
     *
     * @param request
     * @param line
     * @return
     * @author jiangxz 2019/4/16 10:30
     */
     int deleteLine(IRequest request, ExpRequisitionLine line);

    /**
     * <p>报销单关联申请单行查询(头关联)</p>
     *
     * @param reportPageElementCode
     * @param expRequisitionHeaderId
     * @param moExpReportTypeId
     * @param monopolizeFlag
     * @param paymentCurrencyCode
     * @param uncompletelyReleasedFlag
     * @return List<Map>
     * @author yang.duan 2019/4/26 14:34
     **/
    List<Map> expReportFromReqQuery(IRequest request, String reportPageElementCode,  Long moExpReportTypeId, Long expRequisitionHeaderId, String uncompletelyReleasedFlag, String paymentCurrencyCode, String monopolizeFlag);
}