package com.sz.jjj.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.sz.jjj.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author:jjj
 * @data:2018/8/21
 * @description:
 */

public class BitmapActivity extends AppCompatActivity {
    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image4)
    ImageView image4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmap_activity);
        ButterKnife.bind(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);
        image1.setImageBitmap(bitmap);
        Log.e("------width--", bitmap.getWidth() + "");
        Log.e("------height--", bitmap.getHeight() + "");

        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);
        image2.setImageBitmap(bitmap);
        Log.e("------width--", bitmap.getWidth() + "");
        Log.e("------height--", bitmap.getHeight() + "");

        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 5;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);
        image3.setImageBitmap(bitmap);
        Log.e("------width--", bitmap.getWidth() + "");
        Log.e("------height--", bitmap.getHeight() + "");

        image4.setImageBitmap(decodeSampleBitmapFromResource(getResources(), R.mipmap.ic_launcher, 50, 50));

        // 当前进程可用内存大小，单位是kb
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.e("----runtime max memory:", maxMemory + "");
        // 缓存大小为当前可用内存的八分之一
        int cacheSize = maxMemory / 8;
        Log.e("----cacheSize:", cacheSize + "");
        // 缓存对象值
        final Bitmap finalBitmap = bitmap;
        Log.e("----bitmap size:", bitmap.getRowBytes() * bitmap.getHeight() / 1024 + "");
        // 新建缓存类
        LruCache lruCache = new LruCache<Integer, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(Integer key, Bitmap value) {
                // 缓存的大小是1kb
                return 1;
            }
        };
        for (int i = 0; i < cacheSize + 1; i++) {
            lruCache.put(i, finalBitmap);
        }
        // 超出缓存大小，移除改对象值
        for (int i = 0; i < cacheSize + 1; i++) {
            Bitmap bitmap1 = (Bitmap) lruCache.get(i);
            if (bitmap1 == null) {
                Log.d("-----", i + "");
            } else {
                Log.e("-----", i + "");
            }
        }
    }

    public static Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int sampleSize = 1;
        if (width > reqWidth || height > reqHight) {
            final int halfWidth = width / 2;
            final int halfHeight = height / 2;
            while ((halfWidth / sampleSize >= reqWidth) && halfHeight / sampleSize >= reqHight) {
                sampleSize *= 2;
            }
        }
        Log.e("------", sampleSize + "");
        return sampleSize;
    }
}
