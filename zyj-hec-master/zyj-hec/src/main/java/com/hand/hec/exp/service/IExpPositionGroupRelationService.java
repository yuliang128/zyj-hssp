package com.hand.hec.exp.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpPositionGroupRelation;

/**
 * <p>
 * 岗位分配岗位组Service接口
 * </p>
 *
 * @author YHL 2019/01/18 17:00
 */
public interface IExpPositionGroupRelationService
        extends IBaseService<ExpPositionGroupRelation>, ProxySelf<IExpPositionGroupRelationService> {

    /**
     * 查询岗位组中分配的岗位及公司信息
     *
     * @param request
     * @param positionGroupId 岗位组ID
     * @param pageNum
     * @param pageSize
     * @author YHL 2019-01-18 16:58
     * @return 岗位组中分配的岗位及公司信息
     */
    List<ExpPositionGroupRelation> getPositionGroupRelationByPositionGroupId(IRequest request, Long positionGroupId,
            int pageNum, int pageSize);

}