package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoReqTypeAsgnCom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/21 13:51
 * Description:申请单类型定义分配公司Mapper
 */
public interface ExpMoReqTypeAsgnComMapper extends Mapper<ExpMoReqTypeAsgnCom> {

    /**
     * 查询当前未分配的公司列表
     *
     * @param magOrgId       关联组织
     * @param moExpReqTypeId 申请单类型
     * @return 返回公司信息
     * @author jiangxz 2019/2/21 14:27
     */
    List<ExpMoReqTypeAsgnCom> queryFndCompanyTypeId(@Param("magOrgId") Long magOrgId, @Param("moExpReqTypeId") Long moExpReqTypeId);
}