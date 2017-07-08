package jjj.sz.com.mvpdemo.login.model;

/**
 * Created by jjj on 2017/7/8.
 *
 * @description:
 */

public interface IUser  {
    String getName();
    String getPasswd();
    int checkUserValidity(String name, String password);
}
