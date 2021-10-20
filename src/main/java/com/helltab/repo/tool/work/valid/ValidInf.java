package com.helltab.repo.tool.work.valid;

/**
 * Topic
 * @author helltab
 * @version 1.0
 * @date 2021/2/24 11:16
 * @desc 验证接口
 */
public interface ValidInf {

    ValidResult test();

    ValidInf empty();

    default ValidInf and(ValidInf validInf) {
        return validInf;
    }

    default ValidInf or(ValidInf validInf) {
        return validInf;
    }

}
