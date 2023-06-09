package com.neo.bookhouse.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 */
public class BaseContext {
    //private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    //
    //public static Long getCurrentId() {
    //    return threadLocal.get();
    //}
    //
    //public static void setCurrentId(Long id) {
    //    threadLocal.set(id);
    //}

    private static Long userId;

    public static Long getCurrentId() {
        return userId;
    }

    public static void setCurrentId(Long id) {
        userId = id;
    }
}
