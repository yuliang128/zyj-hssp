package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldAccGenSysGeneral;

import java.util.List;

public interface GldAccGenSysGeneralMapper extends Mapper<GldAccGenSysGeneral>{
    /**
     *查询通用匹配组的数据
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/27 15:14
     *@param gldAccGenSysGeneral 查询的dto
     *@return List<GldAccGenSysGeneral>
     *@Version 1.0
     **/
    List<GldAccGenSysGeneral> query(GldAccGenSysGeneral gldAccGenSysGeneral);
}