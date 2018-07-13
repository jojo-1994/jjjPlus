package com.sz.jjj.ipc.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * @author:jjj
 * @data:2018/7/10
 * @description:
 */

public class TPCServerService extends Service {

    public static final String TAG = "TPCServerService";
    private boolean mIsServiceDestoryed = false;
    private String[] mDefinedMessage = new String[]{
            "你好啊，哈哈，",
            "请问你叫什么名字啊？",
            "今天北京天气不错啊，shy",
            "你知道吗，我可以和多个人同时聊天",
            "给你讲个笑话吧，据说爱笑的人运气不会太差，不知道真假"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                Log.e(TAG, "establish tcp server failed, port:8688");
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDestoryed) {
                try {
                    final Socket client = serverSocket.accept();
                    Log.e(TAG, "accept");
                    new Thread() {
                        @Override
                        public void run() {
                            responseClient(client);
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        private void responseClient(Socket client) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                out.println("欢迎来到聊天室");
                while (!mIsServiceDestoryed) {
                    String str = in.readLine();
                    Log.e(TAG, "msg from client ssss");
                    if (str == null) {
                        break;
                    }
                    Log.e(TAG, "msg from client:" + str);
                    int i = new Random().nextInt(mDefinedMessage.length);
                    String msg = mDefinedMessage[i];
                    out.println(msg);
                    Log.e(TAG, "send:" + msg);
                }
                Log.e(TAG, "client quit.");
                MyUtils.close(in);
                MyUtils.close(out);
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
