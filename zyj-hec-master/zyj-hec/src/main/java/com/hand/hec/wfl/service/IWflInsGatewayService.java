package com.hand.hec.wfl.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflInsGateway;

public interface IWflInsGatewayService extends IBaseService<WflInsGateway>, ProxySelf<IWflInsGatewayService>{
    WflInsGateway selectInsGatewayById(Long insGatewayId);
}