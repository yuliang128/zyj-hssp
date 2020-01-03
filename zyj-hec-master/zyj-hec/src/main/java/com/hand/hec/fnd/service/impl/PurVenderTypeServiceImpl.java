package com.hand.hec.fnd.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.PurVenderType;
import com.hand.hec.fnd.service.IPurVenderTypeService;

/**
 * <p>
 * 供应商类型ServiceImpl
 * </p>
 *
 * @author YHL 2019/01/28 18:45
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurVenderTypeServiceImpl extends BaseServiceImpl<PurVenderType> implements IPurVenderTypeService {

}