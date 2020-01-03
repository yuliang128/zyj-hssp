package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.*;
import com.hand.hec.bpm.mapper.PageLayoutBasicMapper;
import com.hand.hec.bpm.mapper.PageLayoutMapper;
import com.hand.hec.bpm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageLayoutBasicServiceImpl extends BaseServiceImpl<PageLayoutBasic> implements IPageLayoutBasicService {
    @Autowired
    PageLayoutBasicMapper pageLayoutBasicMapper;
    @Autowired
    PageLayoutMapper pageLayoutMapper;
    @Autowired
    IPageLayoutFormService pageLayoutFormService;
    @Autowired
    IPageLayoutGridService pageLayoutGridService;
    @Autowired
    IPageLayoutTabService pageLayoutTabService;
    /*@Autowired
    IPageTagBasicService pageTagsBasicService;
    @Autowired
    IPageLayoutButtonService pageLayoutButtonsService;*/

    @Autowired
    IPageLayoutButtonService pageLayoutButtonService;

    @Autowired
    IPageTagService pageTagService;


    @Override
    public List<PageLayout> queryByPageId(IRequest request, PageLayout layout) {
        return pageLayoutMapper.queryByPageId(layout);
    }

    @Override
    public PageLayout insertBasic(IRequest request, PageLayout layout) {
        PageLayoutBasic basic = new PageLayoutBasic();
        basic.setLayoutSequence(layout.getLayoutSequence());
        basic.setPageId(layout.getPageId());

        int size = pageLayoutBasicMapper.select(basic).size();
        //同一模板下的序列必须唯一
        if (size > 0) {
            throw new RuntimeException("序号不能重复!");
        }

        //实例化bpm_page_layout_basic
        basic = layout.convertBasic();

        //插入数据
        basic = self().insert(request, basic);
        layout.setLayoutId(basic.getLayoutId());

        //实例化form，grid，tab
        PageLayoutForm form = layout.convertForm();
        form.setLayoutId(basic.getLayoutId());
        pageLayoutFormService.insert(request, form);

        PageLayoutGrid grid = layout.convertGrid();
        grid.setLayoutId(basic.getLayoutId());
        pageLayoutGridService.insert(request, grid);

        PageLayoutTab tab = layout.convertTab();
        tab.setLayoutId(basic.getLayoutId());
        pageLayoutTabService.insert(request, tab);

        return layout;
    }

    @Override
    public PageLayout updateBasic(IRequest request, PageLayout layout) {

        PageLayoutBasic basic = new PageLayoutBasic();
        basic.setPageId(layout.getPageId());
        basic.setLayoutSequence(layout.getLayoutSequence());
        basic.setLayoutId(layout.getLayoutId());

        self().updateByPrimaryKey(request, layout.convertBasic());

        //实例化form，grid，tab
        pageLayoutFormService.updateByPrimaryKey(request, layout.convertForm());
        pageLayoutGridService.updateByPrimaryKey(request, layout.convertGrid());
        pageLayoutTabService.updateByPrimaryKey(request, layout.convertTab());

        return layout;

    }

    @Override
    public PageLayout deleteBasic(IRequest request, PageLayout layout) {
        PageLayoutBasic basic = layout.convertBasic();
        PageLayoutForm form = layout.convertForm();
        PageLayoutGrid grid = layout.convertGrid();
        PageLayoutTab tab = layout.convertTab();

        //开始删除
        pageLayoutBasicMapper.deleteByPrimaryKey(basic);
        pageLayoutFormService.deleteByPrimaryKey(form);
        pageLayoutGridService.deleteByPrimaryKey(grid);
        pageLayoutTabService.deleteByPrimaryKey(tab);


        //删除按钮
        PageLayoutButton queryLayoutButton = new PageLayoutButton();
        queryLayoutButton.setLayoutId(layout.getLayoutId());
        List<PageLayoutButton> layoutButtonList = pageLayoutButtonService.select(request, queryLayoutButton, 0, 0);
        pageLayoutButtonService.batchDelete(layoutButtonList);


        //删除标签
        List<PageTag> tagList = pageTagService.queryPageTag(request, layout.getLayoutId());
        for (PageTag tag : tagList) {
            pageTagService.deletePageTag(request, tag);
        }


        return layout;
    }

    @Override
    public List<PageLayout> batchUpdateLayout(IRequest request, List<PageLayout> layoutList) {
        for (PageLayout layout : layoutList) {
            switch (layout.get__status()) {
                case DTOStatus.ADD:
                    self().insertBasic(request, layout);
                    break;
                case DTOStatus.UPDATE:
                    self().updateBasic(request, layout);
                    break;
                case DTOStatus.DELETE:
                    self().deleteBasic(request, layout);
                    break;
                default:
                    break;
            }
        }
        return layoutList;
    }
}