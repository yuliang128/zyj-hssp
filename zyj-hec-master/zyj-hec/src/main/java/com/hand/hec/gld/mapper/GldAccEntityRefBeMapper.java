package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldAccEntityRefBe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * GldAccEntityRefBeMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 13:47
 */
public interface GldAccEntityRefBeMapper extends Mapper<GldAccEntityRefBe> {

    /**
     * 核算主体批量分配预算实体时，查询出能分配的预算实体
     *
     * @param accEntityId 核算主体的id
     * @return 该核算主体可分配的预算实体
     * @author ngls.luhui 2019-01-18 18:08
     */
     List<GldAccEntityRefBe> batchSelect(@Param("accEntityId") Long accEntityId);

    /**
     * <p>根据核算主体获取默认预算实体<p/>
     *
     * @param accEntityId meaning
     * @return 预算实体信息
     * @author yang.duan 2019/3/21 10:59
     */
    GldAccEntityRefBe getDeftBgtEntity(@Param("accEntityId") Long accEntityId);
}