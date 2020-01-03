package com.hand.hec.fnd.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.PurVenderTypeRefAe;
import com.hand.hec.fnd.service.IPurVenderTypeRefAeService;
import com.hand.hec.gld.dto.GldAccountingEntity;

/**
 * <p>
 * 供应商类型分配核算主体控制器
 * </p>
 *
 * @author YHL 2019/01/29 12:48
 */
@Controller
@RequestMapping(value = "/pur/vender-type-ref-ae")
public class PurVenderTypeRefAeController extends BaseController {

    @Autowired
    private IPurVenderTypeRefAeService service;

    /**
     * 查询供应商类型中分配的核算主体信息（使用的getVenderTypeRefAeByVenderTypeId为自定义select方法）
     *
     * @param venderTypeId 供应商类型ID
     * @param page
     * @param pageSize
     * @param request
     * @author YHL 2019-01-29 14:14
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(Long venderTypeId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.getVenderTypeRefAeByVenderTypeId(requestContext, venderTypeId, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PurVenderTypeRefAe> dto, BindingResult result,
            HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<PurVenderTypeRefAe> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 供应商类型定义-批量分配（使用的multipleAssign为自定义方法）
     *
     * @param dto
     * @param result
     * @param request
     * @author YHL 2019-02-14 14:13
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/purVenderTypeAeMultipleView")
    @ResponseBody
    public ResponseData purVenderTypeAeMultipleView(@RequestBody List<GldAccountingEntity> dto, BindingResult result,
            HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.multipleAssign(requestCtx, dto));
    }
}