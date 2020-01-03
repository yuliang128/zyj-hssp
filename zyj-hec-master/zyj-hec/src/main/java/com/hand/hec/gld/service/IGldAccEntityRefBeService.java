package com.hand.hec.gld.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldAccEntityRefBe;

import java.util.List;

/**
 * <p>
 * IGldAccEntityRefBeService
 * </p>
 *
 * @author yang.duan 2019/01/10 13:49
 */
public interface IGldAccEntityRefBeService extends IBaseService<GldAccEntityRefBe>, ProxySelf<IGldAccEntityRefBeService> {

    /**
     * 核算主体批量分配预算实体时，查询出能分配的预算实体
     *
     * @param accentityId 核算主体的id
     * @param request
     * @Param page
     * @Param pageSize
     * @return 该核算主体可分配的预算实体
     * @author ngls.luhui 2019-01-18 18:08
     */
    public List<GldAccEntityRefBe> batchSelect(Long accentityId, IRequest request, Integer page, Integer pageSize);

    /**
     * <p>根据核算主体获取默认预算实体<p/>
     * @param request
     * @param accEntityId meaning
     * @return 预算实体信息
     * @author yang.duan 2019/3/21 10:59
     */
    GldAccEntityRefBe getDeftBgtEntity(IRequest request,Long accEntityId);
}