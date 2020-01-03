package com.hand.hec.gld.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldSobRuleGpRefAe;

import java.util.List;

public interface IGldSobRuleGpRefAeService extends IBaseService<GldSobRuleGpRefAe>, ProxySelf<IGldSobRuleGpRefAeService>{

	List<GldSobRuleGpRefAe> queryNotRefAE(IRequest request, GldSobRuleGpRefAe condition, int pageNum, int pageSize);

}