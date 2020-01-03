package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentMethodAsgnCom;

import java.util.List;
/**
 * <p>
 * 付款方式分配公司 Mapper 接口
 * </p>
 *
 * @author yang.cai@hand-china.com 2019/02/19 15:21
 */
public interface CshPaymentMethodAsgnComMapper extends Mapper<CshPaymentMethodAsgnCom>{
    /**
     * 查询符合条件的支付方法配分公司
     * @param cshPaymentMethodAsgnCom
     * @return 返回符合条件的支付方法配分公司
     */
    List<CshPaymentMethodAsgnCom> queryLov(CshPaymentMethodAsgnCom cshPaymentMethodAsgnCom);

    /**
     * 查询分配的公司
     * @param cshPaymentMethodAsgnCom
     * @return 分配的公司
     */
    List<CshPaymentMethodAsgnCom> query(CshPaymentMethodAsgnCom cshPaymentMethodAsgnCom);
}