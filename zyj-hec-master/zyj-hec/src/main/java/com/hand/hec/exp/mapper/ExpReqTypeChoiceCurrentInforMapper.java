package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpReqTypeChoiceCurrentInfor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description 申请单创建选择类型当前用户信息mapper
 *
 * @author jiangxz 2019/03/25 19:00
 */
public interface ExpReqTypeChoiceCurrentInforMapper extends Mapper<ExpReqTypeChoiceCurrentInfor> {

    /**
     * 查询选择类型当前用户信息
     *
     * @param employee_id 员工ID
     * @return 岗位组中分配的岗位及公司信息
     * @author jiangxz 2019-03-25
     */
    List<ExpReqTypeChoiceCurrentInfor> queryExpReqTypeChoiceCurrentInfor(@Param("employee_id") Long employee_id);
}
