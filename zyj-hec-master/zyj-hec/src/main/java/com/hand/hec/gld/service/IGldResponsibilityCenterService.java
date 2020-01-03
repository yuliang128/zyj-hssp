package com.hand.hec.gld.service;

import java.util.Date;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldResponsibilityCenter;

public interface IGldResponsibilityCenterService
                extends IBaseService<GldResponsibilityCenter>, ProxySelf<IGldResponsibilityCenterService> {
    /**
     * 检查成本中心在指定日期是否有效
     *
     * @param date 指定日期
     * @param responsibilityCenterId 成本中心ID
     * @author ngls.luhui 2019-02-22 12:27
     * @return
     */
    Boolean checkInTime(Date date, Long responsibilityCenterId);

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
    GldResponsibilityCenter getDefaultRespCenter(Long unitId, Long accEntityId);

}
