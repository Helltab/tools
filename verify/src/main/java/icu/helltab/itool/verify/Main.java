package icu.helltab.itool.verify;

import cn.hutool.json.JSONUtil;
import icu.helltab.itool.result.web.BaseHttpResult;
import icu.helltab.itool.result.web.HttpStatusInf;
import icu.helltab.itool.verify.annotation.LikeQuery;
import icu.helltab.itool.verify.annotation.ParamVerify;
import icu.helltab.itool.verify.core.VerifyCourt;
import icu.helltab.itool.verify.normal.EmailRule;
import icu.helltab.itool.verify.normal.PhoneRule;
import icu.helltab.itool.verify.util.VerifyU;
import lombok.Data;

public class Main {
    /**
     * 使用示例
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
//        test1();
//        test2();
        testResult();
    }

    private static void test1() {
        VerifyCourt.Result result;
        result = VerifyU.of(PhoneRule.class, "17628092930")
                         .and(VerifyU.of(PhoneRule.class, "17628092930")
                                      .a(EmailRule.class, "3")
                                      .a(EmailRule.class, "4")
                                      .a(EmailRule.class, "5")
                                      .a(EmailRule.class, "6")
                                      .a(EmailRule.class, "7")
                                      .and(VerifyU.of(PhoneRule.class, "17628092930")
                                                   .o(EmailRule.class, "31")
                                                   .o(EmailRule.class, "41")
                                                   .o(EmailRule.class, "51")
                                                   .o(EmailRule.class, "61")
                                                   .o(EmailRule.class, "999@qq.com")

                                      )
                         ).judge();
        System.out.println(JSONUtil.toJsonPrettyStr(result));
    }

    private static void test2() {
        Test test = new Test();
        test.b = "33";
//        test.c = "176280929320";
        test.e = "a*";
        VerifyCourt.Result result = VerifyU.checkFields(test);
        System.out.println(result);
        System.out.println(JSONUtil.toJsonPrettyStr(result));
    }

    private static void testResult() {
        HttpResult httpResult = new HttpResult();
        Test test = new Test();
        test.b = "33";
//        test.c = "176280929320";
        test.e = "a*";
        httpResult.setData(test);
        System.out.println(httpResult);
    }
    static class HttpResult extends BaseHttpResult{
        @Override
        protected HttpStatusInf initSuccessStatus() {
            return HttpHttpStatus.SUCCESS;
        }

        @Override
        protected HttpStatusInf initFailStatus() {
            return HttpHttpStatus.FAIL;
        }
    }
    enum HttpHttpStatus implements HttpStatusInf {
        SUCCESS(8000, "success"), FAIL(8999, "fail");

        private final int code;
        private final String desc;
        HttpHttpStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        @Override
        public int getCode() {
            return code;
        }
        @Override
        public String getDesc() {
            return desc;
        }
    }
    @Data
    static
    class Test {
        @ParamVerify(mustKey = "l", must = false)
        String a;
        @ParamVerify(mustKey = "l", must = false)
        String b;
        @ParamVerify(mustKey = "l1", must = false)
        String a1;
        @ParamVerify(mustKey = "l1", must = false)
        String b1;
        @ParamVerify(rules = {PhoneRule.class})
        String c;
        @ParamVerify()
        String d;
        @ParamVerify(must = false)
        @LikeQuery
        String e;

    }
}
