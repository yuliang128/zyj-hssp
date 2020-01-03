package com.hand.hec.csh.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshMoRepaymentRegType;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/26 13:59
 */
public interface CshMoRepaymentRegTypeMapper extends Mapper<CshMoRepaymentRegType>{

    /**
     *
     * 我的借款申请-借款类型查询
     *
     * @author jialin.xing@hand-china.com 2019-04-26 13:58
     * @return java.util.List<com.hand.hec.csh.dto.CshMoRepaymentRegType>
     */
    List<CshMoRepaymentRegType> queryByCompanyId();

}