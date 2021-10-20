//package com.helltab.repo.tool.work.valid;
//
//import lombok.Data;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Function;
//import java.util.function.Predicate;
//
///**
// * Topic
// *
// * @author chenzhuo
// * @version 1.0
// * @date 2021/4/23 17:20
// * @desc 数据库验证
// */
//@Data
//public class DataBaseValid {
//
//    /**
//     * 根据 class 获取数据库名
//     *
//     * @param tableClass
//     * @return
//     */
//    public static String getTableName(Class<?> tableClass) {
//        return StringUtil.camelCaseToUnderline(tableClass.getSimpleName());
//    }
//
//    //中间变量名称，用于生成SQL
//    public static final String TEMP_NAME = "index_number";
//
//
//    /**
//     * 数据库验证
//     */
//    private List<Valid> validList = new ArrayList<>();
//
//    /**
//     * 验证标志 -- 只要有一个没过，就是没过
//     */
//    private Boolean validFlag;
//
//    /**
//     * 返回消息
//     */
//    private String returnMsg;
//
//    /**
//     * 错误列表索引 - 比如 1，就代表 validList.get(1) 错误
//     */
//    private List<Integer> indexList;
//
//    /**
//     * 只是为了统一验证
//     *
//     * @return
//     */
//    public static DataBaseValid build() {
//        return new DataBaseValid();
//    }
//
//
//    /**
//     * 默认为存在验证 - 如果不存在，记录错误信息
//     *
//     * @param tableClass
//     * @param fieldName
//     * @param value
//     * @param msg
//     * @return
//     */
//    public DataBaseValid addValid(Class<?> tableClass, String fieldName, Object value, String msg) {
//        String[] fieldNames = new String[]{fieldName};
//        return addValid(tableClass, fieldNames, value, msg, true);
//    }
//
//
//    /**
//     * 添加验证规则 - 解决SQL注入问题 - tableName 和 fieldName 都是自己人输入的，所以只需要处理 value 的防注入
//     *
//     * @param tableClass 表名
//     * @param fieldName  字段名
//     * @param value      传入值
//     * @param msg        错误消息 - 正确不返回消息
//     * @param predicate  判断前调用，为
//     * @return
//     */
//    public DataBaseValid addValid(Class<?> tableClass, String fieldName, Object value, String msg, Predicate<Object> predicate) {
//        if (!predicate.test(null)) {
//            return this;
//        }
//        String[] fieldNames = new String[]{fieldName};
//        return addValid(tableClass, fieldNames, value, msg, true);
//    }
//
//    /**
//     * 添加验证规则 - 解决SQL注入问题 - tableName 和 fieldName 都是自己人输入的，所以只需要处理 value 的防注入
//     *
//     * @param tableClass 表名
//     * @param fieldName  字段名
//     * @param value      传入值
//     * @param msg        错误消息 - 正确不返回消息
//     * @param predicate  判断前调用，为true才会添加验证信息
//     * @return
//     */
//    public DataBaseValid addValid(Class<?> tableClass, String fieldName, Object value, String msg, Predicate<Object> predicate, boolean existReturn) {
//        if (!predicate.test(null)) {
//            return this;
//        }
//        String[] fieldNames = new String[]{fieldName};
//        return addValid(tableClass, fieldNames, value, msg, existReturn);
//    }
//
//
//    /**
//     * 添加验证规则 - 解决SQL注入问题 - tableName 和 fieldName 都是自己人输入的，所以只需要处理 value 的防注入
//     *
//     * @param tableClass  表名
//     * @param fieldName   字段名
//     * @param value       传入值
//     * @param existReturn 正确的存在条件。存在为正确则输入true 存在为错误则输入false
//     * @param msg         错误消息 - 正确不返回消息
//     * @return
//     */
//    public DataBaseValid addValid(Class<?> tableClass, String fieldName, Object value, String msg, Boolean existReturn) {
//        String[] fieldNames = new String[]{fieldName};
//        return addValid(tableClass, fieldNames, value, msg, existReturn);
//    }
//
//
//    /**
//     * 添加验证规则 - 解决SQL注入问题 - tableName 和 fieldName 都是自己人输入的，所以只需要处理 value 的防注入
//     *
//     * @param tableClass  表名
//     * @param fieldNames  字段名
//     * @param value       传入值
//     * @param existReturn 正确的存在条件。存在为正确则输入true 存在为错误则输入false
//     * @param msg         错误消息 - 正确不返回消息
//     * @return
//     */
//    public DataBaseValid addValid(Class<?> tableClass, String[] fieldNames, Object value, String msg, Boolean existReturn) {
//
//        Valid valid = new Valid();
//        valid.setTableName(getTableName(tableClass));
//        valid.setFieldName(fieldNames);
//
//        String valueStr = StringUtil.getSQLSeparationValue(value);
//
//        valid.setValue(valueStr);
//        valid.setExistReturn(existReturn);
//        valid.setMsg(msg);
//        validList.add(valid);
//        return this;
//    }
//
//
//    /**
//     * 生成 SQL，不考虑参数问题
//     *
//     * @return
//     */
//    public String createValidSql() {
//
//        if (NullableUtil.isNull(this.validList)) {
//            return null;
//        }
//
//        StringBuilder sql = new StringBuilder();
//        sql.append("select ").append(TEMP_NAME).append(" from ( ");
//
//        for (int i = 0; i < this.validList.size(); i++) {
//            Valid valid = validList.get(i);
//            //存在就是统计数量大于0；不存在就是统计数量等于0 ， 返回-1表示正确
//            if (valid.getExistReturn()) {
//                sql.append("select case when count(1) > 0 then ");
//                sql.append(" -1 else ").append(i).append("  end ").append(TEMP_NAME).append(" from ").append(valid.getTableName()).append(" where ");
//
//            } else {
//                sql.append("select case when count(1) > 0 then ");
//                sql.append(i).append(" else -1 end ").append(TEMP_NAME).append(" from ").append(valid.getTableName()).append(" where ");
//            }
//
//            // .append(valid.getFieldName()).append(" = ").append("'").append(valid.getValue()).append("'");
//
//            for (int j = 0; j < valid.getFieldName().length; j++) {
//                if (StringUtil.checkSpecialSymbols(valid.getValue(), "^")) {
//                    sql.append(valid.getFieldName()[j]).append(" in (");
//                    List<Long> ids = StringUtil.strToLong(valid.getValue());
//                    // in ('1','2'),有一个即为正确
//                    for (int x = 0; x < ids.size(); x++) {
//                        sql.append(StringUtil.SQL_SEPARATION).append(ids.get(x)).append(StringUtil.SQL_SEPARATION);
//                        if (x == ids.size() - 1) {
//                            sql.append(" ) ");
//                        } else {
//                            sql.append(" , ");
//                        }
//                    }
//                } else {
//                    sql.append(valid.getFieldName()[j]).append(" = ").append(StringUtil.SQL_SEPARATION).append(valid.getValue()).append(StringUtil.SQL_SEPARATION);
//                }
//                if (j != valid.getFieldName().length - 1) {
//                    if (valid.getExistReturn()) {
//                        sql.append(" or ");
//                    } else {
//                        sql.append(" and ");
//                    }
//                }
//            }
//            if (i != (this.validList.size() - 1)) {
//                sql.append(" union all \n");
//            }
//        }
//
//        sql.append(" )a where ").append(TEMP_NAME).append(" != -1 ");
//
//
//        return sql.toString();
//    }
//
//    //处理返回消息的
//
//    /**
//     * 返回信息
//     *
//     * @return
//     */
//    public String getErrorInfo() {
//        StringBuilder info = new StringBuilder("");
//        if (NullableUtil.isNotNull(this.getIndexList())) {
//            for (Integer index : this.getIndexList()) {
//                info.append(this.validList.get(index).getMsg()).append(",");
//            }
//            info.replace(info.lastIndexOf(","), info.length(), ".");
//        }
//        return info.toString();
//    }
//
//    /**
//     * 验证数据组织
//     */
//    @Data
//    private class Valid {
//
//        /**
//         * 表名
//         */
//        private String tableName;
//
//        /**
//         * 字段名
//         */
//        private String[] fieldName;
//
//        /**
//         * 值
//         */
//        private String value;
//
//        /**
//         * 验证存在还是不存在
//         */
//        private Boolean existReturn;
//
//        /**
//         * 返回消息
//         */
//        private String msg;
//
//
//    }
//
//
//}
