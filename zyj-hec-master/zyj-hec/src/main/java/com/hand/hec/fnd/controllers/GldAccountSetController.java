package com.hand.hec.fnd.controllers;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.fnd.dto.GldAccountSet;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.service.IGldAccountSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * GldAccountSetController
 * </p>
 * 
 * @author guiyu 2019/01/08 15:31
 */
@Controller
@RequestMapping(value = "/gld/account-set")
public class GldAccountSetController extends BaseController {

    @Autowired
    private IGldAccountSetService service;

    @RequestMapping(value = "/query")
    @ResponseBody
    public ResponseData query(GldAccountSet dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldAccountSet.FIELD_ACCOUNT_SET_CODE, Comparison.LIKE),
                        new WhereField(GldAccountSet.FIELD_ACCOUNT_SET_NAME, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldAccountSet> dto, BindingResult result, HttpServletRequest request) {
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
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldAccountSet> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 科目定义明细
     *
     * @param request
     * @param accountSetId 科目ID
     * @author guiyuting 2019-02-26 18:50
     * @return 
     */
    @RequestMapping(value = "/accountDetail")
    public ModelAndView jumpAccountDetail(HttpServletRequest request, Long accountSetId) {
        ModelAndView view = new ModelAndView("fnd/FND2140/gld_account");
        view.addObject("accountSetId", accountSetId);
        return view;
    }
}
