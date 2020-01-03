package com.hand.hec.bpm.service.impl;

import java.util.List;

import com.hand.hec.bpm.dto.PageGroupEntity;
import com.hand.hec.bpm.service.IPageGroupEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageGroup;
import com.hand.hec.bpm.mapper.PageGroupMapper;
import com.hand.hec.bpm.service.IPageGroupService;
import com.hand.hec.bpm.service.ITemplateService;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageGroupEntityServiceImpl extends BaseServiceImpl<PageGroupEntity> implements IPageGroupEntityService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(PageGroupEntity record) {
        deleteChildEntity(record);
        return 1;
    }

    private void deleteChildEntity(PageGroupEntity parentEntity) {
        //
        // 递归删除子实体
        // ------------------------------------------------------------------------------
        PageGroupEntity queryEntity = new PageGroupEntity();
        queryEntity.setParentEntityId(parentEntity.getEntityId());
        List<PageGroupEntity> childEntities = this.select(this.getRequest(), queryEntity, 0, 0);

        for (PageGroupEntity childEntity : childEntities) {
            deleteChildEntity(childEntity);
        }

        super.deleteByPrimaryKey(parentEntity);
    }
}
