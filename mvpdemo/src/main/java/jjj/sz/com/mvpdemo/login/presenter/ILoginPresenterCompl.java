package jjj.sz.com.mvpdemo.login.presenter;

import android.os.Handler;
import android.os.Looper;

import jjj.sz.com.mvpdemo.login.model.IUser;
import jjj.sz.com.mvpdemo.login.model.UserModel;
import jjj.sz.com.mvpdemo.login.view.ILoginView;

/**
 * Created by jjj on 2017/7/8.
 *
 * @description:
 */

public class ILoginPresenterCompl implements ILoginPresenter {

    ILoginView iLoginView;
    IUser user;
    Handler handler;

    public ILoginPresenterCompl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    private void initUser() {
        user = new UserModel("mvp", "mvp");
    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void doLogin(String name, String password) {
        boolean isLoginSuccess = true;
        final int code = user.checkUserValidity(name, password);
        if (code != 0) {
            isLoginSuccess = false;
        }
        final boolean result = isLoginSuccess;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginResult(result, code);
            }
        }, 5000);
    }

    @Override
    public void setProgerssBarVisibility(int visibility) {
        iLoginView.onSetProgressBarVisibility(visibility);
    }
}
