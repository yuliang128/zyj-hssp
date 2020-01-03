package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndCustomerType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 客户类型定义mapper
 * </p>
 *
 * @author xiuxian.wu 2019/02/14 11:07
 */
public interface FndCustomerTypeMapper extends Mapper<FndCustomerType> {

    /**
     * 根据核算主体查询客户类型
     *
     * @param accEntityId 核算主体ID
     * @author guiyu 2019-01-21 15:18
     * @return 符合条件的核算主体
     */
    List<FndCustomerType> queryByAccEntity(@Param("accEntityId") Long accEntityId);

}