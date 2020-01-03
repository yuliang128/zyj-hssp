package com.hand.hec.expm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoRepTypeRefEle;
import com.hand.hec.exp.service.IExpMoRepEleRefLnDimService;
import com.hand.hec.exp.service.IExpMoRepEleRefLnObjService;
import com.hand.hec.exp.service.IExpMoRepTypeRefEleService;
import com.hand.hec.expm.dto.ExpReportPageElement;
import com.hand.hec.expm.dto.ExpReportTravelLine;
import com.hand.hec.expm.service.IExpReportObjectService;
import com.hand.hec.expm.service.IExpReportPageElementService;
import com.hand.hec.expm.service.IExpReportTravelLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ExpReportTravelLineController
 * </p>
 *
 * @author yang.duan 2019/01/10 14:59
 */

@Controller
@RequestMapping(value = "")
public class ExpReportTravelLineController extends BaseController{

    @Autowired
    private IExpReportTravelLineService service;

    @Autowired
    private IExpMoRepEleRefLnDimService eleRefLnDimService;

    @Autowired
    private IExpMoRepEleRefLnObjService eleRefLnObjService;

    @Autowired
    private IExpReportObjectService expReportObjectService;

    @Autowired
    private IExpReportPageElementService pageElementService;

    @Autowired
    private IExpMoRepTypeRefEleService expMoRepTypeRefEleService;

    @RequestMapping(value = "/exp/report-travel-line/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpReportTravelLine dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/exp/report-travel-line/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpReportTravelLine> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/report-travel-line/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ExpReportTravelLine> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value="/expm/EXP5110/exp_report_maintain_travel_lines.screen.ftl")
    public ModelAndView expReportMaintainTravelLinesView(HttpServletRequest request){
        ModelAndView view = new ModelAndView();
        IRequest requestContext = createRequestContext(request);
        Long companyId = (Long) request.getAttribute("companyId");
        Long bgtEntityId = (Long) request.getAttribute("bgtEntityId");
        Long accEntityId = (Long) request.getAttribute("accEntityId");
        Long reportTypeId = (Long) request.getAttribute("reportTypeId");
        // 获取行维度Field
        List<Map> lnDim = eleRefLnDimService.queryDftDimValue(requestContext, companyId,bgtEntityId,accEntityId, reportTypeId, "TRAVEL_LINES");
        if (lnDim != null && !lnDim.isEmpty()) {
            view.addObject("travel_line_dimensions", lnDim);
        }
        // 获取默认费用对象Field
        List<Map> lnObjList = eleRefLnObjService.queryLnDftObject(requestContext, reportTypeId, "TRAVEL_LINES");
        if (lnObjList != null && !lnObjList.isEmpty()) {
            view.addObject("travel_line_objects", lnObjList);
        }
        // 获取行描述
        ExpReportPageElement pageElement = new ExpReportPageElement();
        pageElement.setReportPageElementCode("TRAVEL_LINES");
        List<ExpReportPageElement> pageElementList = pageElementService.select(requestContext, pageElement, 1, 0);
        if (pageElementList != null && !pageElementList.isEmpty()) {
            view.addObject("travelLinesDescription", pageElementList);
        }
        //获取税率和发票标志
        List<ExpMoRepTypeRefEle> repTypeRefEles = expMoRepTypeRefEleService.queryInvoiceAndTaxFlag(reportTypeId, "TRAVEL_LINES");
        if (repTypeRefEles != null && !repTypeRefEles.isEmpty()) {
            view.addObject("travelLineElement", repTypeRefEles);
        }
        return view;
    }
}