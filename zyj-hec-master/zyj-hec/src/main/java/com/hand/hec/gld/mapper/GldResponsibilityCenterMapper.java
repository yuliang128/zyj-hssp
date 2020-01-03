package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GldResponsibilityCenterMapper extends Mapper<GldResponsibilityCenter> {

    /**
     * 根据核算主体ID查询成本中心
     *
     * @param accEntityId 核算主体ID
     * @author guiyuting 2019-02-27 19:36
     * @return 成本中心
     */
    List<GldResponsibilityCenter> queryByAccEntityId(@Param("accEntityId") Long accEntityId);

    /**
     * 根据核算主体ID与账套级成本中心修改核算主体级成本中心
     *
     * @param dto
     * @author guiyuting 2019-02-27 19:36
     * @return 成本中心
     */
    Long updateByAccEntityIdAndParentRespCenterId(GldResponsibilityCenter dto);

    /**
     * 获取部门默认成本中心
     *
     * @author Tagin
     * @date 2019-04-01 17:04
     * @param unitId 部门ID
     * @param accEntityId 核算主体ID
     * @return java.util.List<com.hand.hec.gld.dto.GldResponsibilityCenter>
     * @version 1.0
     **/
    List<GldResponsibilityCenter> getDefaultRespCenter(@Param("unitId") Long unitId,
                    @Param("accEntityId") Long accEntityId);

}
