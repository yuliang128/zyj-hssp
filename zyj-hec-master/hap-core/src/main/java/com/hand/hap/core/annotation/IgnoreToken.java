package com.hand.hap.core.annotation;

import java.lang.annotation.*;

/**
 *
 * Sometimes you want to disable the token checking for Controller,then use this annotation.
 *
 * <p> Usage: just annotate the Controller with this annotation. See the following example </p>
 * <p>
 *
 * Example:
 * <pre>
 *   &#064;  @IgnoreToken
 *   &#064;  @Controller
 *   &#064;  @RequestMapping(value = "/bgt/entity")
 *   &#064;  public class BgtEntityController extends BaseController{...}
 * </pre>
 *
 * </p>
 *
 *
 *
 * @author rui.shi@hand-china.com  2019/01/2019/1/29
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreToken {
}
