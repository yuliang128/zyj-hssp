package com.hand.hap.fnd.mapper;

import com.hand.hap.fnd.dto.FndCompanyRefAccBe;
import com.hand.hap.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 管理公司关联核算主体关联预算实体Mapper
 * </p>
 *
 * @author yang.duan 2019/01/10 11:06
 * @author xiuxian.wu 2019/01/25 15:16
 */
public interface FndCompanyRefAccBeMapper extends Mapper<FndCompanyRefAccBe> {
    /**
     * 查询批量管理公司与核算主体分配预算实体剩余可以分配的预算实体
     *
     * @param dto 查询条件
     * @return 返回可以分配的预算实体
     * @author xiuxian.wu 2019/1/25 14:35
     */
    List<FndCompanyRefAccBe> queryBgtEntity(FndCompanyRefAccBe dto);

    /**
     * 查询是否存在符合条件的已分配给管理公司与核算主体的预算实体
     *
     * @param dto 查询条件
     * @return 返回符合条件的核算主体或空
     * @author xiuxian.wu 2019/1/25 14:35
     */
    FndCompanyRefAccBe queryFndCompanyRefAccBe(FndCompanyRefAccBe dto);

    /**
     * 将管理公司与核算主体的默认预算主体设置为不启用及不默认
     *
     * @param accRefId 管理公司与核算主体关联ID
     * @return 返回是否成功
     * @author xiuxian.wu 2019/1/25 14:35
     */
    Long setDefaultFlagN(Long accRefId);

    /**
     * 查询已经被分配的给管理公司的核算主体的预算实体
     *
     * @param condition
     * @return 预算实体数据
     * @author xiuxian.wu 2019-03-12 19:50
     */
    List<FndCompanyRefAccBe> queryCompanyAccRefBgtByAccRefId(FndCompanyRefAccBe condition);

    /**
     * <p>
     * 根据公司+核算主体获取默认预算实体
     * <p/>
     *
     * @param companyId 公司ID
     * @param accEntityId 核算主体ID
     * @return 预算实体信息
     * @author yang.duan 2019/3/21 10:51
     */
    FndCompanyRefAccBe getBgtEntityByComAndAcc(@Param("companyId") Long companyId,
                    @Param("accEntityId") Long accEntityId);
}
