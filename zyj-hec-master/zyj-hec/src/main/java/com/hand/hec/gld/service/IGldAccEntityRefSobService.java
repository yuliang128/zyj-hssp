package com.hand.hec.gld.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldAccEntityRefSob;

/**
 * <p>
 * IGldAccEntityRefSobService
 * </p>
 *
 * @author yang.duan 2019/01/10 13:49
 */
public interface IGldAccEntityRefSobService extends IBaseService<GldAccEntityRefSob>, ProxySelf<IGldAccEntityRefSobService> {


    /**
     * 核算主体分配更多的账套需要查询的信息，包括科目表的关联信息
     *
     * @param accEntityId 核算主体ID
     * @param request
     * @Param page
     * @Param pageSize
     * @return 符合要求的核算关联账套的信息集合
     * @author ngls.luhui 2019-01-18 18:15
     */
    public List<GldAccEntityRefSob> queryMoreSob(Long accEntityId, IRequest request,Integer page,Integer pageSize);
}