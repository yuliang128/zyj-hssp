package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndDimensionValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FndDimensionValueMapper extends Mapper<FndDimensionValue> {

    /**
     * 预算检查的维度符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param dimensionId 维度ID
     * @param filtrateMethod 控制规则取值方式
     * @param dimensionValueId 维度值ID
     * @param controlDimValueCodeFrom 控制规则维度值代码从
     * @param controlDimValueCodeTo 控制规则维度值代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的维值
     */
    List<FndDimensionValue> checkFndDimension(@Param("controlType") String controlType,
            @Param("dimensionId") Long dimensionId, @Param("filtrateMethod") String filtrateMethod,
            @Param("dimensionValueId") Long dimensionValueId,
            @Param("controlDimValueCodeFrom") String controlDimValueCodeFrom,
            @Param("controlDimValueCodeTo") String controlDimValueCodeTo);



    /**
     * <p>判断维值是否存在<p/>
     *
     * @param sequence 维度顺序
     * @param dimensionValueId 维值ID
     * @return 0表示不存在,1表示存在
     * @author yang.duan 2019/3/7 10:02
     */
    int checkDimensionValue(@Param("sequence") Long sequence, @Param("dimensionValueId") Long dimensionValueId);

}
