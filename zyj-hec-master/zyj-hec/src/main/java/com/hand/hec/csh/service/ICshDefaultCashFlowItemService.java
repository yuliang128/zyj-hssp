package com.hand.hec.csh.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshDefaultCashFlowItem;
import org.springframework.beans.factory.annotation.Autowired;

public interface ICshDefaultCashFlowItemService extends IBaseService<CshDefaultCashFlowItem>, ProxySelf<ICshDefaultCashFlowItemService>{
    /**
     * 现金流量项分配默认科目查询
     *
     * @param request  请求上下文
     * @param condition  现金流量项分配默认科目实体类
     * @param pageNum   页码
     * @param pageSize  页数
     * @author dingwei.ma@hand-china.com 2019-02-22 14:51
     * @return List<Map>
     */
    List<Map> queryByItemId(IRequest request, CshDefaultCashFlowItem condition, int pageNum, int pageSize);

    /**
     * 科目查询
     *
     * @param request  请求上下文
     * @param setOfBooksId   账套id
     * @param accountCodeFrom   科目代码从
     * @param accountCodeTo  科目代码到
     * @author dingwei.ma@hand-china.com 2019-02-22 14:51
     * @return List<Map>
     */
    List<Map> queryByAccount(IRequest request, Long setOfBooksId, String accountCodeFrom, String accountCodeTo);

    /**
     * 保存
     *
     * @param request  请求上下文
     * @param list   CshDefaultCashFlowItem的list
     * @author dingwei.ma@hand-china.com 2019-02-22 14:51
     * @return List<Map>
     */
    List<CshDefaultCashFlowItem> batchSave(IRequest request, List<CshDefaultCashFlowItem> list);
}