package com.hand.hec.gld.service;


import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.exception.GldSetOfBookException;


/**
 * <p>
 * 账套定义service
 * </p>
 *
 * @author wuxiuxian 2019-01-07
 */

public interface IGldSetOfBookService extends IBaseService<GldSetOfBook>, ProxySelf<IGldSetOfBookService> {
    /**
     *
     * 根据账套id查询账套是否被核算主体使用
     *
     * @param setBooksId
     * @return 返回是否使用
     */
    Boolean judgeUsedByAccounting(Long setBooksId);

    /**
     *
     * 根据账套id查询账套是否被管理组织使用
     *
     * @param setBooksId
     * @return 返回是否使用
     */
    Boolean judgeUsedByOrganization(Long setBooksId);

    /**
     *
     * 根据管理组织ID查询默认账套
     *
     * @param request
     * @return 返回默认账套
     * @author wuxiuxian
     */
    List<GldSetOfBook> selectDefaultSobByMagOrgId(IRequest request);

    /**
     *
     * 汇率类型定义主页面账套查询
     *
     * @param request
     * @author jialin.xing@hand-china.com 2019-01-10 16:45
     * @return 查询结果
     */
    List<GldSetOfBook> querySetOfBookByParam(IRequest request);

    /**
     * 根据参数查找功能页面上的账套下拉框数据
     * 
     * @param request request
     * @param paramValue 参数值
     * @return 账套列表
     * @author rui.shi@hand-china.com 2019-02-21
     */
    List<GldSetOfBook> querySetOfBookOptionsByParamValue(IRequest request, String paramValue);

    /**
     * 根据 账套id 查询会计期间
     * 
     * @param requestContext requestContext
     * @param dto 账套
     * @param page page
     * @param pageSize pageSize
     * @return 会计期
     * @author rui.shi@hand-china.com 2019-02-21
     */
    List<GldSetOfBook> queryForPeriod(IRequest requestContext, GldSetOfBook dto, int page, int pageSize);


    /**
     * 新增或更新账套，并判断账套是否被使用
     *
     * @param dto 更新或新增账套
     * @param requestContext
     * @return 返回结果
     * @throws GldSetOfBookException
     * @author wuxiuxian
     */
    List<GldSetOfBook> batchSubmitGldSetOfBook(IRequest requestContext, List<GldSetOfBook> dto)
                    throws GldSetOfBookException;

    /**
     * 根据管理组织ID查询账套值列表
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/20 16:58
     * @param iRequest 请求上下文
     * @param magOrgId 管理组织Id
     * @return
     * @Version 1.0
     **/
    List<GldSetOfBook> queryByMagId(IRequest iRequest, Long magOrgId);

    /**
     * 获取核算主体默认账套
     *
     * @author Tagin
     * @date 2019-03-27 19:43
     * @param iRequest 请求
     * @param accEntityId 核算主体
     * @return com.hand.hec.gld.dto.GldSetOfBook
     * @version 1.0
     **/
    GldSetOfBook queryDftSetOffBookByAe(IRequest iRequest, Long accEntityId);

}
