package com.hand.hec.fnd.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.GldPeriodSet;
import com.hand.hec.fnd.exception.GldPeriodSetException;
import com.hand.hec.fnd.mapper.GldPeriodSetMapper;
import com.hand.hec.fnd.service.IGldPeriodSetService;
import com.hand.hec.gld.mapper.GldSetOfBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 会计期ServiceImpl
 * </p>
 *
 * @author $JK.Lu$ 2019/01/14 14:45
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldPeriodSetServiceImpl extends BaseServiceImpl<GldPeriodSet> implements IGldPeriodSetService {

	public GldSetOfBookMapper gldSetOfBookMapper;

	@Autowired
	GldPeriodSetMapper gldPeriodSetMapper;



	@Override
	@Transactional(rollbackFor = Exception.class)
	public void periodSetDeleteCheck(List<GldPeriodSet> dto) throws GldPeriodSetException {
		for (GldPeriodSet gldPeriodSet : dto) {
			String code = gldPeriodSet.getPeriodSetName();
			int count = gldSetOfBookMapper.QueryCountByPeriodSetCode(code);
			if (count > 0) {
				throw new GldPeriodSetException(GldPeriodSetException.FND2120_PERIOD_SET_CODE_USING, null);
			}
		}
	}

	@Override
	public GldPeriodSet queryByPeriodSetCode(String periodSetCode) {
		return gldPeriodSetMapper.queryByPeriodSetCode(periodSetCode);
	}
}