package com.hand.hec.bgt.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtOrgRefDetail;
import com.hand.hec.bgt.service.IBgtOrgRefDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;
/**
 * <p>
 * 预算组织来源明细Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:46
 */
@RequestMapping(value = "/bgt/org-ref-detail")
@Controller
public class BgtOrgRefDetailController extends BaseController{

    @Autowired
    private IBgtOrgRefDetailService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(BgtOrgRefDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }


    @RequestMapping(value = "/queryDetail")
    @ResponseBody
    public ResponseData queryDetail(@RequestParam String sourceTypeCode, @RequestParam Long bgtOrgId) {
        return new ResponseData(service.selectDetailBySourceType(sourceTypeCode,bgtOrgId));
    }

    @RequestMapping(value = "/queryCombox")
    @ResponseBody
    public ResponseData queryCombox(@RequestParam String sourceTypeCode,HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectCombox(sourceTypeCode,requestContext));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtOrgRefDetail> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<BgtOrgRefDetail> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}