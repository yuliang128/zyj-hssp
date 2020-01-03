package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldTransitAccount;
import com.hand.hec.gld.service.IGldTransitAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 中转科目定义控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
public class GldTransitAccountController extends BaseController{

    @Autowired
    private IGldTransitAccountService service;

    @RequestMapping(value = "/gld/transit-account/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldTransitAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(GldTransitAccount.FIELD_CR_ACCOUNT_ID,GldTransitAccount.FIELD_MAG_ORG_ID,GldTransitAccount.FIELD_DR_ACCOUNT_ID,GldTransitAccount.FIELD_SET_OF_BOOKS_ID,GldTransitAccount.FIELD_MO_EXPENSE_ITEM_ID);
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/gld/transit-account/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldTransitAccount> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/gld/transit-account/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldTransitAccount> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}