package com.hand.hec.expm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.BeanUtil;
import com.hand.hap.core.web.view.nested.NestedViewExecutionHelper;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hap.exp.service.IExpEmployeeService;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.gld.service.IGldCurrencyService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.service.IBgtPeriodService;
import com.hand.hec.exp.dto.ExpMoExpPolicyDetail;
import com.hand.hec.exp.dto.ExpMoRepTypeRefEle;
import com.hand.hec.exp.dto.ExpMoRepTypeRefHdObj;
import com.hand.hec.exp.service.IExpMoExpPolicyDetailService;
import com.hand.hec.exp.service.IExpMoRepTypeRefEleService;
import com.hand.hec.exp.service.IExpMoRepTypeRefHdDimService;
import com.hand.hec.exp.service.IExpMoRepTypeRefHdObjService;
import com.hand.hec.expm.dto.ExpDocumentAuthority;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportObject;
import com.hand.hec.expm.service.IExpDocumentAuthorityService;
import com.hand.hec.expm.service.IExpReportHeaderService;
import com.hand.hec.expm.service.IExpReportObjectService;
import com.hand.hec.fnd.dto.GldExchangerateType;
import com.hand.hec.fnd.mapper.GldExchangerateTypeMapper;
import com.hand.hec.fnd.mapper.GldPeriodMapper;
import com.hand.hec.fnd.service.IFndDimensionService;
import com.hand.hec.fnd.service.IGldExchangeRateService;
import com.hand.hec.gld.mapper.GldAccountingEntityMapper;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;


/**
 * <p>
 * ExpReportHeaderController
 * </p>
 *
 * @author yang.duan 2019/01/10 14:57
 */

@Controller
@RequestMapping(value = "")
public class ExpReportHeaderController extends BaseController {

    @Autowired
    private IExpReportHeaderService service;

    @Autowired
    private IExpDocumentAuthorityService authorityService;

    @Autowired
    private IFndCompanyService fndCompanyService;

    @Autowired
    private IExpMoRepTypeRefHdObjService expMoRepTypeRefHdObjService;

    @Autowired
    private IExpMoRepTypeRefHdDimService expMoRepTypeRefHdDimService;

    @Autowired
    private IGldCurrencyService gldCurrencyService;

    @Autowired
    private IExpEmployeeAssignService expEmployeeAssignService;

    @Autowired
    private IFndDimensionService fndDimensionService;

    @Autowired
    private IExpMoRepTypeRefEleService expMoRepTypeRefEleService;


    @Autowired
    private GldPeriodMapper gldPeriodMapper;

    @Autowired
    private IExpEmployeeService expEmployeeService;

    @Autowired
    private ISysParameterService sysParameterService;

    @Autowired
    private GldAccountingEntityMapper gldAccountingEntityMapper;

    @Autowired
    private IGldExchangeRateService gldExchangeRateService;

    @Autowired
    private GldExchangerateTypeMapper gldExchangerateTypeMapper;

    @Autowired
    private IExpReportObjectService expReportObjectService;

    @Autowired
    private IBgtPeriodService bgtPeriodService;

    @Autowired
    private IExpMoExpPolicyDetailService expMoExpPolicyDetailService;


    @RequestMapping(value = "/exp/report-header/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpReportHeader dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/exp/report-header/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpReportHeader> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/report-header/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpReportHeader> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/exp/report-header/queryExpReportMain")
    @ResponseBody
    public ResponseData queryExpReportMain(@RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, ExpReportHeader dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        if (dto.getExpReportDateScope() != null) {
            dto.setCreationDate(com.hand.hap.core.util.DateUtils.getDateScopeFrom(dto.getExpReportDateScope()));
        }
        List<ExpReportHeader> headers = service.queryExpReportMain(dto, requestContext, page, pageSize);
        return new ResponseData(headers);
    }

    @RequestMapping(value = "/expm/EXP5110/exp_report_maintain_type_choice.screen")
    public ModelAndView expReportMaintainTypeChoiceView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("expm/EXP5110/exp_report_maintain_type_choice");
        // 获取授权员工
        ExpDocumentAuthority authority = new ExpDocumentAuthority();
        authority.setDocCategory(ExpReportHeader.EXP_REPORT);
        authority.setEmployeeId(requestCtx.getEmployeeId());
        List<ExpEmployee> authEmployeeList = authorityService.queryDocAuthEmployee(requestCtx, authority);
        // 获取当前公司信息
        FndCompany company = new FndCompany();
        company.setCompanyId(requestCtx.getCompanyId());
        List<FndCompany> currentCompany = fndCompanyService.queryFndCompany(requestCtx, company, Integer.valueOf(DEFAULT_PAGE), Integer.valueOf(DEFAULT_PAGE_SIZE));

