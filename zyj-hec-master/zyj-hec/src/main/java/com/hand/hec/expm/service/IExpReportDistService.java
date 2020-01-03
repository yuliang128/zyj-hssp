package com.hand.hec.expm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.expm.dto.ExpReportDist;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportLine;

import java.util.List;

/**
 * <p>
 * IExpReportDistService
 * </p>
 *
 * @author yang.duan 2019/01/10 15:05
 */
public interface IExpReportDistService extends IBaseService<ExpReportDist>, ProxySelf<IExpReportDistService> {
    /**
     * <p>
     * 更新分配行的维度
     * <p/>
     *
     * @param request request请求
     * @param reportLine 标准行DTO
     * @return 返回更新完后的分配行List
     * @author yang.duan 2019/3/7 14:29
     */
    List<ExpReportDist> updateDisLineDim(IRequest request, ExpReportLine reportLine);

    /**
     * <p>
     * 报销单分配行新增
     * <p/>
     *
     * @param request
     * @param reportDist 需要保存的分配行信息
     * @param reportType 报销单类型
     * @param header 报销单头
     * @param line 报销单行
     * @return 返回新增后的分配行信息
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/7 15:11
     */
    ExpReportDist insertDisLine(IRequest request, ExpReportDist reportDist, ExpMoReportType reportType,
                                ExpReportHeader header,ExpReportLine line) throws RuntimeException;


    /**
     * <p>
     * 报销单分配行更新
     * <p/>
     *
     * @param request
     * @param reportDist 需要保存的分配行信息
     * @param reportType 报销单类型
     * @param header 报销单头
     * @param line 报销单行
     * @return 更新后的分配行
     * @throws RuntimeException exception description
     * @author yang.duan 2019/3/8 11:04
     */
    ExpReportDist updateDisLine(IRequest request, ExpReportDist reportDist, ExpMoReportType reportType,
                    ExpReportHeader header,ExpReportLine line) throws RuntimeException;


    /**
     * <p>
     * 重算分配行
     * <p/>
     *
     * @param request
     * @param line 报销单行信息
     * @param header 报销单头信息
     * @param reportType 报销单类型
     * @return 重算后的分配行
     * @author yang.duan 2019/3/8 10:40
     */
    ExpReportDist resetDisLine(IRequest request, ExpReportLine line,ExpReportHeader header, ExpMoReportType reportType);

    /**
     * <p>报销单分配行删除<p/>
     *
     * @param request
     * @param dto
     * @return
     * @author yang.duan 2019/3/29 14:06
     */
    int deleteExpReportDist(IRequest request,ExpReportDist dto);

}
