package com.hand.hec.acp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.acp.mapper.AcpMoPayReqTpAsgnComMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.acp.dto.AcpMoPayReqTpAsgnCom;
import com.hand.hec.acp.service.IAcpMoPayReqTpAsgnComService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class AcpMoPayReqTpAsgnComServiceImpl extends BaseServiceImpl<AcpMoPayReqTpAsgnCom>
                implements IAcpMoPayReqTpAsgnComService {
    @Autowired
    private AcpMoPayReqTpAsgnComMapper moPayReqTpAsgnComMapper;

    @Override
    public List<AcpMoPayReqTpAsgnCom> batchAssign(IRequest request, @StdWho List<AcpMoPayReqTpAsgnCom> list) {
        List<Long> moPayReqTypeIds = list.parallelStream().map(AcpMoPayReqTpAsgnCom::getMoPayReqTypeId).distinct()
                        .collect(Collectors.toList());
        moPayReqTypeIds.forEach(moPayReqTypeId -> {
            List<Long> alreadyCompany = moPayReqTpAsgnComMapper
                            .select(AcpMoPayReqTpAsgnCom.builder().moPayReqTypeId(moPayReqTypeId).build())
                            .parallelStream().map(AcpMoPayReqTpAsgnCom::getCompanyId).collect(Collectors.toList());

            list.parallelStream().filter(
                            AcpMoPayReqTpAsgnCom -> AcpMoPayReqTpAsgnCom.getMoPayReqTypeId().equals(moPayReqTypeId))
                            .filter(AcpMoPayReqTpAsgnCom -> !alreadyCompany
                                            .contains(AcpMoPayReqTpAsgnCom.getCompanyId()))
                            .forEach(AcpMoPayReqTpAsgnCom -> insertSelective(request, AcpMoPayReqTpAsgnCom));
        });
        return list;
    }
}
