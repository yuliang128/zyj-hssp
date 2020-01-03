package com.hand.hec.csh.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshCashFlowItem;
import com.hand.hec.gld.dto.GldSetOfBook;

public interface ICshCashFlowItemService extends IBaseService<CshCashFlowItem>, ProxySelf<ICshCashFlowItemService> {
    /**
     * 根据magOrgId查询账套
     *
     * @param request 上下文
     * @author dingwei.ma@hand-china.com 2019-02-21 13:49
     * @return GldSetOfBook
     */
    GldSetOfBook defaultSetOfBook(IRequest request);

    /**
     * 根据账套
     *
     * @param request 上下文
     * @param condition 现金流量表实体类
     * @param pageNum 页码
     * @param pageSize 页数
     * @author dingwei.ma@hand-china.com 2019-02-21 13:49
     * @return List<GldSetOfBook>
     */
    List<GldSetOfBook> queryForSob(IRequest request, CshCashFlowItem condition, int pageNum, int pageSize);

    /**
     * 现金流量表查询
     *
     * @param request 上下文
     * @param condition 现金流量表实体类
     * @param pageNum 页码
     * @param pageSize 页数
     * @author dingwei.ma@hand-china.com 2019-02-21 13:49
     * @return List<GldSetOfBook>
     */
    List<CshCashFlowItem> queryCashItem(IRequest request, CshCashFlowItem condition, int pageNum, int pageSize);

    /**
     * @Description 根据核算主体（账套）获取现金流量项
     *
     * @Author Tagin
     * @Date 2019-03-07 16:39
     * @param setOfBooksId 账套ID
     * @param accEntityId 核算主体ID
     * @Return java.util.List<com.hand.hec.csh.dto.CshCashFlowItem>
     * @Version 1.0
     **/
    List<CshCashFlowItem> getCashFlowItme(IRequest iRequest, Long setOfBooksId, Long accEntityId);

}
