package com.hand.hec.exp.controllers;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.exp.service.IExpEmployeeService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.exp.dto.ExpDocumentHistory;
import com.hand.hec.exp.service.IExpDocumentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
@RequestMapping(value = "")
public class ExpDocumentHistoryController extends BaseController {

    @Autowired
    private IExpDocumentHistoryService service;


    @Autowired
    private IUserService userService;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private IExpEmployeeService employeeService;

    @RequestMapping(value = "/exp/document-history/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(ExpDocumentHistory dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/exp/document-history/queryPayReqHistory", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryPayReqHistory(Long cshPayHeaderId, int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryPayReqHistory(requestContext, cshPayHeaderId, page, pageSize));
    }

    @RequestMapping(value = "/exp/document-history/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ExpDocumentHistory> dto, BindingResult result,
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

    @RequestMapping(value = "/exp/document-history/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ExpDocumentHistory> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/expm/public/exp_document_history_query.screen")
    public ModelAndView expDocumentHistoryQuery(HttpServletRequest request,
                    @RequestParam(value = "documentId") Long documentId,
                    @RequestParam(value = "documentType") String documentType) {
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("expm/public/exp_document_history_query");
        ExpDocumentHistory history = new ExpDocumentHistory();
        history.setDocumentId(documentId);
        history.setDocumentType(documentType);
        List<ExpDocumentHistory> documentLineList = service.select(requestCtx, history, 0, 0);
        if (documentLineList != null && !documentLineList.isEmpty()) {
            for (ExpDocumentHistory h : documentLineList) {
                User user = new User();
                user.setUserId(h.getUserId());
                user = userService.selectByPrimaryKey(requestCtx, user);
                if (user != null) {
                    ExpEmployee employee = new ExpEmployee();
                    employee.setEmployeeId(user.getEmployeeId());
                    employee = employeeService.selectByPrimaryKey(requestCtx, employee);
                    if (employee != null) {
                        String operater = employee.getName() + "-" + employee.getEmployeeCode();
                        h.setOperater(operater);
                    }
                }
                String operation = codeService.getCodeMeaningByValue(requestCtx, "EXP_DOCUMENT_OPERATION_CODE",
                                h.getOperationCode());
                h.setOperation(operation);
            }
            view.addObject("document_lines_list", documentLineList);
        }
        return view;
    }
}
