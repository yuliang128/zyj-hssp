package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoRepTypeRefEle;

import java.util.List;

/**
 * <p>
 * IExpMoRepTypeRefEleService
 * </p>
 *
 * @author yang.duan 2019/01/10 14:56
 */
public interface IExpMoRepTypeRefEleService
                extends IBaseService<ExpMoRepTypeRefEle>, ProxySelf<IExpMoRepTypeRefEleService> {
    /**
     * <p>
     * 报销单页面元素批量提交
     * <p/>
     *
     * @param IRequest request
     * @param List<ExpMoRepTypeRefEle> expMoRepTypeRefEles
     * @return List<ExpMoRepTypeRefEle>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/3/4 13:33
     */
    List<ExpMoRepTypeRefEle> batchSubmit(IRequest request, List<ExpMoRepTypeRefEle> expMoRepTypeRefEles)
                    throws RuntimeException;

    /**
     * <p>
     * 查询报销单行页面元素
     * <p/>
     *
     * @param expMoReportTypeId 报销单类型ID
     * @param expReportHeaderId 报销单头ID
     * @return 行页面元素list
     * @author yang.duan 2019/3/26 16:58
     */
    List<ExpMoRepTypeRefEle> queryExpReportLineEle(Long expMoReportTypeId, Long expReportHeaderId);


    List<ExpMoRepTypeRefEle> queryInvoiceAndTaxFlag(Long moExpReportTypeId, String reportPageElementCode);
}
