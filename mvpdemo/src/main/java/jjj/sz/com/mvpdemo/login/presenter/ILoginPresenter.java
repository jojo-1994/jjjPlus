package jjj.sz.com.mvpdemo.login.presenter;

/**
 * Created by jjj on 2017/7/8.
 *
 * @description:
 */

public interface ILoginPresenter {
    void clear();
    void doLogin(String name, String password);
    void setProgerssBarVisibility(int visibility);
}
