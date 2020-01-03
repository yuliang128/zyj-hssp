package com.hand.hec.fnd.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.GldAccountHierarchyDetail;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * IGldAccountHierarchyDetailService
 * </p>
 * 
 * @author guiyu 2019/01/08 15:36
 */
public interface IGldAccountHierarchyDetailService
                extends IBaseService<GldAccountHierarchyDetail>, ProxySelf<IGldAccountHierarchyDetailService> {

    public void deleteByAccountSetId(@Param("accountSetId") Long accountSetId);

}
