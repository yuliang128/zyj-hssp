package com.hand.hec.gld.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldAccEntityRefSob;
import com.hand.hec.gld.mapper.GldAccEntityRefSobMapper;
import com.hand.hec.gld.service.IGldAccEntityRefSobService;

/**
 * <p>
 * GldAccEntityRefSobServiceImpl
 * </p>
 * 
 * @author yang.duan 2019/01/10 13:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldAccEntityRefSobServiceImpl extends BaseServiceImpl<GldAccEntityRefSob> implements IGldAccEntityRefSobService{

    @Autowired
    private GldAccEntityRefSobMapper mapper;

    @Override
    public List<GldAccEntityRefSob> queryMoreSob(Long accEntityId,IRequest request,Integer page,Integer pageSize) {
        if(page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return mapper.selectMoreSob(accEntityId);
    }

    /**
     *
     * @param request
     * @param list
     * @author ngls.luhui 2019-01-21 20:13
     * @return java.util.List<com.hand.hec.gld.dto.GldAccEntityRefSob>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<GldAccEntityRefSob> batchUpdate(IRequest request, @StdWho List<GldAccEntityRefSob> list) {
        IBaseService<GldAccEntityRefSob> self = ((IBaseService<GldAccEntityRefSob>) AopContext.currentProxy());
        for (GldAccEntityRefSob accSob : list) {
            switch (accSob.get__status()) {
                case "add":
                    if (accSob.getDefaultFlag().equals("Y") & accSob.getRefId() != null) {
                        //因为前台修改默认账套js是使用了dataset.create，所以status是add，如果是修改默认账套
                        if (mapper.select(accSob).size() != 0) {
                            //数据库中已经存在，不做处理
                            break;
                        } else {
                            //对默认账套做了修改,做更新--1.更新的默认账套不在原本的分配序列中，直接更新 2.更新的默认账套原本已经分配过，更改状态即可
                            List<GldAccEntityRefSob> gldAccEntityRefSobList = mapper.select(GldAccEntityRefSob.builder().accEntityId(accSob.getAccEntityId()).setOfBooksId(accSob.getSetOfBooksId()).build());
                            if(gldAccEntityRefSobList.size()!=0){
                                //重新分配的默认账套原本就存在，更改状态
                                //1.新的默认账套N→Y
                                GldAccEntityRefSob newGldAccEntityRefSob = new GldAccEntityRefSob();
                                newGldAccEntityRefSob.setRefId(gldAccEntityRefSobList.get(0).getRefId());
                                newGldAccEntityRefSob.setAccEntityId(gldAccEntityRefSobList.get(0).getAccEntityId());
                                newGldAccEntityRefSob.setSetOfBooksId(gldAccEntityRefSobList.get(0).getSetOfBooksId());
                                newGldAccEntityRefSob.setEnabledFlag(gldAccEntityRefSobList.get(0).getEnabledFlag());
                                newGldAccEntityRefSob.setDefaultFlag("Y");
                                self.updateByPrimaryKeySelective(request,newGldAccEntityRefSob);
                                //2.旧的默认账套Y→N
                                self.updateByPrimaryKeySelective(request,GldAccEntityRefSob.builder().refId(accSob.getRefId()).defaultFlag("N").build());
                                break;
                            }else{
                                //重新分配的默认预算主体原本不存在，直接更新原本的
                                self.updateByPrimaryKeySelective(request, accSob);
                                break;
                            }
                        }
                    }else{
                        //其他情况做新增
                        self.insertSelective(request, accSob);
                        break;
                    }
                case "update":
                    if (useSelectiveUpdate()) {
                        self.updateByPrimaryKeySelective(request, accSob);
                    } else {
                        self.updateByPrimaryKey(request, accSob);
                    }
                    break;
                case "delete":
                    self.deleteByPrimaryKey(accSob);
                    break;
                default:
                    break;
            }
        }
        return list;
    }
}