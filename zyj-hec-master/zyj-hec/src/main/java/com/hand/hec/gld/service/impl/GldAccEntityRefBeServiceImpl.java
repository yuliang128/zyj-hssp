package com.hand.hec.gld.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldAccEntityRefBe;
import com.hand.hec.gld.mapper.GldAccEntityRefBeMapper;
import com.hand.hec.gld.service.IGldAccEntityRefBeService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * GldAccEntityRefBeServiceImpl
 * </p>
 * 
 * @author yang.duan 2019/01/10 13:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldAccEntityRefBeServiceImpl extends BaseServiceImpl<GldAccEntityRefBe> implements IGldAccEntityRefBeService{

    @Autowired
    private GldAccEntityRefBeMapper mapper;

    @Override
    public List<GldAccEntityRefBe> batchSelect(Long accEntityId,IRequest request, Integer page, Integer pageSize) {
        if(page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return mapper.batchSelect(accEntityId);
    }

    /**
     *
     * @param request
     * @param list
     * @author ngls.luhui 2019-01-21 20:13
     * @return java.util.List<com.hand.hec.gld.dto.GldAccEntityRefBe>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<GldAccEntityRefBe> batchUpdate(IRequest request, @StdWho List<GldAccEntityRefBe> list) {
        IBaseService<GldAccEntityRefBe> self = ((IBaseService<GldAccEntityRefBe>) AopContext.currentProxy());
        for (GldAccEntityRefBe accBe : list) {
            switch (accBe.get__status()) {
                case "add":
                    if (accBe.getDefaultFlag().equals("Y")&accBe.getRefId() != null) {
                        if(accBe.getBgtEntityId() == null){
                            //证明是直接删掉了默认关联关系，则更新取消掉默认值
                            self.updateByPrimaryKeySelective(request,GldAccEntityRefBe.builder().refId(accBe.getRefId()).defaultFlag("N").build());
                            break;
                        }
                        //因为前台修改默认核算主体js是使用了dataset.create，所以status是add，则是修改默认核算主体
                        if (mapper.select(accBe).size() != 0) {
                            //数据库中已经存在，未修改过，不做处理
                            break;
                        } else {
                            //对默认预算主体做了修改,做更新--1.更新的默认预算实体不在原本的分配序列中，直接更新 2.更新的默认预算实体原本已经分配过，更改状态即可
                            List<GldAccEntityRefBe> gldAccEntityRefBeList = mapper.select(GldAccEntityRefBe.builder().accEntityId(accBe.getAccEntityId()).bgtEntityId(accBe.getBgtEntityId()).build());
                            if(gldAccEntityRefBeList.size()!=0){
                                //重新分配的默认预算主体原本就存在，更改状态
                                //1.新的默认预算实体N→Y
                                GldAccEntityRefBe newGldAccEntityRefBe = new GldAccEntityRefBe();
                                newGldAccEntityRefBe.setRefId(gldAccEntityRefBeList.get(0).getRefId());
                                newGldAccEntityRefBe.setAccEntityId(gldAccEntityRefBeList.get(0).getAccEntityId());
                                newGldAccEntityRefBe.setBgtEntityId(gldAccEntityRefBeList.get(0).getBgtEntityId());
                                newGldAccEntityRefBe.setEnabledFlag(gldAccEntityRefBeList.get(0).getEnabledFlag());
                                newGldAccEntityRefBe.setDefaultFlag("Y");
                                self.updateByPrimaryKeySelective(request,newGldAccEntityRefBe);
                                //2.旧的默认预算实体Y→N
                                self.updateByPrimaryKeySelective(request,GldAccEntityRefBe.builder().refId(accBe.getRefId()).defaultFlag("N").build());
                                break;
                            }else{
                                //重新分配的默认预算主体原本不存在，直接更新原本的
                                self.updateByPrimaryKeySelective(request, accBe);
                                break;
                            }
                        }
                    }else{
                        //其他情况,若bgtEntityId不为空的话做新增
                        if(accBe.getBgtEntityId() != null){
                            self.insertSelective(request, accBe);
                            break;
                        }else{
                            break;
                        }
                    }
                case "update":
                    if (useSelectiveUpdate()) {
                        self.updateByPrimaryKeySelective(request, accBe);
                    } else {
                        self.updateByPrimaryKey(request, accBe);
                    }
                    break;
                case "delete":
                    self.deleteByPrimaryKey(accBe);
                    break;
                default:
                    break;
            }
        }
        return list;
    }
    /**
     * <p>
     * 根据核算主体获取默认预算实体
     * <p/>
     *
     * @param request
     * @param accEntityId meaning
     * @return 预算实体信息
     * @author yang.duan 2019/3/21 10:59
     */
    @Override
    public GldAccEntityRefBe getDeftBgtEntity(IRequest request, Long accEntityId) {
        return mapper.getDeftBgtEntity(accEntityId);
    }
}