package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflVersionFlow;
import com.hand.hec.wfl.dto.WflVersionGateway;
import com.hand.hec.wfl.dto.WflVersionTask;

import java.util.List;

/**
 * description
 *
 * @author mouse 2019/02/20 15:33
 */
public interface WflVersionGatewayMapper extends Mapper<WflVersionGateway> {

    /**
     *
     * 查询已发布的工作流网关
     *
     * @author mouse 2019-02-20 15:56
     * @return java.util.List<com.hand.hec.wfl.dto.WflVersionGateway>
     */
    List<WflVersionGateway> selectVersionGateway();
}
