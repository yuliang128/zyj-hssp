package com.hand.hec.bpm.controllers;

import aurora.bean.LovField;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.bpm.dto.TpltTagBasic;
import com.hand.hec.bpm.service.ITpltTagBasicService;
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

@Controller
public class TpltTagBasicController extends BaseController {

    @Autowired
    private ITpltTagBasicService service;

    @Autowired
    private ICodeService codeService;

    @RequestMapping(value = "/bpm/BPM101/tpltTagBasic")
    public ModelAndView jumpTpltLayoutBasic(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("bpm/BPM101/tpltTagBasic");
        IRequest requestCtx = createRequestContext(request);
        List<CodeValue> layoutTypeList = codeService.getCodeValuesByCode(requestCtx, "BPM.TAG_TYPE");
        view.addObject("tagType", layoutTypeList);
        return view;
    }


    @RequestMapping(value = "/bpm/tpltTagBasic/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, TpltTagBasic tag, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        Criteria criteria = new Criteria(tag);
        criteria.where(new WhereField(TpltTagBasic.FIELD_TAG_ID, Comparison.EQUAL), new WhereField(TpltTagBasic.FIELD_LAYOUT_ID, Comparison.EQUAL), new WhereField(TpltTagBasic.FIELD_TAG_CODE, Comparison.LIKE), new WhereField(TpltTagBasic.FIELD_TAG_DESC, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestCtx, tag, criteria, page, pageSize));
    }

    @RequestMapping(value = "/bpm/tpltTagBasic/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<TpltTagBasic> dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    public List<TpltTagBasic> queryByLayoutId(IRequest request, TpltTagBasic bpmTpltTagsBasic) {
        return service.queryByLayoutId(request, bpmTpltTagsBasic);
    }

    @LovField(name = "tag_id", prompt = "BPM_TPLT_TAGS_BASIC.TAG_ID")
    @LovField(name = "tag_code", forDisplay = true, forQuery = true, align = "left", prompt = "BPM_TPLT_TAGS_BASIC.TAG_CODE", queryWidth = 200, displayWidth = 150)
    @LovField(name = "tag_desc", forDisplay = true, forQuery = true, align = "left", prompt = "BPM_TPLT_TAGS_BASIC.TAG_DESC", queryWidth = 200, displayWidth = 150)
    public List<TpltTagBasic> selectTpltTagBasicLov(IRequest request, TpltTagBasic bpmTpltTagsBasic) {
        return service.selectTpltTagBasicLov(request, bpmTpltTagsBasic);
    }

}