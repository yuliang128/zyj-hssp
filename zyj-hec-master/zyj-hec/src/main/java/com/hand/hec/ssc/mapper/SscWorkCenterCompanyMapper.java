package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscWorkCenterCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SscWorkCenterCompanyMapper extends Mapper<SscWorkCenterCompany>{
/**
 *查询共享作业中心业务范围_公司范围
 *Param scopeId
 * @return SscWorkCenterCompany
 * @author bo.zhang 2019-03-18 19:24:36
 */
List<SscWorkCenterCompany> selectSscWorkCenterCompany(@Param("scopeId") Long scopeId);
}