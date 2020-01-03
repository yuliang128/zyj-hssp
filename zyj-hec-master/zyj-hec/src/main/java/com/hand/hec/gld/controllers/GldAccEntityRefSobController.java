package com.hand.hec.gld.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldAccEntityRefSob;
import com.hand.hec.gld.service.IGldAccEntityRefSobService;

/**
 * <p>
 * GldAccEntityRefSobController
 * </p>
 *
 * @author yang.duan 2019/01/10 13:43
 * update by luhui 2019/01/16 15:58
 */

@Controller
@RequestMapping(value = "/gld/acc-entity-ref-sob")
public class GldAccEntityRefSobController extends BaseController {

    @Autowired
    private IGldAccEntityRefSobService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldAccEntityRefSob dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldAccEntityRefSob.FIELD_ACC_ENTITY_ID));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldAccEntityRefSob> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldAccEntityRefSob> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }


    /**
     * 核算主体分配账套-查询当前核算主体关联的账套信息及其科目表信息
     *
     * @param dto
     * @return
     * @author ngls.luhui 2019-01-23 15:01
     */
    @RequestMapping(value = "/queryMoreSob", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryMoreSob(GldAccEntityRefSob dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryMoreSob(dto.getAccEntityId(),requestContext,page,pageSize));
    }
}