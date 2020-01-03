package com.hand.hec.bgt.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBudgetItemMapping;

/**
 * <p>
 * 预算项决定项Service
 * </p>
 * 
 * @author mouse 2019/01/07 16:53
 */
public interface IBgtBudgetItemMappingService
                extends IBaseService<BgtBudgetItemMapping>, ProxySelf<IBgtBudgetItemMappingService> {


    /**
     *
     * <p>根据预算决定项规则获取预算项目<p/>
     * @param businessCategory
     * @param companyId
     * @param documentTypeId
     * @param businessTypeId
     * @param paramValue1Id
     * @param paramValue2Id
     * @param unitId
     * @param positionId
     * @param employeeId
     * @param accEntityId
     * @param respCenterId
     * @param payeeCategory
     * @param payeeId
     * @param dimension1Id
     * @param dimension2Id
     * @param dimension3Id
     * @param dimension4Id
     * @param dimension5Id
     * @param dimension6Id
     * @param dimension7Id
     * @param dimension8Id
     * @param dimension9Id
     * @param dimension10Id
     * @param dimension11Id
     * @param dimension12Id
     * @param dimension13Id
     * @param dimension14Id
     * @param dimension15Id
     * @param dimension16Id
     * @param dimension17Id
     * @param dimension18Id
     * @param dimension19Id
     * @param dimension20Id
     * @author yang.duan 2019-03-07 17:15
     * @return 预算项目ID(找不到返回-1)
     */
    Long getPrioRityItemId(String businessCategory, Long companyId, Long documentTypeId, Long businessTypeId,
                    Long paramValue1Id, Long paramValue2Id, Long unitId, Long positionId, Long employeeId,
                    Long accEntityId, Long respCenterId, String payeeCategory, Long payeeId, Long dimension1Id,
                    Long dimension2Id, Long dimension3Id, Long dimension4Id, Long dimension5Id, Long dimension6Id,
                    Long dimension7Id, Long dimension8Id, Long dimension9Id, Long dimension10Id, Long dimension11Id,
                    Long dimension12Id, Long dimension13Id, Long dimension14Id, Long dimension15Id,
                    Long dimension16Id, Long dimension17Id, Long dimension18Id, Long dimension19Id, Long dimension20Id);
}
