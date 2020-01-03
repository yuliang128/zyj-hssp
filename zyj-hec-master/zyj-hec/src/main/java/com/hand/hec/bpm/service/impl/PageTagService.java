package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.*;
import com.hand.hec.bpm.mapper.PageTagMapper;
import com.hand.hec.bpm.service.*;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagService extends BaseServiceImpl<PageTag> implements IPageTagService {

    @Autowired
    IPageLayoutBasicService layoutBasicService;

    @Autowired
    IPageTagBasicService basicService;

    @Autowired
    IPageTagCheckboxService checkboxService;

    @Autowired
    IPageTagComboboxService comboboxService;

    @Autowired
    IPageTagDatepickerService datepickerService;

    @Autowired
    IPageTagDatetimepickerService datetimepickerService;

    @Autowired
    IPageTagLabelService labelService;

    @Autowired
    IPageTagLovService lovService;

    @Autowired
    IPageTagNumberfieldService numberfieldService;

    @Autowired
    IPageTagRadioService radioService;

    @Autowired
    IPageTagTextfieldService textfieldService;

    @Autowired
    PageTagMapper pageTagMapper;

    @Autowired
    IJsTemplateService jsTemplateService;

    @Autowired
    IPageTagDataGuideService dataGuideService;


    @Override
    public PageTag insertPageTag(IRequest request, PageTag tag) {
        PageLayoutBasic layout = new PageLayoutBasic();
        layout.setLayoutId(tag.getLayoutId());
        layout = layoutBasicService.selectByPrimaryKey(request, layout);

        PageTagBasic basic = tag.convertBasic();
        basic.setBindtarget(layout.getDataset());

        if ("Y".equals(basic.getFooterrenderer())) {
            basic.setFooterrendererJs(jsTemplateService.getSumGridNum(request, null));
        }

        basic = basicService.insert(request, basic);
        tag.setTagId(basic.getTagId());

        PageTagCheckbox ck = tag.convertCheckbox();
        ck = checkboxService.insert(request, ck);

        PageTagCombobox cb = tag.convertCombobox();
        cb = comboboxService.insert(request, cb);

        PageTagDatepicker dp = tag.convertDatepicker();
        dp = datepickerService.insert(request, dp);

        PageTagDatetimepicker dtp = tag.convertDatetimepicker();
        dtp = datetimepickerService.insert(request, dtp);

        PageTagLabel label = tag.convertLabel();
        label = labelService.insert(request, label);

        PageTagLov lov = tag.convertLov();
        lov = lovService.insert(request, lov);

        PageTagNumberfield nf = tag.convertNumberfield();
        nf = numberfieldService.insert(request, nf);

        PageTagRadio radio = tag.convertRadio();
        radio = radioService.insert(request, radio);

        PageTagTextfield tf = tag.convertTextfield();
        tf = textfieldService.insert(request, tf);

        tag = tag.convertTag(basic, ck, cb, dp, dtp, label, lov, nf, radio, tf);

        return tag;
    }

    @Override
    public PageTag updatePageTag(IRequest request, PageTag tag) {
        PageTagBasic basic = tag.convertBasic();

        if ("Y".equals(basic.getFooterrenderer())) {
            basic.setFooterrendererJs(jsTemplateService.getSumGridNum(request, null));
        }

        basic = basicService.updateByPrimaryKey(request, basic);

        PageTagCheckbox ck = tag.convertCheckbox();
        ck = checkboxService.updateByPrimaryKey(request, ck);

        PageTagCombobox cb = tag.convertCombobox();
        cb = comboboxService.updateByPrimaryKey(request, cb);

        PageTagDatepicker dp = tag.convertDatepicker();
        dp = datepickerService.updateByPrimaryKey(request, dp);

        PageTagDatetimepicker dtp = tag.convertDatetimepicker();
        dtp = datetimepickerService.updateByPrimaryKey(request, dtp);

        PageTagLabel label = tag.convertLabel();
        label = labelService.updateByPrimaryKey(request, label);

        PageTagLov lov = tag.convertLov();
        lov = lovService.updateByPrimaryKey(request, lov);

        PageTagNumberfield nf = tag.convertNumberfield();
        nf = numberfieldService.updateByPrimaryKey(request, nf);

        PageTagRadio radio = tag.convertRadio();
        radio = radioService.updateByPrimaryKey(request, radio);

        PageTagTextfield tf = tag.convertTextfield();
        tf = textfieldService.updateByPrimaryKey(request, tf);

        tag = tag.convertTag(basic, ck, cb, dp, dtp, label, lov, nf, radio, tf);

        dataGuideService.clearDataGuideEvent(request, basic,null);
        dataGuideService.generateDataGuideEvent(request, basic,null);

        return tag;
    }

    @Override
    public int deletePageTag(IRequest request, PageTag tag) {
        int count = 0;

        PageTagCheckbox ck = tag.convertCheckbox();
        count = checkboxService.deleteByPrimaryKey(ck);

        PageTagCombobox cb = tag.convertCombobox();
        count = comboboxService.deleteByPrimaryKey(cb);

        PageTagDatepicker dp = tag.convertDatepicker();
        count = datepickerService.deleteByPrimaryKey(dp);

        PageTagDatetimepicker dtp = tag.convertDatetimepicker();
        count = datetimepickerService.deleteByPrimaryKey(dtp);

        PageTagLabel label = tag.convertLabel();
        count = labelService.deleteByPrimaryKey(label);

        PageTagLov lov = tag.convertLov();
        count = lovService.deleteByPrimaryKey(lov);

        PageTagNumberfield nf = tag.convertNumberfield();
        count = numberfieldService.deleteByPrimaryKey(nf);

        PageTagRadio radio = tag.convertRadio();
        count = radioService.deleteByPrimaryKey(radio);

        PageTagTextfield tf = tag.convertTextfield();
        count = textfieldService.deleteByPrimaryKey(tf);

        PageTagBasic basic = tag.convertBasic();
        count = basicService.deleteByPrimaryKey(basic);

        return count;
    }

    @Override
    public List<PageTag> batchUpdatePageTag(IRequest request, List<PageTag> tagList) {
        for (PageTag tag : tagList) {
            switch (tag.get__status()) {
                case DTOStatus.ADD:
                    insertPageTag(request, tag);
                    break;
                case DTOStatus.UPDATE:
                    updatePageTag(request, tag);
                    break;
                case DTOStatus.DELETE:
                    deletePageTag(request, tag);
                    break;
            }
        }

        return tagList;
    }

    @Override
    public List<PageTag> queryPageTag(IRequest request, Long layoutId) {
        return pageTagMapper.queryPageTag(layoutId);
    }
}
