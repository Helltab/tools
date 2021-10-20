package com.helltab.repo.tool.work.valid;


import com.helltab.repo.tool.work.str.DateUtil;
import com.helltab.repo.tool.work.str.NullableUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Topic
 * @author helltab
 * @version 1.0
 * @date 2021/2/24 9:47
 * @desc 表单验证枚举类
 */
public enum ValidFun {
    NOTNULL(NullableUtil::isNotNull, "字段为空"),
    URL(objs -> NullableUtil.isNotNull(objs) && objs[0].toString().matches("^(https?|file|ftp)://\\w+\\..*"), "url 格式不正确"),
    LOGINNAME(objs -> NullableUtil.isNotNull(objs) && objs[0].toString().matches("^[a-zA-Z0-9_]{2,32}$"), "登录名格式不正确 (2-32位数字/密码/下划线)"),
    PASSWORD(objs->NullableUtil.isNotNull(objs)&&objs[0].toString().matches("^[a-zA-Z0-9]{2,32}$"),"密码格式不正确(6-15位数字/大小写字符)"),
    SPECIAL(objs -> NullableUtil.isNotNull(objs) && !objs[0].toString().matches(".*(?=[\\x21-\\x7e]+)[^A-Za-z0-9]+.*"), "包含特殊字符"),
    TEL(objs -> NullableUtil.isNotNull(objs) && objs[0].toString().matches("^\\d{3,4}-\\d{7,8}(-\\d{3,4})?$"), "电话号码格式不正确"),
    PHONE(objs -> NullableUtil.isNotNull(objs) && objs[0].toString().matches("^1[3456789]\\d{9}$"), "手机号码格式不正确"),
    DATE(objs -> NullableUtil.isNotNull(objs) && DateUtil.checkDateFormat(objs[0].toString()), "日期格式不正确"),
    TIME(objs -> NullableUtil.isNotNull(objs) &&  DateUtil.checkTimeFormat(objs[0].toString()), "时间格式不正确"),
    DATE_TIME(objs -> NullableUtil.isNotNull(objs) && DateUtil.checkDateTimeFormat(objs[0].toString()), "日期时间格式不正确"),
    NUMBER_POSITIVE(objs -> NullableUtil.isNotNull(objs)  && objs[0].toString().matches("[1-9]\\d*"), "错误的数字，需要一个正整数" ),
    NUMBER_NATURAL(objs -> NullableUtil.isNotNull(objs)  && objs[0].toString().matches("\\d*"), "错误的数字，需要一个自然数" ),
    LENGTH( objs -> NullableUtil.isNotNull(objs) && NullableUtil.isNotNull(objs[0]) && NullableUtil.isNotNull(objs[1]) && objs[0].toString().length()==Integer.parseInt(objs[1].toString()) ,"长度错误" ),
    ENUM_HAVE(objs -> NullableUtil.isNotNull(objs) && objs[1]!=null , "非法数据"),
    ENUM_HAVE_MANY(objs -> NullableUtil.isNotNull(objs) && NullableUtil.isNotNull(objs[0]) && checkCodeHave(objs[0].toString(), (Set<Object>) objs[1]),"非法枚举值"),
    FIRST_TRUE_AND_SECOND_MUST_HAVE(objs -> NullableUtil.isNotNull(objs)  &&  NullableUtil.isNotNull(objs[0]) && firstTrueAndSecondMustHave((Boolean)objs[0], objs[1]), "第二个数据错误"),
    MUST_HAVE_ONE(objs ->  NullableUtil.isNotNull(objs) && NullableUtil.isNotNull(objs), "全部为空"),
    FIRST_TRUE_AND_SECOND_ENUM_HAVE_MANY(objs -> NullableUtil.isNotNull(objs) &&  NullableUtil.isNotNull(objs[1]) && checkCodeHave(objs[1].toString(), (Set<Object>) objs[2]), "第二个数据错误"),
    STRING_FIRST_BIGGER_THAN_SECOND(objs -> NullableUtil.isNull(objs) || stringFirstBiggerThanSecond(objs[0], objs[1]),"第一个数据小于第二个数据" ),
    ;


    //    MAX_LENGTH(objs ->  NullableUtil.isNull(objs) ||  objs[0].toString().length() > Integer.valueOf(objs[1].toString()) ? false : true  , "数据长度超出范围")

    ValidFun(Predicate<Object[]> predicate, String errMsg) {
        this.predicate = predicate;
        this.errMsg = errMsg;
    }

    private final Predicate<Object[]> predicate;
    private final String errMsg;

    Predicate<Object[]> getP() {
        return this.predicate;
    }

    String getM() {
        return this.errMsg;
    }


    /**
     * 判断code是否都存在
     * @param code
     * @return
     */
    private static boolean checkCodeHave(String code, Set<Object> codeSet) {
        if(null==code || "".equals(code)){
            return false;
        }
        String[] codes = code.split("\\^");
        for (String s : codes) {
            if(!codeSet.contains(s))
                return false;
        }
        return true;
    }

    /**
     * 如果第一个为true，判断第二个是否为 null
     * @param bool
     * @param obj2
     * @return
     */
    private static boolean firstTrueAndSecondMustHave(Boolean bool, Object obj2) {
        if(bool) {
            return NullableUtil.isNotNull(obj2);
        }
        return true;
    }

    /**
     * 第一个字符串不能小于第二个字符串，如果两个都有的话
     * @param obj0
     * @param obj1
     * @return
     */
    private static boolean stringFirstBiggerThanSecond(Object obj0, Object obj1) {

        if(NullableUtil.isNull(obj0) || NullableUtil.isNull(obj1)) {
            return true;
        }
        return obj0.toString().compareTo(obj1.toString()) >= 0;
    }


}
