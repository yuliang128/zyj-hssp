package com.hand.hec.gld.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldAccGenSysGeneral;

import java.util.List;

public interface IGldAccGenSysGeneralService extends IBaseService<GldAccGenSysGeneral>, ProxySelf<IGldAccGenSysGeneralService>{
    /**
     *通用用途代码查询
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/27 15:09
     *@param requestContext 请求上下文
     *@param gldAccGenSysGeneral 查询条件
     *@param page 页数
     *@param pagesize 分页大小
     *@return
     *@Version 1.0
     **/
    List<GldAccGenSysGeneral> query(IRequest requestContext, GldAccGenSysGeneral gldAccGenSysGeneral, int page, int pagesize);
}