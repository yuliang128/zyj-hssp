package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtStructureLayout;

import java.util.List;

/**
 * <p>
 * 预算表维度布局Service
 * </p>
 * 
 * @author mouse 2019/01/07 17:02
 */
public interface IBgtStructureLayoutService
                extends IBaseService<BgtStructureLayout>, ProxySelf<IBgtStructureLayoutService> {

    /**
     * 根据structureId查询预算表维度布局信息
     *
     * @param request
     * @param structureId 预算表ID
     * @author guiyuting 2019-03-05 16:15
     * @return
     */
    List<BgtStructureLayout> queryByStructureId(IRequest request, Long structureId, int page, int pageSize);

    @Override
    List<BgtStructureLayout> batchUpdate(IRequest request, List<BgtStructureLayout> list);
}
