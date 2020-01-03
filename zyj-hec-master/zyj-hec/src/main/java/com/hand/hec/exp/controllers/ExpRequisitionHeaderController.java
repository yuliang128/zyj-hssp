package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.BeanUtil;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoReqTypeRefEle;
import com.hand.hec.exp.dto.ExpMoReqTypeRefHdDim;
import com.hand.hec.exp.dto.ExpRequisitionHeader;
import com.hand.hec.exp.service.IExpMoReqTypeRefEleService;
import com.hand.hec.exp.service.IExpMoReqTypeRefHdDimService;
import com.hand.hec.exp.service.IExpRequisitionHeaderService;
import com.hand.hec.fnd.dto.FndDimension;
import com.hand.hec.fnd.service.IFndDimensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
public class ExpRequisitionHeaderController extends BaseController {

    @Autowired
    private IExpRequisitionHeaderService service;

    @Autowired
    private IExpMoReqTypeRefHdDimService expMoReqTypeRefHdDimService;

    @Autowired
    private IFndDimensionService fndDimensionService;

    @Autowired
    private IExpMoReqTypeRefEleService expMoReqTypeRefEleService;


    @RequestMapping(value = "/exp/requisition-header/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpRequisitionHeader dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/exp/requisition-header/queryExpRequisitionMain", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryExpRequisitionMain(ExpRequisitionHeader dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryExpRequisitionMain(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/exp/requisition-header/expRequisitionHeaderQuery", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData expRequisitionHeaderQuery(@RequestParam("expRequisitionHeaderId") Long expRequisitionHeaderId, @RequestParam("moExpReqTypeId") Long moExpReqTypeId, @RequestParam("accEntityId") Long accEntityId, @RequestParam("paymentCurrencyCode") String paymentCurrencyCode, @RequestParam("employeeId") Long employeeId, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.expRequisitionHeaderQuery(requestContext, expRequisitionHeaderId, moExpReqTypeId, accEntityId, paymentCurrencyCode, employeeId, page, pageSize));
    }

    @RequestMapping(value = "/expm/EXP4010/exp_requisition_maintain_main.screen")
    public ModelAndView expRequisitionMaintainMainView(@RequestParam(value = "expRequisitionHeaderId", defaultValue = "-1") Long expRequisitionHeaderId, @RequestParam("moExpReqTypeId") Long moExpReqTypeId, @RequestParam("accEntityId") Long accEntityId, @RequestParam("paymentCurrencyCode") String paymentCurrencyCode, @RequestParam("employeeId") Long employeeId, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ModelAndView view = new ModelAndView("expm/EXP4010/exp_requisition_maintain_main");
        //获取头信息
        List<ExpRequisitionHeader> headers = service.expRequisitionHeaderQuery(requestContext, expRequisitionHeaderId, moExpReqTypeId, accEntityId, paymentCurrencyCode, employeeId, page, pageSize);
        if (headers != null && !headers.isEmpty()) {
            List<Map> headerList = new ArrayList<>();
            Map headerMap = BeanUtil.convert2Map(headers.get(0));
            headerList.add(headerMap);
            view.addObject("header_info", headerList);
        }
        //获取单据类型分配的页面信息
        List<ExpMoReqTypeRefEle> expMoReqTypeRefEleList = expMoReqTypeRefEleService.expReqEleQuery(requestContext, moExpReqTypeId, expRequisitionHeaderId.intValue() == -1 ? null : expRequisitionHeaderId);
        if (expMoReqTypeRefEleList != null && !expMoReqTypeRefEleList.isEmpty()) {
            view.addObject("elements", expMoReqTypeRefEleList);
        }
        //获取头维度
        ExpMoReqTypeRefHdDim hdDim = new ExpMoReqTypeRefHdDim();
        hdDim.setMoExpReqTypeId(headers.get(0).getMoExpReqTypeId());
        Criteria hdDimCriteria = new Criteria(hdDim);
        List<ExpMoReqTypeRefHdDim> hdDimList = expMoReqTypeRefHdDimService.selectOptions(requestContext, hdDim, hdDimCriteria);
        if (hdDimList != null && !hdDimList.isEmpty()) {
            for (ExpMoReqTypeRefHdDim hd : hdDimList) {
                if ("N".equals(hd.getEnabledFlag())) {
                    hdDimList.remove(hd);
                } else {
                    FndDimension dimension = new FndDimension();
                    dimension.setDimensionId(hd.getDimensionId());
                    dimension = fndDimensionService.selectByPrimaryKey(requestContext, dimension);
                    if (dimension != null) {
                        hd.setReturnField("dimension" + dimension.getDimensionSequence() + "Id");
                        hd.setDisplayField("dimension" + dimension.getDimensionSequence() + "Name");
                    }
                }
            }
            if (!hdDimList.isEmpty()) {
                view.addObject("headerDimensionFields", hdDimList);
            }
        }
        return view;
    }

    @RequestMapping(value = "/exp/requisition-header/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpRequisitionHeader> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        //return new ResponseData(service.batchUpdate(requestCtx, dto));
        return new ResponseData(service.saveReportHeader(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/requisition-header/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpRequisitionHeader> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/exp/requisition-header/queryByPayReqHeaderId", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByPayReqHeaderId(Long headerId, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByPayReqHeaderId(requestContext, headerId, page, pageSize));
    }


    @RequestMapping(value = "/exp/requisition-header/queryExp5360", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryExp5360(ExpRequisitionHeader dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryExp5360(requestContext, dto, page, pageSize));
    }


    @RequestMapping(value = "/exp/requisition-header/queryDetailHead", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryDetailHead(Long headId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryDetailHead(requestContext, headId));
    }

    /**
     * 费用申请财务关闭  关闭逻辑
     *
     * @param head 借款申请单表头实体list
     * @return List<CshPaymentRequisitionHd>
     * @author weikun.wang2019-03-25 14:04
     */
    @RequestMapping(value = "/exp/requisition-header/closeDetailHead", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData closeDetailHead(List<ExpRequisitionHeader> head, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        service.closeDetailHead(requestContext, head);
        return new ResponseData();
    }

    @RequestMapping(value = "/exp/requisition-header/expReportFromReqHeaderQuery")
    @ResponseBody
    public ResponseData expReportFromReqHeaderQuery(HttpServletRequest request, @RequestParam Long moExpReportTypeId, @RequestParam String paymentCurrencyCode, @RequestParam(required = false) String expRequisitionNumber, @RequestParam(required = false) String description) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.expReportFromReqHeaderQuery(requestContext, moExpReportTypeId, expRequisitionNumber, paymentCurrencyCode, description));
    }
}