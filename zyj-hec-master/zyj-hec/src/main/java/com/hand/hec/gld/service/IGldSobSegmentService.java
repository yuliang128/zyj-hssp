package com.hand.hec.gld.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldSobSegment;
import com.hand.hec.gld.dto.GldSegment;

/**
 * <p>
 * 科目段接口
 * </p>
 * 
 * @author zhaohui 2019/02/21 15:34
 */
public interface IGldSobSegmentService extends IBaseService<GldSobSegment>, ProxySelf<IGldSobSegmentService>{
    /**
     * 匹配科目段信息
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/20 16:40
     *@param accEntityId 核算主体ID
     *@param  setOfBooksId 账套ID
     *@param  accountId 科目ID
     *@param  ruleType 规则类型
     *@param  jeLineId 凭证行Id
     *@param drCrFlag 借贷方标识
     *@param docHeaderId 头表Id
     *@param docLineId 行表Id
     *@param docDistId 分配行表Id
     *@param docPmtLineId 计划付款行表Id
     *@return GldSegment 返回的科目段匹配的Dto
     *@Version 1.0
     **/
    GldSegment mappingSegmentValue(Long accEntityId,
                                   Long setOfBooksId,
                                   Long accountId,
                                   String ruleType,
                                   Long jeLineId,
                                   String drCrFlag,
                                   Long docHeaderId,
                                   Long docLineId,
                                   Long docDistId,
                                   Long docPmtLineId);


    /**
     * 查询segment段描述
     *
     * @param request  上下文
     * @author dingwei.ma@hand-china.com 2019-02-25 16:00
     * @return List<Map>
     */
    List<Map> querySegmentDesc(IRequest request);
}