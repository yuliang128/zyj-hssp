package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoWriteCaptionLn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpMoWriteCaptionLnMapper extends Mapper<ExpMoWriteCaptionLn> {
    /**
     * <p>获取单据类型对应的单据填写说明(对应原hec_util.exp_mo_write_caption)<p/>
     *
     * @param docCategory 单据类别
     * @param docTypeId 单据类型ID
     * @return 单据填写说明行list
     * @author yang.duan 2019/3/15 19:30
     */
    List<ExpMoWriteCaptionLn> getWriteCaptionByDocType(@Param("docCategory") String docCategory,
                                                       @Param("docTypeId") Long docTypeId);
}