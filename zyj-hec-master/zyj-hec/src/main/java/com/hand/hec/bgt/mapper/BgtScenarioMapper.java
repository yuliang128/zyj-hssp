package com.hand.hec.bgt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtScenario;

/**
 * <p>
 * 预算场景Mapper
 * </p>
 *
 * @author mouse 2019/01/07 16:49
 */
public interface BgtScenarioMapper extends Mapper<BgtScenario> {

    /**
     * 预算检查的预算场景符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param filtrateMethod 控制规则取值方式
     * @param scenarioCodeFrom 控制规则预算场景代码从
     * @param scenarioCodeTo 控制规则预算场景代码到
     * @author YHL 2019-03-05 18:32
     * @return java.util.List<com.hand.hec.bgt.dto.BgtPeriod> 符合的预算场景
     */
    List<BgtScenario> checkBgtScenario(@Param("controlType") String controlType,
            @Param("filtrateMethod") String filtrateMethod, @Param("scenarioCodeFrom") String scenarioCodeFrom,
            @Param("scenarioCodeTo") String scenarioCodeTo);

}