package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoExpenseObjectType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExpMoExpenseObjectTypeMapper extends Mapper<ExpMoExpenseObjectType> {


    ExpMoExpenseObjectType getExpMoExpenseObjectType(@Param("moExpObjTypeId") Long moExpObjTypeId);

    /**
     * 查询费用对象的LOV
     *
     * @param objectType
     * @param code
     * @param name
     * @author mouse 2019-03-22 14:49
     * @return java.util.List<java.util.Map>
     */
    List<Map> queryMoExpObjValueLov(@Param("objectType") ExpMoExpenseObjectType objectType, @Param("code") String code,
                    @Param("name") String name);
}
