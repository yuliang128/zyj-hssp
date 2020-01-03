package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtStructureLayout;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 预算表维度布局Mapper
 * </p>
 * 
 * @author mouse 2019/01/07 16:50
 */
public interface BgtStructureLayoutMapper extends Mapper<BgtStructureLayout>{

    /**
     * 根据structureId查询预算表维度布局信息
     *
     * @param structureId 预算表ID
     * @author guiyuting 2019-03-05 16:15
     * @return 
     */
    List<BgtStructureLayout> queryByStructureId(@Param("structureId") Long structureId);

}