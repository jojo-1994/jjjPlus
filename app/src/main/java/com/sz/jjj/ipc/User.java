package com.sz.jjj.ipc;

/**
 * @author:jjj
 * @data:2018/7/10
 * @description:
 */

public class User {

    public User() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }

    public int userId;
    public String userName;
    public boolean isMale;

    @Override
    public String toString() {
        return "userId:" + userId + "userName:" + userName + "isMale:" + isMale;
    }
}
