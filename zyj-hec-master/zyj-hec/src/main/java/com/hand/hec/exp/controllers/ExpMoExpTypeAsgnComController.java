package com.hand.hec.exp.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hec.exp.dto.ExpMoExpTypeAsgnCom;
import com.hand.hec.exp.service.IExpMoExpTypeAsgnComService;


/**
 * <p>
 * 报销类型关联公司Controller
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 14:24
 */

@Controller
@RequestMapping(value = "/exp/mo-exp-type-asgn-com")
public class ExpMoExpTypeAsgnComController extends BaseController {

    @Autowired
    private IExpMoExpTypeAsgnComService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoExpTypeAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoExpTypeAsgnCom.FIELD_ASSIGN_ID, Comparison.EQUAL)
                , new WhereField(ExpMoExpTypeAsgnCom.FIELD_COMPANY_ID, Comparison.EQUAL)
                , new WhereField(ExpMoExpTypeAsgnCom.FIELD_ENABLED_FLAG, Comparison.EQUAL)
                , new WhereField(ExpMoExpTypeAsgnCom.FIELD_MO_EXPENSE_TYPE_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoExpTypeAsgnCom> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoExpTypeAsgnCom> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询剩余可以分配给报销类型的公司
     *
     * @param dto      查询条件
     * @param page
     * @param pageSize
     * @param request
     * @return 返回剩余公司
     * @author xiuxian.wu 2019-02-27 14:37
     */
    @RequestMapping(value = "/queryRemainAllCompany", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryRemainAllCompany(ExpMoExpTypeAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryRemainAllCompany(requestContext, dto, page, pageSize));
    }

    /**
     * 批量插入公司于报销类型与公司关系表
     *
     * @param dto     插入的数据
     * @param result
     * @param request
     * @return 已经插入好的关系
     * @author 2019-02-27 14:40
     */
    @RequestMapping(value = "/insertCompanyRelationship")
    @ResponseBody
    public ResponseData insertCompanyRelationship(@RequestBody List<ExpMoExpTypeAsgnCom> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.insertCompany(requestCtx, dto));
    }
}