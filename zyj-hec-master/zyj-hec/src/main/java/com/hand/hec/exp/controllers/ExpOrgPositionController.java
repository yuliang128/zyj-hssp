package com.hand.hec.exp.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hec.exp.exception.PositionParentLoopException;
import org.springframework.stereotype.Controller;
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
import com.hand.hec.exp.dto.ExpOrgPosition;
import com.hand.hec.exp.service.IExpOrgPositionService;

/**
 * <p>
 * ExpOrgPositionController
 * </p>
 *
 * @author guiyuting 2019/02/26 18:43
 */

@Controller
@RequestMapping(value = "/exp/org-position")
public class ExpOrgPositionController extends BaseController {

    @Autowired
    private IExpOrgPositionService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpOrgPosition dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryPosition(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpOrgPosition> dto, BindingResult result, HttpServletRequest request) throws PositionParentLoopException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        // 检查父级岗位是否循环
        for (ExpOrgPosition position : dto) {
            if (position.get__status().equalsIgnoreCase(DTOStatus.UPDATE) && position.getParentPositionId() != null && service.checkParentLoop(requestCtx, position, position.getPositionId())) {
                throw new PositionParentLoopException(PositionParentLoopException.ERROR_PARENT_LOOP, null);
            }
        }
        List<ExpOrgPosition> list = service.batchUpdate(requestCtx, dto);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpOrgPosition> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/queryPositionAndUnit", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryPositionAndUnit(Long employeeId, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryPositionAndUnit(requestContext, employeeId));
    }

    @RequestMapping(value = "/getPositionForEmployeeAssign")
    @ResponseBody
    public ResponseData getPositionForEmployeeAssign(@RequestParam(required = true) Long employeeId,
            @RequestParam(required = true) Long companyId,
            @RequestParam(required = false) Long unitId,
            @RequestParam(required = false) String positionCode,
            @RequestParam(required = false) String positionName,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.getPositionForEmployeeAssign(requestContext, employeeId, companyId, unitId, positionCode, positionName));
    }
}
