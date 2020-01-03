package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoExpTypeAsgnCom;

import java.util.List;

/**
 * <p>
 * 报销类型关联公司Service
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 14:24
 */
public interface IExpMoExpTypeAsgnComService extends IBaseService<ExpMoExpTypeAsgnCom>, ProxySelf<IExpMoExpTypeAsgnComService> {
    /**
     * 查询剩余可以分配给报销类型的公司
     *
     * @param dto            查询条件
     * @param page
     * @param pageSize
     * @param requestContext
     * @return 返回剩余公司
     * @author xiuxian.wu 2019-02-27 14:37
     */
    List<ExpMoExpTypeAsgnCom> queryRemainAllCompany(IRequest requestContext, ExpMoExpTypeAsgnCom dto, Integer page, Integer pageSize);

    /**
     * 批量插入公司于报销类型与公司关系表
     *
     * @param list    插入的数据
     * @param request
     * @return 已经插入好的关系
     * @author 2019-02-27 14:40
     */
    List<ExpMoExpTypeAsgnCom> insertCompany(IRequest request, List<ExpMoExpTypeAsgnCom> list);
}