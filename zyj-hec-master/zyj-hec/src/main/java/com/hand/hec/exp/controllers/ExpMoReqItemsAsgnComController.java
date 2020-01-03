package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoReqItem;
import com.hand.hec.exp.dto.ExpMoReqItemsAsgnCom;
import com.hand.hec.exp.service.IExpMoReqItemsAsgnComService;
import com.hand.hap.fnd.dto.FndCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 申请项目分配公司控制器
 * </p>
 *
 * @author yang.duan 2019/02/19 10:03
 */

@Controller
@RequestMapping(value = "/exp/mo-req-items-asgn-com")
public class ExpMoReqItemsAsgnComController extends BaseController {

    @Autowired
    private IExpMoReqItemsAsgnComService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoReqItemsAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryExpMoReqItemCom(requestContext, dto));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoReqItemsAsgnCom> dto, BindingResult result,
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

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoReqItemsAsgnCom> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryCompanyByItem")
    @ResponseBody
    public ResponseData queryCompanyByItem(HttpServletRequest request, ExpMoReqItemsAsgnCom dto) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCompanyByItem(requestContext,dto));
    }

    @RequestMapping(value = "/batchAsgnCompanyOneToMany")
    @ResponseBody
    public ResponseData batchAsgnCompanyOneToMany(HttpServletRequest request, @RequestBody List<FndCompany> companies) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.batchAsgnCompanyOneToMany(requestContext,companies));
    }

    @RequestMapping(value="/batchAsgnCompanyManyToMany")
    @ResponseBody
    public ResponseData batchAsgnCompanyManyToMany(HttpServletRequest request, @RequestBody List<ExpMoReqItem> expMoReqItems){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.batchAsgnCompanyManyToMany(requestContext,expMoReqItems));
    }
}
