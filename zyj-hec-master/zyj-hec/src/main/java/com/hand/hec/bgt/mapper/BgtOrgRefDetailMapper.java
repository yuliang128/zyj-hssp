package com.hand.hec.bgt.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtOrgRefDetail;

/**
 * <p>
 * 预算组织来源明细Mapper
 * </p>
 *
 * @author mouse 2019/01/07 16:48
 */
public interface BgtOrgRefDetailMapper extends Mapper<BgtOrgRefDetail> {


    /**
     * 预算组织来源明细-管理组织
     *
     * @param bgtOrgId 预算组织ID
     * @return 与该预算组织关联的管理组织明细
     * @author ngls.luhui 2019-01-29 14:37
     */
    public List<BgtOrgRefDetail> selectDetailOfMag(Long bgtOrgId);

    /**
     * 预算组织来源明细-账套
     *
     * @param bgtOrgId 预算组织ID
     * @return 与该预算组织关联的账套明细
     * @author ngls.luhui 2019-01-29 14:37
     */
    public List<BgtOrgRefDetail> selectDetailOfSob(Long bgtOrgId);

}