package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtStructureAsgnMo;
import com.hand.hec.bgt.service.IBgtStructureAsgnMoService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算表分配管理组织ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtStructureAsgnMoServiceImpl extends BaseServiceImpl<BgtStructureAsgnMo> implements IBgtStructureAsgnMoService{

}