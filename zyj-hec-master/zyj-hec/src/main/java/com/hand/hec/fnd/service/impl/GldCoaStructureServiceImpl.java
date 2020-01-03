package com.hand.hec.fnd.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.GldCoaStructure;
import com.hand.hec.fnd.service.IGldCoaStructureService;

/**
 * <p>
 * 科目结构ServiceImpl
 * </p>
 *
 * @author YHL 2019/01/08 16:09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldCoaStructureServiceImpl extends BaseServiceImpl<GldCoaStructure> implements IGldCoaStructureService {

}