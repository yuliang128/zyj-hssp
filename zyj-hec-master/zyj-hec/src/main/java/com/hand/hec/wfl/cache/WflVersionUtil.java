package com.hand.hec.wfl.cache;

/**
 * description
 *
 * @author mouse 2019/03/04 13:50
 */
public class WflVersionUtil {

    /**
     * 获取版本号
     *
     * @param id
     * @param version
     * @author mouse 2019-03-04 13:51
     * @return java.lang.String
     */
    public static String getVersion(Long id, Long version) {
        return id.intValue() + "-V" + version.intValue();
    }

    /**
     * 获取版本号
     *
     * @param id
     * @param version
     * @author mouse 2019-03-04 13:51
     * @return java.lang.String
     */
    public static String getVersion(String prefix, Long id, Long version) {
        return prefix + "-" + id.intValue() + "-V" + version.intValue();
    }
}
