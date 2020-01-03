package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoExpenseItemDesc;

import java.util.List;
/**
 * <p>
 * 费用项目说明定义Mapper
 * </p>
 *
 * @author yang.cai 2019/02/28 18:19
 */
public interface ExpMoExpenseItemDescMapper extends Mapper<ExpMoExpenseItemDesc>{
    List<ExpMoExpenseItemDesc> queryAll(ExpMoExpenseItemDesc expMoExpenseItemDesc);

    List<ExpMoExpenseItemDesc> checkExists(ExpMoExpenseItemDesc dto);
}