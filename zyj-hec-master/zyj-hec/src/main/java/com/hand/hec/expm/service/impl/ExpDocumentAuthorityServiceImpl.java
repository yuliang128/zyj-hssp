package com.hand.hec.expm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.expm.dto.ExpDocumentAuthority;
import com.hand.hec.expm.mapper.ExpDocumentAuthorityMapper;
import com.hand.hec.expm.service.IExpDocumentAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpDocumentAuthorityServiceImpl extends BaseServiceImpl<ExpDocumentAuthority>
                implements IExpDocumentAuthorityService {
    @Autowired
    ExpDocumentAuthorityMapper expDocumentAuthorityMapper;


    @Override
    public List<ExpEmployee> queryDocAuthEmployee(IRequest request, ExpDocumentAuthority dto) {
        return expDocumentAuthorityMapper.queryDocAuthEmployee(dto);
    }

    /**
     * <p>查询当前公司下的授权员工</p>
     *
     * @param dto
     * @return java.util.List<com.hand.hap.exp.dto.ExpEmployee>
     * @author yang.duan 2019/4/25 11:25
     **/
    @Override
    public List<ExpEmployee> getEmpCurCompAuth(IRequest request, ExpDocumentAuthority dto){
        return expDocumentAuthorityMapper.getEmpCurCompAuth(dto);
    }
}
