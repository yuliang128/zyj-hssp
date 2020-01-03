package com.hand.hec.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoExpItemAsgnCom;
import com.hand.hec.exp.dto.ExpMoExpenseItem;
import com.hand.hec.fnd.dto.FndUom;
import com.hand.hec.fnd.dto.FndUomAsgnMo;
import com.hand.hec.fnd.mapper.FndUomAsgnComMapper;
import com.hand.hec.fnd.service.IFndUomAsgnMoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndUomAsgnCom;
import com.hand.hec.fnd.service.IFndUomAsgnComService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndUomAsgnComServiceImpl extends BaseServiceImpl<FndUomAsgnCom> implements IFndUomAsgnComService{

    @Autowired
    private FndUomAsgnComMapper mapper;
    @Autowired
    private IFndUomAsgnMoService fndUomAsgnMoService;

    @Override
   public List<FndUomAsgnCom> selectByMagOrgId(IRequest request, Long magOrgId, FndUomAsgnCom dto, int page, int pageSize){
        PageHelper.startPage(page, pageSize);
        return mapper.selectByMagOrgId(dto,magOrgId);
   }

   @Override
   public List<FndUomAsgnCom> batchAssignCompanyManytoMany(IRequest request,List<FndUom> fndUoms){
        List<FndUomAsgnMo> fndUomAsgnMos = fndUomAsgnMoService.batchAssignMagOrgManytoMany(request,fndUoms);
        List<FndUomAsgnCom> fndUomAsgnComs  = new ArrayList<FndUomAsgnCom>();
        if(!fndUomAsgnMos.isEmpty() && !fndUoms.isEmpty()) {
            List<Long> assignMoId =  fndUomAsgnMos.parallelStream().map(FndUomAsgnMo::getAssignMoId).distinct().collect(Collectors.toList());
            for (Long MoId: assignMoId) {
                for(FndUom fndUom : fndUoms){
                    List<HashMap<String, Object>> itemList =
                            (List<HashMap<String, Object>>) fndUom.getInnerMap().get("companyInfo");
                    for (HashMap<String, Object> map : itemList) {
                        if (!map.isEmpty()) {
                            FndUomAsgnCom fndUomAsgnCom = new FndUomAsgnCom();
                            fndUomAsgnCom.setAssignMoId(MoId);
                            fndUomAsgnCom.setCompanyId(Long.valueOf((Integer) map.get("companyId")));
                            fndUomAsgnCom.setEnabledFlag("Y");
                            if (checkExists(fndUomAsgnCom) == 0) {
                                fndUomAsgnCom.set__status(DTOStatus.ADD);
                                fndUomAsgnComs.add(self().insertSelective(request,fndUomAsgnCom));
                            }else {
                                fndUomAsgnCom.setAssignComId(checkExists(fndUomAsgnCom));
                                fndUomAsgnComs.add(fndUomAsgnCom);
                            }
                        }
                    }
                }
            }
            }
        return fndUomAsgnComs;

   }



    /**
     * 校验计量单位分配公司是否存在(存在则返回主键)
     * @param dto
     * @return Long
     * @author zhongyu 2019-04-26
     */
    private Long checkExists(FndUomAsgnCom dto) {
        int count = 0;
        count = mapper.selectCount(dto);
        if (count != 0) {
            Criteria criteria = new Criteria(dto);
            criteria.where(new WhereField(FndUomAsgnCom.FIELD_ASSIGN_MO_ID, Comparison.EQUAL),
                    new WhereField(FndUomAsgnCom.FIELD_COMPANY_ID, Comparison.EQUAL));
            List<FndUomAsgnCom> list = mapper.selectOptions(dto, criteria);
            return list.get(0).getAssignComId();
        } else {
            return Long.valueOf(count);
        }
    }


    @Override
    public List<FndUomAsgnCom> batchAssignCompanyOnetoMany(IRequest request,List<FndCompany>fndCompanies){
        List<FndUomAsgnCom> fndUomAsgnComs = new ArrayList<FndUomAsgnCom>();
        if (!fndCompanies.isEmpty()) {
            for (FndCompany fc : fndCompanies) {
                FndUomAsgnCom item = new FndUomAsgnCom();
                List<HashMap<String, Object>> itemList =
                        (List<HashMap<String, Object>>) fc.getInnerMap().get("objs");
                if (!itemList.isEmpty()){
                    HashMap<String, Object> map = itemList.get(0);
                    item.setAssignMoId(Long.valueOf((String) map.get("assignMoId")));
                }
                item.setCompanyId(fc.getCompanyId());
                item.setEnabledFlag("Y");
                item.set__status(DTOStatus.ADD);
                fndUomAsgnComs.add(item);
            }
        }
        return this.batchUpdate(request, fndUomAsgnComs);
    }

}