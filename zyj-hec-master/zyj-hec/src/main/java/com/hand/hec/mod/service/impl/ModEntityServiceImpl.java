package com.hand.hec.mod.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.mod.dto.ModEntity;
import com.hand.hec.mod.service.IModEntityService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ModEntityServiceImpl extends BaseServiceImpl<ModEntity> implements IModEntityService {

    /**
     * 重写批量保存逻辑，实现以下需求 1.父子关系保存 2.父子级联删除
     * 
     * @param request
     * @param list
     * @author mouse 2019-06-05 15:21
     * @return java.util.List<com.hand.hec.mod.dto.ModEntity>
     */
    @Override
    public List<ModEntity> batchUpdate(IRequest request, List<ModEntity> list) {

        /**
         * 判断当前是删除还是更新， 如果是一条记录，且状态为删除的话，单独执行删除过程
         *
         */
        if (list.size() == 1 && DTOStatus.DELETE.equals(list.get(0).get__status())) {
            ModEntity entity = list.get(0);
            this.deleteByPrimaryKey(entity);
        } else {
            int currentLevel = 1;
            List<ModEntity> currentLevelList = null;
            List<ModEntity> parentLevelList = null;
            Boolean currentLevelFlag = false;
            while (true) {
                //
                // 找到当前层级的entity，执行更新操作，如果当前层级没有entity，那么退出循环
                // 如果当前entity有值，那么找到当前entity中，parentEntityId为空，但是parentEntityCode不为空，则循环找到parentEntity，并设置ID
                // ------------------------------------------------------------------------------
                int currentCount = 0;
                parentLevelList = new ArrayList<ModEntity>();
                if (currentLevelList != null) {
                    parentLevelList.addAll(currentLevelList);
                }
                currentLevelList = new ArrayList<ModEntity>();

                for (ModEntity entity : list) {
                    /**
                     * 每个实体循环的时候都将currentLevelFlag设置为false，如果当前实体的parentEntityCode在parentLevelList中，说明是当前level
                     */
                    currentLevelFlag = false;
                    /**
                     * 如果当前有父实体信息，则寻找父实体，否则直接添加到currentLevelList，作为根节点
                     */
                    if (currentLevel != 1) {
                        if (entity.getParentEntityCode() != null) {

                            for (ModEntity parentEntity : parentLevelList) {
                                if (parentEntity.getEntityCode().equals(entity.getParentEntityCode())) {
                                    entity.setParentEntityId(parentEntity.getEntityId());
                                    currentLevelFlag = true;
                                    break;
                                }
                            }

                            if (currentLevelFlag && entity.getParentEntityId() != null) {
                                currentLevelList.add(entity);
                                currentCount++;
                            }
                        }
                    } else if (currentLevel == 1 && entity.getParentEntityCode() == null) {
                        currentLevelList.add(entity);
                        currentCount++;
                    }
                }

                if (currentCount == 0) {
                    break;
                }

                currentLevelList = super.batchUpdate(request, currentLevelList);
                currentLevel++;
            }
        }

        return list;
    }

    @Override
    public int deleteByPrimaryKey(ModEntity record) {
        deleteChildEntity(record);
        return 1;
    }


    private void deleteChildEntity(ModEntity parentEntity) {
        //
        // 递归删除子实体
        // ------------------------------------------------------------------------------
        ModEntity queryEntity = new ModEntity();
        queryEntity.setParentEntityId(parentEntity.getEntityId());
        List<ModEntity> childEntities = this.select(this.getRequest(), queryEntity, 0, 0);

        for (ModEntity childEntity : childEntities) {
            deleteChildEntity(childEntity);
        }

        super.deleteByPrimaryKey(parentEntity);
    }
}
