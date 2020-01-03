package com.hand.hec.gld.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldMappingCondGrpLn;

import java.util.List;

public interface IGldMappingCondGrpLnService extends IBaseService<GldMappingCondGrpLn>, ProxySelf<IGldMappingCondGrpLnService>{
    /**
     *@Description 获取匹配组行明细
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/24 11:10
     *@Param
     *@Version 1.0
     **/
    List<GldMappingCondGrpLn> selectGroupLines(IRequest request,String groupName);
}