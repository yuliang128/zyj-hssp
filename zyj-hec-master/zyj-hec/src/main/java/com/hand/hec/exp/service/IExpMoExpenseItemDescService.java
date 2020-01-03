package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoExpenseItemDesc;

import java.util.List;
/**
 * <p>
 * 费用项目说明定义Service接口
 * </p>
 *
 * @author yang.cai 2019/02/28 18:19
 */
public interface IExpMoExpenseItemDescService extends IBaseService<ExpMoExpenseItemDesc>, ProxySelf<IExpMoExpenseItemDescService>{
    List<ExpMoExpenseItemDesc> queryAll(IRequest request, ExpMoExpenseItemDesc expMoExpenseItemDesc, int pageNum, int pageSize);
}