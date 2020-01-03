package com.hand.hec.bgt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtCenter;

/**
 * <p>
 * 预算中心Service
 * </p>
 *
 * @author mouse 2019/01/07 16:55
 */
public interface IBgtCenterService extends IBaseService<BgtCenter>, ProxySelf<IBgtCenterService> {

    /**
     * 预算检查的预算中心符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param bgtOrgId 预算组织ID
     * @param filtrateMethod 控制规则取值方式
     * @param centerId 当前占用行预算中心ID
     * @param controlCenterCodeFrom 控制规则预算中心代码从
     * @param controlCenterCodeTo 控制规则预算中心代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的预算中心
     */
    List<BgtCenter> checkBgtCenter(String controlType, Long bgtOrgId, String filtrateMethod, Long centerId,
                    String controlCenterCodeFrom, String controlCenterCodeTo);

    /**
     * 预算中心定义主页面查询
     *
     * @param sourceTypeCode 来源类型code
     * @param sourceTypeCode 预算中心code 可选查询条件
     * @param sourceTypeCode 预算中心名称 可选查询条件
     * @param page 分页
     * @param pageSize 分页
     * @param request 包含了本地语言信息等
     * @author ngls.luhui 2019-02-19 19:19
     * @return 对应预算组织的预算中心
     */
    List<BgtCenter> querytMain(String sourceTypeCode, Long bgtOrgId, String centerCode, String description,
                    IRequest request, Integer page, Integer pageSize);

    /**
     * 预算中心同步方法
     *
     * @param bgtOrgId 预算组织id
     * @param souceTypeCode 来源类型
     * @author ngls.luhui 2019-02-21 18:52
     * @return
     */
    Boolean manual(Long bgtOrgId, String souceTypeCode, IRequest request);

    /**
     * 预算实体同步预算中心
     *
     * @param bgtOrgId 预算组织ID
     * @param entityIds 预算实体ID数组
     * @author guiyuting 2019-02-27 16:58
     * @return
     */
    Boolean centerSync(IRequest request, Long bgtOrgId, List<Long> entityIds);

    /**
     * 根据部门和预算实体获取默认的预算中心
     *
     * @param request
     * @param unitId
     * @param entityId
     * @author mouse 2019-03-14 17:09
     * @return com.hand.hec.bgt.dto.BgtCenter
     */
    BgtCenter getDefaultCenterByUnit(IRequest request, Long unitId, Long entityId);

    /**
     * 预算日记账类型定义分配预算中心
     *
     * @param bgtOrgId 预算组织ID
     * @param rangeEtsId 预算日记账类型定义分配预算实体ID
     * @author guiyuting 2019-03-20 10:55
     * @return 符合条件的预算中心
     */
    List<BgtCenter> bgtJournalBatch(IRequest request, Long bgtOrgId, Long rangeEtsId);

    /**
     * 查询该预算中心的明细中心
     *
     * @param centerId 预算中心ID
     * @author guiyuting 2019-03-29 16:04
     * @return
     */
    List<BgtCenter> queryDetailCenter(Long centerId);
}
