package com.hand.hec.fnd.mapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hec.fnd.dto.FndDimension;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 维度定义Mapper
 *
 * @author xiuxian.wu 2019/02/18 20:09
 */
public interface FndDimensionMapper extends Mapper<FndDimension> {
    /**
     * 根据组织级别查询公司级别
     *
     * @param dto
     * @return 返回code级别列表
     * @author xiuxian.wu 2019/2/18 18:17
     */
    List<CodeValue> queryCodeValue(CodeValue dto);

    /**
     * 系统级维值定义，查询维度代码
     *
     * @param dimension
     * @author guiyuting 2019-02-26 16:46
     * @return 符合条件的维度
     */
    List<FndDimension> queryForDimensionValue(FndDimension dimension);

    /**
     * 公司级维值定义，查询维度代码
     *
     * @param dimension
     * @author guiyuting 2019-02-26 16:46
     * @return 符合条件的维度
     */
    List<FndDimension> queryForCompanyDimensionValue(FndDimension dimension);

    /**
     * 根据维值ID查询对应维度是否存在
     *
     * @param null
     * @author guiyuting 2019-03-01 15:26
     * @return 0表示不存在，1表示存在
     */
    int checkDimExists(@Param("dimensionValueId") Long dimensionValueId);

    /**
     * 维度定义页面根据条件查询维度信息
     *
     * @param dimension
     * @author guiyuting 2019-04-01 14:43
     * @return 
     */
    List<FndDimension> queryAll(FndDimension dimension);
}
