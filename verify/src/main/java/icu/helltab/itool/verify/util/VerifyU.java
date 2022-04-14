package icu.helltab.itool.verify.util;

import cn.hutool.core.util.ReflectUtil;
import icu.helltab.itool.object.NullableUtil;
import icu.helltab.itool.verify.annotation.LikeQuery;
import icu.helltab.itool.verify.annotation.ParamVerify;
import icu.helltab.itool.verify.core.BaseVerifyRule;
import icu.helltab.itool.verify.core.VerifyCourt;
import icu.helltab.itool.verify.normal.MustKeyRule;
import icu.helltab.itool.verify.normal.NotNullRule;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 校验工具
 */
public class VerifyU {

    private VerifyU() {
    }

    /**
     * 开启一个新的校验
     *
     * @param rule
     * @param param
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> VerifyCourt of(Class<? extends BaseVerifyRule<T>> rule, T param) {
        return InnerCourt.getCourt(rule, param);
    }

    public static VerifyCourt of() {
        return InnerCourt.getCourt();
    }

    /**
     * 校验字段
     *
     * @param params
     * @return
     */
    public static VerifyCourt.Result checkFields(Object ...params) {
        VerifyCourt court = of();
        for (Object param : params) {
            if(NullableUtil.isEmpty(param)
                       || param.getClass().isPrimitive())
                continue;
            processCheck(param, court);
        }
        return court.judge();
    }

    /**
     * 审查过程
     * @param param
     * @param court
     */
    private static void processCheck(Object param, VerifyCourt court) {
        Field[] fields = ReflectUtil.getFields(param.getClass());
        Map<String, Set<Object>> mustKeyMap = new HashMap<>();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object o = field.get(param);
                String fieldName = field.getName();
                ParamVerify annotation = field.getAnnotation(ParamVerify.class);
                if (null != annotation) {
                    String mustKey = annotation.mustKey();
                    // 多字段联合非空
                    if (NullableUtil.isNotNull(mustKey)) {
                        Set<Object> set = mustKeyMap.get(mustKey);
                        // 初始化 set
                        if (null == set) {
                            set = new HashSet<>();
                            mustKeyMap.put(mustKey, set);
                            set.add(fieldName);
                        }
                        if (!NullableUtil.isEmpty(o)) {
                            // 清除 set, 表示已经验证通过了
                            set.clear();
                        }
                        // 如果 set 是空的表示已经验证通过了, 否则添加该项
                        if (!set.isEmpty()) {
                            set.add(fieldName);
                        }
                    }
                    // 不可为空
                    if (annotation.must()) {
                        NotNullRule notNullRule = new NotNullRule();
                        notNullRule.setParamName(fieldName);
                        court.and(notNullRule, Collections.singleton(o));
                    }
                    if (!NullableUtil.isEmpty(o)) {
                        for (Class clazz : annotation.rules()) {
                            court.and(clazz, o);
                        }
                    }

                }
                LikeQuery likeQuery = field.getAnnotation(LikeQuery.class);
                if (NullableUtil.isNotNull(o) && NullableUtil.isNotNull(likeQuery)) {
                    if (field.getType() == String.class) {
                        String value = (String) o;
                        field.set(param, value.replaceAll(likeQuery.flag(), "%"));
                    }

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        mustKeyMap.forEach((k, v) -> {
            if (null != v && !v.isEmpty()) {
                MustKeyRule keyRule = new MustKeyRule();
                String paramName = v.stream().map(Object::toString).collect(Collectors.joining("|"));
                keyRule.setParamName(paramName);
                court.and(keyRule, null);
            }
        });
    }

    /**
     * 内部类实例校验法庭
     *
     * @param
     */
    private static class InnerCourt extends VerifyCourt {

        protected<T> InnerCourt(Class<? extends BaseVerifyRule<T>> ruleClazz, T param) {
            super(ruleClazz, param);
        }

        protected InnerCourt() {
            super();
        }

        public static <T> VerifyCourt getCourt(Class<? extends BaseVerifyRule<T>> ruleClazz, T param) {
            return new InnerCourt(ruleClazz, param);
        }

        public static VerifyCourt getCourt() {
            return new InnerCourt();
        }
    }
}





