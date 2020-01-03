package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.ILovService;
import com.hand.hec.bpm.dto.*;
import com.hand.hec.bpm.service.*;
import org.joor.Reflect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class JsEngineService implements IJsEngineService {

    @Autowired
    IPageLayoutBasicService layoutBasicService;

    @Autowired
    IPageLayoutFormService layoutFormService;

    @Autowired
    IPageLayoutGridService layoutGridService;

    @Autowired
    IPageLayoutTabService layoutTabService;

    @Autowired
    IPageLayoutEventService layoutEventService;

    @Autowired
    IPageLayoutButtonService layoutButtonService;

    @Autowired
    IPageTagBasicService tagBasicService;

    @Autowired
    IPageTagCheckboxService checkboxService;

    @Autowired
    IPageTagComboboxService comboboxService;

    @Autowired
    IPageTagComboboxMapService comboboxMapService;

    @Autowired
    IPageTagDatepickerService datepickerService;

    @Autowired
    IPageTagDatetimepickerService datetimepickerService;

    @Autowired
    IPageTagLabelService labelService;

    @Autowired
    IPageTagLovService lovService;

    @Autowired
    IPageTagLovMapService lovMapService;

    @Autowired
    IPageTagNumberfieldService numberfieldService;

    @Autowired
    IPageTagRadioService radioService;

    @Autowired
    IPageTagTextfieldService textfieldService;

    @Autowired
    IPageTagEventService tagEventService;

    @Autowired
    IPageButtonService pageButtonService;

    @Autowired
    private ILovService commonLovService;

    @Autowired
    BeanFactory beanFactory;

    private Logger logger = LoggerFactory.getLogger(JsEngineService.class);


    @Override
    public List<PageButton> queryPageButton(IRequest request, Long pageId) {
        PageButton button = new PageButton();
        button.setPageId(pageId);
        Criteria criteria = new Criteria(button);
        criteria.where(new WhereField(PageButton.FIELD_PAGE_ID, Comparison.EQUAL));
        return pageButtonService.selectOptions(request, button, criteria, 0, 0);
    }

    @Override
    public List<PageLayoutBasic> queryPageLayoutBasic(IRequest request, Long pageId) {
        PageLayoutBasic basic = new PageLayoutBasic();
        basic.setPageId(pageId);
        Criteria criteria = new Criteria(basic);
        criteria.where(new WhereField(PageLayoutBasic.FIELD_PAGE_ID, Comparison.EQUAL));
        return layoutBasicService.selectOptions(request, basic, criteria, 0, 0);
    }

    @Override
    public List<PageLayoutForm> queryPageLayoutForm(IRequest request, Long layoutId) {
        PageLayoutForm form = new PageLayoutForm();
        form.setLayoutId(layoutId);
        Criteria criteria = new Criteria(form);
        criteria.where(new WhereField(PageLayoutForm.FIELD_LAYOUT_ID, Comparison.EQUAL));
        return layoutFormService.selectOptions(request, form, criteria, 0, 0);
    }

    @Override
    public List<PageLayoutGrid> queryPageLayoutGrid(IRequest request, Long layoutId) {
        PageLayoutGrid grid = new PageLayoutGrid();
        grid.setLayoutId(layoutId);
        Criteria criteria = new Criteria(grid);
        criteria.where(new WhereField(PageLayoutGrid.FIELD_LAYOUT_ID, Comparison.EQUAL));
        return layoutGridService.selectOptions(request, grid, criteria, 0, 0);
    }

    @Override
    public List<PageLayoutTab> queryPageLayoutTab(IRequest request, Long layoutId) {
        PageLayoutTab tab = new PageLayoutTab();
        tab.setLayoutId(layoutId);
        Criteria criteria = new Criteria(tab);
        criteria.where(new WhereField(PageLayoutTab.FIELD_LAYOUT_ID, Comparison.EQUAL));
        return layoutTabService.selectOptions(request, tab, criteria, 0, 0);
    }

    @Override
    public List<PageLayoutEvent> queryPageLayoutEvent(IRequest request, Long layoutId) {
        PageLayoutEvent event = new PageLayoutEvent();
        event.setLayoutId(layoutId);
        Criteria criteria = new Criteria(event);
        criteria.where(new WhereField(PageLayoutEvent.FIELD_LAYOUT_ID, Comparison.EQUAL));
        return layoutEventService.selectOptions(request, event, criteria, 0, 0);
    }

    @Override
    public List<PageLayoutButton> queryPageLayoutButton(IRequest request, Long layoutId) {
        PageLayoutButton button = new PageLayoutButton();
        button.setLayoutId(layoutId);
        Criteria criteria = new Criteria(button);
        criteria.where(new WhereField(PageLayoutButton.FIELD_LAYOUT_ID, Comparison.EQUAL));
        return layoutButtonService.selectOptions(request, button, criteria, 0, 0);
    }

    @Override
    public List<PageTagBasic> queryPageTagBasic(IRequest request, Long layoutId) {
        PageTagBasic basic = new PageTagBasic();
        basic.setLayoutId(layoutId);
        Criteria criteria = new Criteria(basic);
        criteria.where(new WhereField(PageTagBasic.FIELD_LAYOUT_ID, Comparison.EQUAL));
        return tagBasicService.selectOptions(request, basic, criteria, 0, 0);
    }

    @Override
    public List<PageTagCheckbox> queryPageTagCheckbox(IRequest request, Long tagId) {
        PageTagCheckbox checkbox = new PageTagCheckbox();
        checkbox.setTagId(tagId);
        Criteria criteria = new Criteria(checkbox);
        criteria.where(new WhereField(PageTagCheckbox.FIELD_TAG_ID, Comparison.EQUAL));
        return checkboxService.selectOptions(request, checkbox, criteria, 0, 0);
    }

    @Override
    public List<PageTagCombobox> queryPageTagCombobox(IRequest request, Long tagId) {
        PageTagCombobox combobox = new PageTagCombobox();
        combobox.setTagId(tagId);
        Criteria criteria = new Criteria(combobox);
        criteria.where(new WhereField(PageTagCombobox.FIELD_TAG_ID, Comparison.EQUAL));
        return comboboxService.selectOptions(request, combobox, criteria, 0, 0);
    }

    @Override
    public List<PageTagComboboxMap> queryPageTagComboboxMap(IRequest request, Long tagId) {
        PageTagComboboxMap comboboxMap = new PageTagComboboxMap();
        comboboxMap.setTagId(tagId);
        Criteria criteria = new Criteria(comboboxMap);
        criteria.where(new WhereField(PageTagComboboxMap.FIELD_TAG_ID, Comparison.EQUAL));
        return comboboxMapService.selectOptions(request, comboboxMap, criteria, 0, 0);
    }

    @Override
    public List<PageTagDatepicker> queryPageTagDatepicker(IRequest request, Long tagId) {
        PageTagDatepicker datepicker = new PageTagDatepicker();
        datepicker.setTagId(tagId);
        Criteria criteria = new Criteria(datepicker);
        criteria.where(new WhereField(PageTagDatepicker.FIELD_TAG_ID, Comparison.EQUAL));
        return datepickerService.selectOptions(request, datepicker, criteria, 0, 0);
    }

    @Override
    public List<PageTagDatetimepicker> queryPageTagDatetimepicker(IRequest request, Long tagId) {
        PageTagDatetimepicker datetimepicker = new PageTagDatetimepicker();
        datetimepicker.setTagId(tagId);
        Criteria criteria = new Criteria(datetimepicker);
        criteria.where(new WhereField(PageTagDatetimepicker.FIELD_TAG_ID, Comparison.EQUAL));
        return datetimepickerService.selectOptions(request, datetimepicker, criteria, 0, 0);
    }

    @Override
    public List<PageTagLabel> queryPageTagLabel(IRequest request, Long tagId) {
        PageTagLabel label = new PageTagLabel();
        label.setTagId(tagId);
        Criteria criteria = new Criteria(label);
        criteria.where(new WhereField(PageTagLabel.FIELD_TAG_ID, Comparison.EQUAL));
        return labelService.selectOptions(request, label, criteria, 0, 0);
    }

    @Override
    public List<PageTagLov> queryPageTagLov(IRequest request, Long tagId) {
        PageTagLov lov = new PageTagLov();
        lov.setTagId(tagId);
        Criteria criteria = new Criteria(lov);
        criteria.where(new WhereField(PageTagLov.FIELD_TAG_ID, Comparison.EQUAL));
        return lovService.selectOptions(request, lov, criteria, 0, 0);
    }

    @Override
    public List<PageTagLovMap> queryPageTagLovMap(IRequest request, Long tagId) {
        PageTagLovMap lovMap = new PageTagLovMap();
        lovMap.setTagId(tagId);
        Criteria criteria = new Criteria(lovMap);
        criteria.where(new WhereField(PageTagLovMap.FIELD_TAG_ID, Comparison.EQUAL));
        return lovMapService.selectOptions(request, lovMap, criteria, 0, 0);
    }

    @Override
    public List<PageTagNumberfield> queryPageTagNumberfield(IRequest request, Long tagId) {
        PageTagNumberfield numberfield = new PageTagNumberfield();
        numberfield.setTagId(tagId);
        Criteria criteria = new Criteria(numberfield);
        criteria.where(new WhereField(PageTagNumberfield.FIELD_TAG_ID, Comparison.EQUAL));
        return numberfieldService.selectOptions(request, numberfield, criteria, 0, 0);
    }

    @Override
    public List<PageTagRadio> queryPageTagRadio(IRequest request, Long tagId) {
        PageTagRadio radio = new PageTagRadio();
        radio.setTagId(tagId);
        Criteria criteria = new Criteria(radio);
        criteria.where(new WhereField(PageTagRadio.FIELD_TAG_ID, Comparison.EQUAL));
        return radioService.selectOptions(request, radio, criteria, 0, 0);
    }

    @Override
    public List<PageTagTextfield> queryPageTagTextfield(IRequest request, Long tagId) {
        PageTagTextfield textfield = new PageTagTextfield();
        textfield.setTagId(tagId);
        Criteria criteria = new Criteria(textfield);
        criteria.where(new WhereField(PageTagTextfield.FIELD_TAG_ID, Comparison.EQUAL));
        return textfieldService.selectOptions(request, textfield, criteria, 0, 0);
    }

    @Override
    public List<PageTagEvent> queryPageTagEvent(IRequest request, Long tagId) {
        PageTagEvent event = new PageTagEvent();
        event.setTagId(tagId);
        Criteria criteria = new Criteria(event);
        criteria.where(new WhereField(PageTagEvent.FIELD_TAG_ID, Comparison.EQUAL));
        return tagEventService.selectOptions(request, event, criteria, 0, 0);
    }

    @Override
    public String getLovDefaultvalue(IRequest request, CompositeMap context, String lovStr) {
        String lovTmpStr = lovStr.replace("#LOV[", "").replace("]#", "");
        String[] lovArr = lovTmpStr.split("\\.");

        if (lovArr.length != 2) {
            throw new RuntimeException(lovStr + "格式不正确");
        }

        String lovCode = lovArr[0];
        String lovField = lovArr[1];

        Map params = context.getChild("parameter");
        //只取第一条记录作为默认值
        List results = commonLovService.selectDatas(request, lovCode, params, 1, 1);
        if (results.size() != 1) {
            logger.warn(lovStr + "在参数" + ((CompositeMap) params).toXML() + "未能取到默认值");
            return null;
        } else {
            Map result = (Map) results.get(0);
            return result.get(lovField).toString();
        }
    }

    @Override
    public String getJavaDefaultvalue(IRequest request, CompositeMap context, String javaStr) {
        String javaTmpStr = javaStr.replace("#JAVA[", "").replace("]#", "");
        String[] javaArr = javaTmpStr.split("\\.");

        if (javaArr.length < 2) {
            throw new RuntimeException(javaStr + "格式不正确");
        }

        String beanName = javaArr[0];
        String methodName = javaArr[1].replace("()", "");
        Map params = context.getChild("parameter");

        //被调用的方法格式为：methodName(IRequest request,Map param)
        //支持
        //1、基本值传递 xxBean.getXXValue()
        //2、Bean属性get xxBean.getXXBean().getXXValue()
        //3、Map值get xxBean.getXXMap().get("key")
        Object bean = beanFactory.getBean(beanName);
        Reflect methodResult = Reflect.on(bean).call(methodName, request, params);

        if (javaArr.length == 3) {
            String getFieldMethodName = javaArr[2].replace("()", "");
            //如果包含get(说明是Map类型取值
            if (getFieldMethodName.contains("get(")) {
                String key = getFieldMethodName.replaceAll("get\\(\'", "").replaceAll("\'\\)","");
                return methodResult.call("get", key).get() == null ? "" : methodResult.call("get", key).get().toString();
            } else {
                return methodResult.call(getFieldMethodName).get() == null ? "" : methodResult.call(getFieldMethodName).get().toString();
            }
        } else {
            return methodResult.get() == null ? "" : methodResult.get().toString();
        }
    }

    public Long getTestValue(IRequest request, Map param) {
        return 10L;
    }

    public PageTagEvent getTestBean(IRequest request, Map param) {
        PageTagEvent tagEvent = new PageTagEvent();
        tagEvent.setTagId(100L);
        return tagEvent;
    }

    public Map getTestMap(IRequest request, Map param) {
        Map testMap = new HashMap();
        testMap.put("testId", 1000L);
        return testMap;
    }
}
