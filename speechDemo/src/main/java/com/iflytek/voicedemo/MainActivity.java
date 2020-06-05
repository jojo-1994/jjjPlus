package com.iflytek.voicedemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.sunflower.FlowerCollector;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();
	private Toast mToast;

	@SuppressLint("ShowToast")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		requestPermissions();
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		SimpleAdapter listitemAdapter = new SimpleAdapter();
		((ListView) findViewById(R.id.listview_main)).setAdapter(listitemAdapter);

		RecognizerDialog mIatDialog = new RecognizerDialog(MainActivity.this, null);
		mIatDialog.show();
	}

	@Override
	public void onClick(View view) {
		int tag = Integer.parseInt(view.getTag().toString());
		Intent intent = null;
		switch (tag) {
		case 0:
			// 语音转写
			intent = new Intent(MainActivity.this, IatDemo.class);
			break;
		case 1:
			// 语法识别
			intent = new Intent(MainActivity.this, AsrDemo.class);
			break;
		case 2:
			// 语义理解
			intent = new Intent(MainActivity.this, UnderstanderDemo.class);
			break;
		case 3:
			// 语音合成
			intent = new Intent(MainActivity.this, TtsDemo.class);
			break;
		case 4:
			// 语音评测
			intent = new Intent(MainActivity.this, IseDemo.class);
			break;
		case 5:
			// 唤醒
			showTip("请登录：http://www.xfyun.cn/ 下载体验吧！");
			break;
		case 6:
			// 声纹
		default:
			showTip("在IsvDemo中哦，为了代码简洁，就不放在一起啦，^_^");
			break;
		}
		
		if (intent != null) {
			startActivity(intent);
		}
	}

	// Menu 列表
	String items[] = { "立刻体验语音听写", "立刻体验语法识别", "立刻体验语义理解", "立刻体验语音合成",
			"立刻体验语音评测", "立刻体验语音唤醒", "立刻体验声纹密码" };

	private class SimpleAdapter extends BaseAdapter {
		public View getView(int position, View convertView, ViewGroup parent) {
			if (null == convertView) {
				LayoutInflater factory = LayoutInflater.from(MainActivity.this);
				View mView = factory.inflate(R.layout.list_items, null);
				convertView = mView;
			}
			
			Button btn = (Button) convertView.findViewById(R.id.btn);
			btn.setOnClickListener(MainActivity.this);
			btn.setTag(position);
			btn.setText(items[position]);
			
			return convertView;
		}

		@Override
		public int getCount() {
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}

	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}

	@Override
	protected void onResume() {
		// 开放统计 移动数据统计分析
		FlowerCollector.onResume(MainActivity.this);
		FlowerCollector.onPageStart(TAG);
		super.onResume();
	}

	@Override
	protected void onPause() {
		// 开放统计 移动数据统计分析
		FlowerCollector.onPageEnd(TAG);
		FlowerCollector.onPause(MainActivity.this);
		super.onPause();
	}

	private void requestPermissions(){
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				int permission = ActivityCompat.checkSelfPermission(this,
						Manifest.permission.WRITE_EXTERNAL_STORAGE);
				if(permission!= PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
							Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
							Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
							Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS},0x0010);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}
