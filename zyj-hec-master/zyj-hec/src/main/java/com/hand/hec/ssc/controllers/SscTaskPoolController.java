package com.hand.hec.ssc.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.ssc.dto.SscTaskPool;
import com.hand.hec.ssc.service.ISscCoreService;
import com.hand.hec.ssc.service.ISscTaskPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 我已处理的任务控制器
 *
 * @author zhaohui 2019/03/22 14:51
 */
@Controller
@RequestMapping(value = "/ssc/task-pool")
public class SscTaskPoolController extends BaseController{

    @Autowired
    private ISscTaskPoolService service;

    @Autowired
    private ISscCoreService sscCoreService;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SscTaskPool dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SscTaskPool> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<SscTaskPool> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/grid-query",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseData gridQuery(SscTaskPool dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.gridQuery(requestContext,dto,page,pageSize));
    }
    
    /**
     * 退回申请单据查询
     *
     * @author ngls.luhui 2019-03-25 14:45
     */
    @RequestMapping(value = "/returnQuery",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData ReturnQuery(SscTaskPool dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.ReturnQuery(dto.getDocCategory(),dto.getDocNumber(),requestContext,page,pageSize));
    }

    /**
     * 强制收回单据查询
     *
     * @author ngls.luhui 2019-03-25 14:45
     */
    @RequestMapping(value = "/forceQuery",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData forceQuery(SscTaskPool dto,@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.forceQuery(dto, requestContext, page, pageSize));
    }

    /**
     * 我正在处理的任务grid数据查询
     * @Author weikun.wang
     * @Date 2019/3/26 14:46
     * @param request 请求上下文
     * @param dto 查询条件
     * @param page
     * @param pageSize
     * @return List<SscTaskPool>
     * @Version 1.0
     **/
    @RequestMapping(value = "/doing-task-query",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseData doingTaskQuery(SscTaskPool dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.doingTaskQuery(requestContext,dto,page,pageSize));
    }


    /**
     * 我暂挂的任务grid数据查询
     * @Author weikun.wang
     * @Date 2019/3/26 14:46
     * @param request 请求上下文
     * @param dto 查询条件
     * @param page
     * @param pageSize
     * @return List<SscTaskPool>
     * @Version 1.0
     **/
    @RequestMapping(value = "/hold-task-query",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseData holdTaskQuery(SscTaskPool dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.holdTaskQuery(requestContext,dto,page,pageSize));
    }


    /**
     * 我退回的任务grid数据查询
     * @Author weikun.wang
     * @Date 2019/3/26 14:46
     * @param request 请求上下文
     * @param dto 查询条件
     * @param page
     * @param pageSize
     * @return List<SscTaskPool>
     * @Version 1.0
     **/
    @RequestMapping(value = "/return-task-query",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseData ReturnTaskQuery(SscTaskPool dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.returnTaskQuery(requestContext,dto,page,pageSize));
    }

    /**
     * 手动派工
     * @Author weikun.wang
     * @Date 2019/3/26 14:46
     * @param request 请求上下文
     * @return List<SscTaskPool>
     * @Version 1.0
     **/
    @RequestMapping(value = "/manual-dispatch",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseData manualDispatch(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        sscCoreService.manualDispatch(requestContext);
        return new ResponseData(true);
    }

    /**
     * 自动派工
     * @Author weikun.wang
     * @Date 2019/3/26 14:46
     * @param request 请求上下文
     * @return List<SscTaskPool>
     * @Version 1.0
     **/
    @RequestMapping(value = "/auto-dispatch",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseData autoDispatch(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        sscCoreService.autoDispatch(requestContext);
        return new ResponseData(true);
    }

    @RequestMapping(value = "do-batch-process")
    public ResponseData doBatchProcess(HttpServletRequest request,List<SscTaskPool> sscTaskPools) {
        IRequest requestContext = createRequestContext(request);
        service.doBatchProcess(requestContext,sscTaskPools);
        return new ResponseData(true);
    }

}