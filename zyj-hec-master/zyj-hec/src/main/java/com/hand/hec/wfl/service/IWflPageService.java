package com.hand.hec.wfl.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflPage;

import java.util.List;

public interface IWflPageService extends IBaseService<WflPage>, ProxySelf<IWflPageService>{
   public List<WflPage> selectWflPage(WflPage wflPage);
}