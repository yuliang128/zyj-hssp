package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoRepEleRefLnDim;
import com.hand.hec.exp.service.IExpMoRepEleRefLnDimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * ExpMoRepEleRefLnDimController
 * </p>
 *
 * @author yang.duan 2019/01/10 14:41
 */

@Controller
@RequestMapping(value = "")
public class ExpMoRepEleRefLnDimController extends BaseController {

    @Autowired
    private IExpMoRepEleRefLnDimService service;

    @RequestMapping(value = "/exp/mo-rep-ele-ref-ln-dim/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoRepEleRefLnDim dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoRepEleRefLnDim.FIELD_REP_PAGE_ELE_REF_ID, Comparison.EQUAL),
                        new WhereField(ExpMoRepEleRefLnDim.FIELD_DIMENSION_CODE),
                        new WhereField(ExpMoRepEleRefLnDim.FIELD_DIMENSION_NAME),
                        new WhereField(ExpMoRepEleRefLnDim.FIELD_DEFAULT_DIM_VALUE_CODE),
                        new WhereField(ExpMoRepEleRefLnDim.FIELD_DEFAULT_DIM_VALUE_NAME));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/exp/mo-rep-ele-ref-ln-dim/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoRepEleRefLnDim> dto, BindingResult result,
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

    @RequestMapping(value = "/exp/mo-rep-ele-ref-ln-dim/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoRepEleRefLnDim> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "exp/EXP4060/exp_mo_rep_eles_ref_ln_dims.screen")
    @ResponseBody
    public ModelAndView DimensionInit(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("exp/EXP4060/exp_mo_rep_eles_ref_ln_dims");
        view.addObject("companyId", requestCtx.getCompanyId());
        return view;
    }
}
