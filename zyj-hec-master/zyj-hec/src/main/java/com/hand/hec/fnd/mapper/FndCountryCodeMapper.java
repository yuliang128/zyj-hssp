package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndCountryCode;

import java.util.List;

/**
 * <p>
 * FndCountryCodeMapper
 * </p>
 *
 * @author guiyu 2019/01/29 15:28
 */
public interface FndCountryCodeMapper extends Mapper<FndCountryCode>{

    /**
     * 查询国家
     * @author Zhongyu 2019-2-20 16:02
     * @return
     */
    List<FndCountryCode> queryCountry(FndCountryCode dto);
}