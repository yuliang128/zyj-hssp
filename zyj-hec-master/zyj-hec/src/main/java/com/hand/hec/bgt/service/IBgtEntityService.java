package com.hand.hec.bgt.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtEntity;

/**
 * <p>
 * 预算实体Service
 * </p>
 *
 * @author mouse 2019/01/07 16:59
 */
public interface IBgtEntityService extends IBaseService<BgtEntity>, ProxySelf<IBgtEntityService> {

    /**
     * 预算检查的预算实体符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param bgtOrgId 预算组织ID
     * @param filtrateMethod 控制规则取值方式
     * @param entityId 当前占用行预算实体ID
     * @param controlEntityCodeFrom 控制规则预算实体代码从
     * @param controlEntityCodeTo 控制规则预算实体代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的预算实体
     */
    List<BgtEntity> checkBgtEntity(String controlType, Long bgtOrgId, String filtrateMethod, Long entityId,
                    String controlEntityCodeFrom, String controlEntityCodeTo);

    /**
     * 查询默认预算主体
     *
     * @param companyId
     * @author guiyu 2019-01-22 19:01
     * @return 该公司对应的预算主体
     */
    BgtEntity queryDefaultBgtEntity(IRequest request, Long companyId);


    /**
     * 预算实体汇总类型批量分配明细类型
     *
     * @param request
     * @param bgtEntity
     * @param page
     * @param pageSize
     * @author guiyuting 2019-02-25 16:02
     * @return 符合条件的预算实体
     */
    List<BgtEntity> queryForBatch(IRequest request, BgtEntity bgtEntity, int page, int pageSize);

    /**
     * 预算实体同步
     *
     * @param request
     * @param bgtOrgId 预算组织ID
     * @author guiyuting 2019-02-25 16:54
     * @return
     */
    boolean sync(IRequest request, Long bgtOrgId);

    /**
     * 根据公司ID 查询类型不为SUMMARY,并且与管理组织相关联的预算实体
     *
     * @param companyId 公司ID
     * @author guiyuting 2019-02-28 16:32
     * @return
     */
    List<Map<String, Object>> queryByCompanyId(IRequest request, Long companyId);

    /**
     * 预算实体页面根据预算组织查询预算实体
     *
     * @param bgtEntity 预算实体信息
     * @author guiyuting 2019-03-07 15:31
     * @return 符合条件的预算实体
     */
    List<BgtEntity> queryAll(IRequest request, BgtEntity bgtEntity, int page, int pageSize);

    /**
     * 预算日记账类型定义批量分配预算主体
     *
     * @param bgtOrgId 预算组织ID
     * @param rangeId 指定范围ID
     * @author guiyuting 2019-03-20 10:19
     * @return 符合条件的预算实体
     */
    List<BgtEntity> bgtJournalBatch(IRequest request, Long bgtOrgId, Long rangeId);

    /**
     * 查询该预算实体的明细实体
     *
     * @param entityId 预算实体ID
     * @author guiyuting 2019-03-29 16:16
     * @return
     */
    List<BgtEntity> queryDetailEntity(Long entityId);
}
