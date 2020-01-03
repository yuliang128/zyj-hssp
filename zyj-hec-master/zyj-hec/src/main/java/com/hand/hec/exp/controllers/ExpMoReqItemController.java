package com.hand.hec.exp.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoReqItem;
import com.hand.hec.exp.exception.ReqItemMultiException;
import com.hand.hec.exp.service.IExpMoReqItemService;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
@RequestMapping(value = "")
public class ExpMoReqItemController extends BaseController {

    @Autowired
    private IExpMoReqItemService service;

    @Autowired
    private IFndManagingOrganizationService managingOrganizationService;

    @RequestMapping(value = "/exp/mo-req-item/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoReqItem dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoReqItem.FIELD_MAG_ORG_ID, Comparison.EQUAL), new WhereField(ExpMoReqItem.FIELD_MO_REQ_ITEM_CODE, Comparison.LIKE), new WhereField(ExpMoReqItem.FIELD_DESCRIPTION, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria));
    }

    @RequestMapping(value = "/exp/mo-req-item/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoReqItem> dto, BindingResult result, HttpServletRequest request) throws ReqItemMultiException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSubmit(requestCtx, dto));
    }

    @RequestMapping(value = "/exp/mo-req-item/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoReqItem> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * <p>
     * 费用申请项目定义页面初始化
     * <p/>
     *
     * @param request
     * @return ModelAndView
     * @author yang.duan 2019/2/18 17:12
     */
    @RequestMapping(value = "expm/EXP2030/exp_mo_req_items.screen")
    public ModelAndView expMoReqItemsView(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        FndManagingOrganization managingOrganizationList = managingOrganizationService.queryDefaultMagOrg(requestContext);
        ModelAndView view = new ModelAndView("expm/EXP2030/exp_mo_req_items");
        view.addObject("EXP2030_defaultMagList", managingOrganizationList);
        return view;
    }

    @RequestMapping(value = "/exp/mo-req-item/getReqItemByExpenseType")
    @ResponseBody
    public ResponseData getReqItemByExpenseType(HttpServletRequest request, @RequestParam Long moExpenseTypeId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.getReqItemByExpenseType(requestContext, moExpenseTypeId));
    }
}
