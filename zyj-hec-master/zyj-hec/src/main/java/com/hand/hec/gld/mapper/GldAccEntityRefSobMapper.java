package com.hand.hec.gld.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldAccEntityRefSob;

/**
 * <p>
 * GldAccEntityRefSobMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 13:47
 */
public interface GldAccEntityRefSobMapper extends Mapper<GldAccEntityRefSob> {

    /**
     * 核算主体分配更多的账套需要查询的信息，包括科目表的关联信息
     *
     * @param accEntityId 核算主体ID
     * @return 符合要求的核算关联账套的信息集合
     * @author ngls.luhui 2019-01-18 18:15
     */
    List<GldAccEntityRefSob> selectMoreSob(@Param("accEntityId") Long accEntityId);
}