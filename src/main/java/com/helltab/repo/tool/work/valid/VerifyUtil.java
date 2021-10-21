package com.helltab.repo.tool.work.valid;


import com.helltab.repo.tool.work.str.NullableUtil;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Topic
 *
 * @author helltab
 * @version 1.0
 * @date 2021/2/24 9:51
 * @desc 表单验证工具类
 * 文档地址： http://192.168.1.26:8882/v10-alpha/views/design/develop/ver.10-detail.html#_3-5-%E6%A8%A1%E5%9D%97%E8%AE%BE%E8%AE%A1%E8%AF%B4%E6%98%8E
 * 使用说明:
 * 1. 使用工具 ValidUtil.build() 构造出验证包装类
 * 2. 使用工具 ValidUtil.valid() ValidUtil.validCustom() 构造出真实的验证规则
 * 3. 根据实际情况按 and or 的逻辑封装验证规则
 * 4. 调用包装类的 test 方法, 获取到包含最终结果的 ValidResult 对象
 */
public class VerifyUtil {
    /**
     * 生成统一格式的信息
     * todo 暂时不处理
     *
     * @param msg
     * @return
     */
    public static String genMsg(String msg) {
        return msg;
    }

    private static class VerifyWrap implements VerifyInf {
        Predicate<Object[]> predicate;
        private String msg;
        private final Object[] testValues;
        boolean flag = true;
        boolean allowEmpty = false;

        /**
         * 构造函数
         *
         * @param verifyFun
         * @param testValues
         */
        public VerifyWrap(VerifyFun verifyFun, Object[] testValues) {
            this(verifyFun, verifyFun.getM(), testValues);
        }

        public VerifyWrap(VerifyFun verifyFun, String msg, Object[] testValues) {
            this(verifyFun.getP(), genMsg(msg), testValues);
        }

        public VerifyWrap(Predicate<Object[]> predicate, String msg, Object[] testValues) {
            this.predicate = predicate;
            this.msg = genMsg(msg);
            this.testValues = testValues;
        }

        /**
         * 将对象转为字符串
         *
         * @param obj
         * @return
         */
        private static String getString(Object obj) {
            return NullableUtil.isNull(obj) ? "" : obj.toString();
        }

        /**
         * 执行判断, 处理消息
         *
         * @return
         */
        @Override
        public VerifyResult test() {
            if (NullableUtil.isNull(this.testValues)
                        || NullableUtil.isNull(this.testValues[0])
                        || NullableUtil.isNull(this.testValues[0].toString())) {
                if (this.allowEmpty) {
                    return new VerifyResult(flag = true, "");
                } else {
                    return new VerifyResult(flag = false, "参数为空");
                }

            }
            if (flag = this.predicate.test(this.testValues)) {
                msg = "";
            } else {
                String collect = NullableUtil.isNull(this.testValues)
                                         ? null : Arrays.stream(this.testValues).map(VerifyWrap::getString).collect(Collectors.joining(", ", "[", "]"));
                msg += "(value: " + collect + ")";
            }
            return new VerifyResult(flag, msg);
        }

        @Override
        public VerifyInf empty() {
            this.allowEmpty = true;
            return this;
        }
    }

    /**
     * 建立验证
     *
     * @return
     */
    public static VerifyInf build() {
        return new VerifyForm();
    }

    /**
     * 验证主体
     *
     * @param verifyFun
     * @param testValues
     * @return
     */
    public static VerifyInf of(VerifyFun verifyFun, Object[] testValues) {
        return new VerifyWrap(verifyFun, testValues);
    }


    /**
     * 验证主体
     * 自定义返回信息
     *
     * @param verifyFun
     * @param testValues
     * @return
     */
    public static VerifyInf validCustom(VerifyFun verifyFun, String msg, Object[] testValues) {
        return new VerifyWrap(verifyFun, msg, testValues);
    }


    /**
     * 验证主体
     * 自定义处理过程
     * 自定义返回信息
     *
     * @param predicate
     * @param msg
     * @param testValues
     * @return
     */
    public static VerifyInf validCustom(Predicate<Object[]> predicate, String msg, Object[] testValues) {
        return new VerifyWrap(predicate, msg, testValues);
    }


