package com.hand.hec.pur.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.pur.dto.PurSystemVender;

/**
 * <p>
 * 系统级供应商主数据Service接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/18 15:46
 */
public interface IPurSystemVenderService extends IBaseService<PurSystemVender>, ProxySelf<IPurSystemVenderService> {

    /**
     * 系统级供应商管理页面基础查询
     *
     * @param request  请求上下文
     * @param dto      传入模糊查询参数
     * @param page     页数
     * @param pageSize 页大小
     * @return java.util.List<com.hand.hec.pur.dto.PurSystemVender>
     * @author jialin.xing@hand-china.com 2019-02-20 10:52
     */
    List<PurSystemVender> selectForSysLevel(IRequest request, PurSystemVender dto, int page, int pageSize);

    /**
     * 根据传入核算主体ID进行供应商管理页面基础查询
     *
     * @param request     请求上下文
     * @param dto         传入模糊查询参数
     * @param accEntityId 核算主体ID
     * @param page        页数
     * @param pageSize    页大小
     * @return java.util.List<com.hand.hec.pur.dto.PurSystemVender>
     * @author jialin.xing@hand-china.com 2019-02-28 16:31
     */
    List<PurSystemVender> selectForNonSysLevel(IRequest request, PurSystemVender dto, Long accEntityId, int page, int pageSize);

    /**
     * 根据是否是系统级标志执行不同的batchUpdate逻辑
     *
     * @param requestCtx 请求上下文
     * @param dto dto列表
     * @param systemFlag 系统级标志
     * @author jialin.xing@hand-china.com 2019-03-13 11:27
     * @return java.util.List<com.hand.hec.pur.dto.PurSystemVender>
     */
    List<PurSystemVender> batchUpdateVenders(IRequest requestCtx, List<PurSystemVender> dto,boolean systemFlag);
}