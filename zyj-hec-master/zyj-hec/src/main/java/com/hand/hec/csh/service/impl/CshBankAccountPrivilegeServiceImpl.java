package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshBankAccountPrivilege;
import com.hand.hec.csh.mapper.CshBankAccountPrivilegeMapper;
import com.hand.hec.csh.service.ICshBankAccountPrivilegeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshBankAccountPrivilegeServiceImpl extends BaseServiceImpl<CshBankAccountPrivilege> implements ICshBankAccountPrivilegeService{

	@Autowired
	private CshBankAccountPrivilegeMapper mapper;


	@Override
	public List<CshBankAccountPrivilege> selectCshBankAccount(@Param("accEntityId") BigDecimal accEntityId, @Param("bankCode") String bankCode, @Param("bankName") String bankName,IRequest iRequest) {
		List<CshBankAccountPrivilege> cshBankAccountPrivilege1 = mapper.selectCshBankAccount(accEntityId,bankCode,bankName);
		List<CshBankAccountPrivilege> cshBankAccountPrivilege2 = mapper.selectCshBankAccountPrivilege(null);
		for(int i=0;i<cshBankAccountPrivilege2.size();i++){
			for(int j=0;j<cshBankAccountPrivilege1.size();j++){
				if(cshBankAccountPrivilege2.get(i).getBankAccountId().equals(cshBankAccountPrivilege1.get(j).getBankAccountId())){
					cshBankAccountPrivilege1.get(j).setAjustFlag("Y");
				}
			}
		}
		return cshBankAccountPrivilege1;
	}

	@Override
	public List<CshBankAccountPrivilege> selectCshBankAccountPrivilege(@Param("bankAccountId") BigDecimal bankAccountId,IRequest iRequest) {
		return mapper.selectCshBankAccountPrivilege(bankAccountId);
	}


}
