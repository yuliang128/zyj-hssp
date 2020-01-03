package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndUnitDimValueAssign;
import com.hand.hec.fnd.exception.FndUnitValueAssignException;

import java.util.List;

public interface IFndUnitDimValueAssignService
                extends IBaseService<FndUnitDimValueAssign>, ProxySelf<IFndUnitDimValueAssignService> {
    /**
     * 根据部门分配维度ID查询 部门分配维值信息
     *
     * @param fndUnitDimValueAssign
     * @author guiyuting 2019-03-27 14:21
     * @return 符合条件的部门分配维值信息
     */
    List<FndUnitDimValueAssign> queryByDimAssignId(IRequest request, FndUnitDimValueAssign fndUnitDimValueAssign);

    List<FndUnitDimValueAssign> batchSubmit(IRequest request, List<FndUnitDimValueAssign> list) throws FndUnitValueAssignException;

}
