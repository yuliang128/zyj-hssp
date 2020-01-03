package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoReqItemDesc;

import java.util.List;
/**
 * <p>
 * 申请项目说明Mapper
 * </p>
 *
 * @author yang.cai 2019/02/27 18:43
 */
public interface ExpMoReqItemDescMapper extends Mapper<ExpMoReqItemDesc>{
    /**
     * 获取符合条件的申请项目说明
     * @param expMoReqItemDesc
     * @return 符合条件的申请项目说明
     */
    List<ExpMoReqItemDesc> queryAll(ExpMoReqItemDesc expMoReqItemDesc);
}