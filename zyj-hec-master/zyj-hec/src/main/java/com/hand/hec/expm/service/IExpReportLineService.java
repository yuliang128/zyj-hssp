package com.hand.hec.expm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoExpenseItem;
import com.hand.hec.exp.dto.ExpMoExpenseType;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportLine;
import com.hand.hec.expm.dto.ExpReportObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * IExpReportLineService
 * </p>
 *
 * @author yang.duan 2019/01/10 15:06
 */
public interface IExpReportLineService extends IBaseService<ExpReportLine>, ProxySelf<IExpReportLineService> {

    /**
     * 查询默认报销类型
     * 
     * @param request
     * @param moExpReportTypeId
     * @param pageElementCode
     * @param companyId
     * @param page
     * @param pageSize
     * @author yang.cai 2019-3-6 9:54
     * @return 默认报销类型
     */
    List<ExpMoExpenseType> queryExpenseTypeDefault(IRequest request, Long moExpReportTypeId, String pageElementCode,
                    Long companyId, Integer page, Integer pageSize);

    List<ExpMoExpenseItem> queryExpenseItem(IRequest request, Long moExpReportTypeId, Long moExpenseTypeId,
                    String pageElementCode, Long companyId);

    /**
     * 查询报销类型
     * 
     * @param request
     * @param moExpReportTypeId
     * @param pageElementCode
     * @param companyId
     * @param page
     * @param pageSize
     * @author yang.cai 2019-3-6 9:54
     * @return 报销类型
     */
    List<ExpMoExpenseType> queryExpenseType(IRequest request, Long moExpReportTypeId, String pageElementCode,
                    Long companyId, Integer page, Integer pageSize);

//    /**
//     * 查询税种代码
//     *
//     * @param request
//     * @param expReportLine
//     * @param page
//     * @param pageSize
//     * @author yang.cai 2019-3-6 9:54
//     * @return 税种代码
//     */
//    List<ExpReportLine> queryTaxTypeCode(IRequest request, ExpReportLine expReportLine, Integer page, Integer pageSize);

    /**
     * <p>
     * 更新行维度
     * <p/>
     *
     * @param request
     * @param header 头信息
     * @return 更新完后的行list
     * @author yang.duan 2019/3/11 9:54
     */
    List<ExpReportLine> updateLineDim(IRequest request, ExpReportHeader header);

    /**
     * <p>
     * 报销单行新增
     * <p/>
     *
     * @param request
     * @param line 需要插入的报销单行信息
     * @param header 报销单头信息
     * @param reportType 报销单类型
     * @return 插入后的报销单行信息
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/11 9:56
     */
    ExpReportLine insertStandardLine(IRequest request, ExpReportLine line, ExpReportHeader header,
                    ExpMoReportType reportType) throws RuntimeException;

    /**
     * <p>
     * 报销单行更新
     * <p/>
     *
     * @param request
     * @param line 需要更新的报销单行信息
     * @param header 报销单头信息
     * @param reportType 报销单类型
     * @return 更新后的行信息
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/11 9:58
     */
    ExpReportLine updateStandardLine(IRequest request, ExpReportLine line, ExpReportHeader header,
                    ExpMoReportType reportType) throws RuntimeException;

    /**
     * <p>
     * 报销单行费用对象保存
     * <p/>
     *
     * @param request 请求
     * @param line 报销单行信息
     * @return 保存后的行费用对象list
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/13 10:41
     */
    List<ExpReportObject> saveLineObject(IRequest request, ExpReportLine line) throws RuntimeException;


    /**
     * <p>
     * 校验非调整类报销单数量和金额
     * <p/>
     *
     * @param reportType 报销单类型DTO
     * @param number 行金额/数量
     * @return void
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/6 16:57
     */
    void signCheck(ExpMoReportType reportType, BigDecimal number) throws RuntimeException;

    /**
     * <p>
     * 计算金额
     * <p/>
     *
     * @param businessPrice 业务单价
     * @param primaryQuantity 业务数量
     * @param businessCurrencyCode 业务币种
     * @param biz2payExchangeRate 业务币种->支付币种汇率
     * @param paymentCurrencyCode 支付币种
     * @param pay2magExchangeRate 支付币种->管理币种汇率
     * @param managementCurrencyCode 管理币种
     * @param pay2funExchangeRate 支付币种->本位币汇率
     * @param funCurrencyCode 本位币币种
     * @return 返回带有各个金额属性的报销单行DTO
     * @author yang.duan 2019/3/6 16:53
     */
    ExpReportLine calcAmount(BigDecimal businessPrice, BigDecimal primaryQuantity, String businessCurrencyCode,
                    BigDecimal biz2payExchangeRate, String paymentCurrencyCode, BigDecimal pay2magExchangeRate,
                    String managementCurrencyCode, BigDecimal pay2funExchangeRate, String funCurrencyCode);

    /**
     * <p>
     * 费用报销单行删除
     * <p/>
     *
     * @param request
     * @param dto
     * @return
     * @author yang.duan 2019/3/29 13:38
     */
    int deleteExpReportLine(IRequest request, ExpReportLine dto);


    /**
     * <p>
     * 查询报销单行主数据
     * <p/>
     * 
     * @param request
     * @param expReportHeaderId 报销单头ID
     * @param reportPageElementCode 页面元素类型code
     * @param pageNum 页码
     * @param pageSize 分页大小
     * @return 报销单行list
     * @author yang.duan 2019/4/3 13:22
     */
    List<ExpReportLine> reportLineQuery(IRequest request, Long expReportHeaderId, String reportPageElementCode,
                     int pageNum, int pageSize);

}
