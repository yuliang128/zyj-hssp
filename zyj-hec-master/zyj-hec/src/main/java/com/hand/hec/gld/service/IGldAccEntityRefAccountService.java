package com.hand.hec.gld.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.GldAccount;
import com.hand.hec.gld.dto.GldAccEntityRefAccount;
import com.hand.hec.gld.dto.GldAccountingEntity;

/**
 * <p>
 * IGldAccEntityRefAccountService
 * </p>
 * 
 * @author yang.duan 2019/01/10 13:49
 */
public interface IGldAccEntityRefAccountService extends IBaseService<GldAccEntityRefAccount>, ProxySelf<IGldAccEntityRefAccountService>{

    /**
     * 查询出当前核算主体未分配的科目
     *
     * @param accEntityId 核算主体ID
     * @param setOfBooksId 账套ID
     * @author ngls.luhui 2019-02-25 16:48
     * @return
     */
    List<GldAccEntityRefAccount> queryActNotAsgnAccE(Long accEntityId,Long setOfBooksId,IRequest request, Integer page, Integer pageSize);

    /**
     * 查询当前账套下的所有科目表中的科目
     *
     * @param setOfBooksId 账套id
     * @author ngls.luhui 2019-02-25 19:17
     * @return
     */
    List<GldAccEntityRefAccount> queryAccountBySob(Long setOfBooksId,IRequest request,Integer page,Integer pageSize);
    
    /**
     * 核算主体定义-批量分配科目
     *
     * @param gldAccountingEntityList
     * @param request
     * @author ngls.luhui 2019-02-25 19:30
     * @return 
     */
    Boolean batchSubmit(List<GldAccountingEntity> gldAccountingEntityList,IRequest request);
}