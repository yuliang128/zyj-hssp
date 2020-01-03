package com.hand.hec.expm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.expm.dto.ExpReportAccount;
import com.hand.hec.expm.service.IExpReportAccountService;
import com.hand.hec.fnd.mapper.GldPeriodMapper;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.mapper.GldAccountingEntityMapper;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import com.hand.hec.gld.service.IGldSobSegmentService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ExpReportAccountController
 * </p>
 *
 * @author yang.duan 2019/01/10 14:57
 */

@Controller
public class ExpReportAccountController extends BaseController{

    @Autowired
    private IExpReportAccountService service;

    @Autowired
    private IGldAccountingEntityService gldAccountingEntityService;

    @Autowired
    private GldPeriodMapper gldPeriodMapper;

    @Autowired
    private IGldSobSegmentService gldSobSegmentService;

    @Autowired
    private GldAccountingEntityMapper gldAccountingEntityMapper;

    @RequestMapping(value = "expm/EXP5240/exp_report_audit.screen")
    public ModelAndView expReportAuditView(HttpServletRequest request){
        IRequest iRequest = createRequestContext(request);
        ModelAndView modelAndView = new ModelAndView("expm/EXP5240/exp_report_audit");
        GldAccountingEntity gldAccountingEntity = gldAccountingEntityService.queryDefaultAccEntity(iRequest,iRequest.getCompanyId());
        modelAndView.addObject("defaultAccEntity",gldAccountingEntity);
        Date date=new Date();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(date);
        modelAndView.addObject("currentDate",currentDate);
        String[] pattern = new String[] { "yyyy-MM-dd" };
        try {
            date = DateUtils.parseDate(currentDate, pattern);
        } catch (Exception e){
            throw new RuntimeException("日期获取失败! ");
        }
        String periodName = gldPeriodMapper.getPeriodName(date,gldAccountingEntity.getAccEntityId(),"O");
        if(periodName != null && !"".equals(periodName)) {
            modelAndView.addObject("currentPeriodName", periodName);
        }
        List<Map> accountSegmentDesc = gldSobSegmentService.querySegmentDesc(iRequest);
        if(!accountSegmentDesc.isEmpty()) {
            modelAndView.addObject("accountSegmentDesc", accountSegmentDesc);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/exp/report-account/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpReportAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/exp/report-account/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpReportAccount> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.accountUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/report-account/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ExpReportAccount> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/exp/report-account/auditAccountQuery",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData auditAccountQuery(ExpReportAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.auditAccountQuery(requestContext,dto,page,pageSize));
    }
}