package com.hand.hec.csh.mapper;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshCashFlowItem;
import com.hand.hec.gld.dto.GldSetOfBook;
import org.apache.ibatis.annotations.Param;

public interface CshCashFlowItemMapper extends Mapper<CshCashFlowItem> {
    /**
     * 根据magOrgId查询账套
     *
     * @param request 上下文
     * @author dingwei.ma@hand-china.com 2019-02-21 13:53
     * @return List<GldSetOfBook>
     */
    List<GldSetOfBook> defaultSetOfBook(IRequest request);

    /**
     * 查询账套
     *
     * @param allOrganizationalFlag 全组织标志
     * @author dingwei.ma@hand-china.com 2019-02-21 13:53
     * @return List<GldSetOfBook>
     */
    List<GldSetOfBook> queryForSob(@Param("allOrganizationalFlag") String allOrganizationalFlag);

    /**
     * 查询现金流量项
     *
     * @param record 现金流量项实体类
     * @author dingwei.ma@hand-china.com 2019-02-21 13:53
     * @return List<CshCashFlowItem>
     */
    List<CshCashFlowItem> queryCashItem(CshCashFlowItem record);

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
    List<CshCashFlowItem> getCashFlowItem(@Param("setOfBooksId") Long setOfBooksId,
                    @Param("accEntityId") Long accEntityId);
}
