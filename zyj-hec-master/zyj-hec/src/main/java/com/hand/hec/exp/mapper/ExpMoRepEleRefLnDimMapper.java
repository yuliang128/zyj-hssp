package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoRepEleRefLnDim;
import com.hand.hec.exp.dto.ExpMoRepTypeRefHdDim;
import com.hand.hec.fnd.dto.FndDimensionValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * ExpMoRepEleRefLnDimMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 14:46
 */
public interface ExpMoRepEleRefLnDimMapper extends Mapper<ExpMoRepEleRefLnDim> {
    int queryEleRefLnDim(ExpMoRepTypeRefHdDim dto);

    List<Map> queryDftDimValue(@Param("companyId") Long companyId, @Param("bgtEntityId") Long bgtEntityId, @Param("accEntityId") Long accEntityId, @Param("moReportTypeId") Long moReportTypeId, @Param("reportPageElementCode") String reportPageElementCode);

}
