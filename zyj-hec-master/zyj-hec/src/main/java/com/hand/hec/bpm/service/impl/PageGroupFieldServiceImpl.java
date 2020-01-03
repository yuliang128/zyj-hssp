package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageGroupField;
import com.hand.hec.bpm.service.IPageGroupFieldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageGroupFieldServiceImpl extends BaseServiceImpl<PageGroupField> implements IPageGroupFieldService {

    /*@Autowired
    BpmPageGroupFieldsMapper bpmPageGroupFieldsMapper;
    @Autowired
    BpmPageTagsBasicMapper bpmPageTagsBasicMapper;
    @Autowired
    BpmPageTagDataGuideMapper bpmPageTagDataGuideMapper;
    @Autowired
    BpmPageLayoutBasicMapper bpmPageLayoutBasicMapper;
    @Autowired
    BpmPagesMapper bpmPagesMapper;

    @Autowired
    IBpmPageTagsBasicService iBpmPageTagsBasicService;
    @Autowired
    IBpmPageTagsComboboxService iBpmPageTagsComboboxService;
    @Autowired
    IBpmPageTagsComboboxMappersService iBpmPageTagsComboboxMappersService;
    @Autowired
    IBpmPageTagsLovMappersService iBpmPageTagsLovMappersService;*/

    /*@Override
    public void insert(CompositeMap context, BpmPageGroupFields bpmPageGroupFields){
        BpmPageGroupFields queryBpmPageGroupFields = new BpmPageGroupFields();
        queryBpmPageGroupFields.setGroupId(bpmPageGroupFields.getGroupId());
        queryBpmPageGroupFields.setOrderNum(bpmPageGroupFields.getOrderNum());
        List<BpmPageGroupFields> bpmPageGroupFieldsList = bpmPageGroupFieldsMapper.queryByParas(queryBpmPageGroupFields);

        if (bpmPageGroupFieldsList.size() == 1){
            throw new RuntimeException("序号不能重复!");
        }

        queryBpmPageGroupFields = new BpmPageGroupFields();
        queryBpmPageGroupFields.setGroupId(bpmPageGroupFields.getGroupId());
        queryBpmPageGroupFields.setName(bpmPageGroupFields.getName());
        bpmPageGroupFieldsList = bpmPageGroupFieldsMapper.queryByParas(queryBpmPageGroupFields);
        if (bpmPageGroupFieldsList.size() == 1){
            throw new RuntimeException("该物理字段已被使用!");
        }

        Long fieldId = (Long) iPrimaryKeyProvider.getPrimaryKeyValue("select nextval('bpm_page_group_fields_s')",Long.class);
        bpmPageGroupFields.setFieldId(fieldId);
        bpmPageGroupFieldsMapper.insertPageGroupFields(bpmPageGroupFields);
    }

    @Override
    public void update(CompositeMap context, BpmPageGroupFields bpmPageGroupFields){
        BpmPageGroupFields queryBpmPageGroupFields = new BpmPageGroupFields();
        queryBpmPageGroupFields.setGroupId(bpmPageGroupFields.getGroupId());
        queryBpmPageGroupFields.setOrderNum(bpmPageGroupFields.getOrderNum());
        List<BpmPageGroupFields> bpmPageGroupFieldsList = bpmPageGroupFieldsMapper.queryByParas(queryBpmPageGroupFields);

        if (bpmPageGroupFieldsList.size() == 1){
            throw new RuntimeException("序号不能重复!");
        }

        queryBpmPageGroupFields = new BpmPageGroupFields();
        queryBpmPageGroupFields.setGroupId(bpmPageGroupFields.getGroupId());
        queryBpmPageGroupFields.setName(bpmPageGroupFields.getName());
        bpmPageGroupFieldsList = bpmPageGroupFieldsMapper.queryByParas(queryBpmPageGroupFields);
        if (bpmPageGroupFieldsList.size() == 1){
            throw new RuntimeException("该物理字段已被使用!");
        }

        //获取就得bpm_page_group_fields
        BpmPageGroupFields oldBpmPageGroupFields = new BpmPageGroupFields();
        oldBpmPageGroupFields.setFieldId(bpmPageGroupFields.getFieldId());
        oldBpmPageGroupFields = self().selectByPrimaryKey(context,bpmPageGroupFields);

        BpmPageTagsBasic bpmPageTagsBasic = new BpmPageTagsBasic();
        if(!bpmPageGroupFields.getDesc().equals(oldBpmPageGroupFields.getDesc())){
            bpmPageTagsBasic.setTagDesc(bpmPageGroupFields.getDesc());
            bpmPageTagsBasic.setFieldId(bpmPageGroupFields.getFieldId());
            iBpmPageTagsBasicService.updateSelective(context,bpmPageTagsBasic);
        }

        if(!bpmPageGroupFields.getName().equals(oldBpmPageGroupFields.getName())){
            bpmPageTagsBasic = new BpmPageTagsBasic();
            bpmPageTagsBasic.setName(bpmPageGroupFields.getName());
            bpmPageTagsBasic.setFieldId(bpmPageGroupFields.getFieldId());
            iBpmPageTagsBasicService.updateSelective(context,bpmPageTagsBasic);

            //bpm_page_tags_combobox
            iBpmPageTagsComboboxService.updateReturnfield(context,bpmPageGroupFields,oldBpmPageGroupFields.getName());
            //bpm_page_tags_combobox_mapper
            iBpmPageTagsComboboxMappersService.updateToField(context,bpmPageGroupFields,oldBpmPageGroupFields.getName());
            //bpm_page_tags_lov_mapper
            iBpmPageTagsLovMappersService.updateToField(context,bpmPageGroupFields,oldBpmPageGroupFields.getName());
        }

        if(bpmPageGroupFields.getLogicType() == null){
            self().updatePageGroupFields(context,bpmPageGroupFields);
        }else{
            if(bpmPageGroupFields.getLogicType() == oldBpmPageGroupFields.getLogicType()){
                queryBpmPageGroupFields = new BpmPageGroupFields();
                queryBpmPageGroupFields.setLogicBelongFieldId(oldBpmPageGroupFields.getLogicBelongFieldId());
                List<BpmPageGroupFields> queryBpmPageGroupFieldsList = bpmPageGroupFieldsMapper.queryByParas(queryBpmPageGroupFields);

                if (queryBpmPageGroupFieldsList.size() > 0){
                    for (BpmPageGroupFields pageGroupFields : queryBpmPageGroupFieldsList){
                        BpmPageTagsBasic queryBpmPageTagsBasic = new BpmPageTagsBasic();
                        queryBpmPageTagsBasic.setFieldId(pageGroupFields.getFieldId());
                        List<BpmPageTagsBasic> queryBpmPageTagsBasicList = bpmPageTagsBasicMapper.queryByParas(queryBpmPageTagsBasic);

                        if (queryBpmPageTagsBasicList.size() > 0){
                            for (BpmPageTagsBasic pageTagsBasic : queryBpmPageTagsBasicList){
                                //获取
                                queryBpmPageGroupFields = new BpmPageGroupFields();
                                queryBpmPageGroupFields.setLogicBelongFieldId(oldBpmPageGroupFields.getLogicBelongFieldId());
                                queryBpmPageGroupFields.setEnabledFlag("Y");
                                queryBpmPageGroupFields.setLogicType(oldBpmPageGroupFields.getLogicType());

                                queryBpmPageGroupFieldsList = bpmPageGroupFieldsMapper.queryByParas(queryBpmPageGroupFields);

                                if(bpmPageGroupFields.getEnabledFlag().equals("N") || !bpmPageGroupFields.getLogicBelongFieldId().equals(oldBpmPageGroupFields.getLogicBelongFieldId())){
                                    //获取报错信息
                                    BpmPageTagDataGuide queryBpmPageTagDataGuide = new BpmPageTagDataGuide();
                                    queryBpmPageTagDataGuide.setTagId(pageTagsBasic.getTagId());
                                    queryBpmPageTagDataGuide.setCurrentLogicType(oldBpmPageGroupFields.getLogicType());
                                    List<BpmPageTagDataGuide> queryBpmPageTagDataGuideList = bpmPageTagDataGuideMapper.queryByParas(queryBpmPageTagDataGuide);

                                    if(queryBpmPageGroupFieldsList.size() > 2 && queryBpmPageTagDataGuideList.size() > 0){
                                        for (BpmPageTagDataGuide bpmPageTagDataGuide : queryBpmPageTagDataGuideList){
                                            //bpm_page_layout_basic
                                            BpmPageLayoutBasic bpmPageLayoutBasic = new BpmPageLayoutBasic();
                                            bpmPageLayoutBasic.setLayoutId(pageTagsBasic.getLayoutId());
                                            bpmPageLayoutBasic = bpmPageLayoutBasicMapper.selectByPrimaryKey(bpmPageLayoutBasic);

                                            //bpm_pages
                                            BpmPages bpmPages = new BpmPages();
                                            bpmPages.setPageId(bpmPageLayoutBasic.getPageId());
                                            bpmPages = bpmPagesMapper.selectByPrimaryKey(bpmPages);

                                            throw new RuntimeException("页面“" + bpmPages.getPageDesc() + "”中的“" + bpmPageLayoutBasic.getLayoutDesc() + "”组件中的“" + bpmPageTagsBasic.getTagDesc() + "”标签中序号为“" + bpmPageTagDataGuide.getGuideSequence() + "”的数据关系向导引用了此字段的逻辑类型!");
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

            }

            else{
                queryBpmPageGroupFields = new BpmPageGroupFields();
                queryBpmPageGroupFields.setLogicBelongFieldId(oldBpmPageGroupFields.getLogicBelongFieldId());
                List<BpmPageGroupFields> queryBpmPageGroupFieldsList = bpmPageGroupFieldsMapper.queryByParas(queryBpmPageGroupFields);

                if (queryBpmPageGroupFieldsList.size() > 0){
                    for (BpmPageGroupFields pageGroupFields : queryBpmPageGroupFieldsList){
                        BpmPageTagsBasic queryBpmPageTagsBasic = new BpmPageTagsBasic();
                        queryBpmPageTagsBasic.setFieldId(pageGroupFields.getFieldId());
                        List<BpmPageTagsBasic> queryBpmPageTagsBasicList = bpmPageTagsBasicMapper.queryByParas(queryBpmPageTagsBasic);

                        if (queryBpmPageTagsBasicList.size() > 0){
                            for (BpmPageTagsBasic pageTagsBasic : queryBpmPageTagsBasicList){
                                //获取
                                queryBpmPageGroupFields = new BpmPageGroupFields();
                                queryBpmPageGroupFields.setLogicBelongFieldId(oldBpmPageGroupFields.getLogicBelongFieldId());
                                queryBpmPageGroupFields.setEnabledFlag("Y");
                                queryBpmPageGroupFields.setLogicType(oldBpmPageGroupFields.getLogicType());

                                queryBpmPageGroupFieldsList = bpmPageGroupFieldsMapper.queryByParas(queryBpmPageGroupFields);

                                if(bpmPageGroupFields.getEnabledFlag().equals("N") || !bpmPageGroupFields.getLogicBelongFieldId().equals(oldBpmPageGroupFields.getLogicBelongFieldId())){
                                    //获取报错信息
                                    BpmPageTagDataGuide queryBpmPageTagDataGuide = new BpmPageTagDataGuide();
                                    queryBpmPageTagDataGuide.setTagId(pageTagsBasic.getTagId());
                                    queryBpmPageTagDataGuide.setCurrentLogicType(oldBpmPageGroupFields.getLogicType());
                                    List<BpmPageTagDataGuide> queryBpmPageTagDataGuideList = bpmPageTagDataGuideMapper.queryByParas(queryBpmPageTagDataGuide);

                                    if(queryBpmPageGroupFieldsList.size() > 2 && queryBpmPageTagDataGuideList.size() > 0){
                                        for (BpmPageTagDataGuide bpmPageTagDataGuide : queryBpmPageTagDataGuideList){
                                            //bpm_page_layout_basic
                                            BpmPageLayoutBasic bpmPageLayoutBasic = new BpmPageLayoutBasic();
                                            bpmPageLayoutBasic.setLayoutId(pageTagsBasic.getLayoutId());
                                            bpmPageLayoutBasic = bpmPageLayoutBasicMapper.selectByPrimaryKey(bpmPageLayoutBasic);

                                            //bpm_pages
                                            BpmPages bpmPages = new BpmPages();
                                            bpmPages.setPageId(bpmPageLayoutBasic.getPageId());
                                            bpmPages = bpmPagesMapper.selectByPrimaryKey(bpmPages);

                                            throw new RuntimeException("页面“" + bpmPages.getPageDesc() + "”中的“" + bpmPageLayoutBasic.getLayoutDesc() + "”组件中的“" + bpmPageTagsBasic.getTagDesc() + "”标签中序号为“" + bpmPageTagDataGuide.getGuideSequence() + "”的数据关系向导引用了此字段的逻辑类型!");
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        self().updatePageGroupFields(context,bpmPageGroupFields);

    }*/

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}