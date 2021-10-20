package com.helltab.repo.tool.work.valid2;

public interface VerifyInf {
    default boolean and(VerifyInf and) {
        return and.test() && test();
    };
    default boolean or(VerifyInf or) {
        return or.test() || test();
    };
    boolean test();
}
