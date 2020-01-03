package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.*;
import com.hand.hec.fnd.mapper.FndUomAsgnComMapper;
import com.hand.hec.fnd.mapper.FndUomAsgnMoMapper;
import com.hand.hec.fnd.service.IFndUomAsgnComService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.service.IFndUomAsgnMoService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndUomAsgnMoServiceImpl extends BaseServiceImpl<FndUomAsgnMo> implements IFndUomAsgnMoService{

    @Autowired
    FndUomAsgnMoMapper mapper;

    @Autowired
    FndUomAsgnComMapper fndUomAsgnComMapper;

    @Autowired
    IFndUomAsgnComService fndUomAsgnComService;

    @Override
    public List<FndUomAsgnMo> batchAssignMagOrgManytoMany(IRequest request,List<FndUom> fndUoms){
        List<FndUomAsgnMo> fndUomAsgnMos  = new ArrayList<FndUomAsgnMo>();
        if(!fndUoms.isEmpty()){
            for(FndUom fndUom : fndUoms){
                List<HashMap<String, Object>> itemList =
                        (List<HashMap<String, Object>>) fndUom.getInnerMap().get("companyInfo");
                for (HashMap<String, Object> map : itemList) {
                    if (!map.isEmpty()) {
                        FndUomAsgnMo fndUomAsgnMo = new FndUomAsgnMo();
                        fndUomAsgnMo.setUomId(fndUom.getUomId());
                        fndUomAsgnMo.setMagOrgId(Long.valueOf((Integer) map.get("magOrgId")));
                        fndUomAsgnMo.setEnabledFlag("Y");
                        Long Flag = checkExists(fndUomAsgnMo);
                        if ( Flag == 0) {
                            fndUomAsgnMo.set__status(DTOStatus.ADD);
                            fndUomAsgnMos.add(self().insertSelective(request,fndUomAsgnMo));
                        }else{
                            fndUomAsgnMo.setAssignMoId(Flag);
                            fndUomAsgnMos.add(fndUomAsgnMo);
                        }
                    }
                }
            }
        }
        //return this.batchUpdate(request,fndUomAsgnMos);
        return fndUomAsgnMos;
    }

    /**
     * 校验计量单位分配管理组织是否存在(存在则返回主键)
     * @param dto
     * @return Long
     * @author zhongyu 2019-4-26
     */
    private Long checkExists(FndUomAsgnMo dto) {
        int count = 0;
        count = mapper.selectCount(dto);
        if (count != 0) {
            List<FndUomAsgnMo> list = mapper.checkExists(dto);
            return list.get(0).getAssignMoId();
        } else {
            return Long.valueOf(count);
        }
    }

    /**
     * 删除分配的管理组织之前，先删除分配的公司
     *
     * @param dto
     * @author zhongyu 2019-4-28
     * @return
     */
    @Override
    public int deleteByPrimaryKey(FndUomAsgnMo dto) {
        FndUomAsgnCom fndUomAsgnCom = new FndUomAsgnCom();
        fndUomAsgnCom.setAssignMoId(dto.getAssignMoId());
        List<FndUomAsgnCom> fndUomAsgnComList = fndUomAsgnComMapper.select(fndUomAsgnCom);
        if (fndUomAsgnComList != null && fndUomAsgnComList.size() > 0) {
            for (FndUomAsgnCom com : fndUomAsgnComList) {
                fndUomAsgnComService.deleteByPrimaryKey(com);
            }
        }
        return super.deleteByPrimaryKey(dto);
    }

    @Override
    public List<FndUomAsgnMo> batchAssignMoOrgOnetoMany(IRequest request, List<FndManagingOrganization> fndManagingOrganizations){
        List<FndUomAsgnMo> fndUomAsgnMos = new ArrayList<FndUomAsgnMo>();
        if (!fndManagingOrganizations.isEmpty()) {
            for (FndManagingOrganization fm : fndManagingOrganizations) {
                FndUomAsgnMo item = new FndUomAsgnMo();
                List<HashMap<String, Object>> itemList =
                        (List<HashMap<String, Object>>) fm.getInnerMap().get("objs");
                if (!itemList.isEmpty()){
                    HashMap<String, Object> map = itemList.get(0);
                    item.setUomId(Long.valueOf((String) map.get("uomId")));
                }
                item.setMagOrgId(fm.getMagOrgId());
                item.setEnabledFlag("Y");
                item.set__status(DTOStatus.ADD);
                fndUomAsgnMos.add(item);
            }
        }
        return this.batchUpdate(request, fndUomAsgnMos);
    }
}