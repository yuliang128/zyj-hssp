package com.hand.hec.fnd.service.impl;


import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndDimensionValue;
import com.hand.hec.fnd.mapper.FndDimensionValueMapper;
import com.hand.hec.fnd.service.IFndDimensionValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndDimensionValueServiceImpl extends BaseServiceImpl<FndDimensionValue>
                implements IFndDimensionValueService {

    @Autowired
    FndDimensionValueMapper dimensionValueMapper;

    @Override
    public List<FndDimensionValue> checkFndDimension(String controlType, Long dimensionId, String filtrateMethod,
                    Long dimensionValueId, String controlDimValueCodeFrom, String controlDimValueCodeTo) {
        return dimensionValueMapper.checkFndDimension(controlType, dimensionId, filtrateMethod, dimensionValueId,
                        controlDimValueCodeFrom, controlDimValueCodeTo);
    }
}
