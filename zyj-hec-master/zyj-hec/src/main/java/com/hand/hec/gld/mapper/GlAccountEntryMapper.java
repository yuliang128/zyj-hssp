package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GlAccountEntry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 总账分录Mapper
 * </p>
 *
 * @author Tagin 2019/03/29 10:34
 */
public interface GlAccountEntryMapper extends Mapper<GlAccountEntry> {


    /**
     * 更新总账分录导入状态
     *
     * @author Tagin
     * @date 2019-03-29 10:49
     * @param importedFlag 导入标识
     * @param transactionHeaderId 现金事务头ID
     * @param transactionType 现金事务类型
     * @return void
     * @version 1.0
     **/
    void updateImportedFlag(@Param("importedFlag") String importedFlag,
                    @Param("transactionHeaderId") Long transactionHeaderId,
                    @Param("transactionType") String transactionType);

    /**
     * 查询现金事物凭证信息
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/5/9 17:19
     *@param headerId 现金事物头Id
     *@return List<GldAccountEntry>
     *@Version 1.0
     **/
    List<GlAccountEntry> queryCshTransactionAccount(@Param("headerId") Long headerId);

    /**
     * 查询报销单凭证信息
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/5/9 17:19
     *@param headerId 报销单头Id
     *@return List<GldAccountEntry>
     *@Version 1.0
     **/
    List<GlAccountEntry> queryExpReportAccount(@Param("headerId")Long headerId);

    /**
     * 查询核销凭证信息
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/5/9 17:19
     *@param headerId 报销单头Id
     *@return List<GldAccountEntry>
     *@Version 1.0
     **/
    List<GlAccountEntry> queryCshWriteOffAccount(@Param("headerId")Long headerId);

    /**
     * 查询借款单凭证信息
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/5/9 17:19
     *@param headerId 报销单头Id
     *@return List<GldAccountEntry>
     *@Version 1.0
     **/
    List<GlAccountEntry> queryCshPmtReqAccount(@Param("headerId")Long headerId);

    /**
     * 查询付款申请单凭证信息
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/5/9 17:19
     *@param headerId 付款申请单Id
     *@return List<GldAccountEntry>
     *@Version 1.0
     **/
    List<GlAccountEntry> queryAcpReqAccount(@Param("headerId")Long headerId);

}
