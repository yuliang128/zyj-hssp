package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.mapper.FndCompanyMapper;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoExpenseItem;
import com.hand.hec.exp.dto.ExpMoReqItemsAsgnCom;
import com.hand.hec.exp.mapper.ExpMoExpItemAsgnComMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpItemAsgnCom;
import com.hand.hec.exp.service.IExpMoExpItemAsgnComService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpItemAsgnComServiceImpl extends BaseServiceImpl<ExpMoExpItemAsgnCom> implements IExpMoExpItemAsgnComService{


    @Autowired
    ExpMoExpItemAsgnComMapper mapper;
    @Autowired
    FndCompanyMapper fndCompanyMapper;

    @Override
    public List<ExpMoExpItemAsgnCom> queryCompany(IRequest request, ExpMoExpItemAsgnCom dto, int page, int pageSize){
        return mapper.queryCompany(dto);
    }

    @Override
    public List<ExpMoExpItemAsgnCom> queryCompanyByItem(IRequest request,ExpMoExpItemAsgnCom dto,int page,int pageSize){
        return mapper.queryCompanyByItem(dto);
    }

    @Override
    public List<FndCompany> queryBatchCompany(IRequest request, FndCompany dto, int page, int pageSize){
        return fndCompanyMapper.queryBatchCompany(dto);
    }

    @Override
    public List<ExpMoExpItemAsgnCom> batchAssignCompanyManytoMany(IRequest request,List<ExpMoExpenseItem> expMoExpenseItems){
        List<ExpMoExpItemAsgnCom> expMoExpItemAsgnComList = new ArrayList<ExpMoExpItemAsgnCom>();
        if(!expMoExpenseItems.isEmpty()){
            for(ExpMoExpenseItem  item : expMoExpenseItems){
                List<HashMap<String, Object>> itemList =
                        (List<HashMap<String, Object>>) item.getInnerMap().get("companyInfo");
                for (HashMap<String, Object> map : itemList) {
                    if (!map.isEmpty()) {
                        ExpMoExpItemAsgnCom itemAsgnCom = new ExpMoExpItemAsgnCom();
                        itemAsgnCom.setMoExpenseItemId(item.getMoExpenseItemId());
                        itemAsgnCom.setCompanyId(Long.valueOf((Integer) map.get("companyId")));
                        if (checkExists(itemAsgnCom) == 0) {
                            itemAsgnCom.set__status(DTOStatus.ADD);
                        }else {
                            itemAsgnCom.setAssignId(checkExists(itemAsgnCom));
                            itemAsgnCom.set__status(DTOStatus.UPDATE);
                        }
                        itemAsgnCom.setEnabledFlag("Y");
                        expMoExpItemAsgnComList.add(itemAsgnCom);
                    }
                }
            }
        }
        return this.batchUpdate(request,expMoExpItemAsgnComList);
    }

    @Override
    public List<ExpMoExpItemAsgnCom> batchAssignCompanyOnetoMany(IRequest request,List<FndCompany> companies){
        List<ExpMoExpItemAsgnCom> itemAsgnComList = new ArrayList<ExpMoExpItemAsgnCom>();
        if (!companies.isEmpty()) {
            for (FndCompany fc : companies) {
                ExpMoExpItemAsgnCom item = new ExpMoExpItemAsgnCom();
                List<HashMap<String, Object>> ItemList =
                        (List<HashMap<String, Object>>) fc.getInnerMap().get("expItem");
                if (!ItemList.isEmpty()){
                    HashMap<String, Object> map = ItemList.get(0);
                    item.setMoExpenseItemId(Long.valueOf((String) map.get("moExpenseItemId")));
                }
                item.setCompanyId(fc.getCompanyId());
                item.setEnabledFlag("Y");
                item.set__status(DTOStatus.ADD);
                itemAsgnComList.add(item);
            }
        }
        return this.batchUpdate(request, itemAsgnComList);
    }

    /**
     * 校验费用项目分配公司是否存在(存在则返回主键)
     * @param dto
     * @return Long
     * @author zhongyu 2019/2/19 18:16
     */
    private Long checkExists(ExpMoExpItemAsgnCom dto) {
        int count = 0;
        count = mapper.selectCount(dto);
        if (count != 0) {
            Criteria criteria = new Criteria(dto);
            criteria.where(new WhereField(ExpMoExpItemAsgnCom.FIELD_MO_EXPENSE_ITEM_ID, Comparison.EQUAL),
                    new WhereField(ExpMoExpItemAsgnCom.FIELD_COMPANY_ID, Comparison.EQUAL));
            List<ExpMoExpItemAsgnCom> list = mapper.selectOptions(dto, criteria);
            return list.get(0).getAssignId();
        } else {
            return Long.valueOf(count);
        }
    }

    /**
     * 批量分配公司提交
     * @param request
     * @param moExpItemsAsgnComs
     * @return
     * @throws RuntimeException
     * @author zhongyu 2019-3-6 18:48
     */
    @Override
    public List<ExpMoExpItemAsgnCom> batchSubmit(IRequest request, List<ExpMoExpItemAsgnCom> moExpItemsAsgnComs)
            throws RuntimeException {
        Set<String> codeSet = new HashSet<>();
        if (moExpItemsAsgnComs != null && !moExpItemsAsgnComs.isEmpty()) {
            for (ExpMoExpItemAsgnCom asgnCom : moExpItemsAsgnComs) {
                codeSet.add(asgnCom.getCompanyCode());
            }
            if (codeSet.size() != moExpItemsAsgnComs.size()) {
                throw new RuntimeException("该公司已分配,请检查!");
            }
        }
        return this.batchUpdate(request, moExpItemsAsgnComs);
    }

}