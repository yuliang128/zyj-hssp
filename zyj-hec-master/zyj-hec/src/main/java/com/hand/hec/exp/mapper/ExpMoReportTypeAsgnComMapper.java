package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.exp.dto.ExpMoReportTypeAsgnCom;
import com.hand.hap.fnd.dto.FndCompany;

import java.util.List;

/**
 * <p>
 * ExpMoReportTypeAsgnComMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 14:47
 */
public interface ExpMoReportTypeAsgnComMapper extends Mapper<ExpMoReportTypeAsgnCom>{
    List<FndCompany> queryCompanyInfo(ExpMoReportType dto);
}