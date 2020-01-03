package com.hand.hec.gld.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.GldAccount;
import com.hand.hec.gld.dto.GldAccEntityRefAccount;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.service.IGldAccEntityRefAccountService;

/**
 * <p>
 * GldAccEntityRefAccountController
 * </p>
 *
 * @author yang.duan 2019/01/10 13:45
 * update by luhui 2019/01/17 13:58
 */

@Controller
@RequestMapping(value = "/gld/acc-entity-ref-account")
public class GldAccEntityRefAccountController extends BaseController{

    @Autowired
    private IGldAccEntityRefAccountService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldAccEntityRefAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldAccEntityRefAccount.FIELD_ACC_ENTITY_ID),
                new WhereField(GldAccEntityRefAccount.FIELD_SET_OF_BOOKS_ID));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldAccEntityRefAccount> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldAccEntityRefAccount> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryActNotAsgnAccE")
    @ResponseBody
    public ResponseData queryActNotAsgnAccE(GldAccEntityRefAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryActNotAsgnAccE(dto.getAccEntityId(),dto.getSetOfBooksId(),requestContext, page, pageSize));
    }

    @RequestMapping(value = "/queryAccountBySob")
    @ResponseBody
    public ResponseData queryAccountBySob(GldAccEntityRefAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAccountBySob(dto.getSetOfBooksId(), requestContext, page, pageSize));
    }

    @RequestMapping(value = "/batchSubmit")
    @ResponseBody
    public ResponseData batchSubmit(@RequestBody List<GldAccountingEntity> dto, BindingResult result, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSubmit(dto,requestCtx));
    }
}