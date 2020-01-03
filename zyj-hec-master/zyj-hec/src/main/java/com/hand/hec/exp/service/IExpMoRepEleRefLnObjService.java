package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoRepEleRefLnObj;
import com.hand.hec.exp.dto.ExpMoRepTypeRefHdObj;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * IExpMoRepEleRefLnObjService
 * </p>
 *
 * @author yang.duan 2019/01/10 14:52
 */
public interface IExpMoRepEleRefLnObjService
                extends IBaseService<ExpMoRepEleRefLnObj>, ProxySelf<IExpMoRepEleRefLnObjService> {
    /**
     * <p>
     * 报销单类型定义-页面元素-行费用对象批量提交
     * <p/>
     * 
     * @param  request
     * @param  eleRefLnObjs
     * @return List<ExpMoRepEleRefLnObj>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/2/27 18:46
     */
    List<ExpMoRepEleRefLnObj> batchSubmit(IRequest request, List<ExpMoRepEleRefLnObj> eleRefLnObjs)
                    throws RuntimeException;


    /**
     * <p>
     * 校验行费用对象是否存在并启用
     * <p/>
     *
     * @param  dto
     * @return int
     * @author yang.duan 2019/2/27 18:47
     */
    int queryRepEleRefLnObj(ExpMoRepTypeRefHdObj dto);

    /**
     * <p>
     * 查询报销单类型定义行费用对象
     * <p/>
     *
     * @param  request
     * @param  dto
     * @param  criteria
     * @param  page
     * @param  pageSize
     * @return List<ExpMoRepEleRefLnObj>
     * @author yang.duan 2019/3/1 15:08
     */
    List<ExpMoRepEleRefLnObj> queryAllInfomation(IRequest request, ExpMoRepEleRefLnObj dto, Criteria criteria, int page,
                    int pageSize);

    List<Map> queryLnDftObject(IRequest request, Long moExpReportTypeId, String reportPageElementCode);
}
