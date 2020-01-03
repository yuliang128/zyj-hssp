package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoRepEleRefLnObj;
import com.hand.hec.exp.dto.ExpMoRepTypeRefHdObj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * ExpMoRepEleRefLnObjMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 14:46
 */
public interface ExpMoRepEleRefLnObjMapper extends Mapper<ExpMoRepEleRefLnObj> {
    int queryRepEleRefLnObj(ExpMoRepTypeRefHdObj dto);

    List<ExpMoRepEleRefLnObj> queryLnDftObject(@Param("moExpReportTypeId") Long moExpReportTypeId,
                    @Param("reportPageElementCode") String reportPageElementCode);
}
