package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndCodingRuleDetail;
import com.hand.hec.fnd.dto.FndCodingRuleObject;
import com.hand.hec.fnd.mapper.FndCodingRuleDetailMapper;
import com.hand.hec.fnd.mapper.FndCodingRuleMapper;
import com.hand.hec.fnd.service.IFndCodingRuleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndCodingRule;
import com.hand.hec.fnd.service.IFndCodingRuleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndCodingRuleServiceImpl extends BaseServiceImpl<FndCodingRule> implements IFndCodingRuleService{

    @Autowired
    FndCodingRuleMapper mapper;

    @Autowired
    FndCodingRuleDetailMapper detailMapper;

    @Autowired
    IFndCodingRuleDetailService detailService;


    @Override
    public List<FndCodingRule> queryByCodingRuleObjectId(IRequest request, FndCodingRule dto, int page, int pageSize){
        return mapper.queryByCodingRuleObjectId(dto);
    }

    /**
     * 删除编码规则之前先删除编码规则明细
     * @param dto
     * @return
     */
    @Override
    public int deleteByPrimaryKey(FndCodingRule dto){
        FndCodingRuleDetail fndCodingRuleDetail = new FndCodingRuleDetail();
        fndCodingRuleDetail.setCodingRuleId(dto.getCodingRuleId());
        List<FndCodingRuleDetail> detailList = detailMapper.select(fndCodingRuleDetail);
        if(detailList!=null && detailList.size()!=0){
            for (FndCodingRuleDetail detail : detailList){
                detailService.deleteByPrimaryKey(detail);
            }
        }
        return super.deleteByPrimaryKey(dto);
    }

}