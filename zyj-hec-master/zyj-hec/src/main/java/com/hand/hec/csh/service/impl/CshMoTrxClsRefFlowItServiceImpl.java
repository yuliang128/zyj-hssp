package com.hand.hec.csh.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshMoTrxClsRefFlowIt;
import com.hand.hec.csh.exception.CshMoTrxClsRefFlowItException;
import com.hand.hec.csh.mapper.CshMoTrxClsRefFlowItMapper;
import com.hand.hec.csh.service.ICshMoTrxClsRefFlowItService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 现金事物关联现金流量项impl
 * </p>
 *
 * @author LJK 2019/02/19 12:08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshMoTrxClsRefFlowItServiceImpl extends BaseServiceImpl<CshMoTrxClsRefFlowIt>
		implements ICshMoTrxClsRefFlowItService {

	@Autowired
	CshMoTrxClsRefFlowItMapper mapper;

	@Override
	public List<CshMoTrxClsRefFlowIt> queryByTrxClassId(IRequest request, Long moCshTrxClassId, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return mapper.queryByTrxClassId(moCshTrxClassId);
	}

	@Override
	public List<CshMoTrxClsRefFlowIt> batchUpdate(IRequest request,@StdWho List<CshMoTrxClsRefFlowIt> list){
		IBaseService<CshMoTrxClsRefFlowIt> self = ((IBaseService<CshMoTrxClsRefFlowIt>) AopContext.currentProxy());
		for (CshMoTrxClsRefFlowIt cshMoTrxClsRefFlowIt : list) {
			cshMoTrxClsRefFlowIt.getDefaultFlag();
			cshMoTrxClsRefFlowIt.getSetOfBooksName();
			Integer count = mapper.checkDftFlowItem(cshMoTrxClsRefFlowIt.getMoCshTrxClassId(),
					cshMoTrxClsRefFlowIt.getCashFlowItemId(), cshMoTrxClsRefFlowIt.getRefId());
			if(count>0){
				throw new CshMoTrxClsRefFlowItException(CshMoTrxClsRefFlowItException.GLD_SET_BOOK_DEFAULT_FLOW_ITEM_ONLY_ONE, null);
			}
			switch (cshMoTrxClsRefFlowIt.get__status()) {
				case DTOStatus.ADD:
					self.insertSelective(request, cshMoTrxClsRefFlowIt);
					break;
				case DTOStatus.UPDATE:
					if (useSelectiveUpdate()) {
						self.updateByPrimaryKeySelective(request, cshMoTrxClsRefFlowIt);
					} else {
						self.updateByPrimaryKey(request, cshMoTrxClsRefFlowIt);
					}
					break;
				case DTOStatus.DELETE:
					self.deleteByPrimaryKey(cshMoTrxClsRefFlowIt);
					break;
				default:
					break;
			}
		} return list;
	}
}