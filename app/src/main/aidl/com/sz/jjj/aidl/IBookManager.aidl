// IBookManager.aidl
package com.sz.jjj.aidl;

import com.sz.jjj.aidl.Book;
import com.sz.jjj.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
