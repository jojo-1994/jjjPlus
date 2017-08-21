package jjj.sz.com.mvpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jjj.sz.com.mvpdemo.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_main_login)
    TextView tvMainLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        A a = new A();
//        WeakReference<A> reference = new WeakReference<A>(a);
        B b = new B(a);

        a = null;
        b.use();

        System.gc();
        System.gc();
        System.gc();
        System.gc();

        Log.e("testWeakReferences", "A = " + a);
//        Log.e("testWeakReferences", "reference.get() = " + reference.get());
        Log.e("testWeakReferences", "B = " + b);
    }

    class A {
        String dd;

        public void change() {
            dd = "";
        }
    }

    class B {
        A aa;

        public B(A a) {
            aa = a;
            aa.change();
        }

        public void use() {
        }
    }

    @OnClick(R.id.tv_main_login)
    public void onViewClicked() {
        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
