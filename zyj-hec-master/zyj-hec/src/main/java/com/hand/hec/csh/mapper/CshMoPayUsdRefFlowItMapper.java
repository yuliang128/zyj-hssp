package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshMoPayUsdRefFlowIt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 付款用途联系现金流量项Mapper
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */
public interface CshMoPayUsdRefFlowItMapper extends Mapper<CshMoPayUsdRefFlowIt> {
    /**
     * 根据条件查询付款用途联系现金流量项
     *
     * @param cshMoPayUsdRefFlowIt
     * @return 符合条件的付款用途联系现金流量项
     */
    List<CshMoPayUsdRefFlowIt> queryIncludeSetOfBooks(CshMoPayUsdRefFlowIt cshMoPayUsdRefFlowIt);

    /**
     * <p>获取现金流量项(对应exp_util_pkg.get_cash_flow_item_id)<p/>
     *
     * @param paymentUsedeId 付款用途ID
     * @param accEntityId 核算主体ID
     * @return 现金流量项ID
     * @author yang.duan 2019/3/13 18:38
     */
    Long getCashFlowItemId(@Param("paymentUsedeId") Long paymentUsedeId, @Param("accEntityId") Long accEntityId);
}