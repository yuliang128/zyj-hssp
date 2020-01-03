package com.hand.hec.exp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpPositionGroupRelation;
import com.hand.hec.exp.mapper.ExpPositionGroupRelationMapper;
import com.hand.hec.exp.service.IExpPositionGroupRelationService;

/**
 * <p>
 * 岗位分配岗位组ServiceImpl
 * </p>
 *
 * @author YHL 2019/01/18 17:02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpPositionGroupRelationServiceImpl extends BaseServiceImpl<ExpPositionGroupRelation>
        implements IExpPositionGroupRelationService {

    @Autowired
    private ExpPositionGroupRelationMapper positionGroupRelationMapper;

    @Override
    public List<ExpPositionGroupRelation> getPositionGroupRelationByPositionGroupId(IRequest request,
            Long positionGroupId, int pageNum, int pageSize) {
        return positionGroupRelationMapper.getPositionGroupRelationByPositionGroupId(positionGroupId);
    }

}