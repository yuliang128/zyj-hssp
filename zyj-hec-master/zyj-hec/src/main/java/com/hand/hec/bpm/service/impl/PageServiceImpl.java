package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.core.exception.BaseRuntimeExcepiton;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.*;
import com.hand.hec.bpm.exception.BpmException;
import com.hand.hec.bpm.mapper.PageMapper;
import com.hand.hec.bpm.service.*;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageServiceImpl extends BaseServiceImpl<Page> implements IPageService {

    @Autowired
    IPageGroupService groupService;

    @Autowired
    ITemplateService templateService;

    @Autowired
    ITpltButtonService tpltButtonService;

    @Autowired
    ITpltLayoutBasicService tpltLayoutBasicService;

    @Autowired
    ITpltLayoutButtonService tpltLayoutButtonService;

    @Autowired
    ITpltTagBasicService tpltTagBasicService;

    @Autowired
    IPageButtonService pageButtonService;

    @Autowired
    IPageLayoutBasicService pageLayoutBasicService;

    @Autowired
    IPageLayoutButtonService pageLayoutButtonService;

    @Autowired
    IPageTagService pageTagService;


    @Autowired
    IPageTagBasicService pageTagBasicService;


    @Autowired
    private PageMapper pageMapper;

    @Autowired
    private IPageTagTextfieldService pageTagTextfieldService;

    @Autowired
    private IPageTagCheckboxService pageTagCheckboxService;

    @Autowired
    private IPageTagComboboxService pageTagComboboxService;

    @Autowired
    private IPageTagComboboxMapService pageTagComboboxMapService;

    @Autowired
    private IPageTagComboboxFieldService pageTagComboboxFieldService;

    @Autowired
    private IPageTagLovService pageTagLovService;

    @Autowired
    private IPageTagLovMapService pageTagLovMapService;

    @Autowired
    private IPageTagRadioService pageTagRadioService;

    @Autowired
    private IPageTagNumberfieldService pageTagNumberfieldService;

    @Autowired
    private IPageTagDatepickerService pageTagDatepickerService;

    @Autowired
    private IPageTagDatetimepickerService pageTagDatetimepickerService;

    @Autowired
    private IPageTagLabelService pageTagLabelService;

    @Autowired
    private IPageLayoutEventService pageLayoutEventService;

    @Autowired
    private IPageTagDataGuideService pageTagDataGuideService;

    @Autowired
    private IPageLayoutFormService pageLayoutFormService;

    @Autowired
    private IPageLayoutGridService pageLayoutGridService;

    @Autowired
    private IPageLayoutTabService pageLayoutTabService;


    @Override
    public Page insertPage(IRequest request, Page page) {
        page = this.insert(request, page);

        PageGroup group = new PageGroup();
        group.setGroupId(page.getGroupId());
        group = groupService.selectByPrimaryKey(request, group);

        if (group == null) {
            throw new BpmException("BPM_GROUP_NOT_EXISTS", "BPM_GROUP_NOT_EXISTS", null);
        }

        Template template = new Template();
        template.setTemplateId(group.getTemplateId());
        template = templateService.selectByPrimaryKey(request, template);

        if (template == null) {
            throw new BpmException("BPM_TEMPLATE_NOT_EXISTS", "BPM_TEMPLATE_NOT_EXISTS", null);
        }

        //循环生成页面按钮
        TpltButton queryTpltButton = new TpltButton();
        queryTpltButton.setTemplateId(template.getTemplateId());
        List<TpltButton> tpltButtonList = tpltButtonService.select(request, queryTpltButton, 0, 0);
        List<PageButton> pageButtonList = new ArrayList<PageButton>();
        for (TpltButton tpltButton : tpltButtonList) {
            PageButton pageButton = new PageButton(tpltButton, page.getPageId());
            pageButton.set__status(DTOStatus.ADD);
            pageButtonList.add(pageButton);
        }
        pageButtonService.batchUpdate(request, pageButtonList);


        //循环生成布局组件
        TpltLayout queryLayout = new TpltLayout();
        queryLayout.setTemplateId(template.getTemplateId());
        List<TpltLayout> tpltLayoutList = tpltLayoutBasicService.queryByTemplateId(request, queryLayout);
        List<PageLayout> pageLayoutList = new ArrayList<PageLayout>();
        for (TpltLayout tpltLayout : tpltLayoutList) {
            PageLayout pageLayout = new PageLayout(tpltLayout, page.getPageId());
            pageLayout = pageLayoutBasicService.insertBasic(request, pageLayout);
            //循环生成布局组件按钮
            TpltLayoutButton queryTpltLayoutButton = new TpltLayoutButton();
            queryTpltLayoutButton.setLayoutId(tpltLayout.getLayoutId());
            List<TpltLayoutButton> tpltLayoutButtonList = tpltLayoutButtonService.select(request, queryTpltLayoutButton, 0, 0);
            List<PageLayoutButton> pageLayoutButtonList = new ArrayList<PageLayoutButton>();

            for (TpltLayoutButton tpltLayoutButton : tpltLayoutButtonList) {
                PageLayoutButton pageLayoutButton = new PageLayoutButton(tpltLayoutButton, pageLayout.getLayoutId());
                pageLayoutButton.set__status(DTOStatus.ADD);
                pageLayoutButtonList.add(pageLayoutButton);
            }
            pageLayoutButtonService.batchUpdate(request, pageLayoutButtonList);

            //循环生成布局组件下的标签
            TpltTagBasic queryTpltTagBasic = new TpltTagBasic();
            queryTpltTagBasic.setLayoutId(tpltLayout.getLayoutId());
            List<TpltTagBasic> tpltTagBasicList = tpltTagBasicService.select(request, queryTpltTagBasic, 0, 0);
            List<PageTag> pageTagList = new ArrayList<PageTag>();
            for (TpltTagBasic tpltTagBasic : tpltTagBasicList) {
                PageTagBasic basic = new PageTagBasic(tpltTagBasic, pageLayout.getLayoutId());
                basic.setBindtarget(tpltLayout.getDataset());
                PageTag tag = new PageTag();
                tag = tag.convertTag(basic, null, null, null, null, null, null, null, null, null);
                tag.set__status(DTOStatus.ADD);
                pageTagList.add(tag);
            }
            pageTagService.batchUpdatePageTag(request, pageTagList);
        }


        return page;
    }


    @Override
    public int deletePage(IRequest request, Page page) {
        //循环删除
        // 1、页面按钮
        // 2、组件

        PageButton queryButton = new PageButton();
        queryButton.setPageId(page.getPageId());
        List<PageButton> pageButtonList = pageButtonService.select(request, queryButton, 0, 0);
        pageButtonService.batchDelete(pageButtonList);

        PageLayout queryPageLayout = new PageLayout();
        queryPageLayout.setPageId(page.getPageId());
        List<PageLayout> pageLayoutList = pageLayoutBasicService.queryByPageId(request, queryPageLayout);
        for (PageLayout pageLayout : pageLayoutList) {
            pageLayoutBasicService.deleteBasic(request, pageLayout);
        }

        return deleteByPrimaryKey(page);
    }

    @Override
    public List<Page> batchUpdate(IRequest request, @StdWho List<Page> list) {
        IPageService self = (IPageService) AopContext.currentProxy();
        for (Page t : list) {
            switch (((BaseDTO) t).get__status()) {
                case DTOStatus.ADD:
                    self.insertPage(request, t);
                    break;
                case DTOStatus.UPDATE:
                    if (useSelectiveUpdate()) {
                        self.updateByPrimaryKeySelective(request, t);
                    } else {
                        self.updateByPrimaryKey(request, t);
                    }
                    break;
                case DTOStatus.DELETE:
                    self.deletePage(request, t);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    @Override
    public Map queryTemplateReference(IRequest iRequest, Long pageId) {
        return pageMapper.queryTemplateReference(iRequest, pageId);
    }

    @Override
    public void copyPage(Long pageId, IRequest iRequest){
        Page page = new Page();
        page.setPageId(pageId);
        page = pageMapper.selectByPrimaryKey(pageId);

        //页面复制
        Page copyPage = new Page();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        copyPage.setPageType(page.getPageType());
        copyPage.setGroupId(page.getGroupId());
        copyPage.setPageCode(page.getPageCode()+'_'+dateFormat.format(date).toString());
        copyPage.setPageDesc(page.getPageDesc()+"_复制页面:"+dateFormat.format(date).toString());
        copyPage.setEnabledFlag(page.getEnabledFlag());
        copyPage.set__status(DTOStatus.ADD);

        copyPage = this.insert(iRequest,copyPage);

        //页面按钮复制
        PageButton pageButton = new PageButton();
        pageButton.setPageId(pageId);
        List<PageButton> pageButtons = pageButtonService.selectOptions(iRequest,pageButton,new Criteria(pageButton));
        List<PageButton> copyPageButtons = new ArrayList<>();
        for(PageButton button:pageButtons){
            PageButton copyPageButton = new PageButton();
            copyPageButton.setBtnstyle(button.getBtnstyle());
            copyPageButton.setButtonCode(button.getButtonCode());
            copyPageButton.setButtonDesc(button.getButtonDesc());
            copyPageButton.setButtonSequence(button.getButtonSequence());
            copyPageButton.setButtonType(button.getButtonType());
            copyPageButton.setBtnstyle(button.getBtnstyle());
            copyPageButton.setClick(button.getClick());
            copyPageButton.setDisabled(button.getDisabled());
            copyPageButton.setHeight(button.getHeight());
            copyPageButton.setHidden(button.getHidden());
            copyPageButton.setIcon(button.getIcon());
            copyPageButton.setOperationType(button.getOperationType());
            copyPageButton.setTemplateFlag(button.getTemplateFlag());
            copyPageButton.setId(button.getId());
            copyPageButton.setText(button.getText());
            copyPageButton.setWidth(button.getWidth());
            copyPageButton.setPageId(copyPage.getPageId());
            copyPageButton.set__status(DTOStatus.ADD);
            copyPageButtons.add(copyPageButton);
        }
        pageButtonService.batchUpdate(iRequest,copyPageButtons);

        //布局复制
        PageLayout pageLayout = new PageLayout();
        pageLayout.setPageId(pageId);
        List<PageLayout> pageLayouts = pageLayoutBasicService.queryByPageId(iRequest,pageLayout);
        List<PageLayoutBasic> copyPageLayouts = new ArrayList<>();
        for(PageLayout layout : pageLayouts){
            PageLayoutBasic copyPageLayout = layout.convertBasic();
            copyPageLayout.setPageId(copyPage.getPageId());
            copyPageLayout.set__status(DTOStatus.ADD);
            copyPageLayout = pageLayoutBasicService.insert(iRequest,copyPageLayout);
            copyPageLayouts.add(copyPageLayout);

            //复制form
            PageLayoutForm layoutForm = new PageLayoutForm();
            layoutForm.setLayoutId(layout.getLayoutId());
            layoutForm = pageLayoutFormService.selectByPrimaryKey(iRequest,layoutForm);
            if(layoutForm!=null){
                PageLayoutForm copyLayoutForm = new PageLayoutForm();
                copyLayoutForm.setLayoutId(copyPageLayout.getLayoutId());
                copyLayoutForm.setColumnNum(layout.getColumnNum());
                copyLayoutForm.setRowNum(layout.getRowNum());
                copyLayoutForm.setLabelseparator(layout.getLabelseparator());
                copyLayoutForm.setLabelwidth(layout.getLabelwidth());
                copyLayoutForm.setPrompt(layout.getPrompt());
                copyLayoutForm.setTitle(layout.getTitle());
                copyLayoutForm.set__status(DTOStatus.ADD);
                copyLayoutForm = pageLayoutFormService.insert(iRequest,copyLayoutForm);
            }

            //复制gird
            PageLayoutGrid layoutGrid = new PageLayoutGrid();
            layoutGrid.setLayoutId(layout.getLayoutId());
            layoutGrid = pageLayoutGridService.selectByPrimaryKey(iRequest,layoutGrid);
            if(layoutGrid!=null){
                PageLayoutGrid copyLayoutGrid = new PageLayoutGrid();
                copyLayoutGrid.setLayoutId(copyPageLayout.getLayoutId());
                copyLayoutGrid.setNavbar(layout.getNavbar());
                copyLayoutGrid.set__status(DTOStatus.ADD);
                copyLayoutGrid = pageLayoutGridService.insert(iRequest,copyLayoutGrid);
            }

            //复制tab
            PageLayoutTab layoutTab = new PageLayoutTab();
            layoutTab.setLayoutId(layout.getLayoutId());
            layoutTab = pageLayoutTabService.selectByPrimaryKey(iRequest,layoutTab);
            if(layoutTab!=null){
                PageLayoutTab copyLayoutTab = new PageLayoutTab();
                copyLayoutTab.setLayoutId(copyPageLayout.getLayoutId());
                copyLayoutTab.setBodystyle(layout.getBodystyle());
                copyLayoutTab.setCloseable(layout.getCloseable());
                copyLayoutTab.setDisabled(layout.getDisabled());
                copyLayoutTab.setRef(layout.getRef());
                copyLayoutTab.setSelected(layout.getSelected());
                copyLayoutTab.setTabstyle(layout.getTabstyle());
                copyLayoutTab.set__status(DTOStatus.ADD);
                copyLayoutTab = pageLayoutTabService.insert(iRequest,copyLayoutTab);
            }

            //复制布局按钮
            PageLayoutButton layoutButton = new PageLayoutButton();
            layoutButton.setLayoutId(layout.getLayoutId());
            List<PageLayoutButton> layoutButtons = pageLayoutButtonService.selectOptions(iRequest,layoutButton,new Criteria(layoutButton));
            List<PageLayoutButton> copyLayoutButtons = new ArrayList<>();
            for(PageLayoutButton button:layoutButtons){
                PageLayoutButton copyLayoutButton = new PageLayoutButton();
                copyLayoutButton.setButtonCode(button.getButtonCode());
                copyLayoutButton.setButtonDesc(button.getButtonDesc());
                copyLayoutButton.setButtonSequence(button.getButtonSequence());
                copyLayoutButton.setButtonType(button.getButtonType());
                copyLayoutButton.setClick(button.getClick());
                copyLayoutButton.setDisabled(button.getDisabled());
                copyLayoutButton.setHeight(button.getHeight());
                copyLayoutButton.setHidden(button.getHidden());
                copyLayoutButton.setIcon(button.getIcon());
                copyLayoutButton.setOperationType(button.getOperationType());
                copyLayoutButton.setTemplateFlag(button.getTemplateFlag());
                copyLayoutButton.setId(button.getId());
                copyLayoutButton.setText(button.getText());
                copyLayoutButton.setWidth(button.getWidth());
                copyLayoutButton.setLayoutId(copyPageLayout.getLayoutId());
                copyLayoutButton.set__status(DTOStatus.ADD);
                copyLayoutButtons.add(copyLayoutButton);
            }
            pageLayoutButtonService.batchUpdate(iRequest,copyLayoutButtons);

            //复制布局事件
            PageLayoutEvent layoutEvent = new PageLayoutEvent();
            layoutEvent.setLayoutId(layout.getLayoutId());
            List<PageLayoutEvent> layoutEvents = pageLayoutEventService.selectOptions(iRequest,layoutEvent,new Criteria(layoutEvent));
            for(PageLayoutEvent event:layoutEvents){
                PageLayoutEvent copyLayoutEvent = new PageLayoutEvent();
                copyLayoutEvent.setEventHandler(event.getEventHandler());
                copyLayoutEvent.setEventTarget(event.getEventTarget());
                copyLayoutEvent.setEventType(event.getEventType());
                copyLayoutEvent.setLayoutId(copyPageLayout.getLayoutId());
                pageLayoutEventService.insert(iRequest,copyLayoutEvent);
            }


            List<PageTag> pageTags = pageTagService.queryPageTag(iRequest,layout.getLayoutId());
            for(PageTag pageTag : pageTags){
                //复制tagbasic
                PageTagBasic copyPageTagBasic = pageTag.convertBasic();
                copyPageTagBasic.setLayoutId(copyPageLayout.getLayoutId());
                copyPageTagBasic = pageTagBasicService.insert(iRequest,copyPageTagBasic);

                //复制textfield
                PageTagTextfield tagTextfield = new PageTagTextfield();
                tagTextfield.setTagId(pageTag.getTagId());
                tagTextfield = pageTagTextfieldService.selectByPrimaryKey(iRequest,tagTextfield);
                if(tagTextfield!=null){
                    PageTagTextfield copyTagTextfield = new PageTagTextfield();
                    copyTagTextfield.setTypecase(tagTextfield.getTypecase());
                    copyTagTextfield.setTagId(copyPageTagBasic.getTagId());
                    pageTagTextfieldService.insert(iRequest,copyTagTextfield);
                }

                //复制checkBox
                PageTagCheckbox tagCheckbox = new PageTagCheckbox();
                tagCheckbox.setTagId(pageTag.getTagId());
                tagCheckbox = pageTagCheckboxService.selectByPrimaryKey(iRequest,tagCheckbox);
                if(tagCheckbox!=null){
                    PageTagCheckbox copyTagCheckbox = new PageTagCheckbox();
                    copyTagCheckbox.setCheckedvalue(tagCheckbox.getCheckedvalue());
                    copyTagCheckbox.setLabel(tagCheckbox.getLabel());
                    copyTagCheckbox.setUncheckedvalue(tagCheckbox.getUncheckedvalue());
                    copyTagCheckbox.setTagId(copyPageTagBasic.getTagId());
                    pageTagCheckboxService.insert(iRequest,copyTagCheckbox);
                }


                //复制combobox
                PageTagCombobox tagCombobox = new PageTagCombobox();
                tagCombobox.setTagId(pageTag.getTagId());
                tagCombobox = pageTagComboboxService.selectByPrimaryKey(iRequest,tagCombobox);
                if(tagCombobox!=null){
                    PageTagCombobox copyTagCombobox = new PageTagCombobox();
                    copyTagCombobox.setDatasource(tagCombobox.getDatasource());
                    copyTagCombobox.setLovcode(tagCombobox.getLovcode());
                    copyTagCombobox.setDisplayfield(tagCombobox.getDisplayfield());
                    copyTagCombobox.setOptions(tagCombobox.getOptions());
                    copyTagCombobox.setReturnfield(tagCombobox.getReturnfield());
                    copyTagCombobox.setSqltext(tagCombobox.getSqltext());
                    copyTagCombobox.setValuefield(tagCombobox.getValuefield());
                    copyTagCombobox.setSyscode(tagCombobox.getSyscode());
                    copyTagCombobox.setTagId(copyPageTagBasic.getTagId());
                    pageTagComboboxService.insert(iRequest,copyTagCombobox);

                    //combobox对应的mapper
                    PageTagComboboxMap tagComboboxMap = new PageTagComboboxMap();
                    tagComboboxMap.setTagId(pageTag.getTagId());
                    Criteria criteria = new Criteria(tagComboboxMap);
                    criteria.where(new WhereField(PageTagComboboxMap.FIELD_TAG_ID,Comparison.EQUAL));
                    List<PageTagComboboxMap> tagComboboxMaps =  pageTagComboboxMapService.selectOptions(iRequest,tagComboboxMap,criteria);
                    List<PageTagComboboxMap> copyTagComboboxMaps = new ArrayList<>();
                    for(PageTagComboboxMap pageTagComboboxMap:tagComboboxMaps){
                        PageTagComboboxMap copyPageTagComboboxMap = new PageTagComboboxMap();
                        copyPageTagComboboxMap.setFromField(pageTagComboboxMap.getFromField());
                        copyPageTagComboboxMap.setToField(pageTagComboboxMap.getToField());
                        copyPageTagComboboxMap.setMapSequence(pageTagComboboxMap.getMapSequence());
                        copyPageTagComboboxMap.setTagId(copyPageTagBasic.getTagId());
                        copyPageTagComboboxMap.set__status(DTOStatus.ADD);
                        copyTagComboboxMaps.add(copyPageTagComboboxMap);
                    }
                    pageTagComboboxMapService.batchUpdate(iRequest,copyTagComboboxMaps);

                    //combobox对应的field
                    PageTagComboboxField tagComboboxField = new PageTagComboboxField();
                    tagComboboxField.setTagId(pageTag.getTagId());
                    Criteria cbFieldCri = new Criteria(tagComboboxField);
                    cbFieldCri.where(new WhereField(PageTagComboboxMap.FIELD_TAG_ID,Comparison.EQUAL));
                    List<PageTagComboboxField> tagComboboxFields = pageTagComboboxFieldService.selectOptions(iRequest,tagComboboxField,cbFieldCri);
                    List<PageTagComboboxField> copyTagComboboxFields = new ArrayList<>();
                    for(PageTagComboboxField pageTagComboboxField:tagComboboxFields){
                        PageTagComboboxField copyPageTagComboboxField = new PageTagComboboxField();
                        copyPageTagComboboxField.setComboboxField(pageTagComboboxField.getComboboxField());
                        copyPageTagComboboxField.setComboboxFieldId(pageTagComboboxField.getComboboxFieldId());
                        copyPageTagComboboxField.setSortname(pageTagComboboxField.getSortname());
                        copyPageTagComboboxField.setSortorder(pageTagComboboxField.getSortorder());
                        copyPageTagComboboxField.setTagId(copyPageTagBasic.getTagId());
                        copyPageTagComboboxField.set__status(DTOStatus.ADD);
                        copyTagComboboxFields.add(copyPageTagComboboxField);
                    }
                    pageTagComboboxFieldService.batchUpdate(iRequest,copyTagComboboxFields);

                }



                //复制lov
                PageTagLov tagLov = new PageTagLov();
                tagLov.setTagId(pageTag.getTagId());
                tagLov = pageTagLovService.selectByPrimaryKey(iRequest,tagLov);
                if(tagLov!=null){
                    PageTagLov copyTagLov = new PageTagLov();
                    copyTagLov.setAutocomplete(tagLov.getAutocomplete());
                    copyTagLov.setAutocompletefield(tagLov.getAutocompletefield());
                    copyTagLov.setDatasource(tagLov.getDatasource());
                    copyTagLov.setLovautoquery(tagLov.getLovautoquery());
                    copyTagLov.setLovcode(tagLov.getLovcode());
                    copyTagLov.setLovgridheight(tagLov.getLovgridheight());
                    copyTagLov.setLovheight(tagLov.getLovheight());
                    copyTagLov.setLovlabelwidth(tagLov.getLovlabelwidth());
                    copyTagLov.setLovurl(tagLov.getLovurl());
                    copyTagLov.setTitle(tagLov.getTitle());
                    copyTagLov.setSqltext(tagLov.getSqltext());
                    copyTagLov.setSyscode(tagLov.getSyscode());
                    copyTagLov.setTagId(copyPageTagBasic.getTagId());
                    pageTagLovService.insert(iRequest,copyTagLov);

                    //lov对应的mapper
                    PageTagLovMap tagLovMap = new PageTagLovMap();
                    tagLovMap.setTagId(pageTag.getTagId());
                    List<PageTagLovMap> tagLovMaps= pageTagLovMapService.selectOptions(iRequest,tagLovMap,new Criteria(tagLovMap));
                    List<PageTagLovMap> copyTagLovMaps = new ArrayList<>();
                    for(PageTagLovMap lovMap:tagLovMaps){
                        PageTagLovMap copyTagLovMap = new PageTagLovMap();
                        copyTagLovMap.setFromField(lovMap.getFromField());
                        copyTagLovMap.setToField(lovMap.getToField());
                        copyTagLovMap.setMapSequence(lovMap.getMapSequence());
                        copyTagLovMap.setTagId(copyPageTagBasic.getTagId());
                        copyTagLovMap.set__status(DTOStatus.ADD);
                        copyTagLovMaps.add(copyTagLovMap);
                    }
                    pageTagLovMapService.batchUpdate(iRequest,copyTagLovMaps);

                }

                //复制radio
                PageTagRadio tagRadio = new PageTagRadio();
                tagRadio.setTagId(pageTag.getTagId());
                tagRadio = pageTagRadioService.selectByPrimaryKey(iRequest,tagRadio);
                if(tagRadio!=null){
                    PageTagRadio copyTagRadio = new PageTagRadio();
                    copyTagRadio.setDatasource(tagRadio.getDatasource());
                    copyTagRadio.setLabelexpression(tagRadio.getLabelexpression());
                    copyTagRadio.setLabelfield(tagRadio.getLabelfield());
                    copyTagRadio.setLayout(tagRadio.getLayout());
                    copyTagRadio.setOptions(tagRadio.getOptions());
                    copyTagRadio.setSqltext(tagRadio.getSqltext());
                    copyTagRadio.setSyscode(tagRadio.getSyscode());
                    copyTagRadio.setValuefield(tagRadio.getValuefield());
                    copyTagRadio.setTagId(copyPageTagBasic.getTagId());
                    pageTagRadioService.insert(iRequest,copyTagRadio);
                }

                //复制numberfield
                PageTagNumberfield tagNumberfield = new PageTagNumberfield();
                tagNumberfield.setTagId(pageTag.getTagId());
                tagNumberfield = pageTagNumberfieldService.selectByPrimaryKey(iRequest,tagNumberfield);
                if(tagNumberfield!=null){
                    PageTagNumberfield copyTagNumberfield = new PageTagNumberfield();
                    copyTagNumberfield.setAllowdecimals(tagNumberfield.getAllowdecimals());
                    copyTagNumberfield.setAllowformat(tagNumberfield.getAllowformat());
                    copyTagNumberfield.setAllownegative(tagNumberfield.getAllownegative());
                    copyTagNumberfield.setDecimalprecision(tagNumberfield.getDecimalprecision());
                    copyTagNumberfield.setTagId(copyPageTagBasic.getTagId());
                    pageTagNumberfieldService.insert(iRequest,copyTagNumberfield);

                }

                //复制datepicker
                PageTagDatepicker tagDatepicker = new PageTagDatepicker();
                tagDatepicker.setTagId(pageTag.getTagId());
                tagDatepicker = pageTagDatepickerService.selectByPrimaryKey(iRequest,tagDatepicker);
                if(tagDatepicker!=null){
                    PageTagDatepicker copyTagDatepicker = new PageTagDatepicker();
                    copyTagDatepicker.setDayrenderer(tagDatepicker.getDayrenderer());
                    copyTagDatepicker.setEnablebesidedays(tagDatepicker.getEnablebesidedays());
                    copyTagDatepicker.setEnablemonthbtn(tagDatepicker.getEnablemonthbtn());
                    copyTagDatepicker.setRenderer(tagDatepicker.getRenderer());
                    copyTagDatepicker.setViewsize(tagDatepicker.getViewsize());
                    copyTagDatepicker.setTagId(copyPageTagBasic.getTagId());
                    pageTagDatepickerService.insert(iRequest,copyTagDatepicker);
                }

                //复制datetimepicker
                PageTagDatetimepicker tagDatetimepicker = new PageTagDatetimepicker();
                tagDatetimepicker.setTagId(pageTag.getTagId());
                tagDatetimepicker = pageTagDatetimepickerService.selectByPrimaryKey(iRequest,tagDatetimepicker);
                if(tagDatetimepicker!=null){
                    PageTagDatetimepicker copyTagDatetimepicker = new PageTagDatetimepicker();
                    copyTagDatetimepicker.setDayrenderer(tagDatetimepicker.getDayrenderer());
                    copyTagDatetimepicker.setEnablebesidedays(tagDatetimepicker.getEnablebesidedays());
                    copyTagDatetimepicker.setEnablemonthbtn(tagDatetimepicker.getEnablemonthbtn());
                    copyTagDatetimepicker.setRenderer(tagDatetimepicker.getRenderer());
                    copyTagDatetimepicker.setViewsize(tagDatetimepicker.getViewsize());
                    copyTagDatetimepicker.setHour(tagDatetimepicker.getHour());
                    copyTagDatetimepicker.setMinute(tagDatetimepicker.getMinute());
                    copyTagDatetimepicker.setSecond(tagDatetimepicker.getSecond());
                    copyTagDatetimepicker.setTagId(copyPageTagBasic.getTagId());
                    pageTagDatetimepickerService.insert(iRequest,copyTagDatetimepicker);
                }

                //复制label
                PageTagLabel tagLabel = new PageTagLabel();
                tagLabel.setTagId(pageTag.getTagId());
                tagLabel = pageTagLabelService.selectByPrimaryKey(iRequest,tagLabel);
                if(tagLabel!=null){
                    PageTagLabel copyTagLabel = new PageTagLabel();
                    copyTagLabel.setRenderer(tagLabel.getRenderer());
                    copyTagLabel.setTagId(copyPageTagBasic.getTagId());
                    pageTagLabelService.insert(iRequest,copyTagLabel);
                }

            }


        }

        for(PageLayout layout : pageLayouts){
            PageLayoutBasic copyLayoutBasic = new PageLayoutBasic();
            copyLayoutBasic.setPageId(copyPage.getPageId());
            copyLayoutBasic.setLayoutCode(layout.getLayoutCode());
            Criteria layoutCri = new Criteria(copyLayoutBasic);
            layoutCri.where(new WhereField(PageLayoutBasic.FIELD_PAGE_ID,Comparison.EQUAL),new WhereField(PageLayoutBasic.FIELD_LAYOUT_CODE,Comparison.EQUAL));
            List<PageLayoutBasic> layoutBasics = pageLayoutBasicService.selectOptions(iRequest,copyLayoutBasic,layoutCri);
            if(!layoutBasics.isEmpty()){
                copyLayoutBasic = layoutBasics.get(0);
                List<PageTag> pageTags = pageTagService.queryPageTag(iRequest,layout.getLayoutId());
                for(PageTag tag : pageTags){
                    PageTagBasic copyTagBasic = new PageTagBasic();
                    copyTagBasic.setLayoutId(copyLayoutBasic.getLayoutId());
                    copyTagBasic.setTagCode(tag.getTagCode());
                    Criteria tagCri = new Criteria(copyTagBasic);
                    tagCri.where(new WhereField(PageTagBasic.FIELD_LAYOUT_ID,Comparison.EQUAL),new WhereField(PageTagBasic.FIELD_TAG_CODE,Comparison.EQUAL));
                    List<PageTagBasic> tagBasics = pageTagBasicService.selectOptions(iRequest,copyTagBasic,tagCri);
                    if(!tagBasics.isEmpty()){
                        copyTagBasic = tagBasics.get(0);
                        //数据向导复制
                        PageTagDataGuide dataGuide = new PageTagDataGuide();
                        dataGuide.setTagId(tag.getTagId());
                        List<PageTagDataGuide> dataGuides = pageTagDataGuideService.selectOptions(iRequest,dataGuide,new Criteria(dataGuide));
                        List<PageTagDataGuide> copyDataGuides = new ArrayList<>();
                        for(PageTagDataGuide pageTagDataGuide : dataGuides){
                            PageTagDataGuide copyDateGuide = new PageTagDataGuide();
                            copyDateGuide.setFormula(pageTagDataGuide.getFormula());
                            copyDateGuide.setClearFlag(pageTagDataGuide.getClearFlag());
                            copyDateGuide.setConditionValue(pageTagDataGuide.getConditionValue());
                            copyDateGuide.setFilterName(pageTagDataGuide.getFilterName());
                            copyDateGuide.setGuideSequence(pageTagDataGuide.getGuideSequence());
                            copyDateGuide.setCurrentLogicType(pageTagDataGuide.getCurrentLogicType());
                            copyDateGuide.setHiddenFlag(pageTagDataGuide.getHiddenFlag());
                            copyDateGuide.setReadonlyFlag(pageTagDataGuide.getReadonlyFlag());
                            copyDateGuide.setRequiredFlag(pageTagDataGuide.getRequiredFlag());
                            copyDateGuide.setTargetValue(pageTagDataGuide.getTargetValue());
                            copyDateGuide.setTriggerCondition(pageTagDataGuide.getTriggerCondition());
                            copyDateGuide.setTriggerType(pageTagDataGuide.getTriggerType());
                            copyDateGuide.setTagId(copyTagBasic.getTagId());
                            //有问题
                            if(pageTagDataGuide.getTargetLayoutId()!=null){
                                if(pageTagDataGuide.getTargetLayoutId() == layout.getLayoutId()){
                                    copyDateGuide.setTargetLayoutId(copyLayoutBasic.getLayoutId());
                                }else{
                                    PageLayoutBasic oldTargetLayout = new PageLayoutBasic();
                                    oldTargetLayout.setLayoutId(pageTagDataGuide.getTargetLayoutId());
                                    oldTargetLayout = pageLayoutBasicService.selectByPrimaryKey(iRequest,oldTargetLayout);
                                    PageLayoutBasic targetLayoutBasic = new PageLayoutBasic();
                                    targetLayoutBasic.setPageId(copyPage.getPageId());
                                    targetLayoutBasic.setLayoutCode(oldTargetLayout.getLayoutCode());
                                    Criteria cri = new Criteria(targetLayoutBasic);
                                    cri.where(new WhereField(PageLayoutBasic.FIELD_PAGE_ID,Comparison.EQUAL),new WhereField(PageLayoutBasic.FIELD_LAYOUT_CODE,Comparison.EQUAL));
                                    List<PageLayoutBasic> basics = pageLayoutBasicService.selectOptions(iRequest,targetLayoutBasic,cri);
                                    targetLayoutBasic = basics.get(0);
                                    copyDateGuide.setTargetLayoutId(targetLayoutBasic.getLayoutId());

                                }
                            }

                            if(pageTagDataGuide.getTargetTagId()!=null){
                                if(pageTagDataGuide.getTargetTagId() == tag.getTagId()){
                                    copyDateGuide.setTagId(copyTagBasic.getTagId());
                                }else{
                                    PageTagBasic oldTargetTag = new PageTagBasic();
                                    oldTargetTag.setTagId(pageTagDataGuide.getTagId());
                                    oldTargetTag = pageTagBasicService.selectByPrimaryKey(iRequest,oldTargetTag);
                                    PageTagBasic targetTag = new PageTagBasic();
                                    targetTag.setLayoutId(copyDateGuide.getTargetLayoutId());
                                    targetTag.setTagCode(oldTargetTag.getTagCode());
                                    Criteria criteria = new Criteria(targetTag);
                                    criteria.where(new WhereField(PageTagBasic.FIELD_LAYOUT_ID,Comparison.EQUAL),new WhereField(PageTagBasic.FIELD_TAG_CODE,Comparison.EQUAL));
                                    List<PageTagBasic> tagBasicList = pageTagBasicService.selectOptions(iRequest,targetTag,criteria);
                                    targetTag = tagBasicList.get(0);
                                    copyDateGuide.setTargetTagId(targetTag.getTagId());
                                }
                            }
                            copyDateGuide.set__status(DTOStatus.ADD);
                            copyDataGuides.add(copyDateGuide);
                        }
                        copyDataGuides = pageTagDataGuideService.batchUpdate(iRequest,copyDataGuides);
                        pageTagDataGuideService.generateDataGuideEvent(iRequest,copyTagBasic,copyDataGuides);

                    }

                }
            }

        }

    }
}