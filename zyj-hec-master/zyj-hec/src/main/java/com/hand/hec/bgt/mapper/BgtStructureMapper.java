package com.hand.hec.bgt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtStructure;

/**
 * <p>
 * 预算表Mapper
 * </p>
 *
 * @author mouse 2019/01/07 16:50
 */
public interface BgtStructureMapper extends Mapper<BgtStructure> {
    /**
     * 查询所有预算表信息
     *
     * @param bgtStructure
     * @author guiyuting 2019-03-05 13:48
     * @return
     */
    List<BgtStructure> queryAll(BgtStructure bgtStructure);

    /**
     * 根据StructureId更新预算表信息
     *
     * @param bgtStructure
     * @author guiyuting 2019-03-05 15:45
     * @return
     */
    void updateByStructureId(BgtStructure bgtStructure);

    /**
     * 预算检查的预算表符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param filtrateMethod 控制规则取值方式
     * @param structureCodeFrom 控制规则预算表代码从
     * @param structureCodeTo 控制规则预算表代码到
     * @author YHL 2019-03-05 19:32
     * @return java.util.List<com.hand.hec.bgt.dto.BgtStructure> 符合的预算表
     */
    List<BgtStructure> checkBgtStructure(@Param("controlType") String controlType,
                    @Param("filtrateMethod") String filtrateMethod,
                    @Param("structureCodeFrom") String structureCodeFrom,
                    @Param("structureCodeTo") String structureCodeTo);

    /**
     * 获取当前预算表对应的预算配置数据
     *
     * @param structureId
     * @param position
     * @author mouse 2019-03-21 10:48
     * @return java.util.List<java.util.Map>
     */
    List<Map> getStructureDimInfo(@Param("structureId") Long structureId, @Param("position") String position);

    /**
     * 根据预算组织ID 查询与预算日记账类型相关联的预算表信息
     *
     * @param bgtOrgId 预算组织ID
     * @param bgtJournalTypeId 预算日记账类型ID
     * @author guiyuting 2019-03-26 16:48
     * @return 符合条件的预算表信息
     */
    List<BgtStructure> queryForBgtJournal(@Param("bgtOrgId") Long bgtOrgId,
                    @Param("bgtJournalTypeId") Long bgtJournalTypeId);

}
