package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBudgetItemMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 预算项决定项Mapper
 * </p>
 * 
 * @author mouse 2019/01/07 16:46
 */
public interface BgtBudgetItemMappingMapper extends Mapper<BgtBudgetItemMapping> {
    List<BgtBudgetItemMapping> getBgtBudgetItemMapping(@Param("businessCategory") String businessCategory,
                    @Param("companyId") Long companyId);
}
