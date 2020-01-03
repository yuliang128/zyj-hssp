package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshMoTrxClsAsgnCom;
import com.hand.hec.csh.service.ICshMoTrxClsAsgnComService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 现金事物分类分配公司控制器
 *
 * @author LJK 2019-02-19 10:42
 */

@Controller
@RequestMapping(value = "/csh/mo-trx-cls-asgn-com")
public class CshMoTrxClsAsgnComController extends BaseController{

    @Autowired
    private ICshMoTrxClsAsgnComService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(Long magOrgId, Long moCshTrxClassId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByTrxClassId(requestContext,magOrgId,moCshTrxClassId,page,pageSize));
    }

	@RequestMapping(value = "/queryComByTrxClassId",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ResponseData queryComByTrxClassId(Long magOrgId, Long moCshTrxClassId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
		IRequest requestContext = createRequestContext(request);
		return new ResponseData(service.queryComByTrxClassId(requestContext,magOrgId,moCshTrxClassId,page,pageSize));
	}

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshMoTrxClsAsgnCom> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshMoTrxClsAsgnCom> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}