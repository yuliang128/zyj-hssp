package com.hand.hec.gld.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GlAccountEntry;

/**
 * <p>
 * 总账分录Service
 * </p>
 *
 * @author Tagin 2019/03/29 10:34
 */
public interface IGlAccountEntryService extends IBaseService<GlAccountEntry>, ProxySelf<IGlAccountEntryService> {
    /**
     * 总账凭证分录
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/5/9 16:03
     * @param iRequest
     * @param ruleType
     * @param headerId
     * @return
     * @Version 1.0
     **/
    void headerGlAccountEntry(IRequest iRequest, String ruleType, Long headerId);


    /**
     * 更新总账分录导入状态
     *
     * @author Tagin
     * @date 2019-03-29 10:49
     * @param importedFlag 导入标识
     * @param transactionHeaderId 现金事务头ID
     * @param transactionType 现金事务类型
     * @return void
     * @version 1.0
     **/
    void updateImportedFlag(String importedFlag, Long transactionHeaderId, String transactionType);

}
