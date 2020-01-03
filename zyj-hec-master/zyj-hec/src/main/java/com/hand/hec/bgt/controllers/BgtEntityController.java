package com.hand.hec.bgt.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.bgt.dto.BgtEntity;
import com.hand.hec.bgt.dto.BgtOrganization;
import com.hand.hec.bgt.service.IBgtCenterService;
import com.hand.hec.bgt.service.IBgtEntityService;
import com.hand.hec.bgt.service.IBgtOrganizationService;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.gld.service.IGldCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 预算实体Controller
 * </p>
 *
 * @author mouse 2019/01/07 15:37
 */
@Controller
public class BgtEntityController extends BaseController {

    private static final String BGT_ORG_SOURCE_TYPE_CODE = "BGT_ORG_SOURCE_TYPE";
    private static final String BGT_ENTITY_TYPE_CODE = "BGT_ENTITY_TYPE";

    @Autowired
    private IBgtEntityService service;

    @Autowired
    IGldCurrencyService gldCurrencyService;

    @Autowired
    IBgtOrganizationService bgtOrganizationService;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private IBgtCenterService centerService;

    @RequestMapping(value = "/bgt/entity/query")
    @ResponseBody
    public ResponseData query(BgtEntity dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<BgtEntity> bgtEntityList = service.queryAll(requestContext, dto, page, pageSize);
        bgtEntityList.stream().map(bgtEntity -> {
            bgtEntity.setBgtSourceTypeCode(bgtEntity.getSourceTypeCode());
            bgtEntity.setEntityTypeOldValue(bgtEntity.getEntityType());
            return bgtEntity;
        }).collect(Collectors.toList());

        return new ResponseData(bgtEntityList);
    }

    /**
     * 预算实体汇总类型批量分配明细类型
     *
     * @param dto
     * @author guiyuting 2019-02-25 16:02
     * @return 符合条件的预算实体
     */
    @RequestMapping(value = "/bgt/entity/queryForBatch")
    @ResponseBody
    public ResponseData queryForBatch(BgtEntity dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryForBatch(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/bgt/entity/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtEntity> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/bgt/entity/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtEntity> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/bgt/BGT1090/bgt_entity.screen")
    public ModelAndView bgtEntityView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

        List<GldCurrency> currencyList = gldCurrencyService.queryEnabledCurrency(requestContext);

        List<BgtOrganization> bgtOrgList = bgtOrganizationService.queryBgtOrganizationOptions(requestContext);
        List<BgtOrganization> defaultBgtOrgList = bgtOrganizationService.queryDefaultBgtOrganization(requestContext);

        List<CodeValue> bgtOrgSourceTypeList =
                        codeService.getCodeValuesByCode(requestContext, BGT_ORG_SOURCE_TYPE_CODE);
        List<CodeValue> bgtEntityTypeList = codeService.getCodeValuesByCode(requestContext, BGT_ENTITY_TYPE_CODE);


        ModelAndView view = new ModelAndView("bgt/BGT1090/bgt_entity");
        view.addObject("currencyList", currencyList);
        view.addObject("bgtOrgList", bgtOrgList);
        view.addObject("defaultBgtOrgList", defaultBgtOrgList);
        view.addObject("bgtOrgSourceTypeList", bgtOrgSourceTypeList);
        view.addObject("bgtEntityTypeList", bgtEntityTypeList);

        return view;
    }

    /**
     * 预算实体同步
     *
     * @param bgtOrgId
     * @author guiyuting 2019-02-25 16:41
     * @return
     */
    @RequestMapping(value = "/bgt/entity/sync")
    @ResponseBody
    public ResponseData sync(Long bgtOrgId, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.sync(requestCtx, bgtOrgId));
    }

    /**
     * CUSTOMIZE（自定义）类型预算实体同步预算中心
     *
     * @param entityIds 预算实体ID数组
     * @author guiyuting 2019-02-26 15:35
     * @return
     */
    @RequestMapping(value = "/bgt/entity/centerSync")
    @ResponseBody
    public ResponseData centerSync(@RequestBody List<Long> entityIds, BindingResult result,
                    HttpServletRequest request) {
        // getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        centerService.centerSync(requestCtx, null, entityIds);
        return new ResponseData(true);
    }

    @RequestMapping(value = "/bgt/entity/queryByCompany")
    @ResponseBody
    public ResponseData queryByCompany(Long companyId, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.queryByCompanyId(requestCtx, companyId));
    }

    @RequestMapping(value = "/bgt/entity/bgtJournalBatch")
    @ResponseBody
    public ResponseData bgtJournalBatch(Long bgtOrgId, Long rangeId, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.bgtJournalBatch(requestCtx, bgtOrgId, rangeId));
    }
}
