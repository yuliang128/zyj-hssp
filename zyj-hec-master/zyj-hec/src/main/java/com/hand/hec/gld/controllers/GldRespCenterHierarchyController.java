package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldRespCenterHierarchy;
import com.hand.hec.gld.service.IGldRespCenterHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * <p>
 *核算主体级责任中心层级
 * </p>
 *
 * @author rui.shi@hand-china.com 2019/01/08 13:50
 */
@Controller
@RequestMapping("/gld/resp-center-hierarchy")
public class GldRespCenterHierarchyController extends BaseController{

    @Autowired
    private IGldRespCenterHierarchyService service;

    @RequestMapping("/query")
    @ResponseBody
    public ResponseData query(GldRespCenterHierarchy dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

		Criteria criteria = new Criteria(dto);
		criteria.where(new WhereField(GldRespCenterHierarchy.FIELD_PARENT_RESP_CENTER_ID),
				new WhereField(GldRespCenterHierarchy.FIELD_RESPONSIBILITY_CENTER_CODE),
				new WhereField(GldRespCenterHierarchy.FIELD_RESPONSIBILITY_CENTER_NAME));

        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping("/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldRespCenterHierarchy> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping("/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<GldRespCenterHierarchy> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}