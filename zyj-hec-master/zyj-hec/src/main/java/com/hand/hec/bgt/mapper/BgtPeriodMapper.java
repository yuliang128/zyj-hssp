package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtPeriod;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 预算期间Mapper
 * </p>
 *
 * @author mouse 2019/01/07 16:48
 */
public interface BgtPeriodMapper extends Mapper<BgtPeriod> {

    /**
     * 预算检查的预算期间符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param bgtOrgId 预算组织ID
     * @param filtrateMethod 控制规则取值方式
     * @param periodName 当前占用行预算期间
     * @param controlPeriodFrom 控制规则预算实体代码从
     * @param controlPeriodTo 控制规则预算实体代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的预算期间
     */
    List<BgtPeriod> checkBgtPeriod(@Param("controlType") String controlType, @Param("bgtOrgId") Long bgtOrgId,
                    @Param("filtrateMethod") String filtrateMethod, @Param("periodName") String periodName,
                    @Param("controlPeriodFrom") String controlPeriodFrom,
                    @Param("controlPeriodTo") String controlPeriodTo);

    /**
     * 校验输入的年份是否已存在
     *
     * @param periodSetId 预算期间ID
     * @param yearFrom 年份从
     * @param yearTo 年份到
     * @return 返回1表示该年份已存在，0表示不存在
     * @author guiyuting 2019-03-08 15:28
     */
    int checkPeriodExist(@Param("periodSetId") Long periodSetId, @Param("yearFrom") Long yearFrom,
                    @Param("yearTo") Long yearTo);


    /**
     * 校验预算组织级期间是否被使用
     *
     * @param periodSetId 预算期间ID
     * @param yearFrom 年份从
     * @param yearTo 年份到
     * @return 返回1表示已被使用，0表示未被使用
     * @author guiyuting 2019-03-08 15:28
     */
    int checkOrgPeriodUsed(@Param("periodSetId") Long periodSetId, @Param("yearFrom") Long yearFrom,
                    @Param("yearTo") Long yearTo);

    /**
     * 校验预算实体级期间是否被使用
     *
     * @param periodSetId 预算期间ID
     * @param yearFrom 年份从
     * @param yearTo 年份到
     * @return 返回1表示已被使用，0表示未被使用
     * @author guiyuting 2019-03-08 15:28
     */
    int checkEntityPeriodUsed(@Param("periodSetId") Long periodSetId, @Param("yearFrom") Long yearFrom,
                    @Param("yearTo") Long yearTo);

    /**
     * 更新bgtPeriod
     *
     * @param bgtPeriod
     * @return null
     * @author guiyuting 2019-03-08 15:28
     */
    void updateByPeriodSetId(BgtPeriod bgtPeriod);

    /**
     * <p>
     * 根据预算组织ID+期间获取年度
     * <p/>
     *
     * @param bgtOrgId 预算组织ID
     * @param periodName 期间
     * @return the return
     * @author yang.duan 2019/3/7 19:34
     */
    BgtPeriod getBgtPeriodYear(@Param("bgtOrgId") Long bgtOrgId, @Param("periodName") String periodName);

    /**
     * 根据预算组织ID获取预算期间下拉框
     *
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-15 10:18
     * @return java.util.List<com.hand.hec.bgt.dto.BgtPeriod> 该预算组织的预算期间
     */
    List<BgtPeriod> getBgtPeriodOption(@Param("bgtOrgId") Long bgtOrgId);

    /**
     * 季度下拉框
     *
     * @author YHL 2019-03-15 13:29
     * @return java.util.List<com.hand.hec.bgt.dto.BgtPeriod> 季度
     */
    List<BgtPeriod> getQuarterOption();

    /**
     * 根据预算组织ID获取预算年度下拉框
     *
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-15 13:56
     * @return java.util.List<com.hand.hec.bgt.dto.BgtPeriod> 该预算组织的预算年度
     */
    List<BgtPeriod> getPeriodYearOption(@Param("bgtOrgId") Long bgtOrgId);

    /**
     * 根据预算期间名称获取预算期间
     *
     * @param periodName 预算期间名称
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-25 15:25
     * @return com.hand.hec.bgt.dto.BgtPeriod 预算期间
     */
    BgtPeriod getPeriodByPeriodName(@Param("periodName") String periodName, @Param("bgtOrgId") Long bgtOrgId);

    /**
     * <p>
     * 获取预算期间
     * <p/>
     *
     * @param date
     * @param bgtEntityId
     * @param companyId
     * @param status
     * @return 预算期间
     * @author yang.duan 2019/3/20 20:52
     */
    String getBgtPeriodName(@Param("date") Date date, @Param("bgtEntityId") Long bgtEntityId,
                    @Param("companyId") Long companyId, @Param("status") String status);

    /**
     * 预算余额币种扩展获取季度期间名
     *
     * @param bgtPeriod
     * @author guiyuting 2019-03-28 19:04
     * @return
     */
    String queryQuarterPeriodName(BgtPeriod bgtPeriod);

    /**
     * 预算余额币种扩展获取年度期间名
     *
     * @param bgtPeriod
     * @author guiyuting 2019-03-28 19:04
     * @return
     */
    String queryYearPeriodName(BgtPeriod bgtPeriod);

    /**
     * 预算余额查询 - 查询在范围内的期间
     *
     * @author guiyuting 2019-05-28 11:14
     * @return 
     */
    List<String> getPeriodNamesForSummary(BgtPeriod bgtPeriod);
}
