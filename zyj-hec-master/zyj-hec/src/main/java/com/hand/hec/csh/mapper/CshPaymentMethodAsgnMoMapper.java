package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentMethodAsgnMo;

import java.util.List;
/**
 * <p>
 * 付款方式分配管理组织 Mapper 接口
 * </p>
 *
 * @author yang.cai@hand-china.com 2019/02/19 15:21
 */
public interface CshPaymentMethodAsgnMoMapper extends Mapper<CshPaymentMethodAsgnMo>{
    /**
     * 查询分配的管理组织
     * @param cshPaymentMethodAsgnMo
     * @return 分配的管理组织
     */
    List<CshPaymentMethodAsgnMo> query(CshPaymentMethodAsgnMo cshPaymentMethodAsgnMo);
}