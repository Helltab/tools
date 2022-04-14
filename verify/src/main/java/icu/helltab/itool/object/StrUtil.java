package icu.helltab.itool.object;

/**
 * Topic 字符串工具
 * @author helltab
 * @version 1.0
 * @date 2022/2/27 16:23
 */
public class StrUtil {
    /**
     * 格式化字符串
     * @param template
     * @param vars
     * @return
     */
    public static String format(String template, Object... vars) {
        int len = vars.length;
        String[] split = template.split("\\{}");
        int strLen = split.length;
        StringBuilder temp = new StringBuilder();
        int idx = 0;
        for (; idx < len; idx++) {
            if(idx==strLen) {
                break;
            }
            temp.append(split[idx]).append(vars[idx]);
        }
        for (int i = idx; i < strLen; i++) {
            temp.append(split[i]);
        }
        return temp.toString();
    }

}
