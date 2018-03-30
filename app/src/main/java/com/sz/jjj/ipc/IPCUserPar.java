package com.sz.jjj.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jjj on 2018/3/29.
 *
 * @description:
 */

public class IPCUserPar implements Parcelable {
    public String userId;
    public String userNames;
    public String isMale2;
    private static final long serialVersionUID = 1314564;

    public IPCUserPar(String userId, String userName, String isMale) {
        this.userId = userId;
        this.userNames = userName;
        this.isMale2 = isMale;
//        Log.e("serialVersionUID=", serialVersionUID+"" );
    }

    public static final Creator<IPCUserPar> CREATOR = new Creator<IPCUserPar>() {
        @Override
        public IPCUserPar createFromParcel(Parcel in) {
            return new IPCUserPar(in);
        }

        @Override
        public IPCUserPar[] newArray(int size) {
            return new IPCUserPar[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.userNames);
        dest.writeString(this.isMale2);
    }

    protected IPCUserPar(Parcel in) {
        this.userId = in.readString();
        this.userNames = in.readString();
        this.isMale2 = in.readString();
    }

    @Override
    public String toString() {
        return userNames;
    }
}
