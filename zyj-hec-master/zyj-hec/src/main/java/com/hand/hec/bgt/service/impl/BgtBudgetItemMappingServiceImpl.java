package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBudgetItemMapping;
import com.hand.hec.bgt.mapper.BgtBudgetItemMappingMapper;
import com.hand.hec.bgt.service.IBgtBudgetItemMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 预算项决定项ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBudgetItemMappingServiceImpl extends BaseServiceImpl<BgtBudgetItemMapping>
                implements IBgtBudgetItemMappingService {
    @Autowired
    BgtBudgetItemMappingMapper budgetItemMappingMapper;

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
    @Override
    public Long getPrioRityItemId(String businessCategory, Long companyId, Long documentTypeId, Long businessTypeId,
                    Long paramValue1Id, Long paramValue2Id, Long unitId, Long positionId, Long employeeId,
                    Long accEntityId, Long respCenterId, String payeeCategory, Long payeeId, Long dimension1Id,
                    Long dimension2Id, Long dimension3Id, Long dimension4Id, Long dimension5Id, Long dimension6Id,
                    Long dimension7Id, Long dimension8Id, Long dimension9Id, Long dimension10Id, Long dimension11Id,
                    Long dimension12Id, Long dimension13Id, Long dimension14Id, Long dimension15Id, Long dimension16Id,
                    Long dimension17Id, Long dimension18Id, Long dimension19Id, Long dimension20Id) {
        List<BgtBudgetItemMapping> budgetItemMappingList =
                        budgetItemMappingMapper.getBgtBudgetItemMapping(businessCategory, companyId);
        Long bgtItemId = Long.valueOf(-1);
        if (budgetItemMappingList != null && !budgetItemMappingList.isEmpty()) {
            for (BgtBudgetItemMapping itemMapping : budgetItemMappingList) {
                if ((itemMapping.getBusinessCategory().equals(businessCategory))
                                && (itemMapping.getCompanyId() == null || itemMapping.getCompanyId().equals(companyId))
                                && (itemMapping.getDocumentTypeId() == null
                                                || itemMapping.getDocumentTypeId().equals(documentTypeId))
                                && (itemMapping.getBusinessTypeId() == null
                                                || itemMapping.getBusinessTypeId().equals(businessTypeId))
                                && (itemMapping.getParamValue1Id() == null
                                                || itemMapping.getParamValue1Id().equals(paramValue1Id))
                                && (itemMapping.getUnitId() == null || itemMapping.getUnitId().equals(unitId))
                                && (itemMapping.getPositionId() == null
                                                || itemMapping.getPositionId().equals(positionId))
                                && (itemMapping.getEmployeeId() == null
                                                || itemMapping.getEmployeeId().equals(employeeId))
                                && (itemMapping.getAccEntityId() == null
                                                || itemMapping.getAccEntityId().equals(accEntityId))
                                && (itemMapping.getRespCenterId() == null
                                                || itemMapping.getRespCenterId().equals(respCenterId))
                                && (itemMapping.getPayeeCategory() == null
                                                || itemMapping.getPayeeCategory().equals(payeeCategory))
                                && (itemMapping.getPayeeId() == null || itemMapping.getPayeeId().equals(payeeId))
                                && (itemMapping.getDimension1Id() == null
                                                || itemMapping.getDimension1Id().equals(dimension1Id))
                                && (itemMapping.getDimension2Id() == null
                                                || itemMapping.getDimension2Id().equals(dimension2Id))
                                && (itemMapping.getDimension3Id() == null
                                                || itemMapping.getDimension3Id().equals(dimension3Id))
                                && (itemMapping.getDimension4Id() == null
                                                || itemMapping.getDimension4Id().equals(dimension4Id))
                                && (itemMapping.getDimension5Id() == null
                                                || itemMapping.getDimension5Id().equals(dimension5Id))
                                && (itemMapping.getDimension6Id() == null
                                                || itemMapping.getDimension6Id().equals(dimension6Id))
                                && (itemMapping.getDimension7Id() == null
                                                || itemMapping.getDimension7Id().equals(dimension7Id))
                                && (itemMapping.getDimension8Id() == null
                                                || itemMapping.getDimension8Id().equals(dimension8Id))
                                && (itemMapping.getDimension9Id() == null
                                                || itemMapping.getDimension9Id().equals(dimension9Id))
                                && (itemMapping.getDimension10Id() == null
                                                || itemMapping.getDimension10Id().equals(dimension10Id))
                                && (itemMapping.getDimension11Id() == null
                                                || itemMapping.getDimension11Id().equals(dimension11Id))
                                && (itemMapping.getDimension12Id() == null
                                                || itemMapping.getDimension12Id().equals(dimension12Id))
                                && (itemMapping.getDimension13Id() == null
                                                || itemMapping.getDimension13Id().equals(dimension13Id))
                                && (itemMapping.getDimension14Id() == null
                                                || itemMapping.getDimension14Id().equals(dimension14Id))
                                && (itemMapping.getDimension15Id() == null
                                                || itemMapping.getDimension15Id().equals(dimension15Id))
                                && (itemMapping.getDimension16Id() == null
                                                || itemMapping.getDimension16Id().equals(dimension16Id))
                                && (itemMapping.getDimension17Id() == null
                                                || itemMapping.getDimension17Id().equals(dimension17Id))
                                && (itemMapping.getDimension18Id() == null
                                                || itemMapping.getDimension18Id().equals(dimension18Id))
                                && (itemMapping.getDimension19Id() == null
                                                || itemMapping.getDimension19Id().equals(dimension19Id))
                                && (itemMapping.getDimension20Id() == null
                                                || itemMapping.getDimension20Id().equals(dimension20Id))) {
                    bgtItemId = itemMapping.getBudgetItemId();
                    break;
                }
            }
        }
        return bgtItemId;
    }

}
