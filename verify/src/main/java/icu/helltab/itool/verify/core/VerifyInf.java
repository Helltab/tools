package icu.helltab.itool.verify.core;

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
    default <M> VerifyInf<T> and(VerifyInf<M> verifyInf, String msg, M param) {
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
    default <M> VerifyInf<T> or(VerifyInf<M> verifyInf, String msg, M param) {
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
    default <M> VerifyInf<T> calc(VerifyInf<M> verifyInf, String msg, M param, boolean isAnd) {
        return o -> {
            boolean a = test((T)o);
            boolean b;
            if (null == msg) {
                b = verifyInf.test((M)o);
            } else {
                if (!(b = verifyInf.test(param))) {
                    VerifyCourt.result().error(msg + "<" + param + ">");
                }
            }
            return isAnd ? a && b : a || b;
        };
    }

}
