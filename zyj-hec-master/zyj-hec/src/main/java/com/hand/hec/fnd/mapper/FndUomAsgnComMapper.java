package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndUomAsgnCom;
import com.hand.hec.fnd.dto.FndUomAsgnMo;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

public interface FndUomAsgnComMapper extends Mapper<FndUomAsgnCom>{

    /**
     * 根据管理组织查
     * @param dto
     * @param magOrgId
     * @author zhongyu 2019-4-25
     * @return
     */
    List<FndUomAsgnCom> selectByMagOrgId(@Param("dto") FndUomAsgnCom dto, @Param("magOrgId") Long magOrgId);

}