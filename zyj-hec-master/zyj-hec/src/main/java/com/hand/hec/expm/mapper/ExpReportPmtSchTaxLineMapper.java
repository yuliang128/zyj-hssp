package com.hand.hec.expm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.expm.dto.ExpReportPmtSchTaxLine;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * ExpReportPmtSchTaxLineMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 15:03
 */
public interface ExpReportPmtSchTaxLineMapper extends Mapper<ExpReportPmtSchTaxLine>{
    /**
     * <p>获取税额<p/>
     *
     * @param expReportHeaderId 报销单头ID
     * @return 税额
     * @author yang.duan 2019/3/12 14:07
     */
    BigDecimal getTaxAmountByHeaderId(@Param("expReportHeaderId") Long expReportHeaderId);
}