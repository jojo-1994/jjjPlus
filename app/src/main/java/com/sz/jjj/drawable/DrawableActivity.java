package com.sz.jjj.drawable;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.jjj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author:jjj
 * @data:2018/8/28
 * @description:
 */

public class DrawableActivity extends AppCompatActivity {
    @BindView(R.id.iv_level_list)
    ImageView ivLevelList;
    @BindView(R.id.tv_transition)
    TextView ivLevelTransition;
    @BindView(R.id.tv_insert)
    TextView tvInsert;
    @BindView(R.id.iv_scale)
    ImageView ivScale;
    @BindView(R.id.iv_clip)
    ImageView ivClip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawable_activity);
        ButterKnife.bind(this);
        ivLevelList.setImageLevel(2);
        ScaleDrawable scaleDrawable = (ScaleDrawable) ivScale.getDrawable();
        scaleDrawable.setLevel(5000);
        ClipDrawable clipDrawable = (ClipDrawable) ivClip.getDrawable();
        clipDrawable.setLevel(5000);
    }

    @OnClick({R.id.tv_transition})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_transition:
                TransitionDrawable transitionDrawable = (TransitionDrawable) ivLevelTransition.getBackground();
                transitionDrawable.startTransition(5000);
        }
    }
}
