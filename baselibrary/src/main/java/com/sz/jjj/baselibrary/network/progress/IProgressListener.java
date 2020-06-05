package com.sz.jjj.baselibrary.network.progress;

/**
 * @author:jjj
 * @data:2018/6/1
 * @description:
 */

public interface IProgressListener {
    void onProgress(long currentBytes, long contentLength, boolean done);
}
