package com.hand.hec.gld.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldAccountingEntity;
import com.hand.hec.gld.dto.GldResponsibilityCenter;
import com.hand.hec.gld.dto.GldSobRespCenter;

import java.util.List;

/**
 * <p>
 * 账套级成本中心定义Service
 * </p>
 *
 * @author wuxiuxian 2019-01-10
 */
public interface IGldSobRespCenterService extends IBaseService<GldSobRespCenter>, ProxySelf<IGldSobRespCenterService> {
    /**
     *
     * 查询被分配账套级成本中心的核算主体
     *
     * @param request
     * @param condition
     * @param pageNum
     * @param pageSize
     * @author wuxiuxian 2019/2/18
     * @return 返回核算主体
     */
    List<GldResponsibilityCenter> selectAccountingEntity(IRequest request, GldResponsibilityCenter condition, int pageNum, int pageSize);

    /**
     *
     * 查询符合被分配条件的核算主体
     *
     * @param request
     * @param center
     * @param pageNum
     * @param pageSize
     * @return
     * @author wuxiuxian 2019/2/18
     */
    List<GldAccountingEntity> queryAccountingEntity(IRequest request, GldSobRespCenter center, int pageNum, int pageSize);

    List<GldResponsibilityCenter> submitResponsibilityCenter(IRequest request,List<GldResponsibilityCenter> dto);
}