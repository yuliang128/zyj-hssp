package com.hand.hec.fnd.controllers;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.SortType;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.GldExchangerateType;
import com.hand.hec.fnd.service.IGldExchangerateTypeService;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.mapper.GldSetOfBookMapper;
import com.hand.hec.gld.service.IGldSetOfBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 汇率类型控制器
 * </p>
 *
 * @author xingjialin 2019/01/04 18:53
 */
@Controller
public class GldExchangerateTypeController extends BaseController {

    @Autowired
    private IGldExchangerateTypeService service;

    @Autowired
    private IGldSetOfBookService setOfBookService;

    @RequestMapping(value = "/fnd/FND1080/gld_exchangerate_type.screen")
    public ModelAndView initScreenView(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("fnd/FND1080/gld_exchangerate_type");
        IRequest requestCtx = createRequestContext(request);
        List<GldSetOfBook> rs = setOfBookService.selectDefaultSobByMagOrgId(requestCtx);
        view.addObject("rs", rs);
        return view;
    }

    @RequestMapping(value = "/gld/exchangerate-type/query")
    @ResponseBody
    public ResponseData query(GldExchangerateType dto, Long setOfBooksId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setSetOfBooksId(setOfBooksId);
        // Modify Tagin/2019.03.07 增加查询条件
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldExchangerateType.FIELD_ENABLED_FLAG, Comparison.EQUAL),
                new WhereField(GldExchangerateType.FIELD_TYPE_CODE, Comparison.LIKE),
                new WhereField(GldExchangerateType.FIELD_DESCRIPTION,Comparison.LIKE),
                new WhereField(GldExchangerateType.FIELD_SET_OF_BOOKS_ID));
        criteria.sort(GldExchangerateType.FIELD_TYPE_CODE, SortType.ASC);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/gld/exchangerate-type/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldExchangerateType> dto, BindingResult result,
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

    @RequestMapping(value = "/gld/exchangerate-type/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldExchangerateType> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/gld/exchangerate-type/exp-audit-select-type")
    @ResponseBody
    public ResponseData expAuditSelectType(HttpServletRequest request){
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(service.expAuditSelectType(iRequest));
    }
}
