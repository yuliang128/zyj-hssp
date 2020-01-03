package com.hand.hec.wfl.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflElement;

import java.util.List;

public interface IWflElementService extends IBaseService<WflElement>, ProxySelf<IWflElementService>{
   public List<WflElement> processElementQuery(WflElement record);
}