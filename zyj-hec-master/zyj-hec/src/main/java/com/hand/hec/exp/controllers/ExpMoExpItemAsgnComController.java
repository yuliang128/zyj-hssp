package com.hand.hec.exp.controllers;

import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hec.exp.dto.ExpMoExpenseItem;

import org.springframework.stereotype.Controller;
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
import com.hand.hec.exp.dto.ExpMoExpItemAsgnCom;
import com.hand.hec.exp.service.IExpMoExpItemAsgnComService;

/**
 *
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
@RequestMapping(value = "/exp/mo-exp-item-asgn-com")
public class ExpMoExpItemAsgnComController extends BaseController{

    @Autowired
    private IExpMoExpItemAsgnComService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpItemAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpItemAsgnCom> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ExpMoExpItemAsgnCom> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     *根据费用项目和管理组织查询分配公司信息
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @return 分配的公司信息
     * @author zhongyu 2019-2-26 14:55
     */
    @RequestMapping(value = "/queryCompany")
    @ResponseBody
    public ResponseData queryCompany(ExpMoExpItemAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCompany(requestContext,dto,page,pageSize));
    }

    /**
     * 根据费用项目和管理组织
     * 查询不在当前项目已经分配了的公司的公司信息
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     * @author zhongyu 2019-2-26 20:08
     */
    @RequestMapping(value = "/queryCompanyByItem")
    @ResponseBody
    public ResponseData queryCompanyByItem(ExpMoExpItemAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                           @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCompanyByItem(requestContext,dto,page,pageSize));
    }

    /**
     * 当前组织下-公司lov-不限制已分配的公司
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @return
     * @author zhongyu 2019-2-26 16:36
     */
    @RequestMapping(value = "/queryBatchCompany")
    @ResponseBody
    public ResponseData queryBatchCompany(FndCompany dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryBatchCompany(requestContext,dto,page,pageSize));
    }


    /**
     *  多个费用项目分配多个公司
     * @param request
     * @param expMoExpenseItems
     * @return
     * @author zhongyu 2019-2-26
     */
    @RequestMapping(value="/batchAssignCompanyManytoMany")
    @ResponseBody
    public ResponseData batchAssignCompanyManytoMany(HttpServletRequest request, @RequestBody List<ExpMoExpenseItem> expMoExpenseItems){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.batchAssignCompanyManytoMany(requestContext,expMoExpenseItems));
    }

    /**
     * 单个费用项目分配多个公司
     * @param request
     * @param companies
     * @return
     * @author zhongyu 2019-2-26 19:42
     */

    @RequestMapping(value = "/batchAssignCompanyOnetoMany")
    @ResponseBody
    public ResponseData batchAssignCompanyOnetoMany(HttpServletRequest request,@RequestBody List<FndCompany> companies){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.batchAssignCompanyOnetoMany(requestContext,companies));
    }

}