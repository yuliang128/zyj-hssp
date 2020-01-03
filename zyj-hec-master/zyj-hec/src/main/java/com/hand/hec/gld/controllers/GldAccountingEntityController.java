package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 核算主体定义
 * </p>
 *
 * @author ngls.luhui 2019/01/08 13:50
 */
@Controller
@RequestMapping(value = "/gld/account-entity")
public class GldAccountingEntityController extends BaseController {


    @Autowired
    private IGldAccountingEntityService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(GldAccountingEntity dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldAccountingEntity.FIELD_ACC_ENTITY_ID, Comparison.EQUAL),
                        new WhereField(GldAccountingEntity.FIELD_ACC_ENTITY_NAME, Comparison.LIKE),
                        new WhereField(GldAccountingEntity.FIELD_ACC_ENTITY_CODE, Comparison.LIKE),
                        new WhereField(GldAccountingEntity.FIELD_COMPANY_TYPE, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldAccountingEntity> dto, BindingResult result,
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

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldAccountingEntity> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 核算主体定义主页面-查询出核算主体及其关联的预算，账套等信息
     *
     * @param dto
     * @return
     * @author ngls.luhui 2019-01-23 15:02
     */
    @RequestMapping(value = "/queryMore")
    @ResponseBody
    public ResponseData queryMoreSetOfBooks(GldAccountingEntity dto,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        List<GldAccountingEntity> list = service.selectMoreAccEntity(dto, requestCtx, page, pageSize);
        return new ResponseData(list);
    }

    /**
     * 供应商类型定义-分配核算主体-批量分配页面，根据核算主体（代码范围）、供应商类型分配核算主体中的信息查询核算主体
     *
     * @param dto 核算主体
     * @param venderTypeId 供应商类型ID
     * @param page
     * @param pageSize
     * @param request
     * @return com.hand.hap.system.dto.ResponseData
     * @author YHL 2019-01-30 13:04
     */
    @RequestMapping(value = "/getAccEntityByVenderTypeRefAe", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData getAccEntityByVenderTypeRefAe(GldAccountingEntity dto, Long venderTypeId,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        Long magOrgId = (Long) session.getAttribute(FndManagingOrganization.FIELD_MAG_ORG_ID);
        return new ResponseData(service.getAccEntityByVenderTypeRefAe(requestContext, dto, venderTypeId, magOrgId, page,
                        pageSize));
    }

    /**
     * 供应商类型定义-批量分配页面，根据核算主体（代码范围）、供应商类型分配核算主体中的信息查询核算主体
     *
     * @param dto 核算主体
     * @param magOrgId 管理组织ID
     * @param page
     * @param pageSize
     * @param request
     * @return com.hand.hap.system.dto.ResponseData
     * @author YHL 2019-02-01 15:58
     */
    @RequestMapping(value = "/getAccEntityByVenderTypeRefAeMore", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData getAccEntityByVenderTypeRefAeMore(GldAccountingEntity dto, Long magOrgId,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                        service.getAccEntityByVenderTypeRefAeMore(requestContext, dto, magOrgId, page, pageSize));
    }

    /**
     * 客户主文件定义功能选择核算主体
     *
     * @param companyId 公司ID
     * @param request
     * @return com.hand.hap.system.dto.ResponseData
     * @author guiyu 2019-02-20 10:04
     */
    @RequestMapping(value = "/queryByCompany", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAccEntityByCompany(Long companyId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAccEntityByCompany(requestContext, companyId));
    }

    /**
     * 系统级客户主文件定义功能银行账户分配核算主体
     *
     * @param customerId 客户ID
     * @param accountId 账户ID
     * @param taxId 税务ID
     * @param pCustomerId 客户ID
     * @param request
     * @return com.hand.hap.system.dto.ResponseData
     * @author guiyu 2019-02-20 10:04
     */
    @RequestMapping(value = "/ordSystemCustomerQuery", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData ordSystemCustomerQuery(Long customerId, Long accountId, Long taxId, Long pCustomerId,
                    HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                        service.ordSystemCustomerQuery(requestContext, customerId, accountId, taxId, pCustomerId));
    }

    /**
     * 税率定义功能批量分配核算主体
     *
     * @param taxTypeId 税率类型id
     * @param request
     * @return com.hand.hap.system.dto.ResponseData
     * @author weikun.Wand 2019-02-25
     */
    @RequestMapping(value = "/taxTypeQuery", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData taxTypeQuery(Long taxTypeId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.taxTypeQuery(requestContext, taxTypeId,page,pageSize));
    }

    /**
     * 根据公司ID和管理组织ID查询(公司级维值页面)
     *
     * @param companyId 公司ID
     * @param magOrgId 管理组织ID
     * @return
     * @author guiyuting 2019-02-28 16:43
     */
    @RequestMapping(value = "/queryByMagOrg")
    @ResponseBody
    public ResponseData queryByMagOrg(Long companyId, Long magOrgId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByMagOrg(requestContext, companyId, magOrgId));
    }

    /**
     * 查询公司类型不为2，有效日期内的核算主体
     *
     * @return
     * @author guiyuting 2019-03-06 18:21
     */
    @RequestMapping(value = "/selectAccEntityName")
    @ResponseBody
    public ResponseData selectAccEntityName(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectAccEntityName(requestContext));
    }


    @RequestMapping(value = "/queryAccEntityAndCurrencyCode")
    @ResponseBody
    public ResponseData queryAccEntityAndCurrencyCode(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByCompanyId(null, requestContext));
    }

    /**
     * 查询当前公司下符合条件的核算主体(hec_util.gld_acc_entity_company_vl_lov)
     *
     * @param dto
     * @param request
     * @param page
     * @param pageSize
     * @return 符合条件的核算主体
     * @author xiuxian.wu 2019-02-03-14 11:09
     */
    @RequestMapping(value = "/queryAccEntityByCompany")
    @ResponseBody
    public ResponseData queryAccEntityBySessionCompanyId(GldAccountingEntity dto,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAccEntityBySessionCompanyId(dto, requestContext, page, pageSize));
    }

}
