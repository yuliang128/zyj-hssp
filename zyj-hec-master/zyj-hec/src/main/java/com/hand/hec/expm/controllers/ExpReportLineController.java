package com.hand.hec.expm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.BeanUtil;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoRepEleRefLnDim;
import com.hand.hec.exp.dto.ExpMoRepEleRefLnObj;
import com.hand.hec.exp.dto.ExpMoRepTypeRefEle;
import com.hand.hec.exp.service.IExpMoRepEleRefLnDimService;
import com.hand.hec.exp.service.IExpMoRepEleRefLnObjService;
import com.hand.hec.exp.service.IExpMoRepTypeRefEleService;
import com.hand.hec.expm.dto.ExpReportLine;
import com.hand.hec.expm.dto.ExpReportObject;
import com.hand.hec.expm.dto.ExpReportPageElement;
import com.hand.hec.expm.service.IExpReportLineService;
import com.hand.hec.expm.service.IExpReportObjectService;
import com.hand.hec.expm.service.IExpReportPageElementService;
import com.hand.hec.fnd.dto.FndDimensionValue;
import com.hand.hec.vat.service.IVatInvoiceRelationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * ExpReportLineController
 * </p>
 *
 * @author yang.duan 2019/01/10 14:58
 */

@Controller
@RequestMapping(value = "")
public class ExpReportLineController extends BaseController {

    @Autowired
    private IExpReportLineService service;

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

    @Autowired
    private IVatInvoiceRelationService vatInvoiceRelationService;

    @RequestMapping(value = "/exp/report-line/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpReportLine dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/exp/report-line/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpReportLine> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/report-line/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpReportLine> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询默认报销类型
     *
     * @param page
     * @param pageSize
     * @param request
     * @return 默认报销类型
     * @author yang.cai 2019-3-6 9:54
     */
    @RequestMapping(value = "/exp/report-line/queryExpenseTypeDefault", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryExpenseTypeDefault(String pageElementCode, Long moExpReportTypeId, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Long companyId = requestContext.getCompanyId();
        return new ResponseData(service.queryExpenseTypeDefault(requestContext, moExpReportTypeId, pageElementCode, companyId, page, pageSize));
    }

    /**
     * 查询报销类型
     *
     * @param page
     * @param pageSize
     * @param request
     * @return 报销类型
     * @author yang.cai 2019-3-6 9:54
     */
    @RequestMapping(value = "/exp/report-line/queryExpenseType", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryExpenseType(String pageElementCode, Long moExpReportTypeId, Long companyId, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryExpenseType(requestContext, moExpReportTypeId, pageElementCode, companyId, page, pageSize));
    }

    //    /**
    //     * 查询税种代码
    //     *
    //     * @param dto
    //     * @param page
    //     * @param pageSize
    //     * @param request
    //     * @author yang.cai 2019-3-6 9:54
    //     * @return 税种代码
    //     */
    //    @RequestMapping(value = "/exp/report-line/queryTaxTypeCode", method = {RequestMethod.GET, RequestMethod.POST})
    //    @ResponseBody
    //    public ResponseData queryTaxTypeCode(ExpReportLine dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
    //                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
    //        IRequest requestContext = createRequestContext(request);
    //        return new ResponseData(service.queryTaxTypeCode(requestContext, dto, page, pageSize));
    //    }

    @RequestMapping(value = "/exp/report-line/queryExpenseItem")
    @ResponseBody
    public ResponseData queryExpenseItem(String pageElementCode, Long moExpenseTypeId, Long moExpReportTypeId, Long companyId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryExpenseItem(requestContext, moExpReportTypeId, moExpenseTypeId, pageElementCode, companyId));
    }

    @RequestMapping(value = "/exp/report-line/reportLineQuery")
    @ResponseBody
    public ResponseData queryReportLine(Long expReportHeaderId, String reportPageElementCode, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<ExpReportLine> lineList = new ArrayList<>();
        lineList = service.reportLineQuery(requestContext, expReportHeaderId, reportPageElementCode, page, pageSize);
        // 设置费用对象
        Map objectMap = new HashMap();
        List<Map> lineMapList = new ArrayList<>();
        Map lineMap = new HashMap();
        if (lineList != null && !lineList.isEmpty()) {
            for (ExpReportLine line : lineList) {
                lineMap = BeanUtil.convert2Map(line);
                lineMap.replace("dateFrom", DateUtils.date2Str((Date) lineMap.get("dateFrom"), null));
                lineMap.replace("dateTo", DateUtils.date2Str((Date) lineMap.get("dateTo"), null));
                List<ExpReportObject> objectList = expReportObjectService.queryLnObjectByReport(requestContext, expReportHeaderId, line.getExpReportLineId());
                if (objectList != null && !objectList.isEmpty()) {
                    for (ExpReportObject object : objectList) {
                        objectMap.put(object.getDisplayField(), object.getMoExpenseObjectName());
                        objectMap.put(object.getReturnField(), object.getMoExpenseObjectId());
                    }
                }
                lineMap.putAll(objectMap);
                Map vatMap = vatInvoiceRelationService.getExpRefInvoiceLine(requestContext, line.getExpReportLineId());
                if (vatMap != null) {
                    lineMap.putAll(vatMap);
                }
                lineMapList.add(lineMap);
            }
        }
        return new ResponseData(lineMapList);
    }

    @RequestMapping(value = "/exp/report-line/reportLineDelete")
    @ResponseBody
    public ResponseData deleteReportLine(@RequestBody List<ExpReportLine> dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        if (dto != null && !dto.isEmpty()) {
            for (ExpReportLine line : dto) {
                service.deleteExpReportLine(requestContext, line);
            }
        }
        return new ResponseData(dto);
    }

    @RequestMapping(value = "/expm/EXP5110/exp_report_maintain_standard_lines.screen.ftl")
    public ModelAndView expReportStandardLinesView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        IRequest requestContext = createRequestContext(request);
        Long companyId = (Long) request.getAttribute("companyId");
        Long bgtEntityId = (Long) request.getAttribute("bgtEntityId");
        Long accEntityId = (Long) request.getAttribute("accEntityId");
        Long reportTypeId = (Long) request.getAttribute("reportTypeId");
        // 获取行维度Field
        List<Map> lnDim = eleRefLnDimService.queryDftDimValue(requestContext, companyId,bgtEntityId,accEntityId, reportTypeId, "STANDARD");
        if (lnDim != null && !lnDim.isEmpty()) {
            view.addObject("standard_line_dimensions", lnDim);
        }
        // 获取默认费用对象Field
        List<Map> lnObjList = eleRefLnObjService.queryLnDftObject(requestContext, reportTypeId, "STANDARD");
        if (lnObjList != null && !lnObjList.isEmpty()) {
            view.addObject("standard_line_objects", lnObjList);
        }
        // 获取行描述
        ExpReportPageElement pageElement = new ExpReportPageElement();
        pageElement.setReportPageElementCode("STANDARD");
        List<ExpReportPageElement> pageElementList = pageElementService.select(requestContext, pageElement, 1, 0);
        if (pageElementList != null && !pageElementList.isEmpty()) {
            view.addObject("standardLineDescription", pageElementList);
        }
        //获取税率和发票标志
        List<ExpMoRepTypeRefEle> repTypeRefEles = expMoRepTypeRefEleService.queryInvoiceAndTaxFlag(reportTypeId, "STANDARD");
        if (repTypeRefEles != null && !repTypeRefEles.isEmpty()) {
            view.addObject("standardLineElement", repTypeRefEles);
        }
        return view;
    }

