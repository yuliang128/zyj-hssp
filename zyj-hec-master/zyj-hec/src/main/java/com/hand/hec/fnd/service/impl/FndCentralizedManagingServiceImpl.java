package com.hand.hec.fnd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndCentralizedManaging;
import com.hand.hec.fnd.mapper.FndCentralizedManagingMapper;
import com.hand.hec.fnd.service.IFndCentralizedManagingService;

/**
 * <p>
 * 归口管理ServiceImpl
 * </p>
 *
 * @author YHL 2019/01/21 19:43
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndCentralizedManagingServiceImpl extends BaseServiceImpl<FndCentralizedManaging>
        implements IFndCentralizedManagingService {

    @Autowired
    private FndCentralizedManagingMapper mapper;

    @Override
    public List<FndCentralizedManaging> selectCentralizedMagList(IRequest request,
            FndCentralizedManaging fndCentralizedManaging, int pageNum, int pageSize) {
        return mapper.selectCentralizedMagList(fndCentralizedManaging);
    }
}