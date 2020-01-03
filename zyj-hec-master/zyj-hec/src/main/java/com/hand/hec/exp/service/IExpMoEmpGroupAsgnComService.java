package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoEmpGroupAsgnCom;

import java.util.List;

/**
 * <p>
 * 员工组关联公司Service
 * </p>
 *
 * @author xiuxian.wu 2019/03/06 14:48
 */
public interface IExpMoEmpGroupAsgnComService extends IBaseService<ExpMoEmpGroupAsgnCom>, ProxySelf<IExpMoEmpGroupAsgnComService> {
    /**
     * 获取所有剩余可以分配的公司或所有符合条件的公司
     *
     * @param request
     * @param page
     * @param PageSize
     * @param dto      条件
     * @return 返回公司数据
     * @author xiuxian.wu 2019/03/06 15:21
     */
    List<ExpMoEmpGroupAsgnCom> queryAllCompanyInformation(IRequest request, ExpMoEmpGroupAsgnCom dto, Integer page, Integer PageSize);

    /**
     * 批量插入员工组与公司关系
     *
     * @param request
     * @param list
     * @return 已插入的数据
     * @author xiuxian.wu 2019/03/06 15:29
     */
    List<ExpMoEmpGroupAsgnCom> insertCompany(IRequest request, List<ExpMoEmpGroupAsgnCom> list);
}