package com.hand.hec.pur.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.pur.dto.PurSystemVenderRefAe;
import com.hand.hec.pur.mapper.PurSystemVenderRefAeMapper;
import com.hand.hec.pur.service.IPurSystemVenderRefAeService;

/**
 * <p>
 * 供应商分配核算主体逻辑实现类
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/20 15:47
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurSystemVenderRefAeServiceImpl extends BaseServiceImpl<PurSystemVenderRefAe> implements IPurSystemVenderRefAeService {

    @Autowired
    private PurSystemVenderRefAeMapper mapper;

    @Override
    public List<PurSystemVenderRefAe> queryUnallocatedAccEntity(IRequest request,Long venderId) {
        return this.mapper.queryUnallocatedAccEntity(venderId);
    }

    @Override
    public List<PurSystemVenderRefAe> queryByVenderId(IRequest request, Long venderId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return this.mapper.queryByVenderId(venderId);
    }

    @Override
    public List<PurSystemVenderRefAe> multiBatchAssign(IRequest request, List<PurSystemVenderRefAe> dto) {
        List<Long> venderIds = dto.get(0).getVendersId();
        List<PurSystemVenderRefAe> records = new ArrayList<>();
        venderIds.forEach(id -> dto.forEach(record -> {
            PurSystemVenderRefAe v = new PurSystemVenderRefAe();
            record.setVenderId(id);
            BeanUtils.copyProperties(record, v);
            records.add(v);
        }));
        self().batchAssign(request, records);
        return dto;
    }

    @Override
    public boolean batchAssign(IRequest requestContext, List<PurSystemVenderRefAe> records) {
        List<Long> venderId = records.stream().map(PurSystemVenderRefAe::getVenderId).distinct().collect(Collectors.toList());
        for (Long id : venderId) {
            batchAssign(requestContext, records, id);
        }
        return true;
    }

    private void batchAssign(IRequest request, List<PurSystemVenderRefAe> records, Long venderId) {
        List<Long> accEntityIds = records.stream().map(PurSystemVenderRefAe::getAccEntityId).collect(Collectors.toList());

        accEntityIds = accEntityIds.stream().distinct().collect(Collectors.toList());
        /**
         * 查找出未当前供应商未分配的核算主体,与传入list取交集
         * 设置启用状态后再过滤,然后执行插入
         */
        accEntityIds.retainAll(mapper.queryUnallocatedAccEntity(venderId).stream().map(PurSystemVenderRefAe::getAccEntityId).collect(Collectors.toList()));

        if (!accEntityIds.isEmpty()) {

            accEntityIds.forEach(accEntityId ->
                    records.stream()
                            .filter(purSystemVenderRefAe -> Objects.equals(accEntityId, purSystemVenderRefAe.getAccEntityId()))
                            .filter(purSystemVenderRefAe -> Objects.equals(venderId, purSystemVenderRefAe.getVenderId()))
                            .collect(Collectors.toList())
                            .forEach(purSystemVenderRefAe -> {
                                purSystemVenderRefAe.setEnabledFlag(BaseConstants.YES);
                                self().insertSelective(request, purSystemVenderRefAe);
                            }));

        }

    }

}