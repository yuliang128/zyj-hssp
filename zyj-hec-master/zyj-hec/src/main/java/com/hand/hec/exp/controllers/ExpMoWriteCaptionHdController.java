package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoWriteCaptionHd;
import com.hand.hec.exp.exception.WriteCaptionMultiException;
import com.hand.hec.exp.service.IExpMoWriteCaptionHdService;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 单据填写说明定义头控制器
 * </p>
 *
 * @author yang.duan 2019/02/12 15:15
 */

@Controller
@RequestMapping(value = "")
public class ExpMoWriteCaptionHdController extends BaseController {

    @Autowired
    private IExpMoWriteCaptionHdService service;

    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/exp/mo-write-caption-hd/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoWriteCaptionHd dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoWriteCaptionHd.FIELD_MAG_ORG_ID, Comparison.EQUAL),
                        new WhereField(ExpMoWriteCaptionHd.FIELD_CAPTION_CODE, Comparison.LIKE),
                        new WhereField(ExpMoWriteCaptionHd.FIELD_DESCRIPTION, Comparison.LIKE),
                        new WhereField(ExpMoWriteCaptionHd.FIELD_DOC_CATEGORY_CODE, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/exp/mo-write-caption-hd/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoWriteCaptionHd> dto, BindingResult result,
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

    @RequestMapping(value = "/exp/mo-write-caption-hd/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoWriteCaptionHd> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "exp/EXP2180/exp_mo_write_caption_main.screen")
    @ResponseBody
    public ModelAndView expMoWriteCaptionMainView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        FndManagingOrganization fndManagingOrganizationList =
                        fndManagingOrganizationService.queryDefaultMagOrg(requestContext);
        ModelAndView view = new ModelAndView("exp/EXP2180/exp_mo_write_caption_main");
        view.addObject("fndManagingOrganizationList", fndManagingOrganizationList);
        return view;

    }

    @RequestMapping(value = "exp/mo-write-caption-hd/limit/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData limitQuery(ExpMoWriteCaptionHd dto,Long magOrgId,String docCategoryCode, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setMagOrgId(magOrgId);
        dto.setDocCategoryCode(docCategoryCode);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoWriteCaptionHd.FIELD_MAG_ORG_ID, Comparison.EQUAL),
                new WhereField(ExpMoWriteCaptionHd.FIELD_DOC_CATEGORY_CODE, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria));
    }


}
