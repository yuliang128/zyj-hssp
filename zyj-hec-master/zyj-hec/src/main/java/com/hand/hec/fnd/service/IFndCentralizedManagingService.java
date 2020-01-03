package com.hand.hec.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndCentralizedManaging;

/**
 * <p>
 * 归口管理Service接口
 * </p>
 *
 * @author YHL 2019/01/21 19:43
 */
public interface IFndCentralizedManagingService
        extends IBaseService<FndCentralizedManaging>, ProxySelf<IFndCentralizedManagingService> {

    /**
     * 查询归口管理
     *
     * @param request
     * @param fndCentralizedManaging 归口管理
     * @param pageNum
     * @param pageSize
     * @author YHL 2019-01-24 10:41
     * @return java.util.List<com.hand.hec.fnd.dto.FndCentralizedManaging> 归口管理
     */
    List<FndCentralizedManaging> selectCentralizedMagList(IRequest request,
            FndCentralizedManaging fndCentralizedManaging, int pageNum, int pageSize);

}