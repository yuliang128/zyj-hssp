package com.hand.hec.expm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.expm.dto.ExpReportAccount;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * ExpReportAccountMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 15:01
 */
public interface ExpReportAccountMapper extends Mapper<ExpReportAccount>{
    /**
     *获取凭证创建的日期
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/7 10:46
     *@param expReportHeaderId 报销单头Id
     *@return Date
     *@Version 1.0
     **/
    Date getJournalDate(@Param("expReportHeaderId")Long expReportHeaderId);

    /**
     * 单据审核批量更新凭证行数据
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/7 14:36
     * @param expReportHeaderId 报销单头Id
     * @return
     * @Version 1.0
     **/
    ExpReportAccount getDrCrAmountDiff(@Param("expReportHeaderId") Long expReportHeaderId);

    /**
     * 单据审核批量更新凭证行数据
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/7 14:36
     * @param expReportHeaderId 报销单头Id
     * @return
     * @Version 1.0
     **/
    void batchUpdate(@Param("expReportHeaderId") Long expReportHeaderId);

    /**
     *根据报销单头Id获取凭证信息
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/8 16:27
     *@param expReportHeaderId 报销单头Id
     *@return List<ExpReportAccount>
     *@Version 1.0
     **/
    List<ExpReportAccount> getExpReportAccountInfo(@Param("expReportHeaderId") Long expReportHeaderId);

    /**
     *根据核算主体Id获取账套Id
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/8 18:01
     *@param accEntityId 核算主体Id
     *@return Long
     *@Version 1.0
     **/
    Long selectSetOfBooksId(@Param("accEntityId") Long accEntityId);

    /**
     *报销单审核查询凭证信息
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/8 17:08
     *@param dto 查询条件
     *@return List<ExpReportAccount>
     *@Version 1.0
     **/
    List<ExpReportAccount> auditAccountQuery(ExpReportAccount dto);
}