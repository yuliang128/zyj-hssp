package com.hand.hec.bpm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageGroup;
import com.hand.hec.bpm.mapper.PageGroupMapper;
import com.hand.hec.bpm.service.IPageGroupService;
import com.hand.hec.bpm.service.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageGroupServiceImpl extends BaseServiceImpl<PageGroup> implements IPageGroupService {

    @Autowired
    PageGroupMapper pageGroupMapper;

    @Autowired
    ITemplateService templateService;
//    @Autowired
//    PageService pageService;

    @Override
    public List<PageGroup> select(IRequest request, PageGroup condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return pageGroupMapper.queryByCondition(condition);
    }

//    @Override
//    public void setTemplateChangeGroup(CompositeMap context, Long groupId) {
//        BpmPages queryBpmPages = new BpmPages();
//        queryBpmPages.setGroupId(groupId);
//        List<BpmPages> queryBpmPagesList = pageService.select(context,queryBpmPages);
//
//        //获取bpmpagegroup
//        PageGroup bpmPageGroups = new PageGroup();
//        bpmPageGroups.setGroupId(groupId);
//        bpmPageGroups = self().selectByPrimaryKey(context,bpmPageGroups);
//
//        for(BpmPages bpmPages : queryBpmPagesList){
//            templateService.changeBpmPageButtons(context,bpmPageGroups.getTemplateId(),bpmPages.getPageId());
//            templateService.changeBpmPageLayoutBasic(context,bpmPageGroups.getTemplateId(),bpmPages.getPageId());
//        }
//    }
//
//    @Override
//    public List<Map> queryPageGroupEditor(CompositeMap context, Long pageId) {
//        return bpmPageGroupsMapper.queryPageGroupEditor(pageId);
//    }
}