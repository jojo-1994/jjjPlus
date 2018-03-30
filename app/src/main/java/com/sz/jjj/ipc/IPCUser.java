package com.sz.jjj.ipc;

import java.io.Serializable;

/**
 * Created by jjj on 2018/3/29.
 *
 * @description:
 */

public class IPCUser implements Serializable {
    public String userId;
    public String userNames;
    public String isMale2 ;
    private static final long serialVersionUID = 1314564;

    public IPCUser(String userId, String userName, String isMale) {
        this.userId = userId;
        this.userNames = userName;
        this.isMale2 = isMale;
//        Log.e("serialVersionUID=", serialVersionUID+"" );
    }

    @Override
    public String toString() {
        return userNames;
    }
}
