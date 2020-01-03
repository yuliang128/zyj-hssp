package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.GldAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * IGldAccountService
 * </p>
 *
 * @author guiyu 2019/01/08 15:37
 */
public interface IGldAccountService extends IBaseService<GldAccount>, ProxySelf<IGldAccountService> {

    public Map<String, Object> accountSetRefrenceQuery(@Param("accountId") Long accountId,
                    @Param("accountSetId") Long accountSetId);

    public List<GldAccount> selectAccountId(Map<String, Object> accountMap);

    public List<GldAccount> selectByAccountSetId(@Param("accountSetId") Long accountSetId);

    public List<GldAccount> hierarchyTreeQuery(IRequest request , Long accountSetId);
}
