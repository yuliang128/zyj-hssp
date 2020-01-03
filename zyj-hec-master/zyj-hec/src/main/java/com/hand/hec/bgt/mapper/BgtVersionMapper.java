package com.hand.hec.bgt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtVersion;

/**
 * <p>
 * 预算版本Mapper
 * </p>
 *
 * @author mouse 2019/01/07 16:50
 */
public interface BgtVersionMapper extends Mapper<BgtVersion> {

    /**
     * 预算检查的预算版本符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param filtrateMethod 控制规则取值方式
     * @param versionCodeFrom 控制规则预算版本代码从
     * @param versionCodeTo 控制规则预算版本代码到
     * @author YHL 2019-03-05 18:53
     * @return java.util.List<com.hand.hec.bgt.dto.BgtVersion> 符合的预算版本
     */
    List<BgtVersion> checkBgtVersion(@Param("controlType") String controlType,
            @Param("filtrateMethod") String filtrateMethod, @Param("versionCodeFrom") String versionCodeFrom,
            @Param("versionCodeTo") String versionCodeTo);

}