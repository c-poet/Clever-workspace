package cn.cpoet.blog.core.util;

import java.util.UUID;

/**
 * uuid生成工具
 *
 * @author CPoet
 */
public abstract class UUIDUtil {
    private UUIDUtil() {
    }

    /**
     * 生成32位uuid
     *
     * @return 32位uuid
     */
    public static String random() {
        return randomNorm().replaceAll("-", "");
    }

    /**
     * 生成uuid
     *
     * @return uuid
     */
    public static String randomNorm() {
        return UUID.randomUUID().toString();
    }
}
