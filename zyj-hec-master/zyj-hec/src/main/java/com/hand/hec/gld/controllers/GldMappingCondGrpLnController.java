package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.generator.service.impl.FileUtil;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldMappingCondGrpLn;
import com.hand.hec.gld.service.IGldMappingCondGrpLnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * 匹配组行控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
public class GldMappingCondGrpLnController extends BaseController {

    @Autowired
    private IGldMappingCondGrpLnService service;

    @RequestMapping(value = "gld/FND2910/gld_acc_gen_user_dy.screen")
    public ModelAndView gldAccGenUserDyView(HttpServletRequest request,
                    @RequestParam(name = "groupName") String groupName) {
        ModelAndView modelAndView = new ModelAndView("gld/FND2910/gld_acc_gen_user_dy");
        IRequest iRequest = createRequestContext(request);
        List<GldMappingCondGrpLn> gldMappingCondGrpLns = service.selectGroupLines(iRequest, groupName);
        for (GldMappingCondGrpLn gldMappingCondGrpLn : gldMappingCondGrpLns) {
            String mappingConditionCode = gldMappingCondGrpLn.getMappingConditionCode();
            gldMappingCondGrpLn.setMappingConditionCode(FileUtil.columnToCamel(mappingConditionCode));
        }
        modelAndView.addObject("GrpLns", gldMappingCondGrpLns);
        return modelAndView;
    }

    @RequestMapping(value = "/gld/mapping-cond-grp-ln/model-query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData modelQuery(GldMappingCondGrpLn dto, @RequestParam(name = "groupName") String groupName,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<GldMappingCondGrpLn> gldMappingCondGrpLns = service.selectGroupLines(requestContext, groupName);
        for (GldMappingCondGrpLn gldMappingCondGrpLn : gldMappingCondGrpLns) {
            String mappingConditionCode = gldMappingCondGrpLn.getMappingConditionCode();
            gldMappingCondGrpLn.setMappingConditionCode(FileUtil.columnToCamel(mappingConditionCode));
        }
        return new ResponseData(gldMappingCondGrpLns);
    }

    @RequestMapping(value = "/gld/mapping-cond-grp-ln/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldMappingCondGrpLn dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/gld/mapping-cond-grp-ln/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldMappingCondGrpLn> dto, BindingResult result,
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

    @RequestMapping(value = "/gld/mapping-cond-grp-ln/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldMappingCondGrpLn> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
