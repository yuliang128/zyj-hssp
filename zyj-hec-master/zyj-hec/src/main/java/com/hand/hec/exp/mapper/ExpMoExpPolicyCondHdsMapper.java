package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoExpPolicyCondHds;
import com.hand.hec.exp.dto.ExpMoExpPolicyCondLns;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 政策标准明细条件头Mapper
 * </p>
 *
 * @author xiuxian.wu 2019-02-21
 */

public interface ExpMoExpPolicyCondHdsMapper extends Mapper<ExpMoExpPolicyCondHds> {
    /**
     * 查询组件是否显示
     *
     * @param conditionId 政策标准明细条件ID
     * @return 返回需要显示的组件的状态
     * @author xiuxian.wu 2019-02-22 16:01
     */
    List<String> queryAllMatchItemCode(@Param("conditionId") Long conditionId);

    /**
     * 根据政策标准明细条件行ID和类型获取政策标准明细条件头ID
     *
     * @param condLns 政策标准明细条件行数据
     * @return 政策标准明细条件头ID
     * @author xiuxian.wu 2019-02-22 16:01
     */
    Long queryConditionHdsId(ExpMoExpPolicyCondLns condLns);
}