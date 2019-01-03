package com.bangyou.dcom.web.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态码常量
 *
 * @author radiance
 */
public class StatusCodes {

    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int CONFLICT = 409;
    public static final int INVALID_PARAMETER = 450;
    public static final int PERMISSION_DENIED = 452;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NO_REPLY = 558;
    public static final int UNCATCHED_EXCEPTION = 553;

    public static final Map<Integer, String> messages = new HashMap<>();

    static {
        messages.put(OK, "操作成功");
        messages.put(CREATED, "创建成功");
        messages.put(BAD_REQUEST, "请求不合法");
        messages.put(UNAUTHORIZED, "未登录");
        messages.put(NOT_FOUND, "对象不存在");
        messages.put(METHOD_NOT_ALLOWED, "操作不合法");
        messages.put(CONFLICT, "冲突异常");
        messages.put(INVALID_PARAMETER, "参数不合法");
        messages.put(PERMISSION_DENIED, "权限受限");
        messages.put(NO_REPLY, "没有响应");
        messages.put(INTERNAL_SERVER_ERROR, "服务端异常");
        messages.put(UNCATCHED_EXCEPTION, "未知异常");
    }

    public static String message(int code) {
        return messages.get(code);
    }

}
