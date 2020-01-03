package com.hand.hap.sys.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.sys.dto.SystemHelp;
import com.hand.hap.sys.service.ISystemHelpService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 系统帮助定义控制器
 *
 * @author linrz 2019-08-02
 */

@Controller
public class SystemHelpController extends BaseController{

    @Autowired
    private ISystemHelpService service;

    /**
     * 增加条件查询
     * 实现方式为注解@Where + Criteria
     * */
    @RequestMapping(value = "/sys/system-help/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(SystemHelp dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        if(dto.getDescription()!=null){
            criteria.where(new WhereField(SystemHelp.FIELD_DESCRIPTION, Comparison.LIKE));
        }
        return new ResponseData(service.selectOptions(requestContext,dto,criteria));
        //return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/sys/system-help/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SystemHelp> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/sys/system-help/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<SystemHelp> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}