package com.hand.hec.gld.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.gld.dto.GldAccEntityRefBe;
import com.hand.hec.gld.service.IGldAccEntityRefBeService;

/**
 * <p>
 * GldAccEntityRefBeController
 * </p>
 *
 * @author yang.duan 2019/01/10 13:45
 * update by luhui 2019/01/16 15:58
 */

@Controller
@RequestMapping(value = "/gld/acc-entity-ref-be")
public class GldAccEntityRefBeController extends BaseController {

    @Autowired
    private IGldAccEntityRefBeService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(GldAccEntityRefBe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(GldAccEntityRefBe.FIELD_ACC_ENTITY_ID));
        return new ResponseData(service.selectOptions(requestContext, dto, criteria, page, pageSize));
    }

    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<GldAccEntityRefBe> dto, BindingResult result, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<GldAccEntityRefBe> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }


    /**
     * 核算主体批量分配预算实体，查询出未分配过的预算实体
     *
     * @param dto
     * @return
     * @author ngls.luhui 2019-01-23 14:59
     */
    @RequestMapping(value = "/batchSelect", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData batchSelect(GldAccEntityRefBe dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.batchSelect(dto.getAccEntityId(),requestContext,page, pageSize));
    }
}