package com.hand.hec.csh.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshMoRepayRegAsgnCom;

/**
 * <p>
 *
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/04/25 09:55
 */
public interface CshMoRepayRegAsgnComMapper extends Mapper<CshMoRepayRegAsgnCom> {
    /**
     * 复写:还款登记单类型定义-分配公司页面基础查询
     *
     * @param condition
     * @return java.util.List<com.hand.hec.csh.dto.CshMoRepayRegAsgnCom>
     * @author jialin.xing@hand-china.com 2019-04-25 09:55
     */
    @Override
    List<CshMoRepayRegAsgnCom> select(CshMoRepayRegAsgnCom condition);

    /**
     * 还款登记单类型定义-批量分配公司基础查询
     *
     * @param condition
     * @param magOrgId
     * @return java.util.List<com.hand.hap.fnd.dto.FndCompany>
     * @author jialin.xing@hand-china.com 2019-04-25 12:09
     */
    List<CshMoRepayRegAsgnCom> queryUnallocatedCompanies(@Param("dto") CshMoRepayRegAsgnCom condition, @Param("magOrgId") Long magOrgId);

}