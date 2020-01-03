package com.hand.hec.csh.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.csh.dto.CshOfferFormatLns;
import com.hand.hec.csh.service.ICshOfferFormatLnsService;
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
 * @author zhangbo 2019-03-21
 */

@Controller
@RequestMapping(value = "/csh/offer-format-lns")
public class CshOfferFormatLnsController extends BaseController{

    @Autowired
    private ICshOfferFormatLnsService service;


    @RequestMapping(value = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(CshOfferFormatLns dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCshOfferFormatLns(dto.getFormatHdsId()));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CshOfferFormatLns> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        Long fixedMax = service.querycolumnNameMax();
        for(int i=0;i<dto.size();i++){
            if(dto.get(i).getOfferValueType().equals("FIXED")){
                if(fixedMax<=8){
                    dto.get(i).setColumnName("FIXED_0"+(++fixedMax));
                }else{
                    dto.get(i).setColumnName("FIXED_"+(++fixedMax));
                }
                dto.get(i).setColumnSqlText("TO_CHAR(,  '') AS ");
            }
            if(dto.get(i).getColumnName().equals("amount") || dto.get(i).getColumnName().equals("encryption_amount")){
                dto.get(i).setColumnSqlText("TO_CHAR("+dto.get(i).getColumnName()+",  '"+dto.get(i).getColumnFormat()+"') AS "+dto.get(i).getColumnName());
            }else if(dto.get(i).getColumnName().equals("pay_date")||dto.get(i).getColumnName().equals("request_time")||dto.get(i).getColumnName().equals("actual_pay_date")||dto.get(i).getColumnName().equals("itf_pay_date")||dto.get(i).getColumnName().equals("refund_time")){
                dto.get(i).setColumnSqlText("TO_DATE("+dto.get(i).getColumnName()+",  '"+dto.get(i).getColumnFormat()+"') AS "+dto.get(i).getColumnName());
            }else if(!dto.get(i).getOfferValueType().equals("FIXED")){
                service.updateColumnFormat(dto.get(i).getFormatLnsId());
                dto.get(i).setColumnSqlText(dto.get(i).getColumnName());
            }else{
                service.updateColumnFormat(dto.get(i).getFormatLnsId());
            }
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CshOfferFormatLns> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
}