package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.exp.dto.ExpMoReportTypeAsgnCom;
import com.hand.hec.exp.dto.ExpMoReqItemsAsgnCom;
import com.hand.hec.exp.exception.ExpReportTypeException;
import com.hand.hec.exp.mapper.ExpMoReportTypeAsgnComMapper;
import com.hand.hec.exp.service.IExpMoReportTypeAsgnComService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * ExpMoReportTypeAsgnComServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 14:49
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReportTypeAsgnComServiceImpl extends BaseServiceImpl<ExpMoReportTypeAsgnCom>
                implements IExpMoReportTypeAsgnComService {

    @Autowired
    ExpMoReportTypeAsgnComMapper mapper;



    /**
     * <p>
     * 报销单类型-分配公司批量提交
     * <p/>
     *
     * @param IRequest request
     * @param List<ExpMoReportTypeAsgnCom> reportTypeAsgnComs
     * @return List<ExpMoReportTypeAsgnCom>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/3/4 11:21
     */
    @Override
    public List<ExpMoReportTypeAsgnCom> batchSubmit(IRequest request, List<ExpMoReportTypeAsgnCom> reportTypeAsgnComs)
                    throws RuntimeException {
        Set<String> codeSet = new HashSet<String>();
        if (reportTypeAsgnComs != null && !reportTypeAsgnComs.isEmpty()) {
            for(ExpMoReportTypeAsgnCom typeAsgnCom : reportTypeAsgnComs){
                codeSet.add(typeAsgnCom.getCompanyCode());
            }
            if(codeSet.size() != reportTypeAsgnComs.size()){
                //throw new RuntimeException("该公司已分配,请检查!");
                throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_COMPANY_EXIST_ERROR,null);
            }
        }
        return this.batchUpdate(request, reportTypeAsgnComs);
    }


    /**
     * <p>
     * 报销单类型定义-批量分配公司查询
     * <p/>
     *
     * @param ExpMoReportType dto(查询条件)
     * @return List<FndCompany>
     * @author yang.duan 2019/2/21 14:59
     */
    @Override
    public List<FndCompany> queryCompanyInfo(ExpMoReportType dto) {
        return mapper.queryCompanyInfo(dto);
    }

    /**
     * <p>
     * 批量分配公司(一对多)
     * <p/>
     * 
     * @param IRequest request
     * @param List<FndCompany> companies(公司数据)
     * @return List<ExpMoReportTypeAsgnCom>
     * @author yang.duan 2019/2/21 15:00
     */
    @Override
    public List<ExpMoReportTypeAsgnCom> batchAsgnCompanyOneToMany(IRequest request, List<FndCompany> companies) {
        List<ExpMoReportTypeAsgnCom> reportTypeAsgnComList = new ArrayList<ExpMoReportTypeAsgnCom>();
        if (!companies.isEmpty()) {
            for (FndCompany fc : companies) {
                ExpMoReportTypeAsgnCom typeAsgnCom = new ExpMoReportTypeAsgnCom();
                List<HashMap<String, Object>> repTypeList =
                                (List<HashMap<String, Object>>) fc.getInnerMap().get("reportType");
                if (!repTypeList.isEmpty()) {
                    HashMap<String, Object> map = repTypeList.get(0);
                    typeAsgnCom.setMoExpReportTypeId(Long.valueOf((String) map.get("moExpReportTypeId")));
                }
                typeAsgnCom.setCompanyId(fc.getCompanyId());
                typeAsgnCom.setEnabledFlag("Y");
                typeAsgnCom.set__status(DTOStatus.ADD);
                reportTypeAsgnComList.add(typeAsgnCom);
            }
        }
        return this.batchUpdate(request, reportTypeAsgnComList);
    }

    /**
     * <p>
     * 批量分配公司(多对多)
     * <p/>
     * 
     * @param IRequest request
     * @param List<ExpMoReportType> reportTypes(报销单类型数据)
     * @return List<ExpMoReportTypeAsgnCom>
     * @author yang.duan 2019/2/21 15:00
     */
    @Override
    public List<ExpMoReportTypeAsgnCom> batchAsgnCompanyManyToMany(IRequest request,
                    List<ExpMoReportType> reportTypes) {
        List<ExpMoReportTypeAsgnCom> reportTypeAsgnComList = new ArrayList<ExpMoReportTypeAsgnCom>();
        if (!reportTypes.isEmpty()) {
            for (ExpMoReportType type : reportTypes) {
                List<HashMap<String, Object>> comopanyList =
                                (List<HashMap<String, Object>>) type.getInnerMap().get("companyInfo");
                for (HashMap<String, Object> map : comopanyList) {
                    if (!map.isEmpty()) {
                        ExpMoReportTypeAsgnCom typeAsgnCom = new ExpMoReportTypeAsgnCom();
                        typeAsgnCom.setMoExpReportTypeId(type.getMoExpReportTypeId());
                        typeAsgnCom.setCompanyId(Long.valueOf((Integer) map.get("companyId")));
                        if (checkExists(typeAsgnCom) == 0) {
                            typeAsgnCom.set__status(DTOStatus.ADD);
                        } else {
                            typeAsgnCom.setAssignId(checkExists(typeAsgnCom));
                            typeAsgnCom.set__status(DTOStatus.UPDATE);
                        }
                        typeAsgnCom.setEnabledFlag("Y");
                        reportTypeAsgnComList.add(typeAsgnCom);
                    }
                }
            }
        }
        return this.batchUpdate(request, reportTypeAsgnComList);
    }

    /**
     * <p>
     * 校验报销单类型定义分配公司是否存在(存在则返回主键)
     * <p/>
     * 
     * @param dto
     * @return Long
     * @author yang.duan 2019/2/19 18:16
     */
    private Long checkExists(ExpMoReportTypeAsgnCom dto) {
        int count = 0;
        count = mapper.selectCount(dto);
        if (count != 0) {
            Criteria criteria = new Criteria(dto);
            criteria.where(new WhereField(ExpMoReportTypeAsgnCom.FIELD_MO_EXP_REPORT_TYPE_ID, Comparison.EQUAL),
                            new WhereField(ExpMoReqItemsAsgnCom.FIELD_COMPANY_ID, Comparison.EQUAL));
            List<ExpMoReportTypeAsgnCom> list = mapper.selectOptions(dto, criteria);
            return list.get(0).getAssignId();
        } else {
            return Long.valueOf(count);
        }
    }

}
