package com.hand.hec.csh.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshMoRepayRegAsgnCom;
import com.hand.hec.csh.mapper.CshMoRepayRegAsgnComMapper;
import com.hand.hec.csh.service.ICshMoRepayRegAsgnComService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshMoRepayRegAsgnComServiceImpl extends BaseServiceImpl<CshMoRepayRegAsgnCom> implements ICshMoRepayRegAsgnComService {

    @Autowired
    private CshMoRepayRegAsgnComMapper mapper;

    @Override
    public List<CshMoRepayRegAsgnCom> queryUnallocatedCompanies(IRequest request, CshMoRepayRegAsgnCom condition, Long magOrgId) {
        return mapper.queryUnallocatedCompanies(condition, magOrgId);
    }

    @Override
    public List<CshMoRepayRegAsgnCom> batchAssign(IRequest request, @StdWho List<CshMoRepayRegAsgnCom> list) {
        //勾选的申请单类型
        List<Long> types = list.parallelStream().map(CshMoRepayRegAsgnCom::getMoRepaymentRegTypeId).distinct().collect(Collectors.toList());

        types.forEach(id -> {

            List<Long> allocatedCompaniesId = mapper.select(CshMoRepayRegAsgnCom.builder()
                    .moRepaymentRegTypeId(id).build())
                    .parallelStream()
                    .map(CshMoRepayRegAsgnCom::getCompanyId)
                    .collect(Collectors.toList());

            list.parallelStream()
                    .filter(dto -> dto.getMoRepaymentRegTypeId().equals(id))
                    .filter(dto -> !allocatedCompaniesId.contains(dto.getCompanyId())).
                    forEach(dto -> insertSelective(request, dto));
        });

        return list;
    }
}