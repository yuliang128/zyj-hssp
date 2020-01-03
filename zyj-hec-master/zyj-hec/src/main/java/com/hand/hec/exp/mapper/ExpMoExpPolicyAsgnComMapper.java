package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoExpPolicyAsgnCom;

import java.util.List;

/**
 * <p>
 * 政策标准关联管理公司Mapper
 * </p>
 *
 * @author xiuxian.wu 2019/01/19 25:25
 */
public interface ExpMoExpPolicyAsgnComMapper extends Mapper<ExpMoExpPolicyAsgnCom> {
    /**
     * 查找分配给政策标准的管理公司
     *
     * @param dto 查找条件
     * @return 返回剩余管理公司信息
     * @author xiuxian.wu 2019/02/20 15:14
     */
    List<ExpMoExpPolicyAsgnCom> queryCompanyByExpensePolicyId(ExpMoExpPolicyAsgnCom dto);

    /**
     * 查找剩余可以分配给政策标准的管理公司
     *
     * @param dto 查找条件
     * @return 返回剩余管理公司信息
     * @author xiuxian.wu 2019/02/20 15:14
     */
    List<ExpMoExpPolicyAsgnCom> queryRemainingCompanyByExpensePolicyId(ExpMoExpPolicyAsgnCom dto);

}