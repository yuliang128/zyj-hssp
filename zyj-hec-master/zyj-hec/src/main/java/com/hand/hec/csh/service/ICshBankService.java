package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshBank;

import java.util.List;

/**
 * <p>
 * ICshBankService
 * </p>
 *
 * @author guiyu 2019/01/29 14:21
 */
public interface ICshBankService extends IBaseService<CshBank>, ProxySelf<ICshBankService> {
    /**
     * 查询所有银行信息
     *
     * @param request
     * @param cshBank
     * @param page
     * @param pageSize
     * @return 返回符合条件的银行信息
     * @author guiyu 2019-01-29 14:19
     */
    List<CshBank> queryAll(IRequest request, CshBank cshBank, int page, int pageSize);

    /**
     * 批量修改数据
     *
     * @param request
     * @param cshBanks
     * @author guiyu 2019-01-29 14:50
     * @return update的银行数据集合
     */
    List<CshBank> batchSubmit(IRequest request, List<CshBank> cshBanks);
}
