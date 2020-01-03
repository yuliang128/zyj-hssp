package com.hand.hap.system.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.system.dto.Shortcut;

public interface ShortcutMapper extends Mapper<Shortcut> {

    List<Shortcut> selectMyShortcutFunction(Long userId);

}