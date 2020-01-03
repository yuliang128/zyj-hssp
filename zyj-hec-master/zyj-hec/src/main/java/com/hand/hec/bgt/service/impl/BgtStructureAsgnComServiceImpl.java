package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtStructureAsgnCom;
import com.hand.hec.bgt.service.IBgtStructureAsgnComService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算编分配公司ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtStructureAsgnComServiceImpl extends BaseServiceImpl<BgtStructureAsgnCom> implements IBgtStructureAsgnComService{

}