    public static void main(String[] args) {
        VerifyInf inf = VerifyUtil.build();
        VerifyResult result = inf.and(VerifyUtil.of(VerifyFun.PHONE, new String[]{null}).empty()).test();

        VerifyUtil.build().and(VerifyUtil.validCustom(objs -> {

            return true;
        }, "err", new String[]{"param"}));
        System.out.println(result);

        //这里的外面是 ValidForm， 里面是 validCustom 创建的 ValidWrap
        //拼接 and 和 or 的时候就计算了
//        ValidInf build = ValidUtil.build()
//                .or(ValidUtil.validCustom(objes -> true, "true", "0"))
//                .or(ValidUtil.validCustom(objes -> false, "false", "-1"));
//
//        ValidInf build2 = ValidUtil.build()
//                .and(ValidUtil.validCustom(objes -> false, "1-0", "-1"))
//                .and(
//                        ValidUtil.build().and(ValidUtil.validCustom(objes -> false, "2-1", "-2"))
//                        .or(ValidUtil.validCustom(objes -> true, "2-2", "-2"))
//                )
//                .or(ValidUtil.validCustom(objes -> false, "3-0", "-3"))
//                .or(ValidUtil.validCustom(objes -> false, "4-0", "-4"));
//
//
//
//        ValidInf build3 = ValidUtil.build()
//                .and(ValidUtil.valid(ValidFun.LOGINNAME, null));
//
//        ValidResult test = build3.test();
//        System.out.println(test.isFlag());
//        System.out.println(test.getResultMsg());


/*        String loginname = "11";
        String phoneStr = "17628092930";
        ValidResult test = ValidUtil.build().and(ValidUtil.valid(ValidFun.LOGINNAME, loginname).empty()).test();
        ValidResult test2 = ValidUtil.build().and(ValidUtil.valid(ValidFun.PHONE, phoneStr).empty()).test();
        System.out.println(test);
        System.out.println(test2);*/


/*        ValidInf build = ValidUtil.build()
                                 .or(ValidUtil.validCustom(objs -> true, "1", "custom"))
                                 .or(build()
                                             .and(build()
                                                          .and(validCustom(objs -> true, "211", "custom"))
                                                          .or(validCustom(objs -> false, "212", "custom"))
                                             )
                                             .or(validCustom(objs -> false, "22", "custom"))
                                             .and(validCustom(objs -> false, "23", "custom"))
                                             .and(build()
                                                          .and(validCustom(objs -> false, "231", "custom"))
                                                          .or(validCustom(objs -> false, "232", "custom"))
                                             )
                                 )
                                 .or(ValidUtil.validCustom(objs -> false, "3", "custom"));
        ValidInf build2 = ValidUtil.build();

        build2.and(ValidUtil.valid(ValidFun.SPECIAL, "<没有特殊字符"))
                .or(ValidUtil.build()
                            .and(ValidUtil.valid(ValidFun.LOGINNAME, "_dd"))
                            .and(ValidUtil.valid(ValidFun.URL, "http://www.baidu.com"))
                )
                .and(ValidUtil.validCustom(ValidFun.NOTNULL, "自定义错误信息1", ""))
                .or(ValidUtil.validCustom(objs -> false, "自定义错误信息2", "custom"))
                .and(ValidUtil.validCustom(new CustomValid(), "自定义错误信息3", "abc1"))
                .and(ValidUtil.valid(ValidFun.PHONE, "134524121111"));
        ValidResult test = build2.test();
        System.out.println(test.isFlag());
        System.out.println(test.getResultMsg());
        ValidInf build3 = ValidUtil.build()
                                  .and(ValidUtil.valid(ValidFun.NOTNULL, null))
                                  .and(ValidUtil.valid(ValidFun.NOTNULL, new Object[]{}));
        ValidResult test1 = build3.test();
        System.out.println(test1.isFlag());
        System.out.println(test1.getResultMsg());*/
    }
}
