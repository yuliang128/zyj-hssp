package com.hand.hec.bgt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtCenter;


/**
 * <p>
 * 预算中心Mapper
 * </p>
 *
 * @author mouse 2019/01/07 16:47
 */
public interface BgtCenterMapper extends Mapper<BgtCenter> {

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
    List<BgtCenter> checkBgtCenter(@Param("controlType") String controlType, @Param("bgtOrgId") Long bgtOrgId,
            @Param("filtrateMethod") String filtrateMethod, @Param("centerId") Long centerId,
            @Param("controlCenterCodeFrom") String controlCenterCodeFrom,
            @Param("controlCenterCodeTo") String controlCenterCodeTo);

    /**
     * 预算中心定义主页面查询
     *
     * @param sourceTypeCode 来源类型code
     * @param sourceTypeCode 预算中心code 可选查询条件
     * @param sourceTypeCode 预算中心名称 可选查询条件
     * @author ngls.luhui 2019-02-19 19:19
     * @return 对应预算组织的预算中心
     */
    List<BgtCenter> selectMain(@Param("sourceTypeCode") String sourceTypeCode, @Param("bgtOrgId") Long bgtOrgId,
            @Param("centerCode") String centerCode, @Param("description") String description);

    /**
     * 根据父成本中心ID和预算组织ID查询预算中心信息
     *
     * @param parentRespCenterId 成本中心ID
     * @param bgtOrgId 预算组织ID
     * @author guiyuting 2019-02-28 11:15
     * @return
     */
    List<BgtCenter> queryRefByRespCenter(@Param("parentRespCenterId") Long parentRespCenterId,
            @Param("bgtOrgId") Long bgtOrgId);

    /**
     * 预算日记账类型定义分配预算中心
     *
     * @param bgtOrgId 预算组织ID
     * @param rangeEtsId 预算日记账类型定义分配预算实体ID
     * @author guiyuting 2019-03-20 10:55
     * @return 符合条件的预算中心
     */
    List<BgtCenter> bgtJournalBatch(@Param("bgtOrgId") Long bgtOrgId, @Param("rangeEtsId") Long rangeEtsId);

    /**
     * 根据预算中心代码查询预算中心
     *
     * @param bgtOrgId 预算组织ID
     * @param parameterCode 参数代码（值）
     * @param parameterLowerLimit 参数代码始值
     * @param parameterUpperLimit 参数代码止值
     * @author YHL 2019-03-26 15:31
     * @return java.util.List<com.hand.hec.bgt.dto.BgtCenter> 预算中心
     */
    List<BgtCenter> getBgtCenterByBgtCenterCode(@Param("bgtOrgId") Long bgtOrgId,
            @Param("parameterCode") String parameterCode, @Param("parameterLowerLimit") String parameterLowerLimit,
            @Param("parameterUpperLimit") String parameterUpperLimit);

    /**
     * 查询该预算中心的明细中心
     *
     * @param centerId 预算中心ID
     * @author guiyuting 2019-03-29 16:04
     * @return 
     */
    List<BgtCenter> queryDetailCenter(@Param("centerId") Long centerId);

}
