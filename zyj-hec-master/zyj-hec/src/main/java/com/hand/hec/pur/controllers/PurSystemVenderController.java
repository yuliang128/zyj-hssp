package com.hand.hec.pur.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.mapper.CshPaymentMethodMapper;
import com.hand.hec.csh.service.ICshPaymentMethodService;
import com.hand.hec.fnd.dto.PurVenderType;
import com.hand.hec.fnd.service.IFndTaxTypeCodeService;
import com.hand.hec.fnd.service.IPurVenderTypeService;
import com.hand.hec.pur.dto.PurSystemVender;
import com.hand.hec.pur.service.IPurSystemVenderService;

/**
 * <p>
 * 系统级供应商主数据
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/18 14:49
 */

@Controller
public class PurSystemVenderController extends BaseController {

    @Autowired
    private IPurSystemVenderService service;

    @Autowired
    private ICshPaymentMethodService paymentMethodService;

    @Autowired
    private IFndTaxTypeCodeService taxTypeCodeService;

    @Autowired
    private IPurVenderTypeService venderTypeService;

    @RequestMapping(value = "/fnd/FND2410/pur_system_vender.screen")
    public ModelAndView purSystemVenderView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("/fnd/FND2410/pur_system_vender");
        IRequest requestCtx = createRequestContext(request);

        view.addObject("paymentMethodList"
                , paymentMethodService.queryPaymentMethod(requestCtx, requestCtx.getMagOrgId(), requestCtx.getCompanyId()));
        view.addObject("taxTypeList"
                , taxTypeCodeService.selectAll(requestCtx));
        view.addObject("venderTypeList"
                , venderTypeService.selectOptions(requestCtx, PurVenderType.builder().enabledFlag(BaseConstants.YES).build(), null));
        return view;
    }

    @RequestMapping(value = "/pur/system-vender/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(PurSystemVender dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectForSysLevel(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/pur/system-vender/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PurSystemVender> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdateVenders(requestCtx, dto, true));
    }

    @RequestMapping(value = "/pur/system-vender/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<PurSystemVender> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}