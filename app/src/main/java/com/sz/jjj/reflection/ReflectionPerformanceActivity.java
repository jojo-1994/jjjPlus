package com.sz.jjj.reflection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.sz.jjj.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj on 2018/4/2.
 *
 * @description:
 */
public class ReflectionPerformanceActivity extends AppCompatActivity {

    @BindView(R.id.executeResultTxtId)
    TextView executeResultTxtId;
    @BindView(R.id.executeCountEditTxtId)
    EditText executeCountEditTxtId;

    private Executor mPerformanceExecutor = Executors.newSingleThreadExecutor();
    private static final int AVERAGE_COUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reflection_performance_activity);
        ButterKnife.bind(this);
    }

    private void execute() {
        executeResultTxtId.setText("");
        mPerformanceExecutor.execute(new Runnable() {
            @Override
            public void run() {
                long costTime = 0;
                int executeCount = Integer.parseInt(executeCountEditTxtId.getText().toString());
                long reflectMethodCostTime = 0, normalMethodCostTime = 0, reflectFieldCostTime = 0, normalFieldCostTime = 0;
                updateResultTextView(executeCount + "毫秒耗时情况测试");
                for (int index = 0; index < AVERAGE_COUNT; index++) {
                    updateResultTextView("第 " + (index + 1) + " 次");
                    costTime = getNormalCallCostTime(executeCount);
                    reflectMethodCostTime += costTime;
                    updateResultTextView("执行直接调用方法耗时：" + costTime + " 毫秒");
                    costTime = getReflectCallMethodCostTime(executeCount);
                    normalMethodCostTime += costTime;
                    updateResultTextView("执行反射调用方法耗时：" + costTime + " 毫秒");
                    costTime = getNormalFieldCostTime(executeCount);
                    reflectFieldCostTime += costTime;
                    updateResultTextView("执行普通调用实例耗时：" + costTime + " 毫秒");
                    costTime = getReflectCallFieldCostTime(executeCount);
                    normalFieldCostTime += costTime;
                    updateResultTextView("执行反射调用实例耗时：" + costTime + " 毫秒");
                }

                updateResultTextView("执行直接调用方法平均耗时：" + reflectMethodCostTime / AVERAGE_COUNT + " 毫秒");
                updateResultTextView("执行反射调用方法平均耗时：" + normalMethodCostTime / AVERAGE_COUNT + " 毫秒");
                updateResultTextView("执行普通调用实例平均耗时：" + reflectFieldCostTime / AVERAGE_COUNT + " 毫秒");
                updateResultTextView("执行反射调用实例平均耗时：" + normalFieldCostTime / AVERAGE_COUNT + " 毫秒");
            }
        });
    }

    private long getReflectCallMethodCostTime(int count) {
        long startTime = System.currentTimeMillis();
        for (int index = 0; index < count; index++) {
            ReflectionMonkey programMonkey = new ReflectionMonkey("小明", "男", 12);
            try {
                Method setmLanguageMethod = programMonkey.getClass().getMethod("setmLanguage", String.class);
                setmLanguageMethod.setAccessible(true);
                setmLanguageMethod.invoke(programMonkey, "Java");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return System.currentTimeMillis() - startTime;
    }

    private long getReflectCallFieldCostTime(int count) {
        long startTime = System.currentTimeMillis();
        for (int index = 0; index < count; index++) {
            ReflectionMonkey programMonkey = new ReflectionMonkey("小明", "男", 12);
            try {
                Field ageField = programMonkey.getClass().getDeclaredField("mLanguage");
                ageField.set(programMonkey, "Java");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return System.currentTimeMillis() - startTime;
    }

    private long getNormalCallCostTime(int count) {
        long startTime = System.currentTimeMillis();
        for (int index = 0; index < count; index++) {
            ReflectionMonkey programMonkey = new ReflectionMonkey("小明", "男", 12);
            programMonkey.setLanguage("Java");
        }

        return System.currentTimeMillis() - startTime;
    }

    private long getNormalFieldCostTime(int count) {
        long startTime = System.currentTimeMillis();
        for (int index = 0; index < count; index++) {
            ReflectionMonkey programMonkey = new ReflectionMonkey("小明", "男", 12);
            programMonkey.setLanguage("Java");
        }

        return System.currentTimeMillis() - startTime;
    }

    private void updateResultTextView(final String content) {
        ReflectionPerformanceActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                executeResultTxtId.append(content);
                executeResultTxtId.append("\n");
            }
        });
    }

    @OnClick(R.id.btn_test)
    public void onViewClicked() {
        execute();
    }
}
