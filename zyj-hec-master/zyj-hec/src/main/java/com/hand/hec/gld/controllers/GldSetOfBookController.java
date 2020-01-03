package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.exception.GldSetOfBookException;
import com.hand.hec.gld.service.IGldAccountingEntityService;
import com.hand.hec.gld.service.IGldSetOfBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 账套定义控制器
 * </p>
 *
 * @author wuxiuxian 2019-01-07
 */

@Controller
@RequestMapping(value = "/gld-set-of-book")
public class GldSetOfBookController extends BaseController {

    @Autowired
    private IGldSetOfBookService service;
    @Autowired
    private IGldAccountingEntityService accountingEntityService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldSetOfBook dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldSetOfBook.FIELD_ENABLED_FLAG)
                , new WhereField(GldSetOfBook.FIELD_SET_OF_BOOKS_CODE, Comparison.LIKE)
                , new WhereField(GldSetOfBook.FIELD_SET_OF_BOOKS_NAME, Comparison.LIKE),
                new WhereField(GldSetOfBook.FIELD_SET_OF_BOOKS_ID, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }


    /**
     * 根据 账套id 查询会计期间
     *
     * @param dto      账套id
     * @param page     分页
     * @param pageSize 分页大小
     * @param request  request
     * @return 会计期
     * @author rui.shi@hand-china.com  2019-02-21
     */
    @RequestMapping(value = "/queryForPeriod", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryForPeriod(GldSetOfBook dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryForPeriod(requestContext, dto, page, pageSize));
    }


    /**
     * 新增或更新账套，并判断账套是否被使用
     *
     * @param dto     更新或新增账套
     * @param result  判断
     * @param request
     * @return 返回结果
     * @author wuxiuxian
     */
    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldSetOfBook> dto, BindingResult result, HttpServletRequest request) throws GldSetOfBookException {
        getValidator().validate(dto, result);
        ResponseData responseData = new ResponseData(false);
        if (result.hasErrors()) {
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchSubmitGldSetOfBook(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldSetOfBook> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 根据管理组织查询账套
     *
     * @param request
     * @return 返回默认账套
     * @author wuxiuxian
     */
    @RequestMapping(value = "/gld/GLD2050/gld_sob_resp_center.screen")
    public ModelAndView gldSobRespCenter(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<GldSetOfBook> data = service.selectDefaultSobByMagOrgId(requestContext);
        ModelAndView view = new ModelAndView("gld/GLD2050/gld_sob_resp_center");
        if (data != null && !data.isEmpty()) {
            view.addObject("setOfBooksId", data.get(0).getSetOfBooksId());
            view.addObject("setOfBooksName", data.get(0).getSetOfBooksName());
        }
        return view;
    }

    @RequestMapping(value = "/querySetOfBookByParam")
    @ResponseBody
    public ResponseData querySetOfBookByParam(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.querySetOfBookByParam(requestContext));
    }

    @RequestMapping(value = "/query-by-mag-id", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByMagId(GldSetOfBook dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryByMagId(requestContext,dto.getMagOrgId()));
    }
}