package icu.helltab.itool.result.web;

import icu.helltab.itool.verify.core.VerifyInf;

/**
 * Topic Status of http
 *
 * @author helltab
 * @version 1.0
 * @date 2022/2/27 18:00
 */
public abstract class ResultStatus {
    private final int code;
    private final String desc;
    static private ResultStatus INS;

    protected abstract int initCode();

    protected abstract String initDesc();

    private ResultStatus() {

        this.code = initCode();
        this.desc = initDesc();
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}

