package com.hand.hec.wfl.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.wfl.dto.WflGateway;
import com.hand.hec.wfl.dto.WflProcess;
import com.hand.hec.wfl.service.IWflGatewayService;
import com.hand.hec.wfl.service.IWflProcessService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.wfl.dto.WflGatewayBizRuleGroup;
import com.hand.hec.wfl.service.IWflGatewayBizRuleGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value = "/wfl/gateway-biz-rule-group")
public class WflGatewayBizRuleGroupController extends BaseController {

    @Autowired
    private IWflGatewayBizRuleGroupService service;

    @Autowired
    private IWflGatewayService gatewayService;

    @Autowired
    private IWflProcessService processService;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(WflGatewayBizRuleGroup dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        // 如果gatewayId为空，返回空值
        if (dto.getGatewayId() == null) {
            return new ResponseData(Collections.EMPTY_LIST);
        }
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField[] {new WhereField(WflGatewayBizRuleGroup.FIELD_FLOW_ID),
                new WhereField(WflGatewayBizRuleGroup.FIELD_GATEWAY_ID),
                new WhereField(WflGatewayBizRuleGroup.FIELD_GROUP_CODE),
                new WhereField(WflGatewayBizRuleGroup.FIELD_GROUP_NAME),
                new WhereField(WflGatewayBizRuleGroup.FIELD_GROUP_ID)});
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<WflGatewayBizRuleGroup> dto, BindingResult result,
                    HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        service.setRequest(requestCtx);
        List<WflGatewayBizRuleGroup> groups = service.batchUpdate(requestCtx, dto);

        if (dto != null && dto.size() != 0) {
            WflGateway gateway = new WflGateway();
            gateway.setGatewayId(dto.get(0).getGatewayId());
            gateway = gatewayService.selectByPrimaryKey(requestCtx, gateway);
            processService.changeConfigType(requestCtx, gateway.getProcessId(), WflProcess.CONFIG_TYPE_STANDARD);
        }

        return new ResponseData(groups);
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<WflGatewayBizRuleGroup> dto) {
        IRequest requestCtx = createRequestContext(request);
        service.setRequest(requestCtx);
        service.batchDelete(dto);

        if (dto != null && dto.size() != 0) {
            WflGateway gateway = new WflGateway();
            gateway.setGatewayId(dto.get(0).getGatewayId());
            gateway = gatewayService.selectByPrimaryKey(requestCtx, gateway);
            processService.changeConfigType(requestCtx, gateway.getProcessId(), WflProcess.CONFIG_TYPE_STANDARD);
        }

        return new ResponseData();
    }
}
