package com.ziniu.pay.util;

import com.ziniu.pay.entity.AreaInfo;
import com.ziniu.pay.service.JDBCService;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * create by lishengbo on 2018-04-23 10:17
 * DOM4J方式解析XML：
 * <p>
 * --------------
 */
@Component
public class AnalysisDOM4J {

    @Resource
    JDBCService jdbcService;

    static int count = 0;

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
//        parserDOM4J();
//        createXMLDOM4J();
//        parserDOM4JV2();
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><root><ret_code>E110</ret_code><ret_msg>商户订单号已经存在</ret_msg><sign>eefbd57eca9bcac95ad09088dc048d61</sign></root>\n";
////        String xml = "<root><ret_code>E110</ret_code><ret_msg>商户订单号已经存在</ret_msg><sign>eefbd57eca9bcac95ad09088dc048d61</sign></root>";
//        parserDOM4ForStrXml(xml);
    }


    private static void createXMLDOM4J() {
        //创建document对象，代表整个xml文档
        Document document = DocumentHelper.createDocument();
        //创建根节点
        Element rss = document.addElement("rss");
        //添加根节点属性
        rss.addAttribute("version", "2.0");
        //生成子节点及节点内容
        Element channel = rss.addElement("channel");
        Element title = channel.addElement("title");
        title.setText("<国内最新新闻 />");
        //设置生成xml格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("GBK");
        //创建File
        File file = new File("src/main/java/xml/books.xml");
        //生成文件
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileOutputStream(file), format);
            //处理转义字符，默认为true，<>等特殊字符默认转义
            writer.setEscapeText(false);
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void parserDOM4J() {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(new File("src/main/java/xml/books.xml"));
            //获取根节点bookstore
            Element rootElement = document.getRootElement();
            //获取迭代器
            Iterator it = rootElement.elementIterator();
            while (it.hasNext()) {
                count++;
                System.out.println("==================遍历第几本书：" + count);
                //遍历book节点
                Element book = (Element) it.next();
                //获取book属性名属性值
                List<Attribute> attributes = book.attributes();
                for (Attribute attr : attributes
                ) {
                    System.out.println("属性名/属性值：" + attr.getName() + "/" + attr.getValue());
                }
                Iterator iterator = book.elementIterator();
                while (iterator.hasNext()) {
                    Element child = (Element) iterator.next();
                    System.out.println("节点名/节点值：" + child.getName() + "/" + child.getStringValue());

                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void parserDOM4JV2() {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(new File("src/main/resources/data/area.xml"));
            //获取根节点bookstore
            Element rootElement = document.getRootElement();
            //获取迭代器
            Iterator it = rootElement.elementIterator();
            while (it.hasNext()) {
                count++;
                System.out.println("==================：" + count);
                //遍历book节点
                Element book = (Element) it.next();
                //获取book属性名属性值
                List<Attribute> attributes = book.attributes();
                String parent = null;
                for (Attribute attr : attributes
                ) {
                    parent = attr.getValue();
                    System.out.println("属性名/属性值：" + attr.getName() + "/" + attr.getValue());
                }
                Iterator iterator = book.elementIterator();
                while (iterator.hasNext()) {
                    Element child = (Element) iterator.next();
                    System.out.println("节点名/节点值：" + child.getName() + "/" + child.getStringValue());
                    String childName = child.getStringValue();
                    AreaInfo areaInfo = new AreaInfo();
                    areaInfo.setChildName(childName);
                    areaInfo.setIsUse("1");
                    areaInfo.setParentName(parent);
                    jdbcService.insertArearHFB(areaInfo);

                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析xml字符串
     *
     * @param xmlDoc
     */
    public static Map<String, Object> parserDOM4ForStrXml(String xmlDoc) {
        Map<String, Object> map = new HashMap<>();
        try {
//            SAXReader saxReader=new SAXReader();
//            Document document=saxReader.read(new ByteArrayInputStream(xmlDoc.getBytes("utf-8")));
            Document document = DocumentHelper.parseText(xmlDoc);// 将字符串转为XML
            //获取根节点bookstore
            Element rootElement = document.getRootElement();
//            System.out.println("根节点：" + rootElement.getName()); // 拿到根节点的名称
            Iterator it = rootElement.elementIterator();
            while (it.hasNext()) {
                //遍历book节点
                Element book = (Element) it.next();
//                System.err.println(book.getName()+"|"+book.getStringValue());
                map.put(book.getName(), book.getStringValue());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }


}
