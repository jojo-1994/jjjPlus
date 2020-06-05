package com.sz.jjj.ipc.provide;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author:jjj
 * @data:2018/7/10
 * @description:
 */

public class BookProvider extends ContentProvider {

    public static final String TAG = "BookProvider";
    private static final String AUTHORITY = "com.sz.jjj.book.provider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        URI_MATCHER.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mdb;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate, current Thread:" + Thread.currentThread().getName());
        mContext = getContext();
        initProviderData();
        return false;
    }

    private void initProviderData() {
        mdb = new DbOpenHelper(mContext).getWritableDatabase();
        mdb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mdb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mdb.execSQL("insert into book values(3, 'Android');");
        mdb.execSQL("insert into book values(4, 'Ios');");
        mdb.execSQL("insert into book values(5, 'Html5');");
        mdb.execSQL("insert into user values(1, 'jake', 1);");
        mdb.execSQL("insert into user values(2, 'jasmine', 0);");
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Log.e(TAG, "query, current Thread:" + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null) {
            return null;
        }
        return mdb.query(table, strings, s, strings1, null, null, s1, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.e(TAG, "getType, current Thread:" + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.e(TAG, "insert, current Thread:" + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null) {
            return null;
        }
        mdb.insert(table, null, contentValues);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.e(TAG, "delete, current Thread:" + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null) {
            return 0;
        }
        int count = mdb.delete(table, s, strings);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Log.e(TAG, "update, current Thread:" + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null) {
            return 0;
        }
        int row = mdb.update(table, contentValues, s, strings);
        if (row > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (URI_MATCHER.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }
}
