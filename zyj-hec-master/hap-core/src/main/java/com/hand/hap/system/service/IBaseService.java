package com.hand.hap.system.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.mybatis.common.Criteria;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface IBaseService<T> {

    List<T> select(IRequest request, T condition, int pageNum, int pageSize);

    T insert(IRequest request, @StdWho T record);

    T insertSelective(IRequest request, @StdWho T record);

    T updateByPrimaryKey(IRequest request, @StdWho T record);

    @Transactional(rollbackFor = Exception.class)
    T updateByPrimaryKeySelective(IRequest request, @StdWho T record);

    @Transactional(rollbackFor = Exception.class)
    T updateByPrimaryKeyOptions(IRequest request, @StdWho T record, Criteria criteria);

    T selectByPrimaryKey(IRequest request, T record);

    int deleteByPrimaryKey(T record);

    /**
     * DO NOT USE this method when multi-language query
     * 
     * @return
     */
    @Deprecated
    List<T> selectAll();


    List<T> selectAll(IRequest iRequest);

    List<T> batchUpdate(IRequest request, @StdWho List<T> list);

    int batchDelete(List<T> list);


    List<T> selectOptions(IRequest request, T record, Criteria criteria);

    List<T> selectOptions(IRequest request, T record, Criteria criteria, Integer pageNum, Integer pageSize);

    void setRequest(IRequest request);

    IRequest getRequest();
}
