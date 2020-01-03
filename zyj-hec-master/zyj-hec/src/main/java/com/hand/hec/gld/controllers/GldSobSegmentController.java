package com.hand.hec.gld.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.dto.GldSobSegment;
import com.hand.hec.gld.mapper.GldAccountingEntityMapper;
import com.hand.hec.gld.service.IGldSetOfBookService;
import com.hand.hec.gld.service.IGldSobSegmentService;
import com.hand.hap.sys.dto.SysParameter;
import com.hand.hap.sys.service.ISysParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 科目段定义控制器
 *
 * @author hui.zhao01@hand-china.com 2019年1月30日11:02:45
 */

@Controller
public class GldSobSegmentController extends BaseController {

    @Autowired
    private IGldSobSegmentService service;

    @Autowired
    private IGldSetOfBookService setOfBookService;

    @Autowired
    private GldAccountingEntityMapper gldAccountingEntityMapper;

    @Autowired
    private IGldSetOfBookService getSetOfBookService;

    @Autowired
    private ISysParameterService sysParameterService;

    @RequestMapping(value = "/gl/GL1010/gl_segments_define.screen", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ModelAndView glSegmentsDefineView(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        ModelAndView modelAndView = new ModelAndView("/gl/GL1010/gl_segments_define");
        // List<GldSetOfBook> gldSetOfBooks = setOfBookService.selectAll(iRequest);
        // modelAndView.addObject("setOfBooks",gldSetOfBooks);
        List<GldSetOfBook> gldSetOfBooks1 = getSetOfBookService.selectDefaultSobByMagOrgId(iRequest);
        modelAndView.addObject("defaultSobOfMagOrg", gldSetOfBooks1);
        // List<GldAccountingEntity> gldAccountingEntities = gldAccountingEntityMapper.selectValidEntity();
        // modelAndView.addObject("setBooksOnly",gldAccountingEntities);
        return modelAndView;
    }

    @RequestMapping(value = "/gld/sob-segment/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldSobSegment dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(GldSobSegment.FIELD_SET_OF_BOOKS_ID,
                        new WhereField(GldSobSegment.FIELD_SEGMENT_CODE, Comparison.LIKE),
                        new WhereField(GldSobSegment.FIELD_DESCRIPTION, Comparison.LIKE),
                        GldSobSegment.FIELD_SEGMENT_ID, GldSobSegment.FIELD_SEGMENT_FIELD,
                        GldSobSegment.FIELD_SEGMENT_TYPE);
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/gld/sob-segment/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldSobSegment> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/gld/sob-segment/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldSobSegment> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/gld/sob-segment/query-sob-list")
    @ResponseBody
    public ResponseData querySobList(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        // 获取可选择的账套
        return new ResponseData(getSetOfBookService.querySetOfBookOptionsByParamValue(iRequest,
                        SysParameter.PARAM_FND_ALL_ORGANIZATIONAL));
    }
}
