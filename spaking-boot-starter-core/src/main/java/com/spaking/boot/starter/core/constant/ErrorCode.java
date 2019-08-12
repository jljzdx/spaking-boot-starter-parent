package com.spaking.boot.starter.core.constant;

/**
 * TransactionStatus的错误代码常量定义
 *
 * @author XIEZHONG
 */
public class ErrorCode {

    /**
     * get succeed.
     * value = "0"
     */
    public static final String SUCCESS = "0";
    /**
     * SessionContext parameter is illegal.
     * value = "1"
     */
    public static final String FAILURE = "1";

    /**
     * 系统错误请联系管理员
     */
    public static final String ERROR_CODE_999999 = "999999";

    /**
     * SessionContext.UaId Cannot be null.
     */
    public static final String ERROR_MSG_999999 = "system fatal error, please contact administrator.";

    /**
     * failure.
     * value = "9"
     */
    public static final String EXCEPTION = "9";


    /**
     * SessionContext cannot be null
     */
    public static final String ERROR_CODE_990001 = "990001";

    /**
     * SessionContext cannot be null
     */
    public static final String ERROR_MSG_990001 = "SessionContext cannot be null";


    /**
     * SessionContext.Channel cannot be null
     */
    public static final String ERROR_CODE_990002 = "990002";

    /**
     * SessionContext.Channel cannot be null
     */
    public static final String ERROR_MSG_990002 = "SessionContext.Channel cannot be null";


    /**
     * SessionContext.StepCode cannot be null
     */
    public static final String ERROR_CODE_990003 = "990003";

    /**
     * SessionContext.StepCode cannot be null
     */
    public static final String ERROR_MSG_990003 = "SessionContext.StepCode cannot be null";


    /**
     * SessionContext.UserReferenceNumber cannot be null
     */
    public static final String ERROR_CODE_990004 = "990004";

    /**
     * SessionContext.UserReferenceNumber cannot be null
     */
    public static final String ERROR_MSG_990004 = "SessionContext.UserReferenceNumber cannot be null";


    /**
     * SessionContext.ServiceCode cannot be null
     */
    public static final String ERROR_CODE_990005 = "990005";

    /**
     * SessionContext.ServiceCode cannot be null
     */
    public static final String ERROR_MSG_990005 = "SessionContext.ServiceCode cannot be null";

    /**
     * SessionContext.PostingDateText format error
     */
    public static final String ERROR_CODE_990006 = "990006";

    /**
     * SessionContext.PostingDateText format error
     */
    public static final String ERROR_MSG_990006 = "SessionContext.PostingDateText format error,the format must be yyyyMMddHHmmss";

    /**
     * SessionContext.ValueDateText format error
     */
    public static final String ERROR_CODE_990007 = "990007";

    /**
     * SessionContext.ValueDateText format error
     */
    public static final String ERROR_MSG_990007 = "SessionContext.ValueDateText format error,the format must be yyyyMMddHHmmss";

    /**
     * SessionContext.LocalDateTimeText format error
     */
    public static final String ERROR_CODE_990008 = "990008";

    /**
     * SessionContext.LocalDateTimeText format error
     */
    public static final String ERROR_MSG_990008 = "SessionContext.LocalDateTimeText format error,the format must be yyyyMMddHHmmss";

    /**
     * 没获取到服务编号,请检查接口是否发布
     */
    public static final String ERROR_CODE_990101 = "990101";

    /**
     * 没获取到服务编号,请检查接口是否发布
     */
    public static final String ERROR_MSG_990101 = "没获取到服务编号,请检查接口是否发布";


    /**
     * serviceCode和UserId不能为空
     */
    public static final String ERROR_CODE_990102 = "990102";

    /**
     * 参数有问题无法执行，拦截这个请求
     */
    public static final String ERROR_MSG_990102 = "参数有问题无法执行，拦截这个请求";

    /**
     * serviceCode和UserId不能为空
     */
    public static final String ERROR_CODE_990103 = "990103";

    /**
     * serviceCode和UserId不能为空
     */
    public static final String ERROR_MSG_990103 = "serviceCode和UserId不能为空";

    /**
     * 尝试访问:{0} 失败，对方要访问的是:{1}
     */
    public static final String ERROR_CODE_990104 = "990104";

    /**
     * 尝试访问:{0} 失败，对方要访问的是:{1}
     */
    public static final String ERROR_MSG_990104 = "尝试访问:{0} 失败，对方要访问的是:{1}";

    /**
     * 用户{0}访问{1}失败，没有权限访问
     */
    public static final String ERROR_CODE_990105 = "990105";

    /**
     * 执行目标方法错误
     */
    public static final String ERROR_MSG_990105 = "执行目标方法错误";

    /**
     * 用户{0}访问{1}失败，没有权限访问
     */
    public static final String ERROR_CODE_990106 = "990106";

    /**
     * 用户{0}访问{1}失败，没有权限访问
     */
    public static final String ERROR_MSG_990106 = "用户{0}访问{1}失败，没有权限访问";

    /**
     * 基础参数校验不通过
     */
    public static final String ERROR_CODE_990107 = "990107";

    /**
     * 基础参数校验不通过
     */
    public static final String ERROR_MSG_990107 = "基础参数校验不通过";


    /**
     * 基础参数校验不通过
     */
    public static final String ERROR_CODE_990108 = "990108";

    /**
     * 基础参数校验不通过
     */
    public static final String ERROR_MSG_990108 = "操作重复,请指定时间后再重试,操作key:{0},超时设置时间 {1} ms,剩余时间{2}";

    /**
     * SessionContext.UaId Cannot be null.
     */
    public static final String ERROR_CODE_990109 = "990109";

    /**
     * SessionContext.UaId Cannot be null.
     */
    public static final String ERROR_MSG_990109 = "SessionContext.UaId Cannot be null.";


    /**
     * 远程调用错误
     */
    public static final String ERROR_CODE_990110 = "990110";

    /**
     * 远程调用错误
     */
    public static final String ERROR_MSG_990110 = "Remote call error.....";
}
