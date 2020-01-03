package com.hand.hec.gld.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldMappingCondGrpHd;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 *@Description 用途代码定义-配置匹配组
 *@Author hui.zhao01@hand-china.com
 *@Date 2019/1/17 10:24
 *@Param
 *@Version 1.0
 **/
public interface IGldMappingCondGrpHdService extends IBaseService<GldMappingCondGrpHd>, ProxySelf<IGldMappingCondGrpHdService>{

    /**
     *@Description 创建匹配组
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/17 18:30
     *@Param
     *@Version 1.0
     **/
    void createMappingGroup(IRequest iRequest, List<GldMappingCondGrpHd> gldMappingCondGrpHds);

    /**
     *@Description 删除匹配组
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/17 18:30
     *@Param
     *@Version 1.0
     **/
    List<GldMappingCondGrpHd> deleteMappingGroups(IRequest iRequest,List<GldMappingCondGrpHd> gldMappingCondGrpHds);

    /**
     *@Description 查询动态表单返回结果
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/22 15:11
     *@Param
     *@Version 1.0
     **/
    List<HashMap<String,Object>> queryUserDy(IRequest iRequest, GldMappingCondGrpHd gldMappingCondGrpHds, HttpSession session);

    /**
     *@Description 动态表单保存提交
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/29 14:09
     *@Param
     *@Version 1.0
     **/
    void queryUserDySubmit(IRequest iRequest,List<GldMappingCondGrpHd> gldMappingCondGrpHds);
}