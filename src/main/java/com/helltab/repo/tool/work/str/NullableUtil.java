package com.helltab.repo.tool.work.str;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Topic 严格判空工具, 一般判空请使用 == null
 * @author helltab
 * @version 1.0
 * @date 2021/10/21 9:43
 */
public class NullableUtil {
    public static boolean isNotNull(Object obj) {
        return !Objects.isNull(obj);
    }

    public static boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }

    /**
     * 如果字符串为 null 或者 "" 都为空
     * @param obj
     * @return
     */
    public static boolean isNull(String obj) {
        return obj==null || "".equals(obj);
    }
    /**
     * 如果 Map 为 null 或者不含任何元素
     * @param obj
     * @return
     */
    public static boolean isNull(Map<? extends Object, ? extends Object> obj) {
        return Objects.isNull(obj) || obj.isEmpty();
    }

    /**
     * 列表为空的判断: 列表本身不为空; 至少包含一个元素; 至少一个元素不为空
     *
     * @param obj
     * @return
     */
    public static boolean isNull(List<? extends Object> obj) {
        if (Objects.isNull(obj) || obj.isEmpty()) return true;
        for (Object o : obj) {
            if (!Objects.isNull(o)) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        ArrayList<String> l = new ArrayList<>();
        System.out.println(NullableUtil.isNull(l));
    }
}
