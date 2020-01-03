package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoExpObjTpAsgnCom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/26 17:18
 * Description:费用对象分配公司Mapper
 */
public interface ExpMoExpObjTpAsgnComMapper extends Mapper<ExpMoExpObjTpAsgnCom> {
    /**
     * 查询当前未分配的公司列表
     *
     * @param magOrgId       关联组织
     * @param moExpObjTypeId 申请单类型
     * @return 返回公司信息
     * @author jiangxz 2019/2/21 14:27
     */
    List<ExpMoExpObjTpAsgnCom> queryFndCompanyTypeId(@Param("magOrgId") Long magOrgId, @Param("moExpObjTypeId") Long moExpObjTypeId);
}