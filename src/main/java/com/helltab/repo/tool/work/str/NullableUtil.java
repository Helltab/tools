package com.helltab.repo.tool.work.str;

import java.util.Objects;

public class NullableUtil {
    public static boolean isNotNull(Object obj) {
        return !Objects.isNull(obj);
    }
    public static boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }
}
