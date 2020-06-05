package jjj.sz.com.mvpdemo.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jjj.sz.com.mvpdemo.R;
import jjj.sz.com.mvpdemo.login.presenter.ILoginPresenter;
import jjj.sz.com.mvpdemo.login.presenter.ILoginPresenterCompl;
import jjj.sz.com.mvpdemo.login.view.ILoginView;

/**
 * Created by jjj on 2017/7/8.
 *
 * @description:
 */

public class LoginActivity extends AppCompatActivity implements ILoginView {
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.btn_login_login)
    Button btnLoginLogin;
    @BindView(R.id.btn_login_clear)
    Button btnLoginClear;
    @BindView(R.id.progress_login)
    ProgressBar progressLogin;

    ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginPresenter = new ILoginPresenterCompl(this);
        loginPresenter.setProgerssBarVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.btn_login_login, R.id.btn_login_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_login:
                loginPresenter.setProgerssBarVisibility(View.VISIBLE);
                btnLoginClear.setEnabled(false);
                btnLoginLogin.setEnabled(false);
                loginPresenter.doLogin(etLoginUsername.getText().toString(), etLoginPassword.getText().toString());
                break;
            case R.id.btn_login_clear:
                loginPresenter.clear();
                break;
        }
    }

    @Override
    public void onClearText() {
        etLoginUsername.setText("");
        etLoginPassword.setText("");
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressLogin.setVisibility(visibility);
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        progressLogin.setVisibility(View.INVISIBLE);
        btnLoginLogin.setEnabled(true);
        btnLoginClear.setEnabled(true);
        if (result) {
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Login Fail, code=" + code, Toast.LENGTH_SHORT).show();
        }
    }
}
