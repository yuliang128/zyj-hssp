package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldTransitAccount;

public interface GldTransitAccountMapper extends Mapper<GldTransitAccount>{
    /**
     *@Description 获取中转科目
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/13 14:48
     *@Param gldTransitAccount 作为参数查询传入的dto
     *@Version 1.0
     **/
    GldTransitAccount getTransitAccount(GldTransitAccount gldTransitAccount);
}