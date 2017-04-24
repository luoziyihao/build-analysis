package com.coding.web.component;

import lombok.Data;

@Data
public class Ret {
    public static final int code_success = 0;
    public static final int code_fail = 1;
    private int code = 1;
    private String msg = "";

    private Object item = null;

    public static int getCode_success() {
        return code_success;
    }

    public static int getCode_fail() {
        return code_fail;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public Ret() {
    }

    public Ret(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Ret(int code, Object item) {
        this.code = code;
        this.item = item;
    }

    public Ret(int code, String msg, Object item) {
        this.code = code;
        this.msg = msg;
        this.item = item;
    }

    public static Ret fail(String msg) {
        return new Ret(Ret.code_fail, msg);
    }

    public static Ret success(Object item) {
        return new Ret(Ret.code_success, item);
    }

    public static Ret success(String msg, Object item) {
        return new Ret(Ret.code_success, msg, item);
    }

    public static Ret success() {
        return new Ret(Ret.code_success, 0);
    }
}
