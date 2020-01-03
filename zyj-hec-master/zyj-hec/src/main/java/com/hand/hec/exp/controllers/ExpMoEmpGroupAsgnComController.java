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

import com.hand.hec.exp.dto.ExpMoEmpGroupAsgnCom;
import com.hand.hec.exp.service.IExpMoEmpGroupAsgnComService;

/**
 * <p>
 * 员工组关联公司Controller
 * </p>
 *
 * @author xiuxian.wu 2019/03/06 14:48
 */

@Controller
@RequestMapping(value = "/exp/mo-emp-group-asgn-com")
public class ExpMoEmpGroupAsgnComController extends BaseController {

    @Autowired
    private IExpMoEmpGroupAsgnComService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoEmpGroupAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(ExpMoEmpGroupAsgnCom.FIELD_ASSIGN_ID, Comparison.EQUAL)
                , new WhereField(ExpMoEmpGroupAsgnCom.FIELD_COMPANY_ID, Comparison.EQUAL)
                , new WhereField(ExpMoEmpGroupAsgnCom.FIELD_ENABLED_FLAG, Comparison.EQUAL)
                , new WhereField(ExpMoEmpGroupAsgnCom.FIELD_MO_EMP_GROUP_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoEmpGroupAsgnCom> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoEmpGroupAsgnCom> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 批量插入员工组与公司关系
     *
     * @param dto     关联关系
     * @param result
     * @param request
     * @return 已插入的数据
     * @author xiuxian.wu 2019/03/06 15:29
     */
    @RequestMapping(value = "/insertCompany")
    @ResponseBody
    public ResponseData insertCompany(@RequestBody List<ExpMoEmpGroupAsgnCom> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.insertCompany(requestCtx, dto));
    }

    /**
     * 获取所有剩余可以分配的公司或所有符合条件的公司
     *
     * @param request
     * @param page
     * @param pageSize
     * @param dto      条件
     * @return 返回公司数据
     * @author xiuxian.wu 2019/03/06 15:21
     */
    @RequestMapping(value = "/queryAllCompanyInformation", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryAllCompanyInformation(ExpMoEmpGroupAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAllCompanyInformation(requestContext, dto, page, pageSize));
    }


}