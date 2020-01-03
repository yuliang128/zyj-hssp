package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtControlStrategyGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 预算控制策略组Mapper
 * </p>
 * 
 * @author mouse 2019/01/07 16:47
 */
public interface BgtControlStrategyGroupMapper extends Mapper<BgtControlStrategyGroup> {

    /**
     * 根据预算组织ID查询启用状态的控制策略组
     *
     * @param bgtOrgId 预算组织ID
     * @param magOrgId 管理组织ID
     * @author guiyuting 2019-03-06 18:46
     * @return 符合条件的控制策略组
     */
    List<BgtControlStrategyGroup> queryByBgtOrgId(@Param("bgtOrgId") Long bgtOrgId, @Param("magOrgId") Long magOrgId);

}
