package com.hand.hec.fnd.controllers;

import org.springframework.stereotype.Controller;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.FndMagOrgRefBgtOrg;
import com.hand.hec.fnd.dto.FndMagOrgRefGldSob;
import com.hand.hec.fnd.service.IFndMagOrgRefGldSobService;

/**
 * <p>
 *  FndMagOrgRefGldSobController
 * </p>
 *
 * @author yang.duan 2019/01/10 11:05
 */

@Controller
@RequestMapping(value = "/fnd/mag-org-ref-gld-sob")
public class FndMagOrgRefGldSobController extends BaseController{

    @Autowired
    private IFndMagOrgRefGldSobService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndMagOrgRefGldSob dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(FndMagOrgRefGldSob.FIELD_MAG_ORG_ID));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndMagOrgRefGldSob> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<FndMagOrgRefGldSob> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 管理组织与账套-存储关联关系,根据参数来判断表中是否有，从而新增或更新或删除
     *
     * @param dto
     * @author ngls.luhui 2019-02-14 17:23
     * @return boolean
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseData save(@RequestBody List<FndMagOrgRefGldSob> dto, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.save(requestCtx, dto));
    }
}