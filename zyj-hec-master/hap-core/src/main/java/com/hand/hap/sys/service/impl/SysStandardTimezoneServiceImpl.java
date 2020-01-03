package com.hand.hap.sys.service.impl;

import java.util.List;

import com.hand.hap.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.sys.dto.SysStandardTimezone;
import com.hand.hap.sys.mapper.SysStandardTimezoneMapper;
import com.hand.hap.sys.service.ISysStandardTimezoneService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysStandardTimezoneServiceImpl extends BaseServiceImpl<SysStandardTimezone> implements ISysStandardTimezoneService {
    @Autowired
    SysStandardTimezoneMapper mapper;


    @Override
    public List<SysStandardTimezone> queryDefaultTimezoneByCode(String code,IRequest request) {
        return mapper.queryDefaultTimezoneByCode(code);
    }
}