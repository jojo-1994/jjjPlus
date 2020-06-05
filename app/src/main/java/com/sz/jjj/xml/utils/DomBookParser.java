package com.sz.jjj.xml.utils;

import com.sz.jjj.xml.model.Books;
import com.sz.jjj.xml.BookParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by jjj on 2017/8/23.
 *
 * @description:
 */

public class DomBookParser implements BookParser {

    @Override
    public List<Books> parse(InputStream is) throws Exception {
        List<Books> books = new ArrayList<Books>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  //取得DocumentBuilderFactory实例
        DocumentBuilder builder = factory.newDocumentBuilder(); //从factory获取DocumentBuilder实例
        Document doc = builder.parse(is);   //解析输入流 得到Document实例
        Element rootElement = doc.getDocumentElement();
        NodeList items = rootElement.getElementsByTagName("book");
        for (int i = 0; i < items.getLength(); i++) {
            Books book = new Books();
            Node item = items.item(i);
            NodeList properties = item.getChildNodes();
            for (int j = 0; j < properties.getLength(); j++) {
                Node property = properties.item(j);
                String nodeName = property.getNodeName();
                if (nodeName.equals("id")) {
                    book.setId(Integer.parseInt(property.getFirstChild().getNodeValue()));
                } else if (nodeName.equals("name")) {
                    book.setName(property.getFirstChild().getNodeValue());
                } else if (nodeName.equals("price")) {
                    book.setPrice(Float.parseFloat(property.getFirstChild().getNodeValue()));
                } else if (nodeName.equals("test")) {
                    NodeList nodeList = property.getChildNodes();
                    Books.Test test = new Books.Test();
                    for (int l = 0; l < nodeList.getLength(); l++) {
                        Node node = nodeList.item(l);
                        String nodeName1 = node.getNodeName();
                        if (nodeName1.equals("aa")) {
                            test.setAa(node.getFirstChild().getNodeValue());
                        } else if (nodeName1.equals("bb")) {
                            test.setBb(node.getFirstChild().getNodeValue());
                        }
                    }
                    book.setTest(test);
                }
            }
            books.add(book);
        }
        return books;
    }

    @Override
    public String serialize(List<Books> books) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();   //由builder创建新文档

        Element rootElement = doc.createElement("books");

        for (Books book : books) {
            Element bookElement = doc.createElement("book");
            bookElement.setAttribute("id", book.getId() + "");

            Element nameElement = doc.createElement("name");
            nameElement.setTextContent(book.getName());
            bookElement.appendChild(nameElement);

            Element priceElement = doc.createElement("price");
            priceElement.setTextContent(book.getPrice() + "");
            bookElement.appendChild(priceElement);

            rootElement.appendChild(bookElement);
        }

        doc.appendChild(rootElement);

        TransformerFactory transFactory = TransformerFactory.newInstance();//取得TransformerFactory实例
        Transformer transformer = transFactory.newTransformer();    //从transFactory获取Transformer实例
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");            // 设置输出采用的编码方式
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");                // 是否自动添加额外的空白
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");   // 是否忽略XML声明

        StringWriter writer = new StringWriter();

        Source source = new DOMSource(doc); //表明文档来源是doc
        Result result = new StreamResult(writer);//表明目标结果为writer
        transformer.transform(source, result);  //开始转换

        return writer.toString();
    }

}
