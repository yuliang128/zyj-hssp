package com.hand.hec.exp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpPositionGroup;

public interface ExpPositionGroupMapper extends Mapper<ExpPositionGroup> {

    /**
     * 预算检查的岗位组符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param positionId 当前占用行岗位ID
     * @param filtrateMethod 控制规则取值方式
     * @param controlPositionGroupCodeFrom 控制规则岗位组代码从
     * @param controlPositionGroupCodeTo 控制规则岗位组代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的岗位组
     */
    List<ExpPositionGroup> checkExpPositionGroup(@Param("controlType") String controlType,
            @Param("positionId") Long positionId, @Param("filtrateMethod") String filtrateMethod,
            @Param("controlPositionGroupCodeFrom") String controlPositionGroupCodeFrom,
            @Param("controlPositionGroupCodeTo") String controlPositionGroupCodeTo);

    /**
     * 获取岗位组
     *
     * @param parameterCode 参数代码（值）
     * @param parameterLowerLimit 参数代码始值
     * @param parameterUpperLimit 参数代码止值
     * @author YHL 2019-03-27 19:49
     * @return java.util.List<com.hand.hec.exp.dto.ExpPositionGroup> 岗位组
     */
    List<ExpPositionGroup> getPositionGroupListForQuery(@Param("parameterCode") String parameterCode,
            @Param("parameterLowerLimit") String parameterLowerLimit,
            @Param("parameterUpperLimit") String parameterUpperLimit);

}