        company = currentCompany.get(0);
        // 获取当前公司对应的本位币
        String functionalCurrencyCode = gldCurrencyService.getAccEntityFunCurrCode(company.getAccEntityId());
        GldCurrency currency = new GldCurrency();
        currency.setCurrencyCode(functionalCurrencyCode);
        Criteria criteria = new Criteria(currency);
        List<GldCurrency> defaultCurrency = gldCurrencyService.selectOptions(requestCtx, currency, criteria);

        // 获取默认核算主体
        ExpEmployeeAssign employeeAssign = expEmployeeAssignService.getEmployeeAssignInfo(requestCtx, requestCtx.getCompanyId(), requestCtx.getEmployeeId());

        if (employeeAssign != null && employeeAssign.getAccEntityId() != null && employeeAssign.getAccEntityName() != null) {
            for (FndCompany fc : currentCompany) {
                fc.setAccEntityId(employeeAssign.getAccEntityId());
                fc.setDefaultAccEntityName(employeeAssign.getAccEntityName());
            }
        }
        view.addObject("authEmployee", authEmployeeList);
        view.addObject("currentCompany", currentCompany);
        view.addObject("defaultCurrency", defaultCurrency);
        return view;
    }

    @RequestMapping(value = "/expm/EXP5110/exp_report_main.screen")
    public ModelAndView expReportMainView(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("expm/EXP5110/exp_report_main");
        // 获取授权员工
        ExpDocumentAuthority authority = new ExpDocumentAuthority();
        authority.setDocCategory(ExpReportHeader.EXP_REPORT);
        authority.setEmployeeId(requestCtx.getEmployeeId());
        List<ExpEmployee> authEmployeeList = authorityService.queryDocAuthEmployee(requestCtx, authority);
        view.addObject("authEmployee", authEmployeeList);
        return view;
    }

    @RequestMapping(value = "/expm/EXP5110/exp_report_maintain_main.screen")
    public ModelAndView expReportMaintainMainView(HttpServletRequest request, ExpReportHeader dto) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView();
        // 获取报销单头信息
        List<ExpReportHeader> headers = service.expReportHeaderQuery(requestCtx, dto.getExpReportHeaderId(), dto.getMoExpReportTypeId(), dto.getAccEntityId(), dto.getEmployeeId(), dto.getPaymentCurrencyCode());

        // 获取页面元素
        List<ExpMoRepTypeRefEle> eleList = expMoRepTypeRefEleService.queryExpReportLineEle(headers.get(0).getMoExpReportTypeId(), headers.get(0).getExpReportHeaderId());
        List<String> serviceList = new ArrayList<>();
        if (eleList != null && !eleList.isEmpty()) {
            for (ExpMoRepTypeRefEle refEle : eleList) {
                if (refEle.getServiceName() != null) {
                    serviceList.add(refEle.getServiceName());
                }
            }
        }
        // 根据不同行的请求路径去设置对应的model
        request.setAttribute("moExpReportTypeId", headers.get(0).getMoExpReportTypeId());
        request.setAttribute("companyId", headers.get(0).getCompanyId());
        request.setAttribute("accEntityId", headers.get(0).getAccEntityId());
        request.setAttribute("bgtEntityId", headers.get(0).getBgtEntityId());
        request.setAttribute("reportTypeId", headers.get(0).getMoExpReportTypeId());
        if (serviceList != null && !serviceList.isEmpty()) {
            view = NestedViewExecutionHelper.proceedNestedView(serviceList);
        }
        view.setViewName("expm/EXP5110/exp_report_maintain_main");
        view.addObject("elements", eleList);

        // 获取默认头维度信息
        List<Map> hdDimList = expMoRepTypeRefHdDimService.getHeaderDimInfo(requestCtx, headers.get(0).getMoExpReportTypeId());
        view.addObject("headerDimensionFields", hdDimList);


        // 获取默认头费用对象
        ExpMoRepTypeRefHdObj refHdObj = new ExpMoRepTypeRefHdObj();
        refHdObj.setMoExpReportTypeId(headers.get(0).getMoExpReportTypeId());
        Criteria criteria = new Criteria(refHdObj);
        criteria.where(new WhereField(ExpMoRepTypeRefHdObj.FIELD_MO_EXP_REPORT_TYPE_ID, Comparison.EQUAL), new WhereField(ExpMoRepTypeRefHdObj.FIELD_LAYOUT_PRIORITY));
        Map objectMap = expMoRepTypeRefHdObjService.getHeaderObjInfo(requestCtx, refHdObj, criteria);
        List<Map> fieldMap = (List<Map>) objectMap.get("fieldMap");
        Map valueMap = (Map) objectMap.get("valueMap");

        if (headers.get(0).getExpReportHeaderId() != null) {
            List<ExpReportObject> reportObjects = expReportObjectService.queryHdObjectByReport(requestCtx, headers.get(0).getExpReportHeaderId());
            if (reportObjects != null && !reportObjects.isEmpty()) {
                for (ExpReportObject object : reportObjects) {
                    object.setReturnField("#" + object.getMoExpObjTypeId());
                    object.setDisplayField("^#" + object.getMoExpObjTypeId());
                    if (object.getMoExpenseObjectName() != null) {
                        valueMap.replace(object.getDisplayField(), object.getMoExpenseObjectName());
                    }
                }
            }
        }
        if (fieldMap != null) {
            view.addObject("defaultObjectFields", fieldMap);
        }

        List<Map> headerList = new ArrayList<>();
        Map headerMap = BeanUtil.convert2Map(headers.get(0));
        headerMap.putAll(valueMap);
        headerList.add(headerMap);
        view.addObject("header_info", headerList);
        return view;
    }


    @RequestMapping(value = "/expm/EXP5110/exp_report_maintain_travel_main.screen")
    public ModelAndView expReportMaintainTravelMainView(HttpServletRequest request, ExpReportHeader dto) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView();
        // 获取报销单头信息
        List<ExpReportHeader> headers = service.expReportHeaderQuery(requestCtx, dto.getExpReportHeaderId(), dto.getMoExpReportTypeId(), dto.getAccEntityId(), dto.getEmployeeId(), dto.getPaymentCurrencyCode());

        // 获取页面元素
        List<ExpMoRepTypeRefEle> eleList = expMoRepTypeRefEleService.queryExpReportLineEle(headers.get(0).getMoExpReportTypeId(), headers.get(0).getExpReportHeaderId());
        List<String> serviceList = new ArrayList<>();
        if (eleList != null && !eleList.isEmpty()) {
            for (ExpMoRepTypeRefEle refEle : eleList) {
                if (refEle.getServiceName() != null) {
                    serviceList.add(refEle.getServiceName());
                }
            }
        }
        // 根据不同行的请求路径去设置对应的model
        request.setAttribute("moExpReportTypeId", headers.get(0).getMoExpReportTypeId());
        request.setAttribute("companyId", headers.get(0).getCompanyId());
        request.setAttribute("accEntityId", headers.get(0).getAccEntityId());
        request.setAttribute("bgtEntityId", headers.get(0).getBgtEntityId());
        request.setAttribute("reportTypeId", headers.get(0).getMoExpReportTypeId());
        if (serviceList != null && !serviceList.isEmpty()) {
            view = NestedViewExecutionHelper.proceedNestedView(serviceList);
        }
        view.setViewName("expm/EXP5110/exp_report_maintain_travel_main");
        view.addObject("elements", eleList);

        // 获取默认头维度信息
        List<Map> hdDimList = expMoRepTypeRefHdDimService.getHeaderDimInfo(requestCtx, headers.get(0).getMoExpReportTypeId());
        view.addObject("headerDimensionFields", hdDimList);


        // 获取默认头费用对象
        ExpMoRepTypeRefHdObj refHdObj = new ExpMoRepTypeRefHdObj();
        refHdObj.setMoExpReportTypeId(headers.get(0).getMoExpReportTypeId());
        Criteria criteria = new Criteria(refHdObj);
        criteria.where(new WhereField(ExpMoRepTypeRefHdObj.FIELD_MO_EXP_REPORT_TYPE_ID, Comparison.EQUAL), new WhereField(ExpMoRepTypeRefHdObj.FIELD_LAYOUT_PRIORITY));
        Map objectMap = expMoRepTypeRefHdObjService.getHeaderObjInfo(requestCtx, refHdObj, criteria);
        List<Map> fieldMap = (List<Map>) objectMap.get("fieldMap");
        Map valueMap = (Map) objectMap.get("valueMap");

        if (headers.get(0).getExpReportHeaderId() != null) {
            List<ExpReportObject> reportObjects = expReportObjectService.queryHdObjectByReport(requestCtx, headers.get(0).getExpReportHeaderId());
            if (reportObjects != null && !reportObjects.isEmpty()) {
                for (ExpReportObject object : reportObjects) {
                    object.setReturnField("#" + object.getMoExpObjTypeId());
                    object.setDisplayField("^#" + object.getMoExpObjTypeId());
                    if (object.getMoExpenseObjectName() != null) {
                        valueMap.replace(object.getDisplayField(), object.getMoExpenseObjectName());
                    }
                }
            }
        }
        if (fieldMap != null) {
            view.addObject("defaultObjectFields", fieldMap);
        }

        List<Map> headerList = new ArrayList<>();
        Map headerMap = BeanUtil.convert2Map(headers.get(0));
        headerMap.putAll(valueMap);
        headerList.add(headerMap);
        view.addObject("header_info", headerList);
        return view;
    }


    @RequestMapping(value = "/expm/EXP5110/exp_report_view_main.screen")
    public ModelAndView expReportViewMainView(HttpServletRequest request, ExpReportHeader dto) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView();
        // 获取报销单头信息
        List<ExpReportHeader> headers = service.expReportHeaderQuery(requestCtx, dto.getExpReportHeaderId(), dto.getMoExpReportTypeId(), dto.getAccEntityId(), dto.getEmployeeId(), dto.getPaymentCurrencyCode());

        // 获取页面元素
        List<ExpMoRepTypeRefEle> eleList = expMoRepTypeRefEleService.queryExpReportLineEle(headers.get(0).getMoExpReportTypeId(), headers.get(0).getExpReportHeaderId());
        List<String> serviceList = new ArrayList<>();
        if (eleList != null && !eleList.isEmpty()) {
            for (ExpMoRepTypeRefEle refEle : eleList) {
                if (refEle.getReadonlyServiceName() != null) {
                    serviceList.add(refEle.getReadonlyServiceName());
                }
            }
        }
        // 根据不同行的请求路径去设置对应的model
        request.setAttribute("moExpReportTypeId", headers.get(0).getMoExpReportTypeId());
        request.setAttribute("companyId", headers.get(0).getCompanyId());
        request.setAttribute("accEntityId", headers.get(0).getAccEntityId());
        request.setAttribute("bgtEntityId", headers.get(0).getBgtEntityId());
        request.setAttribute("reportTypeId", headers.get(0).getMoExpReportTypeId());
        if (serviceList != null && !serviceList.isEmpty()) {
            view = NestedViewExecutionHelper.proceedNestedView(serviceList);
        }
        view.setViewName("expm/EXP5110/exp_report_view_main");
        view.addObject("elements", eleList);

        // 获取默认头维度信息
        List<Map> hdDimList = expMoRepTypeRefHdDimService.getHeaderDimInfo(requestCtx, headers.get(0).getMoExpReportTypeId());
        view.addObject("headerDimensionFields", hdDimList);
        // 获取默认头费用对象
        ExpMoRepTypeRefHdObj refHdObj = new ExpMoRepTypeRefHdObj();
        refHdObj.setMoExpReportTypeId(headers.get(0).getMoExpReportTypeId());
        Criteria criteria = new Criteria(refHdObj);
        criteria.where(new WhereField(ExpMoRepTypeRefHdObj.FIELD_MO_EXP_REPORT_TYPE_ID, Comparison.EQUAL), new WhereField(ExpMoRepTypeRefHdObj.FIELD_LAYOUT_PRIORITY));
        Map objectMap = expMoRepTypeRefHdObjService.getHeaderObjInfo(requestCtx, refHdObj, criteria);
        List<Map> fieldMap = (List<Map>) objectMap.get("fieldMap");
        Map valueMap = (Map) objectMap.get("valueMap");

        if (headers.get(0).getExpReportHeaderId() != null) {
            List<ExpReportObject> reportObjects = expReportObjectService.queryHdObjectByReport(requestCtx, headers.get(0).getExpReportHeaderId());
            if (reportObjects != null && !reportObjects.isEmpty()) {
                for (ExpReportObject object : reportObjects) {
                    object.setReturnField("#" + object.getMoExpObjTypeId());
                    object.setDisplayField("^#" + object.getMoExpObjTypeId());
                    if (object.getMoExpenseObjectName() != null) {
                        valueMap.replace(object.getDisplayField(), object.getMoExpenseObjectName());
                    }
                }
            }
        }
        if (fieldMap != null) {
            view.addObject("defaultObjectFields", fieldMap);
        }

        List<Map> headerList = new ArrayList<>();
        Map headerMap = BeanUtil.convert2Map(headers.get(0));
        headerMap.putAll(valueMap);
        headerList.add(headerMap);
        view.addObject("header_info", headerList);
        return view;
    }

    @RequestMapping(value = "/exp/report-header/saveHeader")
    @ResponseBody
    public ResponseData saveHeader(@RequestBody List<ExpReportHeader> dto, BindingResult result, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.saveReportHeader(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/report-header/delete")
    @ResponseBody
    public ResponseData deleteHeader(@RequestBody ExpReportHeader dto, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        service.deleteHeader(requestCtx, dto);
        return new ResponseData();
    }


    @RequestMapping(value = "expm/EXP5240/exp_report_reject.screen")
    public ModelAndView expReportRejectView(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        ModelAndView modelAndView = new ModelAndView("expm/EXP5240/exp_report_reject");
        List<ExpEmployee> expEmployees = new ArrayList<>();
        expEmployees = expEmployeeService.queryCurrentEmployee(iRequest);
        modelAndView.addObject("employeeRecord", expEmployees);
        return modelAndView;
    }

    @RequestMapping(value = "/exp/report-header/create-account")
    public ResponseData queryExpReportMain(@RequestBody List<ExpReportHeader> expReportHeaders, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Map<String, Object> map = expReportHeaders.get(0).getInnerMap();
        String[] pattern = new String[]{"yyyy-MM-dd"};
        Date journalDate = new Date();
        try {
            journalDate = DateUtils.parseDate(map.get("journalDate").toString(), pattern);
        } catch (Exception e) {
            throw new RuntimeException("日期格式转换错误！");
        }
        List<ExpReportHeader> expReportHeaders1 = expReportHeaders.get(0).getDetails();
        service.createAccount(requestContext, expReportHeaders1, journalDate);
        return new ResponseData();
    }

    @RequestMapping(value = "/exp/report-header/audit")
    public ResponseData audit(@RequestBody List<ExpReportHeader> expReportHeaders, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        service.audit(requestContext, expReportHeaders);
        return new ResponseData();
    }

    @RequestMapping(value = "/exp/report-header/audit-reject")
    public ResponseData auditReject(@RequestBody List<ExpReportHeader> expReportHeaders, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        service.auditReject(requestContext, expReportHeaders);
        return new ResponseData();
    }

    @RequestMapping(value = "/exp/report-header/get-period-name")
    @ResponseBody
    public ResponseData getPeriodName(@RequestBody List<ExpReportHeader> expReportHeaders, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Map<String, Object> map = expReportHeaders.get(0).getInnerMap();
        Long accEntityId = expReportHeaders.get(0).getAccEntityId();
        if (accEntityId == null) {
            accEntityId = gldAccountingEntityMapper.getDefaultAccEntity(requestContext.getCompanyId()).getAccEntityId();
        }
        String[] pattern = new String[]{"yyyy-MM-dd"};
        Date journalDate = new Date();
        try {
            journalDate = DateUtils.parseDate(map.get("journalDate").toString(), pattern);
        } catch (Exception e) {
            throw new RuntimeException("日期格式转换错误！");
        }
        String periodName = gldPeriodMapper.getPeriodName(journalDate, accEntityId, "O");
        Map<String, String> map1 = new HashMap<>();
        map1.put("periodName", periodName);
        List<Map<String, String>> list = new ArrayList<>();
        list.add(map1);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/exp/report-header/get-exchange-rate")
    @ResponseBody
    public ResponseData getExchangeRate(@RequestBody List<ExpReportHeader> expReportHeaders, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Map<String, Object> map = expReportHeaders.get(0).getInnerMap();
        String[] pattern = new String[]{"yyyy-MM-dd"};
        Date journalDate = new Date();
        try {
            journalDate = DateUtils.parseDate(map.get("exchangeDate").toString(), pattern);
        } catch (Exception e) {
            throw new RuntimeException("日期格式转换错误！");
        }
        BigDecimal exchangeRate = gldExchangeRateService.getExchangeRate(expReportHeaders.get(0).getAccEntityId(), (String) map.get("fromCurrency"), (String) map.get("toCurrency"), (String) map.get("exchangeRateType"), journalDate, (String) map.get("exchangePeriodName"), null);
        Map<String, BigDecimal> map1 = new HashMap<>();
        map1.put("exchangeRate", exchangeRate);
        List<Map<String, BigDecimal>> list = new ArrayList<>();
        list.add(map1);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/exp/report-header/audit-query")
    @ResponseBody
    public ResponseData auditQuery(Long accEntityId, Long moExpReportTypeId, String expReportNumber, String paymentCurrencyCode, Long employeeId, String reportDateFrom, String reportDateTo, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ExpReportHeader expReportHeader = new ExpReportHeader();
        expReportHeader.setMoExpReportTypeId(moExpReportTypeId);
        expReportHeader.setAccEntityId(accEntityId);
        expReportHeader.setEmployeeId(employeeId);
        expReportHeader.setExpReportNumber(expReportNumber);
        expReportHeader.setPaymentCurrencyCode(paymentCurrencyCode);
        String[] pattern = new String[]{"yyyy-MM-dd"};
        try {
            if (reportDateFrom != null && !"".equals(reportDateFrom)) {
                expReportHeader.setReportDateFrom(DateUtils.parseDate(reportDateFrom, pattern));
            }
            if (reportDateTo != null && !"".equals(reportDateFrom)) {
                expReportHeader.setReportDateTo(DateUtils.parseDate(reportDateTo, pattern));
            }
        } catch (Exception e) {
            throw new RuntimeException("日期格式转化错误！");
        }
        return new ResponseData(service.auditQuery(requestContext, expReportHeader, page, pageSize));
    }

    @RequestMapping(value = "/exp/report-header/reverse-query")
    @ResponseBody
    public ResponseData reverseQuery(Long moExpReportTypeId, String expReportNumber, String paymentCurrencyCode, Long employeeId, String reportDateFrom, String reportDateTo, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ExpReportHeader expReportHeader = new ExpReportHeader();
        expReportHeader.setMoExpReportTypeId(moExpReportTypeId);
        expReportHeader.setEmployeeId(employeeId);
        expReportHeader.setExpReportNumber(expReportNumber);
        expReportHeader.setPaymentCurrencyCode(paymentCurrencyCode);
        String[] pattern = new String[]{"yyyy-MM-dd"};
        try {
            if (reportDateFrom != null && !"".equals(reportDateFrom)) {
                expReportHeader.setReportDateFrom(DateUtils.parseDate(reportDateFrom, pattern));
            }
            if (reportDateTo != null && !"".equals(reportDateFrom)) {
                expReportHeader.setReportDateTo(DateUtils.parseDate(reportDateTo, pattern));
            }
        } catch (Exception e) {
            throw new RuntimeException("日期格式转化错误！");
        }
        return new ResponseData(service.reverseQuery(requestContext, expReportHeader, page, pageSize));
    }

    @RequestMapping(value = "/exp/report-header/get-change-exchange-rate-data")
    @ResponseBody
    public ResponseData getChangeExchangeRateData(@RequestBody List<ExpReportHeader> dto, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        Long accEntityId = (Long) dto.get(0).getAccEntityId();
        String[] pattern = new String[]{"yyyy-MM-dd"};
        Date journalDate = null;
        try {
            journalDate = DateUtils.parseDate(dto.get(0).getInnerMap().get("journalDate").toString(), pattern);
        } catch (Exception e) {
            throw new RuntimeException("日期格式转换错误!");
        }
        String periodName = dto.get(0).getPeriodName();
        List<HashMap<String, Object>> maps = (List<HashMap<String, Object>>) dto.get(0).getInnerMap().get("details");
        String exchangeRateType = sysParameterService.queryParamValueByCode("DEFAULT_EXCHANGE_RATE_TYPE", null, null, null, accEntityId, null, null, null);
        String functionCurrencyCode = gldAccountingEntityMapper.queryFuncCurrencyByEntity(accEntityId);
        List<HashMap<String, Object>> list = new ArrayList<>();
        if (!maps.isEmpty()) {
            for (HashMap<String, Object> map : maps) {
                HashMap<String, Object> hashMap = new HashMap<>();
                BigDecimal exchangeRate = null;
                if (exchangeRateType != null && !"".equals(exchangeRateType)) {
                    exchangeRate = gldExchangeRateService.getExchangeRate(accEntityId, functionCurrencyCode, map.get("paymentCurrencyCode").toString(), exchangeRateType, journalDate, periodName, null);
                    // 根据汇率类型获取对应属性
                    List<GldExchangerateType> gldExchangerateTypes = gldExchangerateTypeMapper.queryGldExchangerateTypes(exchangeRateType);
                    if (!gldExchangerateTypes.isEmpty()) {
                        hashMap.put("methodCode", gldExchangerateTypes.get(0).getMethodCode());
                        hashMap.put("exchangeRateTypeName", gldExchangerateTypes.get(0).getDescription());
                    }
                } else {
                    exchangeRate = new BigDecimal(1);
                }
                hashMap.put("expReportHeaderId", map.get("expReportHeaderId"));
                hashMap.put("exchangeRate", exchangeRate);
                hashMap.put("exchangeRateType", exchangeRateType);
                hashMap.put("paymentCurrencyCode", map.get("paymentCurrencyCode"));
                hashMap.put("functionalCurrencyCode", functionCurrencyCode);
                list.add(hashMap);
            }
        }
        return new ResponseData(list);
    }

    @RequestMapping(value = "exp/report-header/update-account")
    public ResponseData updateAccount(@RequestBody List<ExpReportHeader> dto, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) dto.get(0).getInnerMap().get("details");
        service.updateAccount(iRequest, list);
        return new ResponseData();
    }

    @RequestMapping(value = "exp/report-header/reverse")
    public ResponseData reverse(@RequestBody List<ExpReportHeader> dto, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        Map<String, Object> map = dto.get(0).getInnerMap();
        String[] pattern = new String[]{"yyyy-MM-dd"};
        Date journalDate = null;
        try {
            journalDate = DateUtils.parseDate(dto.get(0).getInnerMap().get("journalDate").toString(), pattern);
        } catch (Exception e) {
            throw new RuntimeException("日期格式转换错误!");
        }
        String periodName = dto.get(0).getPeriodName();
        List<ExpReportHeader> expReportHeaders = dto.get(0).getDetails();
        service.reverse(iRequest, journalDate, periodName, expReportHeaders);
        return new ResponseData();
    }

    @RequestMapping(value = "exp/report-header/getPeriodName")
    @ResponseBody
    public ResponseData getPeriodName(HttpServletRequest request, @RequestBody Map<String, Object> requestMap) {
        Long accEntityId = Long.valueOf((Integer) requestMap.get("accEntityId"));
        String paymentCurrencyCode = (String) requestMap.get("paymentCurrencyCode");
        String date = (String) requestMap.get("date");
        IRequest requestCtx = createRequestContext(request);
        List<Map> mapList = new ArrayList<>();
        Map map = new HashMap();
        // 获取对应预算期间
        String periodName = bgtPeriodService.getBgtPeriodName(com.hand.hap.core.util.DateUtils.str2Date(date, null), null, requestCtx.getCompanyId(), null);
        if (periodName != null) {
            map.put("periodName", periodName);
        }
        // 获取支付->本位币汇率
        String funCurrencyCode = gldCurrencyService.getAccEntityFunCurrCode(accEntityId);
        String pay2funRateType = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_DEFAULT_EXCHANGE_RATE_TYPE, null, null, null, accEntityId, null, null, null);

        BigDecimal pay2funExchangeRate = gldExchangeRateService.getExchangeRate(accEntityId, paymentCurrencyCode, funCurrencyCode, pay2funRateType, com.hand.hap.core.util.DateUtils.str2Date(date, null), periodName, "N");
        if (pay2funExchangeRate != null) {
            map.put("pay2funExchangeRate", pay2funExchangeRate);
        }
        // 获取支付->管理币种汇率
        FndCompany company = new FndCompany();
        company.setCompanyId(requestCtx.getCompanyId());
        company = fndCompanyService.selectByPrimaryKey(requestCtx, company);
        String pay2magRateType = sysParameterService.queryParamValueByCode(ParameterConstants.PARAM_DEFAULT_MAG_EXCH_RATE_TYPE, null, null, requestCtx.getCompanyId(), null, null, null, null);

        BigDecimal pay2magExchangeRate = gldExchangeRateService.getExchangeRate(accEntityId, paymentCurrencyCode, company.getManagingCurrency(), pay2magRateType, com.hand.hap.core.util.DateUtils.str2Date(date, null), periodName, "N");
        if (pay2magExchangeRate != null) {
            map.put("pay2magExchangeRate", pay2magExchangeRate);
        }
        mapList.add(map);
        return new ResponseData(mapList);
    }

    @RequestMapping(value = "exp/report-header/getPolicyInfo")
    @ResponseBody
    public ResponseData getPolicyInfo(HttpServletRequest request, @RequestBody Map<String, Object> requestMap) {
        IRequest requestCtx = createRequestContext(request);
        Long employeeId = Long.valueOf(String.valueOf(requestMap.get("employeeId") == null || "".equals(requestMap.get("employeeId")) ? -1 : requestMap.get("employeeId")));
        Long companyId = Long.valueOf(String.valueOf(requestMap.get("companyId") == null || "".equals(requestMap.get("companyId")) ? -1 : requestMap.get("companyId")));
        Long accEntityId = Long.valueOf(String.valueOf(requestMap.get("accEntityId") == null || "".equals(requestMap.get("accEntityId")) ? -1 : requestMap.get("accEntityId")));
        Long placeId = Long.valueOf(String.valueOf(requestMap.get("placeId") == null || "".equals(requestMap.get("placeId")) ? -1 : requestMap.get("placeId")));
        Long placeTypeId = Long.valueOf(String.valueOf(requestMap.get("placeTypeId") == null || "".equals(requestMap.get("placeTypeId")) ? -1 : requestMap.get("placeTypeId")));
        Long positionId = Long.valueOf(String.valueOf(requestMap.get("positionId") == null || "".equals(requestMap.get("positionId")) ? -1 : requestMap.get("positionId")));
        Long unitId = Long.valueOf(String.valueOf(requestMap.get("unitId") == null || "".equals(requestMap.get("unitId")) ? -1 : requestMap.get("unitId")));
        String vehicleCode = (String) (requestMap.get("vehicleCode") == null || "".equals(requestMap.get("vehicleCode")) ? "-1" : requestMap.get("vehicleCode"));
        String docCategory = (String) (requestMap.get("docCategory") == null || "".equals(requestMap.get("docCategory")) ? "-1" : requestMap.get("docCategory"));
        Long docTypeId = Long.valueOf(String.valueOf(requestMap.get("docTypeId") == null || "".equals(requestMap.get("docTypeId")) ? -1 : requestMap.get("docTypeId")));
        Long moExpenseTypeId = Long.valueOf(String.valueOf(requestMap.get("moExpenseTypeId") == null || "".equals(requestMap.get("moExpenseTypeId")) ? -1 : requestMap.get("moExpenseTypeId")));
        Long moExpenseItemId = Long.valueOf(String.valueOf(requestMap.get("moExpenseItemId") == null || "".equals(requestMap.get("moExpenseItemId")) ? -1 : requestMap.get("moExpenseItemId")));
        ExpEmployeeAssign employeeAssign = expEmployeeAssignService.getEmployeeAssignInfo(requestCtx, companyId, employeeId);
        FndCompany company = fndCompanyService.getCompany(requestCtx, companyId);

        List<ExpMoExpPolicyDetail> policyDetails = expMoExpPolicyDetailService.getPolicyInfo(requestCtx, company.getMagOrgId(), companyId, accEntityId, employeeAssign == null ? null : employeeAssign.getEmployeeJobId(), employeeAssign == null ? null : employeeAssign.getEmployeeLevelsId(), placeId, placeTypeId, positionId, unitId, vehicleCode, docCategory, docTypeId, moExpenseTypeId, moExpenseItemId, null);
        return new ResponseData(policyDetails);
    }

    @RequestMapping(value = "exp/report-header/submitReport")
    @ResponseBody
    public ResponseData submitReport(HttpServletRequest request, @RequestBody Map<String, Object> requestMap) {
        IRequest requestCtx = createRequestContext(request);
        Long expReportHeaderId = Long.valueOf((Integer) requestMap.get("expReportHeaderId"));
        String bgtIgnoreWarningFlag = (String) requestMap.get("bgtIgnoreWarningFlag");
        String vatIgnoreAccEntityFlag = (String) requestMap.get("vatIgnoreAccEntityFlag");
        String vatIgnoreDateFlag = (String) requestMap.get("vatIgnoreDateFlag");
        Map submitMap = service.submitExpReport(requestCtx, expReportHeaderId, bgtIgnoreWarningFlag, vatIgnoreAccEntityFlag, vatIgnoreDateFlag);
        List<Map> mapList = new ArrayList<>();
        mapList.add(submitMap);
        return new ResponseData(mapList);
    }


    @RequestMapping(value = "/exp/report-header/getEmployeeByCompanyId")
    @ResponseBody
    public ResponseData getEmployeeByCompanyId(@RequestParam(required = true) Long companyId, @RequestParam(required = false) Long positionId, @RequestParam(required = false) String primaryPositionFlag, @RequestParam(required = false) String employeeCode, @RequestParam(required = false) String employeeName, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.getEmployeeByCompanyId(requestCtx, companyId, positionId, primaryPositionFlag, employeeCode, employeeName));
    }

}
