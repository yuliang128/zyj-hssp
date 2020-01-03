/*
 * #{copyright}#
 */

package com.hand.hap.cache;

import java.lang.annotation.*;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Inherited
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheDelete {
    String cache();
}
