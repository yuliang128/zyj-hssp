package com.hand.hec.pur.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.pur.dto.PurVenderAccount;

/**
 * <p>
 * 供应商银行账户Mapper接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/21 14:25
 */
public interface PurVenderAccountMapper extends Mapper<PurVenderAccount> {

    /**
     * 系统级供应商银行账户页面基础查询
     *
     * @param venderId 供应商ID
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderAccount>
     * @author jialin.xing@hand-china.com 2019-02-21 14:32
     */
    List<PurVenderAccount> queryByVenderId(@Param("venderId") Long venderId);

    /**
     * 供应商银行账户页面基础查询
     *
     * @param venderId    供应商ID
     * @param accEntityId 核算主体ID
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderAccount>
     * @author jialin.xing@hand-china.com 2019-03-02 19:25
     */
    List<PurVenderAccount> queryByVenderIdAndAccEntityId(@Param("venderId") Long venderId,
                                                         @Param("accEntityId") Long accEntityId);

}