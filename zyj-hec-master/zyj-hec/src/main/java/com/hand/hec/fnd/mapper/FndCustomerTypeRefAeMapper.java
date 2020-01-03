package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndCustomerTypeRefAe;
import com.hand.hec.gld.dto.GldAccountingEntity;

import java.util.List;

/**
 * <p>
 * 客户类型关联核算主体Mapper
 * </p>
 *
 * @author xiuxian.wu 2019/02/14 11:07
 */
public interface FndCustomerTypeRefAeMapper extends Mapper<FndCustomerTypeRefAe> {

    /**
     * 查询所有符合条件可以分配的核算主体
     *
     * @param dto 条件
     * @return 返回符合条件的核算主体
     * @author xiuxian.wu 2019/2/15 10:16
     */
    List<GldAccountingEntity> queryAllAccountingEntity(FndCustomerTypeRefAe dto);

    /**
     * 检查客户类型与核算主体表中是否含有相同的关联关系
     *
     * @param dto 新增的关联关系
     * @return 返回含有数据的条数
     * @author xiuxian.wu 2019/2/15 16:16
     */
    long queryFndCustomerTypeRefAe(FndCustomerTypeRefAe dto);
}