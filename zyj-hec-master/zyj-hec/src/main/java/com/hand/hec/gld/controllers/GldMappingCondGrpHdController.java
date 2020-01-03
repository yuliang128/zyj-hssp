package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.IgnoreToken;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldMappingCondGrpHd;
import com.hand.hec.gld.service.IGldMappingCondGrpHdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * 匹配组头控制器
 *
 * @author xxx YYYY-mm-dd
 */
@IgnoreToken
@Controller
@RequestMapping(value = "/gld/mapping-cond-grp-hd")
public class GldMappingCondGrpHdController extends BaseController{

    @Autowired
    private IGldMappingCondGrpHdService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldMappingCondGrpHd dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldMappingCondGrpHd.FIELD_GROUP_NAME, Comparison.LIKE),new WhereField(GldMappingCondGrpHd.FIELD_DESCRIPTION,Comparison.LIKE),GldMappingCondGrpHd.FIELD_USAGE_CODE_DESC,GldMappingCondGrpHd.FIELD_USAGE_CODE);
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldMappingCondGrpHd> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        if("update".equals(dto.get(0).get__status())){
            Long upperLimitPriority = 999L;
            for (GldMappingCondGrpHd gldMappingCondGrpHd:dto){
                if(gldMappingCondGrpHd.getPriority().longValue() == upperLimitPriority.longValue()){
                    ResponseData responseData = new ResponseData(false);
                    responseData.setMessage("优先级最大为998,优先级999已经为系统保留,为通用匹配组使用!");
                    return responseData;
                }
            }
            IRequest requestCtx = createRequestContext(request);
            return new ResponseData(service.batchUpdate(requestCtx, dto));
        } else if("delete".equals(dto.get(0).get__status())){
            for (GldMappingCondGrpHd gldMappingCondGrpHd:dto){
                if("Y".equals(gldMappingCondGrpHd.getSysFlag())){
                    ResponseData responseData = new ResponseData(false);
                    responseData.setMessage("");
                    return responseData;
                }
            }
            IRequest requestCtx = createRequestContext(request);
            return new ResponseData(service.deleteMappingGroups(requestCtx,dto));
        }
        return new ResponseData();
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldMappingCondGrpHd> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     *@Description 创建匹配组
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/17 17:16
     *@Param
     *@Version 1.0
     **/
    @RequestMapping(value = "/batch_update")
    @ResponseBody
    public ResponseData createMappingGroup(@RequestBody List<GldMappingCondGrpHd> dto, BindingResult result, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        service.createMappingGroup(requestCtx,dto);
        return new ResponseData();
    }

    /**
     *@Description 查询动态表单返回结果
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/22 15:12
     *@Param
     *@Version 1.0
     **/
    @RequestMapping(value = "/query-user-dy",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryUserDy(GldMappingCondGrpHd dto, HttpServletRequest request, HttpSession session) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryUserDy(requestContext,dto,session));
    }

    /**
     *@Description 通用匹配组动态生成的表单保存更新删除
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/28 10:39
     *@Param
     *@Version 1.0
     **/
    @RequestMapping(value = "/query-user-dy-submit",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryUserDySubmit(@RequestBody List<GldMappingCondGrpHd> dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        service.queryUserDySubmit(requestContext,dto);
        return new ResponseData(dto);
    }
}