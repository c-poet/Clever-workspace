package cn.cpoet.blog.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 驼峰下划线工具
 *
 * @author CPoet
 */
public abstract class CamelUtil {

    private final static Pattern UNDERLINE_TO_CAMEL_PATTERN = Pattern.compile("([A-Za-z\\d]+)(_)?");
    private final static Pattern CAMEL_TO_UNDERLINE_PATTERN = Pattern.compile("[A-Z]([a-z\\d]+)?");


    private CamelUtil() {
    }

    /**
     * 下划线转驼峰法
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line) {
        return underline2Camel(line, true);
    }

    /**
     * 下划线转驼峰法
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Matcher matcher = UNDERLINE_TO_CAMEL_PATTERN.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) :
                Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuilder sb = new StringBuilder();
        Matcher matcher = CAMEL_TO_UNDERLINE_PATTERN.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }
}
