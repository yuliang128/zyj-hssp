package com.hand.hec.pur.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.pur.dto.PurVenderAccountRefAe;

/**
 * <p>
 * 供应商银行账户核算主体分配Mapper接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/21 14:25
 */
public interface PurVenderAccountRefAeMapper extends Mapper<PurVenderAccountRefAe> {

    /**
     * 供应商印上账户核算主体分配基础查询
     *
     * @param accountId 核算主体ID
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderAccountRefAe>
     * @author jialin.xing@hand-china.com 2019-02-21 17:26
     */
    List<PurVenderAccountRefAe> queryByAccountId(@Param("accountId") Long accountId);

    /**
     * 根绝账户ID与供应商ID查询银行账户下未分配的核算主体
     *
     * @param accountId 账户ID
     * @param venderId  供应商ID
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderAccountRefAe>
     * @author jialin.xing@hand-china.com 2019-02-22 14:05
     */
    List<PurVenderAccountRefAe> queryAccEntityByVenderIdAndAccountId(@Param("accountId") Long accountId, @Param("venderId") Long venderId);

    /**
     * 检查当前核算主体与作为同一供应商下不同银行账号的主账号数量
     *
     * @param dto dto
     * @return java.lang.Long
     * @author jialin.xing@hand-china.com 2019-02-22 16:19
     */
    Long checkMainAccount(PurVenderAccountRefAe dto);

}