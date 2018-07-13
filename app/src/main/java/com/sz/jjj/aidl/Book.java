package com.sz.jjj.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author:jjj
 * @data:2018/6/26
 * @description:
 */

public class Book implements Parcelable {

    public int bookId;

    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }

    public Book() {

    }

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String bookName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bookId);
        parcel.writeString(bookName);
    }

    @Override
    public String toString() {
        return "bookId:" + bookId + "bookName:" + bookName;
    }
}
