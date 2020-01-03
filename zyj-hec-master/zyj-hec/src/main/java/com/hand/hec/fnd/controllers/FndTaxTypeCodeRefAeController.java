package com.hand.hec.fnd.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.FndTaxTypeCodeRefAe;
import com.hand.hec.fnd.dto.GldExchangeRate;
import com.hand.hec.fnd.service.IFndTaxTypeCodeRefAeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * xx控制器
 *
 * @w xxx YYYY-mm-dd
 */

@Controller
@RequestMapping(value = "/fnd/tax-type-code-ref-ae")
public class FndTaxTypeCodeRefAeController extends BaseController{

    @Autowired
    private IFndTaxTypeCodeRefAeService service;

    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndTaxTypeCodeRefAe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        if(dto.getTaxTypeId()!=null && !"undefined".equals(dto.getTaxTypeId())){
            criteria.where(new WhereField(FndTaxTypeCodeRefAe.FIELD_TAX_TYPE_ID, Comparison.EQUAL));
        }
        return new ResponseData(service.selectOptions(requestContext,dto,criteria,page,pageSize));
    }


    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndTaxTypeCodeRefAe> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        System.out.println(dto.get(0).get__status());
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        /*for(int i=0;i<dto.size();i++){
            dto.get(i).set__status(DTOStatus.ADD);
        }*/
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<FndTaxTypeCodeRefAe> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}