package com.hand.hec.expm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.expm.mapper.ExpPolicyPlaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.expm.dto.ExpPolicyPlace;
import com.hand.hec.expm.service.IExpPolicyPlaceService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpPolicyPlaceServiceImpl extends BaseServiceImpl<ExpPolicyPlace> implements IExpPolicyPlaceService{

    @Autowired
    ExpPolicyPlaceMapper mapper;

    /**
     * 页面初始查询
     * @author Zhongyu 2019-2-20 17:00
     * @return
     */
    @Override
    public List<ExpPolicyPlace> queryPolicyPlace(IRequest request, ExpPolicyPlace expPolicyPlace, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return mapper.queryPolicyPlace(expPolicyPlace);
    }
}