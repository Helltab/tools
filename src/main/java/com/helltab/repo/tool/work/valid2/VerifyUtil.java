package com.helltab.repo.tool.work.valid2;

public class VerifyUtil {
    static class VerifyWrap implements VerifyInf {
        VerifyInf verifier;
        Object[] testParams;

        public VerifyWrap(VerifyInf verifier, Object[] testParams) {
            this.verifier = verifier;
            this.testParams = testParams;
        }

        @Override
        public boolean test() {
            return true;
        }


    }

}
