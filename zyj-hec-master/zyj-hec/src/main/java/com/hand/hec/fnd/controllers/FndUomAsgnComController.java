package com.hand.hec.fnd.controllers;

import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hec.exp.dto.ExpMoExpenseItem;
import com.hand.hec.fnd.dto.FndUom;
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
import com.hand.hec.fnd.dto.FndUomAsgnCom;
import com.hand.hec.fnd.service.IFndUomAsgnComService;

/**
 *
 * 计量分配公司控制器
 *
 * @author zhongyu
 */

@Controller
@RequestMapping(value = "/fnd/uom-asgn-com")
public class FndUomAsgnComController extends BaseController{

    @Autowired
    private IFndUomAsgnComService service;

    @Autowired
    private IFndCompanyService fndCompanyService;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndUomAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/queryByMagOrgId",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndUomAsgnCom dto,Long magOrgId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectByMagOrgId(requestContext,magOrgId,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndUomAsgnCom> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<FndUomAsgnCom> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询公司信息（分配/未分配）
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryCompanyForUoms",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryCompanyForUoms(FndCompany dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                             @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(fndCompanyService.queryCompanyForUoms(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/queryNoAssignedCompanyForUoms",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryNoAssignedCompanyForUoms(Long assignMoId,Long magOrgId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(fndCompanyService.queryNoAssignedCompanyForUoms(requestContext,assignMoId,magOrgId,page,pageSize));
    }


    /**
     *  多个计量单位分配多个公司
     * @param request
     * @param fndUoms
     * @return
     * @author zhongyu 2019-4-25
     */
    @RequestMapping(value="/batchAssignCompanyManytoMany")
    @ResponseBody
    public ResponseData batchAssignCompanyManytoMany(HttpServletRequest request, @RequestBody List<FndUom> fndUoms){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.batchAssignCompanyManytoMany(requestContext,fndUoms));
    }

    @RequestMapping(value="/batchAssignCompanyOnetoMany")
    @ResponseBody
    public ResponseData batchAssignCompanyOnetoMany(HttpServletRequest request, @RequestBody List<FndCompany> fndCompanies){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.batchAssignCompanyOnetoMany(requestContext,fndCompanies));
    }

}