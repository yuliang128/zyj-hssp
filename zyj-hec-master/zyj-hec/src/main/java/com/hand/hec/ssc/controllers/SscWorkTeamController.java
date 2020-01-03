package com.hand.hec.ssc.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.ssc.dto.SscWorkCenter;
import com.hand.hec.ssc.dto.SscWorkTeam;
import com.hand.hec.ssc.exception.SscWorkTeamException;
import com.hand.hec.ssc.service.ISscCoreService;
import com.hand.hec.ssc.service.ISscWorkCenterService;
import com.hand.hec.ssc.service.ISscWorkTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * SscWorkTeam控制器
 *
 * @author bo.zhang 2019-03-15
 */

@Controller
public class SscWorkTeamController extends BaseController{

    @Autowired
    private ISscWorkTeamService service;
    @Autowired
    private ISscWorkCenterService sscWorkCenterService;

    @RequestMapping(value = "/ssc/work-team/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SscWorkTeam dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.querySscWorkTeam(dto.getWorkCenterId(),dto.getWorkTeamCode(),dto.getDescription(),requestContext));
    }

    @RequestMapping(value = "/ssc/work-team/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SscWorkTeam> dto, BindingResult result, HttpServletRequest request)throws SscWorkTeamException{
        getValidator().validate(dto, result);
        ResponseData responseData = new ResponseData(false);
        if (result.hasErrors()) {
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        if(dto.size()!=0){
            List<SscWorkTeam> sscWorkTeams = service.querySscWorkTeam(dto.get(0).getWorkCenterId(),null,null,requestCtx);
            for(int i=0;i<dto.size();i++){
                if(!service.notMutualSuperior(dto.get(i).getWorkTeamId(),dto.get(i).getParentWorkTeamId(),sscWorkTeams,sscWorkTeams.size())){
                    service.notMutualSuperiorException(false);
                    return responseData;
                }
            }

        }
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/ssc/work-team/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<SscWorkTeam> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "ssc/SSC1020/ssc_work_team.screen")
    public ModelAndView sscWorkTeamView(HttpServletRequest request){
        IRequest requestContext = createRequestContext(request);
        List<SscWorkCenter> sscWorkCenterList = sscWorkCenterService.querySscWorkCenter(requestContext,null,null);
        ModelAndView view = new ModelAndView("ssc/SSC1020/ssc_work_team");
        view.addObject("sscWorkCenterList",sscWorkCenterList);
        return view;
    }

    @RequestMapping(value = "/ssc/work-team/queryEnableTeam",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryEnableTeam(SscWorkTeam dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryEnableTeam(dto.getWorkCenterId(),dto.getWorkTeamCode(),dto.getDescription(),requestContext));
    }

    @RequestMapping(value = "/ssc/work-team/queryEmpOfUserOnTeam",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryEmpOfUserOnTeam(SscWorkTeam dto, HttpServletRequest request,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                             @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryEmpOfUserOnTeam(dto.getWorkCenterId(),null,null,requestContext,page,pageSize));
    }
}