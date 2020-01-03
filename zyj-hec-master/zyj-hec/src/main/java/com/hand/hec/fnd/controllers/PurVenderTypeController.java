package com.hand.hec.fnd.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.PurVenderType;
import com.hand.hec.fnd.service.IPurVenderTypeService;

/**
 * <p>
 * 供应商类型控制器
 * </p>
 *
 * @author YHL 2019/01/28 18:45
 */
@Controller
@RequestMapping(value = "/pur/vender-type")
public class PurVenderTypeController extends BaseController {

    @Autowired
    private IPurVenderTypeService service;

    /**
     * 查询供应商类型（实现模糊查询）
     *
     * @param dto 供应商类型
     * @param page
     * @param pageSize
     * @param request
     * @author YHL 2019-01-28 21:56
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(PurVenderType dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(PurVenderType.FIELD_VENDER_TYPE_CODE, Comparison.LIKE),
                new WhereField(PurVenderType.FIELD_DESCRIPTION, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PurVenderType> dto, BindingResult result, HttpServletRequest request)
            throws BaseException {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<PurVenderType> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 核算主体分配
     *
     * @param request
     * @param venderTypeId 供应商类型ID
     * @author YHL 2019-01-29 12:51
     * @return org.springframework.web.servlet.ModelAndView “供应商类型定义” 模块中，“核算主体分配”功能的页面
     */
    @RequestMapping(value = "/fndVenderTypeAeView")
    public ModelAndView fndVenderTypeAeView(HttpServletRequest request, Long venderTypeId) {
        HttpSession session = request.getSession();
        Long companyId = (Long) session.getAttribute(FndCompany.FIELD_COMPANY_ID);
        ModelAndView view = new ModelAndView("fnd/FND2400/fnd_vender_type_ae");
        view.addObject("venderTypeId", venderTypeId);
        view.addObject("companyId", companyId);
        return view;
    }

    @RequestMapping(value = "/queryEnabledVenderType", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData queryEnabledVenderType(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        PurVenderType pvt = new PurVenderType();
        pvt.setEnabledFlag(BaseConstants.YES);
        return new ResponseData(service.select(requestContext, pvt, 0, 0));
    }
}