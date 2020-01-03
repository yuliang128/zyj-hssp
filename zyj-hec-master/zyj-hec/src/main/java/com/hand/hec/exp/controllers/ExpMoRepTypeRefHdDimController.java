package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoRepTypeRefHdDim;
import com.hand.hec.exp.service.IExpMoRepTypeRefHdDimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * ExpMoRepTypeRefHdDimController
 * </p>
 *
 * @author yang.duan 2019/01/10 14:42
 */

@Controller
@RequestMapping(value = "")
public class ExpMoRepTypeRefHdDimController extends BaseController {

    @Autowired
    private IExpMoRepTypeRefHdDimService service;

    @RequestMapping(value = "/exp/mo-rep-type-ref-hd-dim/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoRepTypeRefHdDim dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoRepTypeRefHdDim.FIELD_MO_EXP_REPORT_TYPE_ID, Comparison.EQUAL),
                        new WhereField(ExpMoRepTypeRefHdDim.FIELD_DIMENSION_CODE),
                        new WhereField(ExpMoRepTypeRefHdDim.FIELD_DIMENSION_NAME),
                        new WhereField(ExpMoRepTypeRefHdDim.FIELD_DEFAULT_DIM_VALUE_CODE),
                        new WhereField(ExpMoRepTypeRefHdDim.FIELD_DEFAULT_DIM_VALUE_NAME));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/exp/mo-rep-type-ref-hd-dim/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoRepTypeRefHdDim> dto, BindingResult result,
                    HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSubmit(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/mo-rep-type-ref-hd-dim/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoRepTypeRefHdDim> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "exp/EXP4060/exp_mo_rep_type_ref_hd_dims.screen")
    @ResponseBody
    public ModelAndView DimensionInit(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("exp/EXP4060/exp_mo_rep_type_ref_hd_dims");
        view.addObject("companyId", requestCtx.getCompanyId());
        return view;
    }
}
