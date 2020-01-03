package com.hand.hec.bgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtScenario;
import com.hand.hec.bgt.mapper.BgtScenarioMapper;
import com.hand.hec.bgt.service.IBgtScenarioService;

/**
 * <p>
 * 预算场景ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtScenarioServiceImpl extends BaseServiceImpl<BgtScenario> implements IBgtScenarioService {

    @Autowired
    private BgtScenarioMapper mapper;

    @Override
    public List<BgtScenario> checkBgtScenario(String controlType, String filtrateMethod, String scenarioCodeFrom,
            String scenarioCodeTo) {
        return mapper.checkBgtScenario(controlType, filtrateMethod, scenarioCodeFrom, scenarioCodeTo);
    }

    @Override
    public BgtScenario getDefaultBgtScenarioByBgtOrgId(IRequest request, Long bgtOrgId) {
        BgtScenario bgtScenario = new BgtScenario();
        bgtScenario.setBgtOrgId(bgtOrgId);
        bgtScenario.setEnabledFlag("Y");
        bgtScenario.setDefaultFlag("Y");
        Criteria criteria = new Criteria(bgtScenario);
        criteria.where(new WhereField(BgtScenario.FIELD_BGT_ORG_ID), new WhereField(BgtScenario.FIELD_ENABLED_FLAG),
                new WhereField(BgtScenario.FIELD_DEFAULT_FLAG));
        List<BgtScenario> scenarioList = selectOptions(request, bgtScenario, criteria);
        if (scenarioList != null && !scenarioList.isEmpty()) {
            return scenarioList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<BgtScenario> getBgtScenarioOption(IRequest request, Long bgtOrgId) {
        BgtScenario bgtScenario = new BgtScenario();
        bgtScenario.setBgtOrgId(bgtOrgId);
        bgtScenario.setEnabledFlag("Y");
        Criteria criteria = new Criteria(bgtScenario);
        criteria.where(new WhereField(BgtScenario.FIELD_BGT_ORG_ID), new WhereField(BgtScenario.FIELD_ENABLED_FLAG));
        return selectOptions(request, bgtScenario, criteria);
    }
}