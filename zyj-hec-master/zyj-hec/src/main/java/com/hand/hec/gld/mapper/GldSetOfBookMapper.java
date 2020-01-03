package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldSetOfBook;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 账套定义mapper
 * </p>
 *
 * @author wuxiuxian 2019-01-07
 */

public interface GldSetOfBookMapper extends Mapper<GldSetOfBook> {
    /**
     *
     * 查询账套是否被核算主体使用
     *
     * @param setBooksId
     * @return 使用此账套的核算主体个数
     * @author wuxiuxian
     */
    Long judgeUsedByAccounting(Long setBooksId);

    Integer QueryCountByPeriodSetCode(@Param("periodSetCode") String periodSetCode);

    /**
     *
     * 查询账套是否被管理组织使用
     *
     * @param setBooksId
     * @return 使用此账套的管理组织个数
     * @author wuxiuxian
     */
    Long judgeUsedByOrganization(Long setBooksId);

    /**
     * 汇率类型定义主页面账套查询
     *
     * @param magOrgId 管理组织ID
     * @param parameterValue 参数值
     * @author jialin.xing@hand-china.com 2019-03-15 15:33
     * @return java.util.List<com.hand.hec.gld.dto.GldSetOfBook>
     */
    List<GldSetOfBook> querySetOfBookByParam(@Param("magOrgId") Long magOrgId,
                    @Param("parameterValue") String parameterValue);

    /**
     *
     * 查询默认账套
     *
     * @return 返回默认账套
     * @author wuxiuxian
     */
    List<GldSetOfBook> selectDefaultSobByMagOrgId();

    /**
     * 查询当前用户所属的管理组织下的账套
     *
     * @param gldSetOfBook 账套dto
     * @author jialin.xing@hand-china.com 2019-02-18 18:19
     * @return java.util.List<com.hand.hec.gld.dto.GldSetOfBook>
     */
    @Deprecated
    List<GldSetOfBook> querySobByMagOrgId(GldSetOfBook gldSetOfBook);


    /**
     * 根据参数查找功能页面上的账套下拉框数据
     *
     * @param magOrgId 管理组织id
     * @param parameterValue 参数值
     * @return 账套列表
     * @author rui.shi@hand-china.com 2019-02-21
     */
    List<GldSetOfBook> querySetOfBookOptionsByParamValue(@Param("magOrgId") Long magOrgId,
                    @Param("parameterValue") String parameterValue);


    /**
     * 根据账套id查询会计期
     * 
     * @param dto setOfBooksId
     * @return
     */
    List<GldSetOfBook> queryForPeriod(GldSetOfBook dto);

    /**
     * 根据管理组织Id查询账套数据
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/20 17:00
     * @param magOrgId 管理组织Id
     * @return List<GldSetOfBook>
     * @Version 1.0
     **/
    List<GldSetOfBook> queryByMagId(@Param("magOrgId") Long magOrgId);

    /**
     * 获取核算主体默认账套
     *
     * @author Tagin
     * @date 2019-03-27 19:43
     * @param accEntityId 核算主体
     * @return java.util.List<com.hand.hec.gld.dto.GldSetOfBook>
     * @version 1.0
     **/
    List<GldSetOfBook> queryDftSetOffBookByAe(@Param("accEntityId") Long accEntityId);

}

