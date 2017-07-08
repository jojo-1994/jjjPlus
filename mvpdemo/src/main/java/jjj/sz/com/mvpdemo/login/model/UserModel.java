package jjj.sz.com.mvpdemo.login.model;

/**
 * Created by jjj on 2017/7/8.
 *
 * @description:
 */

public class UserModel implements IUser {

    String name;
    String passwd;

    public UserModel(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPasswd() {
        return passwd;
    }

    @Override
    public int checkUserValidity(String name, String password) {
        if(name != null && password != null && !name.equals(getName()) && !password.equals(getPasswd())){
            return -1;
        }
        return 0;
    }
}
