package com.hand.hec.csh.controllers;

import org.springframework.stereotype.Controller;

import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshMoRepayRegAsgnCom;
import com.hand.hec.csh.service.ICshMoRepayRegAsgnComService;

/**
 * <p>
 * 还款登记单类型定义-分配公司控制器
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/25 09:57
 */

@Controller
@RequestMapping(value = "/csh/mo-repay-reg-asgn-com")
public class CshMoRepayRegAsgnComController extends BaseController{

    @Autowired
    private ICshMoRepayRegAsgnComService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshMoRepayRegAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshMoRepayRegAsgnCom> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshMoRepayRegAsgnCom> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryUnallocated")
    public ResponseData queryUnallocated(HttpServletRequest request,CshMoRepayRegAsgnCom condition,Long magOrgId) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(this.service.queryUnallocatedCompanies(requestCtx,condition,magOrgId));
    }

    @RequestMapping(value = "/assignCompanies")
    public ResponseData assignCompanies(HttpServletRequest request,@RequestBody List<CshMoRepayRegAsgnCom> dto) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchAssign(requestCtx, dto));
    }
}