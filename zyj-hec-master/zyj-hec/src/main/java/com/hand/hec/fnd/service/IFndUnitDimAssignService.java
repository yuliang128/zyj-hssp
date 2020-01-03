package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndUnitDimAssign;

import java.util.List;

public interface IFndUnitDimAssignService extends IBaseService<FndUnitDimAssign>, ProxySelf<IFndUnitDimAssignService>{

    /**
     * 根据部门信息查询部门分配维度信息
     *
     * @param fndUnitDimAssign
     * @param page 页数
     * @param pageSize 页面大小
     * @author guiyuting 2019-03-27 14:25
     * @return 
     */
    List<FndUnitDimAssign> queryByUnit(IRequest request, FndUnitDimAssign fndUnitDimAssign,int page,int pageSize);

    @Override
    List<FndUnitDimAssign> batchUpdate(IRequest request,List<FndUnitDimAssign> list);
}