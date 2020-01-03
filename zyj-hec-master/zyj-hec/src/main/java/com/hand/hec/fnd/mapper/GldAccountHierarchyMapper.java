package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.GldAccountHierarchy;
import com.hand.hec.fnd.dto.GldAccountHierarchyTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * GldAccountHierarchyMapper
 * </p>
 *
 * @author guiyu 2019/01/08 15:34
 */
public interface GldAccountHierarchyMapper extends Mapper<GldAccountHierarchy> {

    /**
     * 科目层次结构树查询
     *
     * @param accountSetId accountSetId
     * @return List<GldAccountHierarchyTree>
     */
    public List<GldAccountHierarchyTree> accountHierarchyTreeQuery(@Param("accountSetId") Long accountSetId);

}
