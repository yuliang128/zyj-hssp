package com.hand.hec.pur.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.pur.dto.PurSystemVender;

/**
 * <p>
 * 系统级供应商主数据Mapper接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/18 15:45
 */
public interface PurSystemVenderMapper extends Mapper<PurSystemVender> {

    /**
     * 系统级供应商管理页面基础查询
     *
     * @param systemVender 模糊查询传入参数
     * @return java.util.List<com.hand.hec.pur.dto.PurSystemVender>
     * @author jialin.xing@hand-china.com 2019-02-19 10:22
     */
    List<PurSystemVender> queryForSystemLevel(PurSystemVender systemVender);

    /**
     * 供应商管理页面基础查询
     *
     * @param systemVender
     * @param accEntity
     * @return java.util.List<com.hand.hec.pur.dto.PurSystemVender>
     * @author jialin.xing@hand-china.com 2019-02-28 16:43
     */
    List<PurSystemVender> queryByNonSysLevel(@Param("systemVender") PurSystemVender systemVender,
                                             @Param("accEntityId") Long accEntity);

}