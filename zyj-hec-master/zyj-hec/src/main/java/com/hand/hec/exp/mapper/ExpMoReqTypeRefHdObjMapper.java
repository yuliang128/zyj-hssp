package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoObjectValue;
import com.hand.hec.exp.dto.ExpMoReqTypeRefHdObj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/28 10:49
 * Description:申请类型页面元素分配费用对象Mapper
 */
public interface ExpMoReqTypeRefHdObjMapper extends Mapper<ExpMoReqTypeRefHdObj> {
    /**
     * 查询当前费用对象值
     *
     * @param moExpObjTypeId    费用对象类型id
     * @param moExpenseObjectId 费用对象值id
     * @return 返回对象值
     * @author jiangxz 2019/2/21 14:27
     */
    ExpMoObjectValue queryObjectValue(@Param("moExpObjTypeId") Long moExpObjTypeId, @Param("moExpenseObjectId") Long moExpenseObjectId);

    /**
     * 查询当前费用对象值
     *
     * @param sqlQuery          费用对象查询sql语句
     * @param moExpenseObjectId 费用对象值id
     * @return 返回对象值
     * @author jiangxz 2019/2/21 14:27
     */
    ExpMoObjectValue queryObjectSearch(@Param("sqlQuery") String sqlQuery, @Param("moExpenseObjectId") Long moExpenseObjectId);

    /**
     * 查询当前费用对象值
     *
     * @param sqlQuery          费用对象查询sql语句
     * @param moExpenseObjectId 费用对象值id
     * @return 返回对象值
     * @author jiangxz 2019/2/21 14:27
     */
    List<ExpMoObjectValue> queryObjectSearchAll(@Param("sqlQuery") String sqlQuery, @Param("moExpenseObjectId") Long moExpenseObjectId);
}