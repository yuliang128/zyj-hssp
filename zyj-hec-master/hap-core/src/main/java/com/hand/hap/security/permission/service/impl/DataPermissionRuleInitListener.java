package com.hand.hap.security.permission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.hand.hap.core.AppContextInitListener;

/**
 * @author jialong.zuo@hand-china.com on 2017/8/30.
 */
@Service
public class DataPermissionRuleInitListener implements AppContextInitListener {
    @Autowired
    DataPermissionCacheContainer container;

    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        container.initContainer();
    }
}
