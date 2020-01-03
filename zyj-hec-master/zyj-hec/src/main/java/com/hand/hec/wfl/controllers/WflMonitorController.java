package com.hand.hec.wfl.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import aurora.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.hand.hap.core.web.view.nested.NestedViewExecutionHelper;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.function.service.IResourceService;
import com.hand.hec.fnd.dto.FndDocInfo;
import com.hand.hec.fnd.service.IFndDocEngineService;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflPage;
import com.hand.hec.wfl.dto.WflProcess;
import com.hand.hec.wfl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.wfl.dto.WflApproveRecord;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WflMonitorController extends BaseController {

    @Autowired
    private IWflMonitorService service;

    @Autowired
    private IWflInstanceService instanceService;

    @Autowired
    private IWflProcessService processService;

    @Autowired
    private IWflPageService pageService;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IWflReceiverUtilService utilService;

    @Autowired
    private IFndDocEngineService docEngineService;

    @RequestMapping(value = "/wfl/monitor/select-wfl-instance")
    @ResponseBody
    public ResponseData selectWflInstance(Map param, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectWflInstance(requestContext, param, page, pageSize));
    }

    @RequestMapping(value = "/wfl/monitor/select-wfl-approve-info")
    @ResponseBody
    public ResponseData selectWflApproveInfo(WflInstance instance, BindingResult result, HttpServletRequest request,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.selectWflApproveInfo(requestCtx, instance.getInstanceId(), page, pageSize));
    }

    @RequestMapping(value = "/wfl/public/wfl_view.screen")
    public ModelAndView jumpWflView(HttpServletRequest request, Long instanceId) {
        IRequest requestCtx = createRequestContext(request);


        // 获取当前screenInclude的页面URL
        WflInstance instance = new WflInstance();
        instance.setInstanceId(instanceId);
        instance = instanceService.selectByPrimaryKey(requestCtx, instance);

        WflProcess process = new WflProcess();
        process.setProcessId(instance.getProcessId());
        process = processService.selectByPrimaryKey(requestCtx, process);

        WflPage page = new WflPage();
        page.setPageId(process.getViewPageId());
        page = pageService.selectByPrimaryKey(requestCtx, page);

        Resource resource = new Resource();
        resource.setResourceId(page.getResourceId());
        resource = resourceService.selectByPrimaryKey(requestCtx, resource);


        //
        // 根据requestMapping引入对应的model设置
        // ------------------------------------------------------------------------------
        FndDocInfo docInfo = docEngineService.getDocHeadInfo(instance.getDocCategory());
        request.setAttribute(BeanUtil.convertFieldNameD2J(docInfo.getIdFieldName()), instance.getDocId());

        List<String> nestedViewList = Arrays.asList(resource.getUrl());
        ModelAndView view = NestedViewExecutionHelper.proceedNestedView(nestedViewList);
        if (view == null) {
            view = new ModelAndView();
        }
        view.setViewName("/wfl/public/wfl_view");


        // 获取当前单据参数
        FndDocInfo headInfo = utilService.getHeadDocInfo(instance);

        view.addObject("docPage", resource.getUrl());
        view.addObject("docParamName", BeanUtil.convertFieldNameD2J(docInfo.getIdFieldName()));
        view.addObject("docId", instance.getDocId());

        return view;
    }
}
