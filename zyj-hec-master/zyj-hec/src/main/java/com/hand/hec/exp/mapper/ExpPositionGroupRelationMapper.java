package com.hand.hec.exp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpPositionGroupRelation;

/**
 * <p>
 * 岗位分配岗位组Mapper接口
 * </p>
 *
 * @author YHL 2019/01/18 17:01
 */
public interface ExpPositionGroupRelationMapper extends Mapper<ExpPositionGroupRelation> {

    /**
     * 查询岗位组中分配的岗位及公司信息
     *
     * @param positionGroupId 岗位组ID
     * @author YHL 2019-01-18 16:58
     * @return 岗位组中分配的岗位及公司信息
     */
    List<ExpPositionGroupRelation> getPositionGroupRelationByPositionGroupId(
            @Param("positionGroupId") Long positionGroupId);
}