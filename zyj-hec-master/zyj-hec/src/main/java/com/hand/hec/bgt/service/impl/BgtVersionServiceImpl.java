package com.hand.hec.bgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtVersion;
import com.hand.hec.bgt.mapper.BgtVersionMapper;
import com.hand.hec.bgt.service.IBgtVersionService;

/**
 * <p>
 * 预算版本ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 16:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtVersionServiceImpl extends BaseServiceImpl<BgtVersion> implements IBgtVersionService {

    @Autowired
    private BgtVersionMapper mapper;

    @Override
    public List<BgtVersion> checkBgtVersion(String controlType, String filtrateMethod, String versionCodeFrom,
            String versionCodeTo) {
        return mapper.checkBgtVersion(controlType, filtrateMethod, versionCodeFrom, versionCodeTo);
    }

    @Override
    public BgtVersion getCurrentBgtVersionByBgtOrgId(IRequest request, Long bgtOrgId) {
        BgtVersion bgtVersion = new BgtVersion();
        bgtVersion.setBgtOrgId(bgtOrgId);
        bgtVersion.setEnabledFlag("Y");
        bgtVersion.setStatus("CURRENT");
        Criteria criteria = new Criteria(bgtVersion);
        criteria.where(new WhereField(BgtVersion.FIELD_BGT_ORG_ID), new WhereField(BgtVersion.FIELD_ENABLED_FLAG),
                new WhereField(BgtVersion.FIELD_STATUS));
        List<BgtVersion> versionList = selectOptions(request, bgtVersion, criteria);
        if (versionList != null && !versionList.isEmpty()) {
            return versionList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<BgtVersion> getBgtVersionOption(IRequest request, Long bgtOrgId) {
        BgtVersion bgtVersion = new BgtVersion();
        bgtVersion.setBgtOrgId(bgtOrgId);
        bgtVersion.setEnabledFlag("Y");
        Criteria criteria = new Criteria(bgtVersion);
        criteria.where(new WhereField(BgtVersion.FIELD_BGT_ORG_ID), new WhereField(BgtVersion.FIELD_ENABLED_FLAG));
        return selectOptions(request, bgtVersion, criteria);
    }
}