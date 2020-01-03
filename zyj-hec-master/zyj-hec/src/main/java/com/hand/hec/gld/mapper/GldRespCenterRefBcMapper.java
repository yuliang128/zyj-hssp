package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldRespCenterRefBc;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * GldRespCenterRefBcMapper
 * </p>
 * 
 * @author yang.duan 2019/01/10 13:47
 */
public interface GldRespCenterRefBcMapper extends Mapper<GldRespCenterRefBc> {

    /**
     * 根据成本中心ID，预算实体ID，预算实体ID，查询关联关系是否存在
     *
     * @param null
     * @author guiyuting 2019-02-28 10:00
     * @return
     */

    int queryRefRalationNum(@Param("respCenterId") Long respCenterId, @Param("bgtEntityId") Long bgtEntityId,
                    @Param("bgtCenterId") Long bgtCenterId);

}
