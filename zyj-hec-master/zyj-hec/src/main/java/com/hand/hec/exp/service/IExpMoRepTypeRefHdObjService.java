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
 * IExpMoRepTypeRefHdObjService
 * </p>
 *
 * @author yang.duan 2019/01/10 14:56
 */
public interface IExpMoRepTypeRefHdObjService
                extends IBaseService<ExpMoRepTypeRefHdObj>, ProxySelf<IExpMoRepTypeRefHdObjService> {
    /**
     * <p>
     * 报销单类型定义-页面元素-头费用对象批量提交
     * <p/>
     * 
     * @param List<ExpMoRepTypeRefHdObj> typeRefHdObjs
     * @param IRequest request
     * @return List<ExpMoRepTypeRefHdObj>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/2/27 18:30
     */
    List<ExpMoRepTypeRefHdObj> batchSubmit(IRequest request, List<ExpMoRepTypeRefHdObj> typeRefHdObjs)
                    throws RuntimeException;


    /**
     * <p>
     * 校验头费用对象是否存在并启用
     * <p/>
     *
     * @param ExpMoRepEleRefLnObj dto
     * @return int
     * @author yang.duan 2019/2/27 18:49
     */
    int queryExpTypeHdObj(ExpMoRepEleRefLnObj dto);

    /**
     * <p>
     * 查询报销单类型定义头费用对象
     * <p/>
     *
     * @param IRequest request
     * @param Criteria criteria
     * @param ExpMoRepTypeRefHdObj dto
     * @param int page
     * @param int pageSize
     * @return List<ExpMoRepTypeRefHdObj>
     * @author yang.duan 2019/3/1 14:51
     */
    List<ExpMoRepTypeRefHdObj> queryAllInfomation(IRequest request, ExpMoRepTypeRefHdObj dto, Criteria criteria,
                    int page, int pageSize);



    Map getHeaderObjInfo(IRequest request, ExpMoRepTypeRefHdObj dto, Criteria criteria);
}
