package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.mapper.TpltButtonMapper;
import com.hand.hec.bpm.dto.TpltButton;
import com.hand.hec.bpm.service.ITpltButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class TpltButtonServiceImpl extends BaseServiceImpl<TpltButton> implements ITpltButtonService {
    @Autowired
    TpltButtonMapper bpmTpltButtonsMapper;

    @Override
    public List<TpltButton> queryByTemplateId(IRequest request, TpltButton bpmTpltButtons) {
        return bpmTpltButtonsMapper.queryByTemplateId(bpmTpltButtons);
    }

    @Override
    public void batchSaves(IRequest request, List<TpltButton> bpmTpltButtonsList) {
        for (TpltButton bpmTpltButtons : bpmTpltButtonsList) {
            switch (bpmTpltButtons.get__status()) {
                case DTOStatus.ADD:
                    self().insertTpltButtons(request, bpmTpltButtons);
                    continue;
                case DTOStatus.UPDATE:
                    self().updateTpltButtons(request, bpmTpltButtons);
                    continue;
                case DTOStatus.DELETE:
                    self().deleteTpltButtons(request, bpmTpltButtons);
                    continue;
            }
        }
    }

    @Override
    public TpltButton insertTpltButtons(IRequest request, TpltButton bpmTpltButtons) {
        self().insert(request, bpmTpltButtons);

        //唯一性校验
        List<Map> mapList = bpmTpltButtonsMapper.checkTemplateId(bpmTpltButtons.getTemplateId());
        if (mapList.size() > 0) {
            throw new RuntimeException("模板按钮ID:#CODE已存在!");
        }

        return bpmTpltButtons;
    }

    @Override
    public TpltButton updateTpltButtons(IRequest request, TpltButton bpmTpltButtons) {
        self().updateByPrimaryKey(request, bpmTpltButtons);

        //唯一性校验
        List<Map> mapList = bpmTpltButtonsMapper.checkTemplateId(bpmTpltButtons.getTemplateId());
        if (mapList.size() > 0) {
            throw new RuntimeException("模板按钮ID:#CODE已存在!");
        }

        return bpmTpltButtons;
    }

    @Override
    public TpltButton deleteTpltButtons(IRequest request, TpltButton bpmTpltButtons) {
        self().deleteByPrimaryKey(bpmTpltButtons);
        return bpmTpltButtons;
    }
}