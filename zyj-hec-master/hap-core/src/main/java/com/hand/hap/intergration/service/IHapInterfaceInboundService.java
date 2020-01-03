package com.hand.hap.intergration.service;

import javax.servlet.http.HttpServletRequest;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.intergration.dto.HapInterfaceInbound;
import com.hand.hap.system.service.IBaseService;

public interface IHapInterfaceInboundService
        extends IBaseService<HapInterfaceInbound>, ProxySelf<IHapInterfaceInboundService> {

    @Deprecated
    int inboundInvoke(HttpServletRequest request, HapInterfaceInbound inbound, Throwable throwable);

    int inboundInvoke(HapInterfaceInbound inbound);
}