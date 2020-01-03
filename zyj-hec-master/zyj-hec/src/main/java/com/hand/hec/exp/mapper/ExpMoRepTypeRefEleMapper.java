package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoRepTypeRefEle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * ExpMoReportTypeAsgnComMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 14:47
 */
public interface ExpMoRepTypeRefEleMapper extends Mapper<ExpMoRepTypeRefEle> {

    /**
     * <p>查询报销单行页面元素<p/>
     *
     * @param expMoReportTypeId 报销单类型ID
     * @param expReportHeaderId 报销单头ID
     * @return 行页面元素list
     * @author yang.duan 2019/3/26 16:58
     */
    List<ExpMoRepTypeRefEle> queryExpReportLineEle(@Param("expMoReportTypeId") Long expMoReportTypeId, @Param("expReportHeaderId") Long expReportHeaderId);

    List<ExpMoRepTypeRefEle> queryInvoiceAndTaxFlag(@Param("moExpReportTypeId") Long moExpReportTypeId, @Param("reportPageElementCode") String reportPageElementCode);
}
