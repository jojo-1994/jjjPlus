package com.sz.jjj.ipc.aidl;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.sz.jjj.R;
import com.sz.jjj.aidl.Book;
import com.sz.jjj.aidl.IBookManager;
import com.sz.jjj.aidl.IOnNewBookArrivedListener;

import java.util.List;

/**
 * @author:jjj
 * @data:2018/7/9
 * @description:
 */

public class BookManagerActivity extends Activity {

    private static final int MESSAGE_NEW_BOOK_ARRICED = 1;

    private IBookManager mIBookManager;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IBookManager bookManager = IBookManager.Stub.asInterface(iBinder);
            try {
                mIBookManager = bookManager;
                List<Book> list = bookManager.getBookList();
                Log.e(BookManagerService.TAG, "query book list:" + list.getClass().getCanonicalName());
                for (Book book : list) {
                    Log.e(BookManagerService.TAG, "query book:" + book.getBookName());
                }
                Book newBook = new Book(3, "安卓开发艺术探索");
                bookManager.addBook(newBook);
                Log.e(BookManagerService.TAG, "add book:" + newBook.getBookName());
                List<Book> newList = bookManager.getBookList();
                for (Book book : newList) {
                    Log.e(BookManagerService.TAG, "query book:" + book.getBookName());
                }
                bookManager.registerListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIBookManager = null;
            Log.e(BookManagerService.TAG, "binder died." + getAppNameByPID());
        }
    };

    public String getAppNameByPID(){
        int pid = android.os.Process.myPid();//获取进程pid
        String processName = "";
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);//获取系统的ActivityManager服务
        for (ActivityManager.RunningAppProcessInfo appProcess : am.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                processName = appProcess.processName;
                break;
            }
        }
        return processName;
    }

    private IOnNewBookArrivedListener mIOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRICED, newBook).sendToTarget();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        findViewById(R.id.btn_bookList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Book> list = null;
                        try {
                            list = mIBookManager.getBookList();
                            Log.e(BookManagerService.TAG, "query book list2222:" + list.getClass().getCanonicalName());
                            for (Book book : list) {
                                Log.e(BookManagerService.TAG, "query book2222:" + book.getBookName());
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRICED:
                    Book book = (Book) msg.obj;
                    Log.e(BookManagerService.TAG, "receive new book:" + book.getBookName());
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (mIBookManager != null && mIBookManager.asBinder().isBinderAlive()) {
            try {
                mIBookManager.unregisterListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
