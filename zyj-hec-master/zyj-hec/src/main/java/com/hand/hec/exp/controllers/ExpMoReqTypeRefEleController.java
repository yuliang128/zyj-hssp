package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoReqTypeRefEle;
import com.hand.hec.exp.service.IExpMoReqTypeRefEleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/3/18 15:16
 * Description: 申请单类型分配页面元素控制器
 */

@Controller
@RequestMapping(value = "/exp/mo-req-type-ref-ele")
public class ExpMoReqTypeRefEleController extends BaseController {

    @Autowired
    private IExpMoReqTypeRefEleService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoReqTypeRefEle dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoReqTypeRefEle.FIELD_MO_EXP_REQ_TYPE_ID));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoReqTypeRefEle> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoReqTypeRefEle> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}