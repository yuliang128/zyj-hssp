package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpRequisitionObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpRequisitionObjectMapper extends Mapper<ExpRequisitionObject> {

    /**
     * <p>
     * 申请单费用对象更新
     * <p/>
     *
     * @param dto 需要更新的申请单费用对象
     * @return 返回null
     * @author jiangxz 2019/4/4 13:26
     */
    void updateExpReqObject(ExpRequisitionObject dto);

    /**
     * <p>
     * 查询申请单头对象
     * <p/>
     *
     * @param moExpReqTypeId 申请单类型ID
     * @return 费用头对象的list
     * @author jiangxz 2019/4/4 10:09
     */
    List<ExpRequisitionObject> queryObjectByReqHd(@Param("moExpReqTypeId") Long moExpReqTypeId);

    /**
     * <p>
     * 查询申请单行对象
     * <p/>
     *
     * @param moExpReqTypeId 申请单类型ID
     * @param reqPageElementCode 申请单行页面类型代码
     * @return 费用头对象的list
     * @author jiangxz 2019/4/4 10:09
     */
    List<ExpRequisitionObject> queryObjectByReqLn(@Param("moExpReqTypeId") Long moExpReqTypeId, @Param("reqPageElementCode") Long reqPageElementCode);
}