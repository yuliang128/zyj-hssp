package com.hand.hec.fnd.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.GldAccountHierarchy;
import com.hand.hec.fnd.dto.GldAccountHierarchyTree;
import com.hand.hec.fnd.dto.GldAccountSet;
import com.hand.hec.fnd.service.IGldAccountHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * GldAccountHierarchyController
 * </p>
 * 
 * @author guiyu 2019/01/08 15:31
 */
@Controller
@RequestMapping(value = "/gld/account-hierarchy")
public class GldAccountHierarchyController extends BaseController{

    @Autowired
    private IGldAccountHierarchyService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(GldAccountHierarchy dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        if(dto.getParentAccountId()!=null && !"undefined".equals(dto.getParentAccountId())) {
            criteria.where(new WhereField(GldAccountHierarchy.FIELD_PARENT_ACCOUNT_ID, Comparison.EQUAL));
        }
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldAccountHierarchy> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldAccountHierarchy> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping( value = "/accountHierarchyTree")
    public ModelAndView jumpAccountHierarchyDetail(HttpServletRequest request, Long accountSetId) {
        ModelAndView view = new ModelAndView("fnd/public/gld_account_hierarchy_tree");
        List<GldAccountHierarchyTree> gldAccountHierarchyTree = service.accountHierarchyTreeQuery(accountSetId);
        view.addObject("accountSetId",accountSetId);
        view.addObject("accountHierarchyTree", gldAccountHierarchyTree);
        return view;
    }

    @RequestMapping( value = "/refreshAccountHierarchy")
    @ResponseBody
    public ResponseData refreshAccountHierarchy(HttpServletRequest request, GldAccountSet gldAccountSet) {
        IRequest requestCtx = createRequestContext(request);

        if(gldAccountSet !=null && gldAccountSet.getAccountSetId()!=null){
            service.refreshAccountHierarchy(requestCtx, gldAccountSet.getAccountSetId());
        }else{
            return new ResponseData(false);
        }
        return new ResponseData(true);
    }

    @RequestMapping( value = "/refreshGldAccountHierarchy")
    @ResponseBody
    public ResponseData refreshGldAccountHierarchy(HttpServletRequest request, long accountSetId) {
        IRequest requestCtx = createRequestContext(request);
        /*System.out.println("99999"+accountSetId);*/
        if(accountSetId !=0){
            service.refreshAccountHierarchy(requestCtx, accountSetId);
        }else{
            return new ResponseData(false);
        }
        return new ResponseData(true);
    }
}