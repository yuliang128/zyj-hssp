package com.hand.hec.pur.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
import com.hand.hec.fnd.service.IFndTaxTypeCodeService;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import com.hand.hec.pur.dto.PurSystemVender;
import com.hand.hec.pur.service.IPurSystemVenderService;

/**
 * <p>
 * 供应商管理控制器
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/28 16:26
 */
@Controller
public class PurAeVenderController extends BaseController {

    @Autowired
    private IPurSystemVenderService service;

    @Autowired
    private CshPaymentMethodMapper paymentMethodMapper;

    @Autowired
    private IFndTaxTypeCodeService taxTypeCodeService;

    @Autowired
    private IGldAccountingEntityService accEntityService;

    @RequestMapping(value = "/fnd/FND2420/pur_ae_vender.screen")
    public ModelAndView purAeVenderView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("/fnd/FND2420/pur_ae_vender");

        IRequest requestCtx = createRequestContext(request);
        List<GldAccountingEntity> accEntities = accEntityService.queryAccEntityByCompany(requestCtx, requestCtx.getCompanyId());
        /*view.addObject("paymentMethodList", paymentMethodMapper.queryPaymentMethod());*/
        view.addObject("accEntityList",accEntities);
        view.addObject("defaultAccEntity",accEntities.stream().filter(record->Objects.equals(record.getDefaultFlag(), BaseConstants.YES)).collect(Collectors.toList()));
        view.addObject("taxTypeCodeList", taxTypeCodeService.queryByAccEntityId(requestCtx, requestCtx.getCompanyId()));
        return view;
    }

    @RequestMapping(value = "/pur/ae-vender/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(PurSystemVender dto, Long accEntityId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectForNonSysLevel(requestContext, dto, accEntityId, page, pageSize));
    }

    @RequestMapping(value = "/pur/ae-vender/submit", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData update(@RequestBody List<PurSystemVender> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdateVenders(requestCtx, dto,false));
    }

}
