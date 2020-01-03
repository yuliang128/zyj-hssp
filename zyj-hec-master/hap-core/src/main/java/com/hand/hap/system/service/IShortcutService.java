package com.hand.hap.system.service;

import java.util.List;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.Shortcut;

public interface IShortcutService extends IBaseService<Shortcut>, ProxySelf<IShortcutService> {

    List<Shortcut> selectMyShortcutFunction(Long userId);

}