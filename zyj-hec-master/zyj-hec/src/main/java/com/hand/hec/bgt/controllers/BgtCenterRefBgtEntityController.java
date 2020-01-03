package com.hand.hec.bgt.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtCenter;
import com.hand.hec.bgt.dto.BgtCenterRefBgtEntity;
import com.hand.hec.bgt.service.IBgtCenterRefBgtEntityService;
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
 * 预算中心关联预算实体Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:28
 */
@Controller
@RequestMapping(value = "/bgt/center-ref-bgt-entity")
public class BgtCenterRefBgtEntityController extends BaseController{

    @Autowired
    private IBgtCenterRefBgtEntityService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(BgtCenterRefBgtEntity dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtCenterRefBgtEntity> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    /**
     * 预算中心定义-分配预算实体-批量分配-查询可分配的预算实体
     *
     * @param dto
     * @author ngls.luhui 2019-02-21 10:57
     * @return
     */
    @RequestMapping(value = "/queryEntityCanAsgn")
    @ResponseBody
    public ResponseData queryEntityCanAsgn(BgtCenterRefBgtEntity dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryEntityCanAsgn(dto.getCenterId(),dto.getBgtOrgId(),requestContext,page,pageSize));
    }

    /**
     * 预算中心定义-批量分配预算实体(多对多分配)
     *
     * @param dto
     * @author ngls.luhui 2019-02-21 10:57
     * @return 
     */
    @RequestMapping(value = "/batchSubmit")
    @ResponseBody
    public ResponseData batchSubmit(@RequestBody List<BgtCenter> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSubmit(dto,requestCtx));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<BgtCenterRefBgtEntity> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    
    /**
     * 预算中心定义-分配预算实体主查询
     *
     * @param dto 带过来的预算中心id
     * @author ngls.luhui 2019-02-21 10:44
     * @return 
     */
    @RequestMapping(value = "/queryMain")
    @ResponseBody
    public ResponseData queryMain(BgtCenterRefBgtEntity dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryMain(dto.getCenterId(),requestContext,page,pageSize));
    }
}