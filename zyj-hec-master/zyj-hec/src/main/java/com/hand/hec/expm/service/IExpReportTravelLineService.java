package com.hand.hec.expm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportLine;
import com.hand.hec.expm.dto.ExpReportTravelLine;
/**
 * <p>
 * IExpReportTravelLineService
 * </p>
 *
 * @author yang.duan 2019/01/10 15:07
 */
public interface IExpReportTravelLineService extends IBaseService<ExpReportTravelLine>, ProxySelf<IExpReportTravelLineService>{

    /**
     * <p>报销单差旅行新增</p>
     *
     * @param request
     * @param header 报销单头
     * @param line 报销单行
     * @param reportType 报销单类型
     * @param travelLine 差旅行
     * @return ExpReportTravelLine
     * @throws RuntimeException
     * @author yang.duan 2019/6/10 10:05
    **/
    ExpReportTravelLine insertTravelLine(IRequest request,
            ExpReportHeader header, ExpReportLine line,
            ExpMoReportType reportType, ExpReportTravelLine travelLine) throws RuntimeException;

    /**
     * <p>报销单差旅行更新</p>
     *
     * @param request
     * @param header 报销单头
     * @param line 报销单行
     * @param reportType 报销单类型
     * @param travelLine 差旅行
     * @return ExpReportTravelLine
     * @throws RuntimeException
     * @author yang.duan 2019/6/10 10:05
     **/
    ExpReportTravelLine updateTravelLine(IRequest request,
            ExpReportHeader header, ExpReportLine line,
            ExpMoReportType reportType, ExpReportTravelLine travelLine) throws RuntimeException;

}