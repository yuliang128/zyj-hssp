package com.hand.hap.sys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hand.hap.sys.service.IHomeService;
import com.hand.hap.system.controllers.BaseController;

/**
 * @desc: 主页Controller
 * @author: hao.zhou@hand-china.com
 * @date: 2019/6/19
 */
@Controller
public class HomeController extends BaseController {

    @Autowired
    IHomeService homeService;

}
