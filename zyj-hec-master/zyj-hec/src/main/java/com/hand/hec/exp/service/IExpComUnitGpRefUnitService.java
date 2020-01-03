package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpComUnitGpRefUnit;

import java.util.List;

public interface IExpComUnitGpRefUnitService
                extends IBaseService<ExpComUnitGpRefUnit>, ProxySelf<IExpComUnitGpRefUnitService> {
    /**
     * 部门组批量分配公司
     *
     * @param request
     * @param unitList
     * @author guiyu 2019-01-21 15:16
     * @return  分配完成的关联信息
     */
    List<ExpComUnitGpRefUnit> batchAssignCompany(IRequest request, List<ExpComUnitGpRefUnit> unitList);

    /**
     * 查找该部门组所有分配信息
     *
     * @param request
     * @param expComUnitGpRefUnit
     * @param page 第几页
     * @param pageSize 每页数据量
     * @author guiyu 2019-01-21 15:18
     * @return 部门组分配信息
     */
    List<ExpComUnitGpRefUnit> queryRefUnitInfo(IRequest request,ExpComUnitGpRefUnit expComUnitGpRefUnit,int page,int pageSize);
}