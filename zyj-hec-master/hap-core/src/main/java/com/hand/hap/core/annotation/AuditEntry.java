/*
 * #{copyright}#
 */
package com.hand.hap.core.annotation;


import java.lang.annotation.*;
/**
 * 修订,历史记录入口方法标记.
 *
 * @author shengyang.zhou@hand-china.com
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditEntry {
    String value() default "";
}