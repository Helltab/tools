package com.helltab.tools.verify.core;

/**
 * 校验接口
 * 函数式接口
 */
public interface VerifyInf<T> {
    boolean test(T param);

    /**
     * 串接另一个校验
     *
     * @param verifyInf
     * @param msg
     * @param param
     * @return
     */
    default VerifyInf<T> and(VerifyInf<T> verifyInf, String msg, T param) {
        return calc(verifyInf, msg, param, true);
    }

    /**
     * 并接另一个校验
     *
     * @param verifyInf
     * @param msg
     * @param param
     * @return
     */
    default VerifyInf<T> or(VerifyInf<T> verifyInf, String msg, T param) {
        return calc(verifyInf, msg, param, false);
    }

    /**
     * 连接另一个校验的具体过程
     *
     * @param verifyInf
     * @param msg
     * @param param
     * @param isAnd
     * @return
     */
    default VerifyInf<T> calc(VerifyInf<T> verifyInf, String msg, T param, boolean isAnd) {
        return o -> {
            boolean a = test(o);
            boolean b;
            if (null == msg) {
                b = verifyInf.test(o);
            } else {
                if (!(b = verifyInf.test(param))) {
                    VerifyCourt.Result.error(msg + "<" + param + ">");
                }
            }
            return isAnd ? a && b : a || b;
        };
    }

}
