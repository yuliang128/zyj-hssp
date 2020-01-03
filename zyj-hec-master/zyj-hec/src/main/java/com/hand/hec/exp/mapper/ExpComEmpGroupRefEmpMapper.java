package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpComEmpGroupRefEmp;

import java.util.List;

/**
 * <p>
 * 员工分配员工组Mapper
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
public interface ExpComEmpGroupRefEmpMapper extends Mapper<ExpComEmpGroupRefEmp> {
    /**
     * 查询所有已分配给员工的员工组
     *
     * @param dto 查询条件
     * @return 返回员工组
     * @author xiuxian.wu 2019-02-26 16:21
     */
    List<ExpComEmpGroupRefEmp> queryAll(ExpComEmpGroupRefEmp dto);
}