package cn.cpoet.blog.core.util;

import java.security.MessageDigest;

/**
 * md5工具
 *
 * @author wanggf
 */
public abstract class Md5Util {
    private Md5Util() {
    }

    public static String md5hex(String str) {
        return binary2hex(md5binary(str));
    }

    public static String md5hex(byte[] bytes) {
        return binary2hex(md5binary(bytes));
    }

    public static byte[] md5binary(String str) {
        return md5binary(str == null ? "null".getBytes() : str.getBytes());
    }

    public static byte[] md5binary(byte[] bytes) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return md5.digest(bytes);
    }

    public static String binary2hex(byte[] bytes) {
        StringBuilder md5str = new StringBuilder();
        int digital;
        for (byte bit : bytes) {
            digital = bit;
            if (digital < 0) {
                digital &= 0xFF;
            }
            if (digital < 0x10) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString();
    }
}
