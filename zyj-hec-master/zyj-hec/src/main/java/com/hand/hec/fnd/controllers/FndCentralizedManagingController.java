package com.hand.hec.fnd.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.FndCentralizedManaging;
import com.hand.hec.fnd.service.IFndCentralizedManagingService;

/**
 * <p>
 * 归口管理控制器
 * </p>
 *
 * @author YHL 2019/01/21 19:44
 */
@Controller
@RequestMapping(value = "/fnd/centralized-managing")
public class FndCentralizedManagingController extends BaseController {

    @Autowired
    private IFndCentralizedManagingService service;

    @Autowired
    private IFndManagingOrganizationService managingOrganizationService;

    /**
     * 查询归口管理（使用的selectCentralizedMagList为自定义select方法）
     *
     * @param dto 归口管理
     * @param page
     * @param pageSize
     * @param request
     * @author YHL 2019-01-24 10:43
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndCentralizedManaging dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectCentralizedMagList(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndCentralizedManaging> dto, BindingResult result,
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<FndCentralizedManaging> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 归口管理定义-查询管理组织
     *
     * @param request
     * @author YHL 2019-01-22 15:05
     * @return org.springframework.web.servlet.ModelAndView 归口管理定义页面
     */
    @RequestMapping(value = "/fndCentralizedManagingView")
    public ModelAndView fndCentralizedManagingView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        Long companyId = (Long) session.getAttribute(FndCompany.FIELD_COMPANY_ID);
        FndManagingOrganization managingOrganization = managingOrganizationService
                .defaultManageOrganizationQuery(requestContext, companyId);
        ModelAndView view = new ModelAndView("fnd/FND1130/fnd_centralized_managing");
        view.addObject("managingOrganization", managingOrganization);
        return view;
    }

}