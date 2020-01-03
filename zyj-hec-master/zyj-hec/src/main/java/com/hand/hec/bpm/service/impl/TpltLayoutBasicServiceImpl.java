package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.*;
import com.hand.hec.bpm.mapper.TpltLayoutBasicMapper;
import com.hand.hec.bpm.mapper.TpltLayoutMapper;
import com.hand.hec.bpm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TpltLayoutBasicServiceImpl extends BaseServiceImpl<TpltLayoutBasic> implements ITpltLayoutBasicService {
    @Autowired
    TpltLayoutBasicMapper tpltLayoutBasicMapper;
    @Autowired
    TpltLayoutMapper tpltLayoutMapper;
    @Autowired
    ITpltLayoutFormService tpltLayoutFormService;
    @Autowired
    ITpltLayoutGridService tpltLayoutGridService;
    @Autowired
    ITpltLayoutTabService tpltLayoutTabService;
    @Autowired
    ITpltTagBasicService tpltTagsBasicService;
    @Autowired
    ITpltLayoutButtonService tpltLayoutButtonsService;


    @Override
    public List<TpltLayout> queryByTemplateId(IRequest request, TpltLayout layout) {
        return tpltLayoutMapper.queryByTemplateId(layout);
    }

    @Override
    public TpltLayout insertBasic(IRequest request, TpltLayout layout) {
        TpltLayoutBasic basic = new TpltLayoutBasic();
        basic.setLayoutSequence(layout.getLayoutSequence());
        basic.setTemplateId(layout.getTemplateId());

        int size = tpltLayoutBasicMapper.select(basic).size();
        //同一模板下的序列必须唯一
        if (size > 0) {
            throw new RuntimeException("序号不能重复!");
        }

        //实例化bpm_tplt_layout_basic
        basic = layout.convertBasic();

        //插入数据
        basic = self().insert(request, basic);

        //实例化form，grid，tab
        TpltLayoutForm form = layout.convertForm();
        form.setLayoutId(basic.getLayoutId());
        tpltLayoutFormService.insert(request, form);

        TpltLayoutGrid grid = layout.convertGrid();
        grid.setLayoutId(basic.getLayoutId());
        tpltLayoutGridService.insert(request, grid);

        TpltLayoutTab tab = layout.convertTab();
        tab.setLayoutId(basic.getLayoutId());
        tpltLayoutTabService.insert(request, tab);

        return layout;
    }

    @Override
    public TpltLayout updateBasic(IRequest request, TpltLayout layout) {

        TpltLayoutBasic basic = new TpltLayoutBasic();
        basic.setTemplateId(layout.getTemplateId());
        basic.setLayoutSequence(layout.getLayoutSequence());
        basic.setLayoutId(layout.getLayoutId());

        self().updateByPrimaryKey(request, layout.convertBasic());

        //实例化form，grid，tab
        tpltLayoutFormService.updateByPrimaryKey(request, layout.convertForm());
        tpltLayoutGridService.updateByPrimaryKey(request, layout.convertGrid());
        tpltLayoutTabService.updateByPrimaryKey(request, layout.convertTab());

        return layout;

    }

    @Override
    public TpltLayout deleteBasic(IRequest request, TpltLayout layout) {
        TpltLayoutBasic basic = layout.convertBasic();
        TpltLayoutForm form = layout.convertForm();
        TpltLayoutGrid grid = layout.convertGrid();
        TpltLayoutTab tab = layout.convertTab();

        TpltTagBasic tag = new TpltTagBasic();
        tag.setLayoutId(layout.getLayoutId());
        List<TpltTagBasic> bpmTpltTagsBasicList = tpltTagsBasicService.select(request, tag, 0, 0);

        TpltLayoutButton button = new TpltLayoutButton();
        button.setLayoutId(layout.getLayoutId());
        List<TpltLayoutButton> bpmTpltLayoutButtonsList = tpltLayoutButtonsService.select(request, button, 0, 0);

        //开始删除
        tpltLayoutBasicMapper.deleteByPrimaryKey(basic);
        tpltLayoutFormService.deleteByPrimaryKey(form);
        tpltLayoutGridService.deleteByPrimaryKey(grid);
        tpltLayoutTabService.deleteByPrimaryKey(tab);
        tpltTagsBasicService.batchDelete(bpmTpltTagsBasicList);
        tpltLayoutButtonsService.batchDelete(bpmTpltLayoutButtonsList);

        return layout;
    }

    @Override
    public List<TpltLayout> batchUpdateLayout(IRequest request, List<TpltLayout> layoutList) {
        for (TpltLayout layout : layoutList) {
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