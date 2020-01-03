package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoRepEleRefLnObj;
import com.hand.hec.exp.dto.ExpMoRepTypeRefHdObj;
/**
 * <p>
 * ExpMoRepTypeRefHdObjMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 14:48
 */
public interface ExpMoRepTypeRefHdObjMapper extends Mapper<ExpMoRepTypeRefHdObj>{
    int queryExpTypeHdObj(ExpMoRepEleRefLnObj dto);
}