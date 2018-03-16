package com.sz.jjj.xml;

import com.sz.jjj.xml.model.Books;

import java.io.InputStream;
import java.util.List;

/**
 * Created by jjj on 2017/8/23.
 *
 * @description:
 */

public interface BookParser {
    /**
     * 解析输入流 得到Book对象集合
     * @param is
     * @return
     * @throws Exception
     */
    public List<Books> parse(InputStream is) throws Exception;

    /**
     * 序列化Book对象集合 得到XML形式的字符串
     * @param books
     * @return
     * @throws Exception
     */
    public String serialize(List<Books> books) throws Exception;
}
