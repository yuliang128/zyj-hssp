package com.hand.hec.expm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.expm.dto.ExpPolicyPlcTpAsgnCom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 费用政策地点类型分配公司Controller
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 15:50
 */

public interface ExpPolicyPlcTpAsgnComMapper extends Mapper<ExpPolicyPlcTpAsgnCom> {
    /**
     * 查询剩余可以分配给费用政策类型的公司
     *
     * @param dto
     * @return 公司数据
     * @author xiuxian.wu 2019-02-27
     */
    List<ExpPolicyPlcTpAsgnCom> queryRemainExpPolicyPlcTpAsgnCom(ExpPolicyPlcTpAsgnCom dto);



}