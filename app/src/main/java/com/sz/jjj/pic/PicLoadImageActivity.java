package com.sz.jjj.pic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.sz.jjj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj on 2018/4/2.
 *
 * @description:
 */

public class PicLoadImageActivity extends AppCompatActivity {
    @BindView(R.id.btn_load)
    Button btnLoad;
    @BindView(R.id.image)
    NetworkImageView image;
    @BindView(R.id.image2)
    ImageView image2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_loadimage_activity);
        ButterKnife.bind(this);

        Glide.with(this).load("https://www.baidu.com/img/bd_logo1.png").into(image2);
    }

    @OnClick({R.id.btn_load, R.id.image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load:
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                String url = "https://www.baidu.com/img/bd_logo1.png";
                ImageRequest imageRequest = new ImageRequest(url,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                image.setImageBitmap(bitmap);
                            }
                        }, 500, 500, Bitmap.Config.ARGB_8888,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(getBaseContext(), "网络异常", Toast.LENGTH_SHORT).show();

                            }
                        });
                requestQueue.add(imageRequest);

                break;
            case R.id.image:
                break;
        }
    }
}
