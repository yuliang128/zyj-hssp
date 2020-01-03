package com.hand.hec.ssc.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.ssc.dto.SscTaskPoolPrepare;
import com.hand.hec.ssc.service.ISscTaskPoolPrepareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * xx控制器
 *
 * @author bo.zhang 2019-03-26
 */

@Controller
@RequestMapping(value = "/ssc/task-pool-prepare")
public class SscTaskPoolPrepareController extends BaseController{

    @Autowired
    private ISscTaskPoolPrepareService service;
    @Autowired
    IFndManagingOrganizationService fndManagingOrganizationService;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SscTaskPoolPrepare dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectSscTaskPoolPrepare(requestContext,
                 dto.getDocNumber(),dto.getDocCategory(),dto.getTaskStatus(),
                 dto.getDispatchStatus(),dto.getWorkEmployeeId(),dto.getDocEmployeeId(),dto.getMagOrgId(),
                dto.getCompanyId(),dto.getWorkflowId(),dto.getNodeId(),dto.getEnterTime(),dto.getEnterTimeTo(),
                dto.getExitTime(),dto.getExitTimeTo()));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SscTaskPoolPrepare> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<SscTaskPoolPrepare> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * <p>
     * 管理组织的下拉框
     * </p>
     */
    @RequestMapping(value = "/queryMagOrgOption")
    public ResponseData magOrgOption(FndManagingOrganization organization, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        List<FndManagingOrganization> magOrgs = fndManagingOrganizationService.magOrgOption(requestCtx);
        return new ResponseData(magOrgs);
    }
}