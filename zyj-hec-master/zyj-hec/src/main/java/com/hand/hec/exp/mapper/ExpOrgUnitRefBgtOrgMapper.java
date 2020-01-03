package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpOrgUnitRefBgtOrg;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  ExpOrgUnitRefBgtOrgMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 11:17
 */
public interface ExpOrgUnitRefBgtOrgMapper extends Mapper<ExpOrgUnitRefBgtOrg>{

    /**
     * 根据成本中心ID，预算实体ID，预算实体ID，查询关联关系是否存在
     *
     * @param null
     * @author guiyuting 2019-02-28 10:00
     * @return
     */
    int queryRefRalationNum(@Param("unitId") Long unitId, @Param("bgtEntityId") Long bgtEntityId,
                            @Param("bgtCenterId") Long bgtCenterId);


}