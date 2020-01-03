package com.hand.hec.expm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.expm.dto.ExpReportPageElement;
import com.hand.hec.expm.service.IExpReportPageElementService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 费用报销单页面元素ServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 15:00
 * @author xiuxian.wu 2019/01/19 25:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpReportPageElementServiceImpl extends BaseServiceImpl<ExpReportPageElement> implements IExpReportPageElementService{
    @Override
    protected boolean useSelectiveUpdate(){
        return false;
    }
}