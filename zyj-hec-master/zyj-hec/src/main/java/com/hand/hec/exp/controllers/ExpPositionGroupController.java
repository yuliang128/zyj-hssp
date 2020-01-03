package com.hand.hec.exp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpPositionGroup;
import com.hand.hec.exp.service.IExpPositionGroupService;

/**
 * <p>
 * 岗位组控制器
 * </p>
 *
 * @author YHL 2019/01/18 12:33
 */
@Controller
@RequestMapping(value = "/exp/position-group")
public class ExpPositionGroupController extends BaseController {

    @Autowired
    private IExpPositionGroupService service;

    /**
     * 查询岗位组（实现模糊查询功能）
     *
     * @param dto 岗位组
     * @param page
     * @param pageSize
     * @param request
     * @author YHL 2019-01-25 15:40
     * @return com.hand.hap.system.dto.ResponseData
     */
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpPositionGroup dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        HttpSession session = request.getSession();
        Long companyId = (Long) session.getAttribute(FndCompany.FIELD_COMPANY_ID);
        dto.setCompanyId(companyId);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField[]{new WhereField(ExpPositionGroup.FIELD_COMPANY_ID, Comparison.EQUAL),
                new WhereField(ExpPositionGroup.FIELD_POSITION_GROUP_CODE, Comparison.LIKE),
                new WhereField(ExpPositionGroup.FIELD_DESCRIPTION, Comparison.LIKE)});
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpPositionGroup> dto, BindingResult result,
            HttpServletRequest request) throws BaseException {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpPositionGroup> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 岗位分配
     *
     * @param request
     * @param positionGroupId 岗位组ID
     * @author YHL 2019-01-19 0:01
     * @return org.springframework.web.servlet.ModelAndView “岗位组定义” 模块中，“岗位分配”功能的页面
     */
    @RequestMapping(value = "/expPositionGroupRelationView")
    public ModelAndView expPositionGroupRelationView(HttpServletRequest request, Long positionGroupId) {
        ModelAndView view = new ModelAndView("exp/EXP1100/exp_position_group_relations");
        view.addObject("positionGroupId", positionGroupId);
        return view;
    }
}