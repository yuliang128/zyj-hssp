package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoExpTypeAsgnCom;

import java.util.List;

/**
 * <p>
 * 报销类型关联公司Mapper
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 14:24
 */
public interface ExpMoExpTypeAsgnComMapper extends Mapper<ExpMoExpTypeAsgnCom> {
    /**
     * 查询剩余可以分配给报销类型的公司
     *
     * @param dto 查询条件
     * @return 返回剩余公司
     * @author xiuxian.wu 2019-02-27 14:37
     */
    List<ExpMoExpTypeAsgnCom> queryRemainAllCompany(ExpMoExpTypeAsgnCom dto);

    /**
     * 查询此公司是否已经被分配给报销类型
     *
     * @param dto
     * @return 是否插入 1为插入 0为无
     * @author xiuxian.wu 2019-02-27 14:42
     */
    long queryExpTypeAsgnCompany(ExpMoExpTypeAsgnCom dto);
}