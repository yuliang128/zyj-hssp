package com.hand.hec.wfl.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mouse 2019/03/07 14:27
 */
public interface WflMonitorMapper {

    /**
     * 查询工作流实例
     *
     * @param param 查询参数
     * @author mouse 2019-03-07 14:32
     * @return java.util.List<java.util.Map>
     */
    List<Map<String, Object>> selectWflInstance(@Param(value = "param") Map<String, Object> param);

    /**
     * 查询工作流实例的审批信息
     *
     * @param instanceId 工作流实例ID
     * @author mouse 2019-03-07 14:32
     * @return java.util.List<java.util.Map>
     */
    List<Map<String, Object>> selectWflApproveInfo(@Param(value = "instanceId") Long instanceId);

}
