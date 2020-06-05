// IOnNewBookArrivedListener.aidl
package com.sz.jjj.aidl;

import com.sz.jjj.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
