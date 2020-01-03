package com.hand.hec.bpm.service;

import com.hand.hap.core.IRequest;
import com.hand.hec.bpm.dto.*;
import uncertain.composite.CompositeMap;

import java.util.List;

public interface IJsEngineService {

    List<PageButton> queryPageButton(IRequest request, Long pageId);

    List<PageLayoutBasic> queryPageLayoutBasic(IRequest request, Long pageId);

    List<PageLayoutForm> queryPageLayoutForm(IRequest request, Long layoutId);

    List<PageLayoutGrid> queryPageLayoutGrid(IRequest request, Long layoutId);

    List<PageLayoutTab> queryPageLayoutTab(IRequest request, Long layoutId);

    List<PageLayoutEvent> queryPageLayoutEvent(IRequest request, Long layoutId);

    List<PageLayoutButton> queryPageLayoutButton(IRequest request, Long layoutId);

    List<PageTagBasic> queryPageTagBasic(IRequest request, Long layoutId);

    List<PageTagCheckbox> queryPageTagCheckbox(IRequest request, Long tagId);

    List<PageTagCombobox> queryPageTagCombobox(IRequest request, Long tagId);

    List<PageTagComboboxMap> queryPageTagComboboxMap(IRequest request, Long tagId);

    List<PageTagDatepicker> queryPageTagDatepicker(IRequest request, Long tagId);

    List<PageTagDatetimepicker> queryPageTagDatetimepicker(IRequest request, Long tagId);

    List<PageTagLabel> queryPageTagLabel(IRequest request, Long tagId);

    List<PageTagLov> queryPageTagLov(IRequest request, Long tagId);

    List<PageTagLovMap> queryPageTagLovMap(IRequest request, Long tagId);

    List<PageTagNumberfield> queryPageTagNumberfield(IRequest request, Long tagId);

    List<PageTagRadio> queryPageTagRadio(IRequest request, Long tagId);

    List<PageTagTextfield> queryPageTagTextfield(IRequest request, Long tagId);

    List<PageTagEvent> queryPageTagEvent(IRequest request, Long tagId);

    String getLovDefaultvalue(IRequest request, CompositeMap context, String lovStr);

    String getJavaDefaultvalue(IRequest request, CompositeMap context, String javaStr);
}
