package com.sz.jjj.ipc.messenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sz.jjj.R;

import static com.sz.jjj.ipc.messenger.MessengerService.MSG_FROM_SERVICE;
import static com.sz.jjj.ipc.messenger.MessengerService.TAG;

/**
 * @author:jjj
 * @data:2018/6/27
 * @description:
 */

public class MessengerActivity extends Activity {
    private static final String TGA = "MessengerActivity";

    private Messenger mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
            Message msg = Message.obtain(null, MessengerService.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg", "hello,this is client.");
            msg.setData(data);
            msg.replyTo = mGetReplayMessenger;
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private Messenger mGetReplayMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_FROM_SERVICE:
                    Log.e(TAG, "receive msg from client:" + msg.getData().getString("replay"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipc_messenger_activity);
        Intent intent = new Intent(MessengerActivity.this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
