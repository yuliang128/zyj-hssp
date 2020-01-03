package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.dto.SscTaskPoolPrepare;
import com.hand.hec.ssc.mapper.SscTaskPoolPrepareMapper;
import com.hand.hec.ssc.service.ISscTaskPoolPrepareService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscTaskPoolPrepareServiceImpl extends BaseServiceImpl<SscTaskPoolPrepare> implements ISscTaskPoolPrepareService{

	@Autowired
	SscTaskPoolPrepareMapper mapper;

	@Override
	public List<SscTaskPoolPrepare> selectSscTaskPoolPrepare(IRequest iRequest,
															 @Param("docNumber") String docNumber,
															 @Param("docCategory") String docCategory,
															 @Param("taskStatus") String taskStatus,
															 @Param("dispatchStatus") String dispatchStatus,
															 @Param("workEmployeeId") Long workEmployeeId,
															 @Param("docEmployeeId") Long docEmployeeId,
															 @Param("magOrgId") Long magOrgId,
															 @Param("companyId") Long companyId,
															 @Param("workflowId") Long workflowId,
															 @Param("nodeId") Long nodeId,
															 @Param("enterTime" )Date enterTime,
															 @Param("enterTimeTo")Date enterTimeTo,
															 @Param("exitTime")Date exitTime,
															 @Param("exitTimeTo")Date exitTimeTo) {
		List<SscTaskPoolPrepare> list = mapper.selectSscTaskPoolPrepare(docNumber,docCategory,taskStatus,dispatchStatus,workEmployeeId,docEmployeeId,magOrgId,companyId,workflowId,nodeId,enterTime,enterTimeTo,exitTime,exitTimeTo);
		for(int i=0;i<list.size();i++){
			if(list.get(i).getCurAdviceEmpName() == null){
				List<String> name = selectCurAdviceEmpName(list.get(i).getTaskId());
				String curAdviceEmpName = "";
				for(int j=0;j<name.size();j++){
					if(j<name.size()-1) {
						curAdviceEmpName=curAdviceEmpName.concat(name.get(j).toString()+"; ");
					}
					else {
						curAdviceEmpName=curAdviceEmpName.concat(name.get(j).toString());
					}
				}
				list.get(i).setCurAdviceEmpName(curAdviceEmpName);
			}
			else{
				list.get(i).setCurAdviceEmpName("");
			}
		}
		return list;
	}

	@Override
	public List<String> selectCurAdviceEmpName(@Param("taskId") Long taskId) {
		return mapper.selectCurAdviceEmpName(taskId);
	}
}