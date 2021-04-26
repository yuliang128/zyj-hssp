package com.hand.hec.fnd.controllers;

import com.hand.hec.fnd.dto.GldAccountHierarchyDetail;
import com.hand.hec.fnd.service.IGldAccountHierarchyDetailService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
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
 * GldAccountHierarchyDetailController
 * </p>
 *
 * @author guiyu 2019/01/08 15:31
 */
@Controller
@RequestMapping(value = "/gld/account-hierarchy-detail")
public class GldAccountHierarchyDetailController extends BaseController{

    @Autowired
    private IGldAccountHierarchyDetailService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(GldAccountHierarchyDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldAccountHierarchyDetail> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldAccountHierarchyDetail> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}