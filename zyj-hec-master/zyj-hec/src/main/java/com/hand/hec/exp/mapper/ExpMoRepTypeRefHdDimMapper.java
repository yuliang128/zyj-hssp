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
 * ExpMoRepTypeRefHdDimMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 14:48
 */
public interface ExpMoRepTypeRefHdDimMapper extends Mapper<ExpMoRepTypeRefHdDim>{
    int queryRepTypeRefHdDim(ExpMoRepEleRefLnDim dto);
    /**
     * <p>获取报销单类型关联头维度的默认维值<p/>
     *
     * @param moExpReportTypeId 报销单类型ID
     * @param sequence 维度顺序
     * @return 维值DTO
     * @author yang.duan 2019/3/21 14:27
     */
    FndDimensionValue getDftDimensionValue(@Param("moExpReportTypeId") Long moExpReportTypeId,
                                           @Param("sequence") Long sequence);

    List<Map> getHeaderDimInfo(@Param("moExpReportTypeId") Long moExpReportTypeId);
}