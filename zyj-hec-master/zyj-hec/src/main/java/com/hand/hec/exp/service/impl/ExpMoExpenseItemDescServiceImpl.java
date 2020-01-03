package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoExpItemAsgnCom;
import com.hand.hec.exp.exception.ExpMoExpenseItemDescException;
import com.hand.hec.exp.mapper.ExpMoExpenseItemDescMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpenseItemDesc;
import com.hand.hec.exp.service.IExpMoExpenseItemDescService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 费用项目说明定义Service实现类
 * </p>
 *
 * @author yang.cai 2019/02/28 18:19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpenseItemDescServiceImpl extends BaseServiceImpl<ExpMoExpenseItemDesc> implements IExpMoExpenseItemDescService{

    @Autowired
    ExpMoExpenseItemDescMapper mapper;
    @Override
    public List<ExpMoExpenseItemDesc> queryAll(IRequest request, ExpMoExpenseItemDesc expMoExpenseItemDesc, int pageNum, int pageSize) {
        return mapper.queryAll(expMoExpenseItemDesc);
    }

    @Override
    public List<ExpMoExpenseItemDesc>batchUpdate(IRequest request,List<ExpMoExpenseItemDesc> dto) throws RuntimeException{

        if(!dto.isEmpty()){
            for(ExpMoExpenseItemDesc desc : dto){
                if(checkExists(desc)!=0) {
                    throw new ExpMoExpenseItemDescException(ExpMoExpenseItemDescException.Data_Duplication, null);
                }
            }
        }
        return super.batchUpdate(request,dto);
    }

    /**
     * 校验该费用项目说明定义是否存在(存在则抛出异常)
     * @param dto
     * @return Long
     * @author zhongyu 2019年4月11日10:07
     */
    private Long checkExists(ExpMoExpenseItemDesc dto) {
            List<ExpMoExpenseItemDesc> list = mapper.checkExists(dto);
            if(!list.isEmpty() ){
                Long id= list.get(0).getItemDesc();
                if(id.equals(dto.getItemDesc())){
                    return Long.valueOf(0);
                }else {
                    return Long.valueOf(1);
                }
        }
            return Long.valueOf(0);
        }

}