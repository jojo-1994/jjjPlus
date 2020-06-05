package com.sz.jjj.baselibrary.network.common;

/**
 * Created by jjj on 2018/5/21.
 *
 * @description:
 */

public class BasicResponse<T> {

    private String errmsg;
    private int errcode;
    private T data;

    public BasicResponse() {
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
