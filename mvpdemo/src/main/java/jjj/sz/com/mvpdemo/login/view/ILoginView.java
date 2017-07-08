package jjj.sz.com.mvpdemo.login.view;

/**
 * Created by jjj on 2017/7/8.
 *
 * @description:
 */

public interface ILoginView {
    void onClearText();
    void onSetProgressBarVisibility(int visibility);
    void onLoginResult(Boolean result, int code);
}
