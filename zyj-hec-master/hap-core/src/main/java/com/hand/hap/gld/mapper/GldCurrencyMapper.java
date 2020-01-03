package com.hand.hap.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.gld.dto.GldCurrency;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 币种Mapper接口
 * </p>
 *
 * @author MouseZhou 2019/01/07 10:36
 */
public interface GldCurrencyMapper extends Mapper<GldCurrency> {
    /**
     * @Description 获取本位币对应的小数精度
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/19 15:26
     * @Param currencyCode 本位币币种
     * @Version 1.0
     **/
    int getPrecision(@Param("currencyCode") String currencyCode);

    /**
     * <p>获取币种对应的业务精度</p>
     *
     * @param currencyCode
     * @return 业务精度
     * @author yang.duan 2019/5/5 17:04
    **/
    int getTransactionPrecision(@Param("currencyCode") String currencyCode);

    /**
     * 预算检查的预算币种符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param filtrateMethod 控制规则取值方式
     * @param currencyCodeFrom 控制规则币种代码从
     * @param currencyCodeTo 控制规则币种代码到
     * @author YHL 2019-03-05 19:06
     * @return java.util.List<com.hand.hec.fnd.dto.GldCurrency> 符合的预算币种
     */
    List<GldCurrency> checkBgtCurrency(@Param("controlType") String controlType,
                    @Param("filtrateMethod") String filtrateMethod, @Param("currencyCodeFrom") String currencyCodeFrom,
                    @Param("currencyCodeTo") String currencyCodeTo);

    /**
     * <p>
     * 获取核算主体本位币
     * <p/>
     *
     * @param accEntityId 核算主体ID
     * @return String 本位币币种
     * @author yang.duan 2019/3/6 15:20
     */
    String getAccEntityFunCurrCode(@Param("accEntityId") Long accEntityId);
}
