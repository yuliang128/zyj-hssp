package com.hand.hap.fnd.mapper;

import java.util.List;

import com.hand.hap.fnd.dto.FndCompanyRefAccEntity;
import com.hand.hap.mybatis.common.Mapper;

/**
 * <p>
 * 管理公司关联核算主体Mapper
 * </p>
 *
 * @author yang.duan 2019/01/10 11:06
 * @author xiuxian.wu 2019/01/25 15:16
 */
public interface FndCompanyRefAccEntityMapper extends Mapper<FndCompanyRefAccEntity> {

    /**
     * 查询是否存在符合条件的已分配给管理公司的核算主体
     *
     * @param dto 查询条件
     * @return 返回符合条件的核算主体或空
     * @author xiuxian.wu 2019/1/25 14:35
     */
    FndCompanyRefAccEntity queryFndCompanyRefAccEntity(FndCompanyRefAccEntity dto);

    /**
     * 将管理公司的默认核算主体设置为不启用及不默认
     *
     * @param companyId 管理公司ID
     * @return 返回是否成功
     * @author xiuxian.wu 2019/1/25 14:35
     */
    Long setDefaultFlagN(Long companyId);

    /**
     * 查询可以批量分配给管理公司的剩余核算主体
     *
     * @param dto 查询条件
     * @return 返回剩余核算主体信息
     * @author xiuxian.wu 2019/1/25 14:39
     */
    List<FndCompanyRefAccEntity> queryAccEntity(FndCompanyRefAccEntity dto);

    /**
     * 查询已经分配给管理公司的核算主体信息
     *
     * @param dto 查询条件 公司ID
     * @return 核算主体信息
     * @author xiuxian.wu 2019/1/25 14:42
     */
    List<FndCompanyRefAccEntity> queryAllFndCompanyRefAccEntity(FndCompanyRefAccEntity dto);

    /**
     * 公司Id查询默认核算主体
     *
     * @return 核算主体信息
     * @author dingwei.ma@hand-china.com 2019/1/25 14:42
     */
    Long queryDftAccByComId();
}