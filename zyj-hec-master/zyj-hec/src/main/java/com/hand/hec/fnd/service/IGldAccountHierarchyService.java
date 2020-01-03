package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.GldAccountHierarchy;
import com.hand.hec.fnd.dto.GldAccountHierarchyTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * IGldAccountHierarchyService
 * </p>
 *
 * @author guiyu 2019/01/08 15:36
 */
public interface IGldAccountHierarchyService
                extends IBaseService<GldAccountHierarchy>, ProxySelf<IGldAccountHierarchyService> {

    public List<GldAccountHierarchyTree> accountHierarchyTreeQuery(@Param("accountSetId") Long accountSetId);

    public void refreshAccountHierarchy(IRequest request, @Param("accountSetId") Long accountSetId);
}
