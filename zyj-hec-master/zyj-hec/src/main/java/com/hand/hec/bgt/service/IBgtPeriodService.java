package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtPeriod;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 预算期间Service
 * </p>
 *
 * @author mouse 2019/01/07 17:02
 */
public interface IBgtPeriodService extends IBaseService<BgtPeriod>, ProxySelf<IBgtPeriodService> {

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
    List<BgtPeriod> checkBgtPeriod(String controlType, Long bgtOrgId, String filtrateMethod, String periodName,
                    String controlPeriodFrom, String controlPeriodTo);

    /**
     * 校验输入的年份
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
     * 校验期间是否被使用
     *
     * @param periodSetId 预算期间ID
     * @param yearFrom 年份从
     * @param yearTo 年份到
     * @return 返回1表示已被使用，0表示未被使用
     * @author guiyuting 2019-03-08 15:28
     */
    int checkPeriodUsed(@Param("periodSetId") Long periodSetId, @Param("yearFrom") Long yearFrom,
                    @Param("yearTo") Long yearTo);

    /**
     * 根据年份保存
     *
     * @param period
     * @return null
     * @author guiyuting 2019-03-08 15:28
     */
    void batchSubmit(IRequest request, BgtPeriod period);

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
    BgtPeriod getBgtPeriodYear(Long bgtOrgId, String periodName);

    /*
     * 根据预算组织ID获取预算期间下拉框
     * 
     * @param request
     * 
     * @param bgtOrgId 预算组织ID
     * 
     * @author YHL 2019-03-15 10:20
     * 
     * @return java.util.List<com.hand.hec.bgt.dto.BgtPeriod> 该预算组织的预算期间
     */
    List<BgtPeriod> getBgtPeriodOption(IRequest request, Long bgtOrgId);


    /**
     * 季度下拉框
     *
     * @param request
     * @author YHL 2019-03-15 13:31
     * @return java.util.List<com.hand.hec.bgt.dto.BgtPeriod> 季度
     */
    List<BgtPeriod> getQuarterOption(IRequest request);

    /**
     * 根据预算组织ID获取预算年度下拉框
     *
     * @param request
     * @param bgtOrgId 预算组织ID
     * @author YHL 2019-03-15 13:57
     * @return java.util.List<com.hand.hec.bgt.dto.BgtPeriod> 该预算组织的预算年度
     */
    List<BgtPeriod> getPeriodYearOption(IRequest request, Long bgtOrgId);

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
    String getBgtPeriodName(Date date, Long bgtEntityId, Long companyId, String status);

    /**
     * 检查预算期间的开关情况
     *
     * @param bgtOrgId
     * @param periodName
     * @author mouse 2019-03-27 14:42
     * @return java.lang.Boolean
     */
    Boolean checkPeriodOpen(IRequest request, Long bgtOrgId, Long bgtEntityId, String periodName);

    /**
     * 预算余额币种扩展获取季度期间名
     *
     * @param bgtPeriod
     * @author guiyuting 2019-03-28 19:04
     * @return
     */
    String queryQuarterPeriodName(BgtPeriod bgtPeriod);

    /**
     * 预算余额币种扩展获取季度期间名
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
    List<String> getPeriodNamesForSummary(IRequest request, BgtPeriod bgtPeriod);

}
