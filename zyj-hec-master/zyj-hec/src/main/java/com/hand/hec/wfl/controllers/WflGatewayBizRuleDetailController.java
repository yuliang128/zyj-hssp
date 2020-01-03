package com.hand.hec.wfl.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.wfl.dto.WflGateway;
import com.hand.hec.wfl.dto.WflTask;
import com.hand.hec.wfl.service.IWflGatewayService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.wfl.dto.WflGatewayBizRuleDetail;
import com.hand.hec.wfl.service.IWflGatewayBizRuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
public class WflGatewayBizRuleDetailController extends BaseController {

    @Autowired
    private IWflGatewayBizRuleDetailService service;

    @Autowired
    private IWflGatewayService gatewayService;

    @RequestMapping(value = "/wfl/gateway-biz-rule-detail/query")
    @ResponseBody
    public ResponseData query(WflGatewayBizRuleDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        // 如果groupId为空，返回空值
        if (dto.getGroupId() == null) {
            return new ResponseData(Collections.EMPTY_LIST);
        }
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField[] {new WhereField(WflGatewayBizRuleDetail.FIELD_BUSINESS_RULE_ID),
                new WhereField(WflGatewayBizRuleDetail.FIELD_DETAIL_ID),
                new WhereField(WflGatewayBizRuleDetail.FIELD_GROUP_ID)});
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/wfl/gateway-biz-rule-detail/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<WflGatewayBizRuleDetail> dto, BindingResult result,
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

    @RequestMapping(value = "/wfl/gateway-biz-rule-detail/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<WflGatewayBizRuleDetail> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }


    @RequestMapping(value = {"wfl/WFL1010/wfl_gateway_biz_rule"})
    @ResponseBody
    public ModelAndView getDefaultBusinessRule(@RequestParam Long gatewayId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

        WflGateway gateway = new WflGateway();
        gateway.setGatewayId(gatewayId);
        gateway = gatewayService.selectByPrimaryKey(requestContext, gateway);

        ModelAndView modelAndView = new ModelAndView("wfl/WFL1010/wfl_gateway_biz_rule");
        modelAndView.addObject("gatewayInfo", gateway);

        return modelAndView;
    }
}
