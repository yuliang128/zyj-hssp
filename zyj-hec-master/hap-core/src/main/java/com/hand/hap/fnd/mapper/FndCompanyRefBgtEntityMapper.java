package com.hand.hap.fnd.mapper;

import com.hand.hap.fnd.dto.FndCompanyRefBgtEntity;
import com.hand.hap.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 管理公司关联预算实体Mapper
 * </p>
 *
 * @author yang.duan 2019/01/10 11:07
 * @author xiuxian.wu 2019/01/25 15:16
 */
public interface FndCompanyRefBgtEntityMapper extends Mapper<FndCompanyRefBgtEntity> {
    /**
     * 查询是否存在符合条件的已分配给管理公司的预算实体
     *
     * @param dto 查询条件
     * @return 返回符合条件的预算实体或空
     * @author xiuxian.wu 2019/1/25 14:35
     */
    FndCompanyRefBgtEntity queryFndCompanyRefBgtEntity(FndCompanyRefBgtEntity dto);

    /**
     * 将管理公司的默认预算主体设置为不启用及不默认
     *
     * @param companyId 管理公司ID
     * @return 返回是否成功
     * @author xiuxian.wu 2019/1/25 14:35
     */
    Long setDefaultFlagN(Long companyId);

    /**
     * 查询可以批量分配给管理公司的剩余预算实体
     *
     * @param dto 查询条件 公司ID
     * @return 返回剩余预算实体信息
     * @author xiuxian.wu 2019/01/25 14:50
     */
    List<FndCompanyRefBgtEntity> queryBgtEntity(FndCompanyRefBgtEntity dto);

    /**
     * 查询已经分配给管理公司的预算实体
     *
     * @param condition
     * @return 预算实体数据
     * @author xiuxian.wu 2019-03-12 19:55
     */
    List<FndCompanyRefBgtEntity> queryCompanyRefBgtEntityByCompanyId(FndCompanyRefBgtEntity condition);

    /**
     * <p>获取公司默认的预算实体<p/>
     *
     * @param companyId 公司ID
     * @return 预算实体信息
     * @author yang.duan 2019/3/21 11:24
     */
    FndCompanyRefBgtEntity getDftBgtEntity(@Param("companyId") Long companyId);
}