    @RequestMapping(value = "/expm/EXP5110/exp_report_view_standard_lines.screen.ftl")
    public ModelAndView expReportViewStandardLinesView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        IRequest requestContext = createRequestContext(request);
        Long companyId = (Long) request.getAttribute("companyId");
        Long bgtEntityId = (Long) request.getAttribute("bgtEntityId");
        Long accEntityId = (Long) request.getAttribute("accEntityId");
        Long reportTypeId = (Long) request.getAttribute("reportTypeId");
        // 获取行维度Field
        List<Map> lnDim = eleRefLnDimService.queryDftDimValue(requestContext, companyId, bgtEntityId, accEntityId, reportTypeId, "STANDARD");

        if (lnDim != null && !lnDim.isEmpty()) {
            view.addObject("standard_line_dimensions", lnDim);
        }
        // 获取默认费用对象Field
        List<Map> lnObjList = eleRefLnObjService.queryLnDftObject(requestContext, reportTypeId, "STANDARD");
        if (lnObjList != null && !lnObjList.isEmpty()) {
            view.addObject("standard_line_objects", lnObjList);
        }
        // 获取行描述
        ExpReportPageElement pageElement = new ExpReportPageElement();
        pageElement.setReportPageElementCode("STANDARD");
        List<ExpReportPageElement> pageElementList = pageElementService.select(requestContext, pageElement, 1, 0);
        if (pageElementList != null && !pageElementList.isEmpty()) {
            view.addObject("standardLineDescription", pageElementList);
        }
        return view;
    }

    @RequestMapping(value = "/expm/EXP5110/exp_report_maintain_standard_lines_extend.screen")
    public ModelAndView expReportStandardLinesExtendView(HttpServletRequest request, @RequestParam Long moExpReportTypeId, @RequestParam Long companyId, @RequestParam Long bgtEntityId, @RequestParam Long accEntityId) {
        ModelAndView view = new ModelAndView("expm/EXP5110/exp_report_maintain_standard_lines_extend");
        IRequest requestContext = createRequestContext(request);
        // 获取行维度Field
        List<Map> lnDim = eleRefLnDimService.queryDftDimValue(requestContext, companyId,bgtEntityId,accEntityId, moExpReportTypeId, "STANDARD");
        if (lnDim != null && !lnDim.isEmpty()) {
            view.addObject("standard_line_dimensions", lnDim);
        }
        // 获取默认费用对象Field
        List<Map> lnObjList = eleRefLnObjService.queryLnDftObject(requestContext, moExpReportTypeId, "STANDARD");
        if (lnObjList != null && !lnObjList.isEmpty()) {
            view.addObject("standard_line_objects", lnObjList);
        }
        return view;
    }

    @RequestMapping(value = "/expm/EXP5110/exp_report_maintain_standard_lines_view_extend.screen")
    public ModelAndView expReportStandardLinesViewExtendView(HttpServletRequest request, @RequestParam Long moExpReportTypeId, @RequestParam Long companyId, @RequestParam Long bgtEntityId, @RequestParam Long accEntityId) {
        ModelAndView view = new ModelAndView("expm/EXP5110/exp_report_maintain_standard_lines_view_extend");
        IRequest requestContext = createRequestContext(request);
        // 获取行维度Field
        List<Map> lnDim = eleRefLnDimService.queryDftDimValue(requestContext, companyId,bgtEntityId,accEntityId, moExpReportTypeId, "STANDARD");
        if (lnDim != null && !lnDim.isEmpty()) {
            view.addObject("standard_line_dimensions", lnDim);
        }
        // 获取默认费用对象Field
        List<Map> lnObjList = eleRefLnObjService.queryLnDftObject(requestContext, moExpReportTypeId, "STANDARD");
        if (lnObjList != null && !lnObjList.isEmpty()) {
            view.addObject("standard_line_objects", lnObjList);
        }
        return view;
    }
}
