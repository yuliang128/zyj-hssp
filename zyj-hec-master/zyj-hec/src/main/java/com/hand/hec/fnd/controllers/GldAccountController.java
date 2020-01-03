package com.hand.hec.fnd.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.GldAccount;
import com.hand.hec.fnd.service.IGldAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * GldAccountController
 * </p>
 *
 * @author guiyu 2019/01/08 15:29
 */
@Controller
@RequestMapping(value = "/gld/account")
public class GldAccountController extends BaseController {

    @Autowired
    private IGldAccountService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(GldAccount dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldAccount.FIELD_ACCOUNT_SET_ID, Comparison.EQUAL),
                        new WhereField(GldAccount.FIELD_ACCOUNT_CODE, Comparison.LIKE),
                        new WhereField(GldAccount.FIELD_DESCRIPTION, Comparison.LIKE));
        if(dto.getAccountCodeFrom()!=null && !"undefined".equals(dto.getAccountCodeFrom())) {
            criteria.where(new WhereField(GldAccount.FIELD_ACCOUNT_CODE_FROM));
        }
        if(dto.getAccountCodeTo()!=null && !"undefined".equals(dto.getAccountCodeTo())) {
            criteria.where(new WhereField(GldAccount.FIELD_ACCOUNT_CODE_TO));
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldAccount> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldAccount> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/accountHierarchyDetail")
    public ModelAndView jumpAccountHierarchyDetail(HttpServletRequest request, Long accountSetId, Long accountId) {
        ModelAndView view = new ModelAndView("fnd/public/gld_account_hierarchy_detail");
        Map accountSetMap = service.accountSetRefrenceQuery(accountId, accountSetId);
        view.addObject("accountSetMap", accountSetMap);
        view.addObject("accountSetId", accountSetId);
        view.addObject("accountId", accountId);
        return view;
    }


    /**
     * 科目层次树
     *
     * @param request
     * @param accountSetId 科目ID
     * @author weikun.wang2019-02-28
     * @return ResponseData
     */
    @RequestMapping(value = "/hierarchyTreeQuery", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData hierarchyTreeQuery(Long accountSetId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        System.out.println("1111执行查询");
        return new ResponseData(service.hierarchyTreeQuery(requestContext,accountSetId));
    }
}
