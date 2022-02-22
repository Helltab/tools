package com.helltab.tools.verify;

import cn.hutool.json.JSONUtil;
import com.helltab.tools.verify.annotation.LikeQuery;
import com.helltab.tools.verify.annotation.ParamVerify;
import com.helltab.tools.verify.core.VerifyCourt;
import com.helltab.tools.verify.normal.EmailRule;
import com.helltab.tools.verify.normal.PhoneRule;
import com.helltab.tools.verify.util.VerifyU;
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
        test2();
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
        System.out.println(JSONUtil.toJsonPrettyStr(result));
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
