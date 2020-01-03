package com.hand.hec.bpm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bpm.dto.JsTemplate;
import com.hand.hec.bpm.dto.PageTagBasic;
import com.hand.hec.bpm.dto.PageTagDataGuide;

/**
 * @author hao.zhou@hand-china.com
 * @date Description
 */
public interface IJsTemplateService extends IBaseService<JsTemplate>, ProxySelf<IJsTemplateService> {


    String getCurLogicFieldName(IRequest request, PageTagDataGuide dataGuide);

    String getSumGridNum(IRequest request,PageTagDataGuide dataGuide);

    String getChangeReadonly(IRequest request,PageTagDataGuide dataGuide);

    String getChangeRequired(IRequest request,PageTagDataGuide dataGuide);

    String getChangeClear(IRequest request,PageTagDataGuide dataGuide);

    String getChangeHidden(IRequest request,PageTagDataGuide dataGuide);

    String getChangeLayoutHideShow(IRequest request,PageTagDataGuide dataGuide);

    String getChangeLovParam(IRequest request,PageTagDataGuide dataGuide);

    String getChangeGridColHideShow(IRequest request,PageTagDataGuide dataGuide);

    String getLoadReadonly(IRequest request,PageTagDataGuide dataGuide);

    String getLoadRequired(IRequest request,PageTagDataGuide dataGuide);

    String getLoadClear(IRequest request,PageTagDataGuide dataGuide);

    String getLoadHidden(IRequest request,PageTagDataGuide dataGuide);

    String getLoadLayoutHideShow(IRequest request,PageTagDataGuide dataGuide);

    String getLoadLovParam(IRequest request,PageTagDataGuide dataGuide);

    String getLoadGridColHideShow(IRequest request,PageTagDataGuide dataGuide);

    String getCellClickLovPara(IRequest request,PageTagDataGuide dataGuide);

    String getChangeFormula(IRequest request,PageTagDataGuide dataGuide,PageTagBasic pageTagBasic,PageTagBasic resultTagBasic,String formulaType,String precision);
}