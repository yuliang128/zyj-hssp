package com.hand.hec.ssc.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.ssc.dto.SscWorkflow;
import com.hand.hec.ssc.service.ISscWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 共享工作流程控制器
 *
 * @author luhui 2019-03-04
 */

@Controller
@RequestMapping(value = "/ssc/workflow")
public class SscWorkflowController extends BaseController{

    @Autowired
    private ISscWorkflowService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SscWorkflow dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(SscWorkflow.FIELD_WORKFLOW_ID,Comparison.EQUAL),
                new WhereField(SscWorkflow.FIELD_DESCRIPTION,Comparison.LIKE),
                new WhereField(SscWorkflow.FIELD_WORKFLOW_CODE,Comparison.LIKE),
                new WhereField(SscWorkflow.FIELD_DOC_CATEGORY,Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SscWorkflow> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<SscWorkflow> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/batchSubmit")
    @ResponseBody
    public ResponseData batchSubmit(@RequestBody List<SscWorkflow> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSubmit(dto,requestCtx));
    }

    @RequestMapping(value = "/querySscWorkflow")
    @ResponseBody
    public ResponseData querySscWorkflow(SscWorkflow dto,HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.querySscWorkflow(dto.getWorkflowCode(),dto.getDescription(),dto.getDocCategory(),requestContext));
    }

}