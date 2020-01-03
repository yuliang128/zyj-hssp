package com.hand.hap.system.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.Hotkey;

public interface IHotkeyService extends IBaseService<Hotkey>, ProxySelf<IHotkeyService>{

    /**
     * 获取首选项展示的热键数据
     *
     * @return
     */
     List<Hotkey> preferenceQuery(IRequest iRequest);

}