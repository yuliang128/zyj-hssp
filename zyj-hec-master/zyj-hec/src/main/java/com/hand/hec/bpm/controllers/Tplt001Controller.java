package com.hand.hec.bpm.controllers;


import com.hand.hap.code.rule.exception.CodeRuleException;
import com.hand.hap.code.rule.service.ISysCodeRuleProcessService;
import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.Company;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.fnd.service.ICompanyService;
import com.hand.hap.fnd.service.IFndCompanyService;
import com.hand.hap.hr.dto.Employee;
import com.hand.hap.hr.service.IEmployeeService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bpm.dto.*;
import com.hand.hec.bpm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class Tplt001Controller extends BaseController {
    public static final String CODE_LAYOUT_HEADER = "HEADER_FORM";
    public static final String CODE_LAYOUT_LINE = "LINE_GRID";

    @Autowired
    private IDocHeaderService docHeaderService;
    @Autowired
    private ITplt001HeaderService headerService;
    @Autowired
    private ITplt001LineService lineService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IFndCompanyService companyService;

    @Autowired
    private IDynamicDataLineService dataLineService;

    @Autowired
    private ICoreEngine coreEngine;

    @Autowired
    private IPageService pageService;

    @Autowired
    private IPageGroupService pageGroupService;

    @Autowired
    private ISysCodeRuleProcessService codeRuleProcessService;

    @RequestMapping(value = "/bpm/tplt001")
    public ModelAndView jumpTplt001(HttpServletRequest request, @RequestParam Long pageId) {
        ModelAndView view = new ModelAndView("bpm/tplt001");
        IRequest requestCtx = createRequestContext(request);

        Map<String, String> docHeaderInfo = new HashMap<String, String>();
        Employee employee = employeeService.queryInfoByCode(requestCtx.getEmployeeCode());
        docHeaderInfo.put("employeeId", employee.getEmployeeId().toString());
        docHeaderInfo.put("employeeCode", requestCtx.getEmployeeCode());
        docHeaderInfo.put("employeeName", employee.getName());
        docHeaderInfo.put("companyId", requestCtx.getCompanyId().toString());
        FndCompany company = new FndCompany();
        company.setCompanyId(requestCtx.getCompanyId());
        company = companyService.selectByPrimaryKey(requestCtx, company);
        docHeaderInfo.put("companyName", company.getCompanyShortName());
        view.addObject("docHeaderInfo", docHeaderInfo);
        view.addObject("requestContext", requestCtx);

        Page page = new Page();
        page.setPageId(pageId);
        page = pageService.selectByPrimaryKey(requestCtx, page);

        PageGroup group = new PageGroup();
        group.setGroupId(page.getGroupId());
        group = pageGroupService.selectByPrimaryKey(requestCtx, group);

        view.addObject("templateId", group.getTemplateId());
        view.addObject("pageGroupId", page.getGroupId());

        return view;
    }

    @RequestMapping(value = "/bpm/tplt001/docHeader/query")
    @ResponseBody
    public ResponseData queryDocHeader(HttpServletRequest request, DocHeader docHeader) {
        if (docHeader.getDocHeaderId() == null) {
            return new ResponseData(Collections.emptyList());
        } else {
            IRequest requestCtx = createRequestContext(request);
            Criteria criteria = new Criteria(docHeader);
            criteria.where(new WhereField(DocHeader.FIELD_DOC_HEADER_ID, Comparison.EQUAL));
            return new ResponseData(docHeaderService.selectOptions(requestCtx, docHeader, criteria, 0, 0));
        }
    }

    @RequestMapping(value = "/bpm/tplt001/header/query")
    @ResponseBody
    public ResponseData queryHeader(HttpServletRequest request, Tplt001Header header) {
        if (header.getDocHeaderId() == null) {
            return new ResponseData(Collections.emptyList());
        } else {
            IRequest requestCtx = createRequestContext(request);
            Criteria criteria = new Criteria(header);
            criteria.where(new WhereField(Tplt001Header.FIELD_DOC_HEADER_ID, Comparison.EQUAL));
            return new ResponseData(headerService.selectOptions(requestCtx, header, criteria, 0, 0));
        }
    }

    @RequestMapping(value = "/bpm/tplt001/line/query")
    @ResponseBody
    public ResponseData queryLine(HttpServletRequest request, Tplt001Line line) {
        if (line.getHeaderId() == null) {
            return new ResponseData(Collections.emptyList());
        } else {
            IRequest requestCtx = createRequestContext(request);
            Criteria criteria = new Criteria(line);
            criteria.where(new WhereField(Tplt001Line.FIELD_HEADER_ID, Comparison.EQUAL));
            return new ResponseData(lineService.selectOptions(requestCtx, line, criteria, 0, 0));
        }
    }

    @RequestMapping(value = "/bpm/tplt001/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<DocHeader> dto, @RequestParam Long pageId, HttpServletRequest request,
                    BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);

        for (DocHeader docHeader : dto) {
            if (docHeader.getDocumentNumber() == null) {
                try {
                    String documentNumber = codeRuleProcessService.getRuleCode("BPM");
                    docHeader.setDocumentNumber(documentNumber);
                } catch (CodeRuleException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        dto = docHeaderService.batchUpdate(requestCtx, dto);

        for (DocHeader docHeader : dto) {
            // 设置docHeaderId
            for (Tplt001Header tplt001Header : docHeader.getTplt001Headers()) {
                tplt001Header.setDocHeaderId(docHeader.getDocHeaderId());
            }
            // 保存Tplt001Header
            List<Tplt001Header> tplt001Headers = headerService.batchUpdate(requestCtx, docHeader.getTplt001Headers());
            // 保存Tplt001Header对应的动态数据
            List<DynamicDataLine> headerDataLines = new ArrayList<DynamicDataLine>();
            for (Tplt001Header tplt001Header : tplt001Headers) {
                DynamicDataLine headerDataLine = tplt001Header.convertDynamicDataLine();
                headerDataLine.setDocHeaderId(docHeader.getDocHeaderId());
                headerDataLine.setBusinessCategory(Tplt001Header.BUSINESS_CATEGORY);
                headerDataLine.setDocId(tplt001Header.getHeaderId());
                headerDataLines.add(headerDataLine);
            }
            headerDataLines = dataLineService.batchUpdate(requestCtx, headerDataLines);

            for (Tplt001Header tplt001Header : tplt001Headers) {
                if (tplt001Header.getTplt001Lines() == null) {
                    continue;
                }

                Long maxLineNum = lineService.queryMaxLineNum(tplt001Header.getHeaderId());
                Long lineNum = 0L;
                if (maxLineNum != null) {
                    lineNum = maxLineNum;
                }

                // 设置tplt001HeaderId
                for (Tplt001Line tplt001Line : tplt001Header.getTplt001Lines()) {
                    tplt001Line.setHeaderId(tplt001Header.getHeaderId());
                    if (tplt001Line.getLineNum() == null) {
                        lineNum += 10;
                        tplt001Line.setLineNum(lineNum);
                    }
                }
                // 保存Tplt001Line
                List<Tplt001Line> tplt001Lines = lineService.batchUpdate(requestCtx, tplt001Header.getTplt001Lines());

                // 保存Tplt001Line对应的动态数据
                List<DynamicDataLine> lineDataLines = new ArrayList<DynamicDataLine>();
                for (Tplt001Line tplt001Line : tplt001Lines) {
                    DynamicDataLine lineDataLine = tplt001Line.convertDynamicDataLine();
                    lineDataLine.setDocHeaderId(docHeader.getDocHeaderId());
                    lineDataLine.setBusinessCategory(Tplt001Line.BUSINESS_CATEGORY);
                    lineDataLine.setDocId(tplt001Line.getLineId());
                    lineDataLine.setParentBusinessCategory(Tplt001Line.PARENT_BUSINESS_CATEGORY);
                    lineDataLine.setParentDocId(tplt001Header.getHeaderId());
                    lineDataLines.add(lineDataLine);
                }
                lineDataLines = dataLineService.batchUpdate(requestCtx, lineDataLines);

                tplt001Header.setTplt001Lines(tplt001Lines);

                // 触发行组件对应的回写过程
                for (DynamicDataLine dataLine : lineDataLines) {
                    coreEngine.fireLayoutWriteBack(requestCtx, pageId, CODE_LAYOUT_LINE, dataLine);
                }
            }
            docHeader.setTplt001Headers(tplt001Headers);

            // 触发头组件对应的回写过程
            for (DynamicDataLine dataLine : headerDataLines) {
                coreEngine.fireLayoutWriteBack(requestCtx, pageId, CODE_LAYOUT_HEADER, dataLine);
            }
        }

        return new ResponseData(dto);
    }
}
