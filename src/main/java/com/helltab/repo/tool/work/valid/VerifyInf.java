package com.helltab.repo.tool.work.valid;

/**
 * Topic
 * @author helltab
 * @version 1.0
 * @date 2021/2/24 11:16
 * @desc 验证接口
 */
public interface VerifyInf {

    VerifyResult test();

    VerifyInf empty();

    default VerifyInf and(VerifyInf verifyInf) {
        return verifyInf;
    }

    default VerifyInf or(VerifyInf verifyInf) {
        return verifyInf;
    }

}
