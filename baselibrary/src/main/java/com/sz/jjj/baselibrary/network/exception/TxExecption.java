package com.sz.jjj.baselibrary.network.exception;

/**
 * @author:jjj
 * @data:2018/5/23
 * @description:
 */

public class TxExecption {

    private String errMsg;

    private int errCode;

    public TxExecption(String msg) {
        this.errMsg = msg;
    }

    public TxExecption(int code) {
        this.errCode = code;
    }

    public TxExecption(int code, String msg) {
        this.errCode = code;
        this.errMsg = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
