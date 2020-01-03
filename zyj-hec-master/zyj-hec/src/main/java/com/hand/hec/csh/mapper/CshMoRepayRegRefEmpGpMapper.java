package com.hand.hec.csh.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshMoRepayRegRefEmpGp;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/24 20:23
 */
public interface CshMoRepayRegRefEmpGpMapper extends Mapper<CshMoRepayRegRefEmpGp>{

    /**
     * 复写还款登记单类型定义-员工组分配页面基础查询
     *
     * @param condition condition
     * @author jialin.xing@hand-china.com 2019-04-24 20:24
     * @return java.util.List<com.hand.hec.csh.dto.CshMoRepayRegRefEmpGp>
     */
    @Override
    List<CshMoRepayRegRefEmpGp> select(CshMoRepayRegRefEmpGp condition);

}