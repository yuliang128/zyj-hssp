package com.hand.hec.fnd.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.FndMagOrgRefBgtOrg;
import com.hand.hec.fnd.service.IFndMagOrgRefBgtOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * <p>
 *  FndMagOrgRefBgtOrgController
 * </p>
 *
 * @author yang.duan 2019/01/10 11:05
 */
@Controller
@RequestMapping(value = "/fnd/mag-org-ref-bgt-org")
public class FndMagOrgRefBgtOrgController extends BaseController{

    @Autowired
    private IFndMagOrgRefBgtOrgService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndMagOrgRefBgtOrg dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(FndMagOrgRefBgtOrg.FIELD_MAG_ORG_ID,Comparison.EQUAL),
                new WhereField(FndMagOrgRefBgtOrg.FIELD_BGT_ORG_ID,Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndMagOrgRefBgtOrg> dto, BindingResult result, HttpServletRequest request){
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
    public ResponseData delete(HttpServletRequest request,@RequestBody List<FndMagOrgRefBgtOrg> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }

    
    /**
     * 管理组织与预算组织-存储关联关系,根据参数来判断表中是否有，从而新增或更新或删除
     *
     * @param dto
     * @author ngls.luhui 2019-02-14 17:23
     * @return boolean
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseData save(@RequestBody List<FndMagOrgRefBgtOrg> dto, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.save(requestCtx, dto));
    }
}