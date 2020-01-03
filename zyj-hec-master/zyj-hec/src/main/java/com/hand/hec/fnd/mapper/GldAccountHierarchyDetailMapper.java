package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.GldAccountHierarchyDetail;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * GldAccountHierarchyDetailMapper
 * </p>
 * 
 * @author guiyu 2019/01/08 15:34
 */
public interface GldAccountHierarchyDetailMapper extends Mapper<GldAccountHierarchyDetail> {
    /**
     * 根据accountSetId删除gld_account_hierarchy_detail表数据
     *
     * @param accountSetId meaning
     * @return void
     */
    public void deleteByAccountSetId(@Param("accountSetId") Long accountSetId);
}
