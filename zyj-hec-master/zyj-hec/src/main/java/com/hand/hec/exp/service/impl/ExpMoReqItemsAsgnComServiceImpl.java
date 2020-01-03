package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoReqItem;
import com.hand.hec.exp.dto.ExpMoReqItemsAsgnCom;
import com.hand.hec.exp.mapper.ExpMoReqItemsAsgnComMapper;
import com.hand.hec.exp.service.IExpMoReqItemsAsgnComService;
import com.hand.hap.fnd.dto.FndCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReqItemsAsgnComServiceImpl extends BaseServiceImpl<ExpMoReqItemsAsgnCom>
                implements IExpMoReqItemsAsgnComService {
    @Autowired
    ExpMoReqItemsAsgnComMapper mapper;


    /**
     * <p>
     * 申请项目-分配公司批量提交
     * <p/>
     *
     * @param IRequest request
     * @param List<ExpMoReqItemsAsgnCom> moReqItemsAsgnComs
     * @return List<ExpMoReqItemsAsgnCom>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/3/4 16:11
     */
    @Override
    public List<ExpMoReqItemsAsgnCom> batchSubmit(IRequest request, List<ExpMoReqItemsAsgnCom> moReqItemsAsgnComs)
                    throws RuntimeException {
        Set<String> codeSet = new HashSet<>();
        if (moReqItemsAsgnComs != null && !moReqItemsAsgnComs.isEmpty()) {
            for (ExpMoReqItemsAsgnCom asgnCom : moReqItemsAsgnComs) {
                codeSet.add(asgnCom.getCompanyCode());
            }
            if (codeSet.size() != moReqItemsAsgnComs.size()) {
                throw new RuntimeException("该公司已分配,请检查!");
            }
        }
        return this.batchUpdate(request, moReqItemsAsgnComs);
    }

    /**
     * <p>
     * 申请项目-分配公司主查询
     * <p/>
     *
     * @param request
     * @param dto(查询条件)
     * @return List<ExpMoReqItemsAsgnCom>
     * @author yang.duan 2019/2/19 16:10
     */
    @Override
    public List<ExpMoReqItemsAsgnCom> queryExpMoReqItemCom(IRequest request, ExpMoReqItemsAsgnCom dto) {
        return mapper.queryExpMoReqItemCom(dto);
    }

    /**
     * <p>
     * 申请项目-批量分配公司查询公司
     * <p/>
     *
     * @param request
     * @param dto(查询条件)
     * @return List<FndCompany>
     * @author yang.duan 2019/2/19 16:10
     */
    @Override
    public List<FndCompany> queryCompanyByItem(IRequest request, ExpMoReqItemsAsgnCom dto) {
        return mapper.queryCompanyByItem(dto);
    }

    /**
     * <p>
     * 申请项目-批量分配公司(一对多)
     * <p/>
     * 
     * @param request
     * @param companies(json数组)
     * @return List<ExpMoReqItemsAsgnCom>
     * @author yang.duan 2019/2/19 16:10
     */
    @Override
    public List<ExpMoReqItemsAsgnCom> batchAsgnCompanyOneToMany(IRequest request, List<FndCompany> companies) {
        List<ExpMoReqItemsAsgnCom> expMoReqItemsAsgnComList = new ArrayList<ExpMoReqItemsAsgnCom>();
        if (!companies.isEmpty()) {
            for (FndCompany fc : companies) {
                ExpMoReqItemsAsgnCom itemsAsgnCom = new ExpMoReqItemsAsgnCom();
                List<HashMap<String, Object>> reqItemList =
                                (List<HashMap<String, Object>>) fc.getInnerMap().get("reqItem");
                if (!reqItemList.isEmpty()) {
                    HashMap<String, Object> map = reqItemList.get(0);
                    itemsAsgnCom.setMoReqItemId(Long.valueOf((String) map.get("moReqItemId")));
                }
                itemsAsgnCom.setCompanyId(fc.getCompanyId());
                itemsAsgnCom.setEnabledFlag("Y");
                itemsAsgnCom.set__status(DTOStatus.ADD);
                expMoReqItemsAsgnComList.add(itemsAsgnCom);
            }
        }
        return this.batchUpdate(request, expMoReqItemsAsgnComList);
    }


    /**
     * <p>
     * 申请项目-批量分配公司(多对多)
     * <p/>
     *
     * @param request
     * @param expMoReqItems(json数组)
     * @return List<ExpMoReqItemsAsgnCom>
     * @author yang.duan 2019/2/19 16:10
     */
    @Override
    public List<ExpMoReqItemsAsgnCom> batchAsgnCompanyManyToMany(IRequest request, List<ExpMoReqItem> expMoReqItems) {
        List<ExpMoReqItemsAsgnCom> expMoReqItemsAsgnComList = new ArrayList<ExpMoReqItemsAsgnCom>();
        if (!expMoReqItems.isEmpty()) {
            for (ExpMoReqItem item : expMoReqItems) {
                List<HashMap<String, Object>> itemList =
                                (List<HashMap<String, Object>>) item.getInnerMap().get("companyInfo");
                for (HashMap<String, Object> map : itemList) {
                    if (!map.isEmpty()) {
                        ExpMoReqItemsAsgnCom itemsAsgnCom = new ExpMoReqItemsAsgnCom();
                        itemsAsgnCom.setMoReqItemId(item.getMoReqItemId());
                        itemsAsgnCom.setCompanyId(Long.valueOf((Integer) map.get("companyId")));
                        if (checkExists(itemsAsgnCom) == 0) {
                            itemsAsgnCom.set__status(DTOStatus.ADD);
                        } else {
                            itemsAsgnCom.setAssignId(checkExists(itemsAsgnCom));
                            itemsAsgnCom.set__status(DTOStatus.UPDATE);
                        }
                        itemsAsgnCom.setEnabledFlag("Y");
                        expMoReqItemsAsgnComList.add(itemsAsgnCom);
                    }
                }
            }
        }
        return this.batchUpdate(request, expMoReqItemsAsgnComList);

    }

    /**
     * <p>
     * 校验申请项目分配公司是否存在(存在则返回主键)
     * <p/>
     * 
     * @param dto
     * @return Long
     * @author yang.duan 2019/2/19 18:16
     */
    private Long checkExists(ExpMoReqItemsAsgnCom dto) {
        int count = 0;
        count = mapper.selectCount(dto);
        if (count != 0) {
            Criteria criteria = new Criteria(dto);
            criteria.where(new WhereField(ExpMoReqItemsAsgnCom.FIELD_MO_REQ_ITEM_ID, Comparison.EQUAL),
                            new WhereField(ExpMoReqItemsAsgnCom.FIELD_COMPANY_ID, Comparison.EQUAL));
            List<ExpMoReqItemsAsgnCom> list = mapper.selectOptions(dto, criteria);
            return list.get(0).getAssignId();
        } else {
            return Long.valueOf(count);
        }
    }
}
