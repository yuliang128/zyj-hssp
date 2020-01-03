package com.hand.hec.gld.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldAccEntityHierarchy;
import com.hand.hec.gld.service.IGldAccEntityHierarchyService;

/**
 * 核算主体层次控制器
 *
 * @author luhui  2019-01-22
 */

@Controller
@RequestMapping(value = "/gld/acc-entity-hierarchy")
public class GldAccEntityHierarchyController extends BaseController {

    @Autowired
    private IGldAccEntityHierarchyService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldAccEntityHierarchy dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    /**
     * 查询对应核算主体的子核算主体
     *
     * @param parentEntityId
     * @return com.hand.hap.system.dto.ResponseData
     * @author ngls.luhui 2019-01-22 16:39
     */
    @RequestMapping(value = "/queryChild", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryChild(@RequestParam Long parentEntityId,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectChild(parentEntityId,requestContext,page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldAccEntityHierarchy> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldAccEntityHierarchy> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }


    /**
     * 查询核算主体层次的树形结构
     *
     * @param request
     * @return
     * @author ngls.luhui 2019-01-23 14:56
     */
    @RequestMapping(value = "/gld_acc_entity_hierarchy_tree")
    public ModelAndView jumpAccountHierarchyDetail(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("gld/GLD2040/gld_acc_entity_hierarchy_tree");
        List<GldAccEntityHierarchy> acchList = service.selectTree();
        view.addObject("acchList", acchList);
        return view;
    }
}