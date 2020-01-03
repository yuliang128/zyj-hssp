package com.hand.hap.system.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.dto.Shortcut;
import com.hand.hap.system.service.IShortcutService;

@Controller
public class ShortcutController extends BaseController {

    @Autowired
    private IShortcutService service;


    @RequestMapping(value = "/sys/shortcut/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectMyShortcutFunction(requestContext.getUserId()));
    }

    @RequestMapping(value = "/sys/shortcut/submit")
    @ResponseBody
    public ResponseData update(HttpServletRequest request, String functionCode) {
        IRequest requestContext = createRequestContext(request);
        Shortcut shortcuts = new Shortcut(requestContext.getUserId(), functionCode);
        List<Shortcut> shortcutList = service.select(requestContext, shortcuts, 1, 10);
        if (shortcutList.isEmpty()) {
            service.insert(requestContext, shortcuts);
        } else {
            return null;
        }
        return new ResponseData();
    }

    @RequestMapping(value = "/sys/shortcut/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, String shortcutId) {
        Shortcut shortcut = new Shortcut();
        shortcut.setShortcutId(Long.parseLong(shortcutId));
        service.deleteByPrimaryKey(shortcut);
        return new ResponseData();
    }
}