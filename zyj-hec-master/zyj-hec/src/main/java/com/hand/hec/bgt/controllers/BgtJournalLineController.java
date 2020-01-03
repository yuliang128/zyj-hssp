package com.hand.hec.bgt.controllers;

import com.hand.hap.core.util.BeanUtil;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hec.bgt.dto.BgtJournalObject;
import com.hand.hec.bgt.service.IBgtJournalObjectService;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bgt.dto.BgtJournalLine;
import com.hand.hec.bgt.service.IBgtJournalLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;

import java.util.*;

/**
 * <p>
 * 预算日记账行Controller
 * </p>
 * 
 * @author mouse 2019/01/07 15:40
 */
@Controller
public class BgtJournalLineController extends BaseController {

    @Autowired
    private IBgtJournalLineService service;

    @Autowired
    private IBgtJournalObjectService objectService;

    @RequestMapping(value = "/bgt/journal-line/query")
    @ResponseBody
    public ResponseData query(BgtJournalLine dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);


        if (dto.getJournalHeaderId() == null) {
            return new ResponseData(Collections.emptyList());
        }

        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField[] {new WhereField(BgtJournalLine.FIELD_JOURNAL_HEADER_ID),
                new WhereField(BgtJournalLine.FIELD_JOURNAL_LINE_ID)});

        List<BgtJournalLine> lineList = service.selectOptions(requestContext, dto, criteria, page, pageSize);
        List<Map> lineMapList = new ArrayList<Map>();

        lineList.forEach(line -> {
            Map lineMap = BeanUtil.convert2Map(line);
            lineMapList.add(lineMap);
        });

        //
        // 生成预算日记账行上的费用对象数据
        // ------------------------------------------------------------------------------
        BgtJournalObject queryObj = new BgtJournalObject();
        queryObj.setJournalHeaderId(dto.getJournalHeaderId());
        List<BgtJournalObject> objList = objectService.select(requestContext, queryObj, 0, 0);

        lineMapList.forEach(lineMap -> {
            objList.forEach(lineObj -> {
                if (lineMap.get("journalLineId").equals(lineObj.getJournalLineId())) {
                    lineMap.put("^#" + lineObj.getExpenseObjectTypeId(), lineObj.getExpenseObjectDesc());
                    lineMap.put("#" + lineObj.getExpenseObjectTypeId(), lineObj.getExpenseObjectId());
                }
            });
        });


        return new ResponseData(lineMapList);
    }

    @RequestMapping(value = "/bgt/journal-line/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<BgtJournalLine> dto, BindingResult result,
                    HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);

        service.batchUpdate(requestCtx,dto);

        return new ResponseData(dto);
    }

    @RequestMapping(value = "/bgt/journal-line/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<BgtJournalLine> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}
