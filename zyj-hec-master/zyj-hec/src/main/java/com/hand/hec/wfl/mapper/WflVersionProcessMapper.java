package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflVersionGateway;
import com.hand.hec.wfl.dto.WflVersionProcess;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description
 *
 * @author mouse 2019/02/20 15:35
 */
public interface WflVersionProcessMapper extends Mapper<WflVersionProcess>{

    /**
     *
     * 查询已发布的工作流
     *
     * @author mouse 2019-02-20 15:56
     * @return java.util.List<com.hand.hec.wfl.dto.WflVersionProcess>
     */
    List<WflVersionProcess> selectVersionProcess(WflVersionProcess process);

    /**
     * 查询当前工作流的当前版本
     *
     * @param processId
     * @author mouse 2019-03-01 11:26
     * @return java.lang.Long
     */
    Long getProcessCurrentVersion(@Param(value = "processId") Long processId);
}
