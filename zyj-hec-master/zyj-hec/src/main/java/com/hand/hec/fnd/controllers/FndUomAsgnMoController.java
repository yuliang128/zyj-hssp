package com.hand.hec.fnd.controllers;

import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.service.IFndManagingOrganizationService;
import com.hand.hec.expm.dto.ExpPolicyDistrict;
import com.hand.hec.fnd.dto.FndUom;
import com.hand.hec.fnd.service.IFndUomService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.fnd.dto.FndUomAsgnMo;
import com.hand.hec.fnd.service.IFndUomAsgnMoService;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 计量单位分配管理组织控制器
 *
 * @author zhongyu
 */

@Controller
public class FndUomAsgnMoController extends BaseController{

    @Autowired
    private IFndUomAsgnMoService service;
    @Autowired
    private IFndManagingOrganizationService fndManagingOrganizationService;
    @Autowired
    private IFndUomService fndUomService;

    @RequestMapping(value = "/fnd/uom-asgn-mo/query",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData query(FndUomAsgnMo dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/fnd/uom-asgn-mo/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<FndUomAsgnMo> dto, BindingResult result, HttpServletRequest request){
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/fnd/uom-asgn-mo/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<FndUomAsgnMo> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    /**
     * 计量单位未分配管理组织
     * @param request
     * @param dto
     * @param uomId
     * @param page
     * @param pageSize
     * @return
     * @author zhongyu 2019-4-24
     */

    @RequestMapping(value = "/fnd/uom-asgn-mo/queryNoneByFndUomId",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseData queryByFndUomId(HttpServletRequest request, FndManagingOrganization dto, Long uomId,
                                        @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(fndManagingOrganizationService.queryNoneByFndUomId(requestContext,uomId,dto,page,pageSize));
    }

    /**
     *  单个计量单位分配多个管理组织
     * @param request
     * @param fndManagingOrganizations
     * @return
     * @author zhongyu 2019-4-26
     */
    @RequestMapping(value="/fnd/uom-asgn-mo/batchAssignMoOrgOnetoMany")
    @ResponseBody
    public ResponseData batchAssignMoOrgOnetoMany(HttpServletRequest request, @RequestBody List<FndManagingOrganization> fndManagingOrganizations){
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.batchAssignMoOrgOnetoMany(requestContext,fndManagingOrganizations));
    }


    /**
     * 查询该计量单位详细信息
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @author zhongyu 2019-4-24
     * @return
     */
    @RequestMapping(value = "/fnd/FND1040/fnd_uoms_asgn_mo_org.screen")
    public ModelAndView expPolicyPlaceView( HttpServletRequest request,  FndUom dto,
      @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize){
        IRequest requestCtx = createRequestContext(request);
        ModelAndView view = new ModelAndView("fnd/FND1040/fnd_uoms_asgn_mo_org");
        List<FndUom>  fndUoms = fndUomService.select(requestCtx,dto,page,pageSize);
        view.addObject("uomCode",fndUoms.get(0).getUomCode());
        view.addObject("uomName",fndUoms.get(0).getDescription());
        return view;
    }

}