package com.hand.hec.exp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.exp.dto.ExpMoEmpTypeAsgnCom;
import com.hand.hec.exp.service.IExpMoEmpTypeAsgnComService;
import com.hand.hap.fnd.dto.FndCompany;

/**
 * 员工类型分配管理公司控制器
 *
 * @author luhui 2019/01/25
 */

@Controller
@RequestMapping(value = "/exp/mo-emp-type-asgn-com")
public class ExpMoEmpTypeAsgnComController extends BaseController {

    @Autowired
    private IExpMoEmpTypeAsgnComService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpMoEmpTypeAsgnCom dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpMoEmpTypeAsgnCom> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    /**
     * 员工类型批量分配公司
     *
     * @param
     * @author ngls.luhui 2019-01-25 17:27
     * @return
     */
    @RequestMapping(value = "/batchSubmit")
    @ResponseBody
    public ResponseData batchSubmit(@RequestBody List<FndCompany> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSubmit(requestCtx,dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpMoEmpTypeAsgnCom> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 根据员工类型查找该员工属于的公司，及公司的管理组织的一些信息
     *
     * @param dto 包含员工类型ID的dto实体类对象
     * @return
     * @author ngls.luhui 2019-01-25 15:02
     */
    @RequestMapping(value = "/queryCompanyByEmpType", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryCompanyByEmpType(ExpMoEmpTypeAsgnCom dto) {
        return new ResponseData(service.selectCompanyByEmpType(dto.getEmployeeTypeId()));
    }
}