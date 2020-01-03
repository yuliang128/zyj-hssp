package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoEmpGroupAsgnCom;

import java.util.List;

/**
 * <p>
 * 员工组关联公司Mapper
 * </p>
 *
 * @author xiuxian.wu 2019/03/06 14:48
 */
public interface ExpMoEmpGroupAsgnComMapper extends Mapper<ExpMoEmpGroupAsgnCom> {
    /**
     * 查询此公司是否已存在
     *
     * @param dto 查询条件
     * @return 返回是否 1为是 0 为否
     * @author xiuxian.wu 2019/03/06 14:55
     */
    long queryMoEmpGroupAsgnCom(ExpMoEmpGroupAsgnCom dto);

    /**
     * 获取所有剩余可以分配的公司或所有符合条件的公司
     *
     * @param dto 条件
     * @return 返回公司数据
     * @author xiuxian.wu 2019/03/06 15:21
     */
    List<ExpMoEmpGroupAsgnCom> queryAllCompanyInformation(ExpMoEmpGroupAsgnCom dto);
}