package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoWriteCaptionLn;
import com.hand.hec.exp.exception.WriteCaptionMultiException;
import com.hand.hec.exp.service.IExpMoWriteCaptionLnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 单据填写说明行控制器
 * </p>
 *
 * @author yang.duan 2019/02/13 9:57
 */

@Controller
@RequestMapping(value = "/exp/mo-write-caption-ln")
public class ExpMoWriteCaptionLnController extends BaseController {

    @Autowired
    private IExpMoWriteCaptionLnService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoWriteCaptionLn dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoWriteCaptionLn.FIELD_CAPTION_HDS_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoWriteCaptionLn> dto, BindingResult result,
                    HttpServletRequest request) throws WriteCaptionMultiException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSubmit(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoWriteCaptionLn> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/getWriteCaptionByDocType")
    @ResponseBody
    public ResponseData getWriteCaptionByDocType(HttpServletRequest request, ExpMoWriteCaptionLn dto) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.getWriteCaptionByDocType(requestCtx,dto.getDocCategory(), dto.getDocTypeId()));
    }
}
