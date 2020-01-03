package com.hand.hec.fnd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndTaxTypeCode;

/**
 * <p>
 * 税率定义Mapper接口
 * </p>
 * 
 * @author jialin.xing@hand-china.com 2019/02/28 14:43
 */
public interface FndTaxTypeCodeMapper extends Mapper<FndTaxTypeCode>{

    /**
     * 根据核算主体ID查找税率信息
     *
     * @param accEntityId 核算主体ID
     * @author jialin.xing@hand-china.com 2019-02-28 14:44
     * @return java.util.List<com.hand.hec.fnd.dto.FndTaxTypeCode>
     */
    List<FndTaxTypeCode> queryByAccEntityId(@Param("accEntityId")Long accEntityId);

    /**
     * <p>获取税率信息下拉框</p>
     *
     * @return List<FndTaxTypeCode>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/4/18 9:59
    **/
    List<FndTaxTypeCode> queryTaxTypeWithholding();
}