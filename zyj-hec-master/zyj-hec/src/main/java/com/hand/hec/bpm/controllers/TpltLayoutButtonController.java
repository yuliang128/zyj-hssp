package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.bpm.dto.TpltLayoutButton;
import com.hand.hec.bpm.service.ITpltLayoutButtonService;
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
public class TpltLayoutButtonController extends BaseController {

    @Autowired
    private ITpltLayoutButtonService service;

    @Autowired
    private ICodeService codeService;

    @RequestMapping(value = "/bpm/BPM101/tpltLayoutButton")
    public ModelAndView jumpTpltLayoutBasic(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("bpm/BPM101/tpltLayoutButton");
        IRequest requestCtx = createRequestContext(request);
        List<CodeValue> operatorTypeList = codeService.getCodeValuesByCode(requestCtx, "BPM.BUTTON_OPERATOR_TYPE");
        view.addObject("operatorType", operatorTypeList);
        List<CodeValue> buttonTypeList = codeService.getCodeValuesByCode(requestCtx, "BPM.BUTTON_TYPE");
        view.addObject("buttonType", buttonTypeList);
        return view;
    }


    @RequestMapping(value = "/bpm/tpltLayoutButton/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, TpltLayoutButton layoutBtn, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        Criteria criteria = new Criteria(layoutBtn);
        criteria.where(TpltLayoutButton.FIELD_BUTTON_ID, TpltLayoutButton.FIELD_LAYOUT_ID, new WhereField(TpltLayoutButton.FIELD_BUTTON_CODE, Comparison.LIKE), new WhereField(TpltLayoutButton.FIELD_BUTTON_DESC, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestCtx, layoutBtn, criteria, page, pageSize));
    }

    @RequestMapping(value = "/bpm/tpltLayoutButton/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<TpltLayoutButton> dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }
}