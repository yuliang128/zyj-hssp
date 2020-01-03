package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hec.bpm.dto.TpltButton;
import com.hand.hec.bpm.service.ITpltButtonService;
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
public class TpltButtonController extends BaseController {

    @Autowired
    private ITpltButtonService service;

    @Autowired
    private ICodeService codeService;

    @RequestMapping(value = "/bpm/BPM101/tpltButton")
    public ModelAndView jumpTpltLayoutBasic(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("bpm/BPM101/tpltButton");
        IRequest requestCtx = createRequestContext(request);
        List<CodeValue> layoutTypeList = codeService.getCodeValuesByCode(requestCtx, "BPM.BUTTON_OPERATOR_TYPE");
        view.addObject("operationType", layoutTypeList);
        return view;
    }


    @RequestMapping(value = "/bpm/tpltButton/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, TpltButton button, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);
        Criteria criteria = new Criteria(button);
        criteria.where(new WhereField(TpltButton.FIELD_BUTTON_ID, Comparison.EQUAL), new WhereField(TpltButton.FIELD_TEMPLATE_ID, Comparison.EQUAL), new WhereField(TpltButton.FIELD_BUTTON_CODE, Comparison.LIKE), new WhereField(TpltButton.FIELD_BUTTON_DESC, Comparison.EQUAL));
        return new ResponseData(service.selectOptions(requestCtx, button, criteria, page, pageSize));
    }

    @RequestMapping(value = "/bpm/tpltButton/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<TpltButton> dto, HttpServletRequest request, BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    public List<TpltButton> queryByTemplateId(IRequest request, TpltButton bpmTpltButtons) {
        return service.queryByTemplateId(request, bpmTpltButtons);
    }

    public void batchSave(IRequest request, List<TpltButton> bpmTpltButtonsList) {
        service.batchSaves(request, bpmTpltButtonsList);
    }
}