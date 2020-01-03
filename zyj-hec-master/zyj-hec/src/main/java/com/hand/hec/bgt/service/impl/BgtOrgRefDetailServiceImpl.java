package com.hand.hec.bgt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.extensible.components.ServiceListenerChainFactory;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtOrgRefDetail;
import com.hand.hec.bgt.mapper.BgtOrgRefDetailMapper;
import com.hand.hec.bgt.service.IBgtOrgRefDetailService;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.mapper.FndManagingOrganizationMapper;
import com.hand.hec.gld.dto.GldSetOfBook;
import com.hand.hec.gld.mapper.GldSetOfBookMapper;

/**
 * <p>
 * 预算组织来源明细ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtOrgRefDetailServiceImpl extends BaseServiceImpl<BgtOrgRefDetail> implements IBgtOrgRefDetailService {

    @Autowired
    private BgtOrgRefDetailMapper detailMapper;

    @Autowired
    private FndManagingOrganizationMapper magMapper;

    @Autowired
    private GldSetOfBookMapper sobMapper;

    @Autowired
    private ServiceListenerChainFactory chainFactory;

    @Override
    public List<BgtOrgRefDetail> selectDetailBySourceType(String sourceTypeCode, Long bgtOrgId) {
        List<BgtOrgRefDetail> detailList = new ArrayList<>();
        switch (sourceTypeCode) {
            case "MANAGEMENT":
                detailList = detailMapper.selectDetailOfMag(bgtOrgId);
                break;
            case "ACCOUNTING":
                detailList = detailMapper.selectDetailOfSob(bgtOrgId);
                break;
            default:
                break;
        }
        return detailList;
    }

    @Override
    public List<BgtOrgRefDetail> selectCombox(String sourceTypeCode,IRequest request) {
        List<BgtOrgRefDetail> detailList = new ArrayList<>();
        switch (sourceTypeCode) {
            case "MANAGEMENT":
                List<FndManagingOrganization> magList = magMapper.selectAll();
                for (FndManagingOrganization mag : magList) {
                    BgtOrgRefDetail bgt = new BgtOrgRefDetail();
                    bgt.setSourceId(mag.getMagOrgId());
                    bgt.setSourceCode(mag.getMagOrgCode());
                    bgt.setSourceName(mag.getDescription());
                    detailList.add(bgt);
                }
                break;
            case "ACCOUNTING":
                List<GldSetOfBook> sobList = sobMapper.selectAll();
                for (GldSetOfBook sob : sobList) {
                    BgtOrgRefDetail bgt = new BgtOrgRefDetail();
                    bgt.setSourceId(sob.getSetOfBooksId());
                    bgt.setSourceCode(sob.getSetOfBooksCode());
                    bgt.setSourceName(sob.getSetOfBooksName());
                    detailList.add(bgt);
                }
                break;
            default:
                break;
        }
        return detailList;
    }
}