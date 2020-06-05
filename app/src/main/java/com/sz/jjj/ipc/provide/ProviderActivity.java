package com.sz.jjj.ipc.provide;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sz.jjj.R;
import com.sz.jjj.aidl.Book;
import com.sz.jjj.ipc.User;

import static com.sz.jjj.ipc.provide.BookProvider.BOOK_CONTENT_URI;
import static com.sz.jjj.ipc.provide.BookProvider.USER_CONTENT_URI;

/**
 * @author:jjj
 * @data:2018/7/10
 * @description:
 */

public class ProviderActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipc_provider_activity);
        Uri uri = Uri.parse("content://com.sz.jjj.book.provider");
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);

        Uri bookUri = BOOK_CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "程序设计的艺术");
        getContentResolver().insert(bookUri, values);

        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            Log.e(BookProvider.TAG, "query book:" + book.toString());
        }
        bookCursor.close();

        Uri userUri = USER_CONTENT_URI;
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        while (userCursor.moveToNext()) {
            User user = new User();
            user.userId = userCursor.getInt(0);
            user.userName = userCursor.getString(1);
            user.isMale = userCursor.getInt(2) == 1;
            Log.e(BookProvider.TAG, "query user:" + user.toString());
        }
        userCursor.close();
    }
}
