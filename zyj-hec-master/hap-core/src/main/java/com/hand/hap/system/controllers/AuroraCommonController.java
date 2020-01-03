package com.hand.hap.system.controllers;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.cache.impl.LovCache;
import com.hand.hap.core.ILanguageProvider;
import com.hand.hap.core.impl.DefaultTlTableNameProvider;
import com.hand.hap.excel.service.impl.HapExcelExportService;
import com.hand.hap.mybatis.entity.EntityField;
import com.hand.hap.system.dto.*;
import com.hand.hap.system.mapper.MultiLanguageMapper;
import com.hand.hap.system.service.ICodeService;

@RestController
public class AuroraCommonController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(AuroraCommonController.class);

    @Autowired
    private MultiLanguageMapper multiLanguageMapper;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private ILanguageProvider languageProvider;

    @Autowired
    private LovCache lovCache;

    @Autowired
    private HapExcelExportService newExportService;

    /**
     * 处理多语言字段.
     *
     * @param request HttpServletRequest
     * @param id      主键值
     * @param dto     dto全名
     * @param field   多语言字段名称(dto中的属性名)
     * @return Map
     */
    @RequestMapping(value = "/sys/auroraMultiLanguage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView loadAuroraMultiLanguageFields(HttpServletRequest request, @RequestParam String id,
                                                      @RequestParam String dto, @RequestParam String field) {
        ModelAndView tlView = new ModelAndView("/sys/TlEdit");
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(dto) && StringUtils.isNotEmpty(field)) {
            Class<?> clazz;
            try {
                clazz = Class.forName(dto);
                Table table = clazz.getAnnotation(Table.class);
                EntityField idField = DTOClassInfo.getIdFields(clazz)[0];
                EntityField tlField = DTOClassInfo.getEntityField(clazz, field);
                if (table != null && idField != null && tlField != null) {
                    Map<String, String> map = new HashMap<>(4);
                    map.put("table", DefaultTlTableNameProvider.getInstance().getTlTableName(table.name()));
                    map.put("idName", DTOClassInfo.getColumnName(idField));
                    map.put("tlName", DTOClassInfo.getColumnName(tlField));
                    map.put("id", id);
                    List list = multiLanguageMapper.select(map);
                    tlView.addObject("list", list);

                }
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error(e.getMessage(), e);
                }
            }
        } else {
            List<Language> list = languageProvider.getSupportedLanguages();
            list.sort(Comparator.comparing(Language::getLangCode));
            tlView.addObject("list", list);

        }
        return tlView;
    }

    @RequestMapping(value = "/common/auroraCode/{code}")
    @ResponseBody
    public ResponseData getAuroraCode(@RequestParam(required = false) String parentCode, @RequestParam(required = false) String parentValue, @PathVariable String code, HttpServletRequest request) {
        List codeValueList ;
        if (parentCode != null) {
            codeValueList = codeService.getChildCodeValue(createRequestContext(request), parentCode, parentValue, code);
            if (codeValueList == null) {
                codeValueList = Collections.emptyList();
            }
        } else {
            codeValueList = codeService.getCodeValuesByCode(createRequestContext(request), code);
            if (codeValueList == null) {
                codeValueList = Collections.emptyList();
            }
        }
        return new ResponseData(codeValueList);
    }


    @RequestMapping(value = "/common/auroraChildCode/{code}")
    @ResponseBody
    public ResponseData getAuroraChildCode(@RequestParam(required = false) String parentCode, @RequestParam(required = false) String parentValue, @PathVariable String code, HttpServletRequest request) {
        List codeValueList = codeService.getChildCodeValue(createRequestContext(request), parentCode, parentValue, code);
        if (codeValueList == null) {
            codeValueList = Collections.emptyList();
        }
        return new ResponseData(codeValueList);
    }

    @RequestMapping(value = "/export_excel_template.screen")
    public ModelAndView renderExportExcelTemplate(HttpServletRequest request, String tableName,String url) {
        ModelAndView view  = new ModelAndView("/export_excel_template");
        List<Map<String,Object>> listMap = newExportService.parseColumns(tableName,request);
        view.addObject("list",listMap);
        view.addObject("size",listMap.size());
        view.addObject("url",url);
        return view;
    }

    /**
     * Aurora通用LOV的展示的url.
     *
     * @param id      lovId
     * @return ResponseData ResponseData
     */
    @RequestMapping(value = "/common/auroraLov/{id}")
    public ModelAndView rendererAuroraLov(@PathVariable String id) {
        return renderLov(id,"sys/sys_aurora_lov");
    }

    /**
     * Aurora通用LOV的展示的url.
     *
     * @param id      lovId
     * @return ResponseData ResponseData
     */
    @RequestMapping(value = "/common/auroraMultiLov/{id}")
    public ModelAndView rendererAuroraMultiLov(@PathVariable String id) {
        return renderLov(id,"sys/sys_aurora_multi_lov");
    }

    private ModelAndView renderLov(String id,String viewName){
        String[] lovCodeArray = StringUtils.split(id, "?");
        Lov lov = lovCache.getValue(id);
        //2018-12-11 gyt 修改lov字段顺序
        lov.getLovItems().sort(Comparator.comparingInt(LovItem::getGridFieldSequence));
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("lov", lov);
        modelAndView.addObject("lov_code", id);
        if (lovCodeArray.length > 1) {
            modelAndView.addObject("lov_param", lovCodeArray[1]);
        }
        return modelAndView;
    }


    @RequestMapping(value = {"/{folder1}/{name}.screen"})
    public ModelAndView renderFolder1Screen(@PathVariable String folder1, @PathVariable String name, Model model) {
        return new ModelAndView(
                new StringBuilder(getViewPath()).append("/").append(folder1).append("/").append(name).toString());
    }

    @RequestMapping(value = {"/{folder1}/{folder2}/{name}.screen"})
    public ModelAndView renderFolder2Screen(@PathVariable String folder1, @PathVariable String folder2,
                                            @PathVariable String name, Model model) {
        return new ModelAndView(new StringBuilder(getViewPath()).append("/").append(folder1).append("/").append(folder2)
                .append("/").append(name).toString());
    }

    @RequestMapping(value = {"/{folder1}/{folder2}/{folder3}/{name}.screen"})
    public ModelAndView renderFolder3Screen(@PathVariable String folder1, @PathVariable String folder2,
                                            @PathVariable String folder3, @PathVariable String name, Model model) {
        return new ModelAndView(new StringBuilder(getViewPath()).append("/").append(folder1).append("/").append(folder2)
                .append("/").append(folder3).append("/").append(name).toString());
    }

    @RequestMapping(value = {"/{folder1}/{folder2}/{folder3}/{folder4}/{name}.screen"})
    public ModelAndView renderFolder4Screen(@PathVariable String folder1, @PathVariable String folder2,
                                            @PathVariable String folder3, @PathVariable String folder4, @PathVariable String name, Model model) {
        return new ModelAndView(new StringBuilder(getViewPath()).append("/").append(folder1).append("/").append(folder2)
                .append("/").append(folder3).append("/").append(folder4).append("/").append(name).toString());
    }

    @RequestMapping(value = {"/{name}.screen"})
    public ModelAndView renderScreen(@PathVariable String name, Model model) {
        return new ModelAndView(name);
    }

}
