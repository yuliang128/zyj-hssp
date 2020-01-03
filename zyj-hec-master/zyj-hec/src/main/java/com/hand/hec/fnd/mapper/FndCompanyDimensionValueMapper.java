package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndCompanyDimensionValue;

import java.util.List;

/**
 * <p>
 * FndCompanyDimensionValueMapper
 * </p>
 * 
 * @author guiyuting 2019/02/27 11:03
 */
public interface FndCompanyDimensionValueMapper extends Mapper<FndCompanyDimensionValue> {
    /**
     * 根据维值ID和对应维度的系统级别查询
     *
     * @param companyDimensionValue
     * @author guiyuting 2019-02-27 10:58
     * @return 符合条件的公司级维值
     */
    List<FndCompanyDimensionValue> queryByDimension(FndCompanyDimensionValue companyDimensionValue);

    /**
     * 公司级维值定义，查询公司级维值信息
     *
     * @param companyDimensionValue
     * @author guiyuting 2019-02-28 17:19
     * @return 
     */
    List<FndCompanyDimensionValue> queryWithDimension(FndCompanyDimensionValue companyDimensionValue);
    
    /**
     * 根据公司ID和维值ID 更新 启用状态
     *
     * @param null
     * @author guiyuting 2019-03-01 15:39
     * @return 
     */
     void updateByCompanyId(FndCompanyDimensionValue companyDimensionValue);

}
