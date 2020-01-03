package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoReportType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * ExpMoReportTypeAsgnComMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 14:47
 */
public interface ExpMoReportTypeMapper extends Mapper<ExpMoReportType>{
    /**
     * <p>根据员工和公司获取报销单类型(对应原exp_mo_report_type_list.bm)<p/>
     *
     * @param employeeId 员工ID
     * @param companyId 公司ID
     * @return 单据类型list
     * @author yang.duan 2019/3/18 18:17
     */
    List<ExpMoReportType> queryExpMoReportTypeList(@Param("employeeId") Long employeeId,@Param("companyId") Long companyId);

    /**
     * <p>根据公司获取报销单类型<p/>
     *
     * @param companyId 公司ID
     * @return 单据类型list
     * @author yang.duan 2019/3/19 19:30
     */
    List<ExpMoReportType> queryExpReportTypeByCom(@Param("companyId") Long companyId);
}