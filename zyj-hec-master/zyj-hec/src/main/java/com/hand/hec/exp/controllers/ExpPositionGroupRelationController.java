package com.hand.hec.exp.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpPositionGroupRelation;
import com.hand.hec.exp.service.IExpPositionGroupRelationService;

/**
 * <p>
 * 岗位分配岗位组控制器
 * </p>
 *
 * @author YHL 2019/01/18 12:42
 */
@Controller
@RequestMapping(value = "/exp/position-group-relation")
public class ExpPositionGroupRelationController extends BaseController {

    @Autowired
    private IExpPositionGroupRelationService service;

    /**
     * 查询岗位组中分配的岗位及公司信息（使用的getPositionGroupRelationByPositionGroupId为自定义select方法）
     *
     * @param positionGroupId 岗位组ID
     * @param page
     * @param pageSize
     * @param request
     * @author YHL 2019-01-19 0:05
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(Long positionGroupId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(
                service.getPositionGroupRelationByPositionGroupId(requestContext, positionGroupId, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpPositionGroupRelation> dto, BindingResult result,
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpPositionGroupRelation> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}