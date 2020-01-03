package com.hand.hap.system.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.dto.Form;

public interface IFormBuilderService extends IBaseService<Form>, ProxySelf<IFormBuilderService>{

    List<Form> batchUpdate(IRequest iRequest, @StdWho List<Form> forms);

    int batchDelete(List<Form> forms);

}