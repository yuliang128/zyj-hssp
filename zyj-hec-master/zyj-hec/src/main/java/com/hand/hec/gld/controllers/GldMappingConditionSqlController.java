package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldMappingConditionSql;
import com.hand.hec.gld.service.IGldMappingConditionSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 匹配项控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
public class GldMappingConditionSqlController extends BaseController{

    @Autowired
    private IGldMappingConditionSqlService service;

    @RequestMapping(value = "/gld/mapping-condition-sql/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldMappingConditionSql dto, @RequestParam String usageCode, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectGridData(requestContext,usageCode,page,pageSize));
    }

    @RequestMapping(value = "/gld/mapping-condition-sql/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldMappingConditionSql> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/gld/mapping-condition-sql/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldMappingConditionSql> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/gld/mapping-condition-sql/execute")
    @ResponseBody
    public ResponseData check(HttpServletRequest request,@RequestBody GldMappingConditionSql dto){
        if(dto.getInnerMap().get("RESP_CENTER") != null && dto.getInnerMap().get("ACC_ENTITY") == null){
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage("选择责任中心之前要先选择核算主体!");
            return responseData;
        }
        if(dto.getInnerMap().get("ORG_UNIT") != null && dto.getInnerMap().get("FND_COMPANY") == null){
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage("选择部门之前要先选择管理公司!");
            return responseData;
        }
        if(dto.getInnerMap().get("EXP_POSITION") != null && dto.getInnerMap().get("FND_COMPANY") == null){
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage("选择岗位之前要先选择管理公司!");
            return responseData;
        }
        return new ResponseData();
    }
}