package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.GldPeriod;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会计期间Mapper接口
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:42
 */
public interface GldPeriodMapper extends Mapper<GldPeriod> {

    /**
     * 检查期间是否已存在
     *
     * @param periodSetId 会计期id
     * @param yearFrom 年度从
     * @param yearTo 年度到
     * @author LJK 2019-01-22 14:13
     * @return count
     */
    Integer checkPeriodExists(@Param("periodSetId") Long periodSetId, @Param("yearFrom") Long yearFrom,
                    @Param("yearTo") Long yearTo, @Param("internalPeriodNum") Long internalPeriodNum);

    /**
     * 检查期间是否已被使用
     *
     * @param periodSetId 会计期id
     * @param yearFrom 年度从
     * @param yearTo 年度到
     * @author LJK 2019-01-22 14:13
     * @return count
     */
    Integer checkPeriodUsed(@Param("periodSetId") Long periodSetId, @Param("yearFrom") Long yearFrom,
                    @Param("yearTo") Long yearTo);

    /**
     * 检查期间是否存在于核算主体级控制表
     *
     * @param periodSetId 会计期id
     * @param internalPeriodNum 期间序号
     * @author LJK 2019-01-23 14:13
     * @return count
     */
    Integer checkPeriodInStatus(@Param("periodSetId") Long periodSetId,
                    @Param("internalPeriodNum") Long internalPeriodNum);

    /**
     * 根据会计期id、年、月份查询期间id
     *
     * @param periodSetId 会计期id
     * @param periodYear 年
     * @param periodNum 月份
     * @author LJK 2019-01-23 14:13
     * @return count
     */
    Long periodIdQuery(@Param("periodSetId") Long periodSetId, @Param("periodYear") Long periodYear,
                    @Param("periodNum") Long periodNum);

    /**
     *获取期间序号
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/18 14:24
     * @param  accEntityId 核算主体ID
     * @param periodName 期间名称
     * @return Long
     * @Version 1.0
     **/
    Long getInternalPeriodNum(@Param("accEntityId") Long accEntityId, @Param("periodName") String periodName);

    /**
     * 获取会计期间
     *
     * @Author Tagin
     * @Date 2019-02-20 17:20
     * @param date 日期
     * @param accEntityId 核算主体ID
     * @param status 状态 "O" 或者 为空
     * @return  java.lang.String
     * @Version 1.0
     **/
    String getPeriodName(@Param("date") Date date, @Param("accEntityId") Long accEntityId,
                    @Param("status") String status);

    /**
     *  获取期间的第一天
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/18 14:24
     * @param periodName 期间名称
     * @param accEntityId 核算主体ID
     * @return Date
     * @Version 1.0
     **/
    Date getFirstDate(String periodName, Long accEntityId);



     /**
      * 根据条件查询GldPeriod
      * @param  record 查询条件
      * @author rui.shi@hand-china.com 2019-03-08 15:17
      * @return
      */
	List<GldPeriod> queryGldPeriod(GldPeriod record);


     /**
      * 查询上一个期间序号
      * @param  gldPeriod  gldPeriod
      * @author rui.shi@hand-china.com 2019-03-08 15:17
      * @return 前一个期间序号
      */
	Long queryLastInternalPeriodNum(GldPeriod gldPeriod);


	/**
      *  查询下一个期间序号
      * @param  periodSetCode  期间代码
      * @param  internalPeriodNum  当前期间序号
      * @author rui.shi@hand-china.com 2019-03-08 15:17
      * @return 下一个期间序号
      */
	Long queryNextInternalPeriodNum(@Param("periodSetCode") String periodSetCode, @Param("internalPeriodNum") Long internalPeriodNum);

    /**
     *根据核算主体和期间名称获取期间数据
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/5/10 14:21
     *@param accEntityId 核算主体Id
     *@param periodName 期间名称
     *@return
     *@Version 1.0
     **/
    GldPeriod queryPeriodByAccEntityAndPeriodName(@Param("accEntityId")Long accEntityId,@Param("periodName") String periodName);
}
