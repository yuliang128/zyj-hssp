package com.hand.hec.expm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.expm.dto.ExpReportPageElement;
import com.hand.hec.expm.dto.ExpReportPmtSchedule;
import com.hand.hec.expm.service.IExpReportPageElementService;
import com.hand.hec.expm.service.IExpReportPmtScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * ExpReportPmtScheduleController
 * </p>
 *
 * @author yang.duan 2019/01/10 14:58
 */

@Controller
@RequestMapping(value = "")
public class ExpReportPmtScheduleController extends BaseController {

    @Autowired
    private IExpReportPmtScheduleService service;

    @Autowired
    private IExpReportPageElementService pageElementService;

    @RequestMapping(value = "/exp/report-pmt-schedule/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(Long expReportHeaderId, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryPmtSchedule(requestContext, expReportHeaderId, page, pageSize));
    }

    @RequestMapping(value = "/exp/report-pmt-schedule/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpReportPmtSchedule> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/report-pmt-schedule/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpReportPmtSchedule> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/exp/report-pmt-schedule/deletePmt")
    @ResponseBody
    public ResponseData deletePmt(HttpServletRequest request, @RequestBody List<ExpReportPmtSchedule> dto) {
        IRequest requestCtx = createRequestContext(request);
        if (dto != null && !dto.isEmpty()) {
            for (ExpReportPmtSchedule pmtSchedule : dto) {
                service.deleteExpRepPmtSchedule(requestCtx, pmtSchedule);
            }
        }
        return new ResponseData(dto);
    }

    @RequestMapping(value = "expm/EXP5110/exp_report_maintain_pmt_schedule_lines.screen")
    public ModelAndView pmtScheduleLinesView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView();
        //获取计划付款行描述
        ExpReportPageElement pageElement = new ExpReportPageElement();
        pageElement.setReportPageElementCode("STANDARD_PMT");
        List<ExpReportPageElement> pageElementList = pageElementService.select(requestCtx, pageElement, 1, 0);
        if (pageElementList != null && !pageElementList.isEmpty()) {
            view.addObject("standard_pmt_line_description", pageElementList);
        }
        return view;
    }

    @RequestMapping(value = "expm/EXP5110/exp_report_view_pmt_schedule_lines.screen")
    public ModelAndView pmtScheduleViewLinesView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView();
        //获取计划付款行描述
        ExpReportPageElement pageElement = new ExpReportPageElement();
        pageElement.setReportPageElementCode("STANDARD_PMT");
        List<ExpReportPageElement> pageElementList = pageElementService.select(requestCtx, pageElement, 1, 0);
        if (pageElementList != null && !pageElementList.isEmpty()) {
            view.addObject("standard_pmt_line_description", pageElementList);
        }
        return view;
    }

    @RequestMapping(value = "/exp/report-pmt-schedule/writeOffPrepaymentHeaderQuery")
    @ResponseBody
    public ResponseData writeOffPrepaymentHeaderQuery(HttpServletRequest request, Long paymentScheduleLineId) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.writeOffPrepaymentHeaderQuery(requestCtx, paymentScheduleLineId));
    }

    @RequestMapping(value = "/exp/report-pmt-schedule/writeOffPrepaymentHistoryQuery")
    @ResponseBody
    public ResponseData writeOffPrepaymentHistoryQuery(HttpServletRequest request, Long paymentScheduleLineId, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.writeOffPrepaymentHistoryQuery(requestCtx, paymentScheduleLineId, page, pageSize));
    }
}
