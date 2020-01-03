package com.hand.hec.expm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.expm.dto.ExpPolicyPlcTpRefPlc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 费用政策地点类型分配政策地点Mapper
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 15:50
 */
public interface ExpPolicyPlcTpRefPlcMapper extends Mapper<ExpPolicyPlcTpRefPlc> {
    /**
     * 查找所有已分配给费用政策类型的地点
     *
     * @param dto
     * @return 返回地点
     * @author xiuxian.wu 2019-02-27
     */
    List<ExpPolicyPlcTpRefPlc> queryAll(ExpPolicyPlcTpRefPlc dto);

    /**
     * 查询剩余费用政策类型的地点
     *
     * @param dto
     * @return 费用政策类型的地点
     * @author xiuxian.wu 2019-02-27
     */
    List<ExpPolicyPlcTpRefPlc> queryRemainPolicyPlace(ExpPolicyPlcTpRefPlc dto);
}