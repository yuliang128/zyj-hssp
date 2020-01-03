package com.hand.hec.bgt.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtStructure;
import com.hand.hec.fnd.dto.FndDimensionValue;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 预算表Service
 * </p>
 *
 * @author mouse 2019/01/07 17:05
 */
public interface IBgtStructureService extends IBaseService<BgtStructure>, ProxySelf<IBgtStructureService> {
    /**
     * 查询所有预算表信息
     *
     * @param bgtStructure
     * @author guiyuting 2019-03-05 13:48
     * @return
     */
    List<BgtStructure> queryAll(IRequest request, BgtStructure bgtStructure, int page, int pageSize);

    @Override
    List<BgtStructure> batchUpdate(IRequest request, List<BgtStructure> list);

    /**
     * 预算表数据更新
     *
     * @param bgtStructure
     * @author guiyuting 2019-03-05 15:25
     * @return
     */
    BgtStructure updateBgtStructure(IRequest request, BgtStructure bgtStructure);

    /**
     * 检查预算表是否被预算日记账引用
     *
     * @param structureId 预算表ID
     * @author guiyuting 2019-03-05 15:48
     * @return true被引用，false未被引用
     */
    boolean budgetStructureInvokeCheck(Long structureId);

    /**
     * 预算检查的预算表符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param filtrateMethod 控制规则取值方式
     * @param structureCodeFrom 控制规则预算表代码从
     * @param structureCodeTo 控制规则预算表代码到
     * @author YHL 2019-03-05 19:34
     * @return java.util.List<com.hand.hec.bgt.dto.BgtStructure> 符合的预算表
     */
    List<BgtStructure> checkBgtStructure(String controlType, String filtrateMethod, String structureCodeFrom,
                    String structureCodeTo);

    /**
     * 根据预算组织ID查询预算表
     *
     * @param request
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-14 13:49
     * @return java.util.List<com.hand.hec.bgt.dto.BgtStructure> 查询到的预算表
     */
    List<BgtStructure> getBgtStructureByBgtOrgId(IRequest request, Long bgtOrgId);

    /**
     * 获取预算表
     *
     * @param request
     * @param structureId
     * @author mouse 2019-03-14 18:43
     * @return com.hand.hec.bgt.dto.BgtStructure
     */
    BgtStructure getBgtStructure(IRequest request, Long structureId);

    /**
     * 获取预算表的某个维度的默认维值
     *
     * @param request
     * @param structureId
     * @param sequence
     * @author mouse 2019-03-14 19:14
     * @return com.hand.hec.fnd.dto.FndDimensionValue
     */

    FndDimensionValue getDefaultDimensionValue(IRequest request, Long structureId, Long sequence);

    /**
     * 获取当前预算表对应的预算配置数据
     *
     * @param structureId
     * @param position
     * @author mouse 2019-03-21 10:48
     * @return java.util.List<java.util.Map>
     */
    List<Map> getStructureDimInfo(IRequest request, Long structureId, String position);

    /**
     * 根据预算组织ID 查询与预算日记账类型相关联的预算表信息
     *
     * @param bgtOrgId 预算组织ID
     * @param bgtJournalTypeId 预算日记账类型ID
     * @author guiyuting 2019-03-26 16:48
     * @return 符合条件的预算表信息
     */
    List<BgtStructure> queryForBgtJournal(IRequest request, Long bgtOrgId, Long bgtJournalTypeId);
}
