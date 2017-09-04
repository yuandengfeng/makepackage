package rest.util.xml;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by yuandengfeng on 2016/11/4.
 *
 * 本类包含了xml文件的生成，读取，添加，删除，修改
 */
public class JdomXml {

    private static Log log = LogFactory.getLog(JdomXml.class);

     static Book[] books = new Book[]
            {
                    new Book("江南出版社","1-3631-4","盗墓笔记","南派三叔","25.00"),
                    new Book("延边出版社","2-3631-4","三重门","韩寒","35.00"),
                    new Book("华夏出版社","3-3631-4","平凡的世界","路遥","27.00"),
                    new Book("湖北出版社","4-3631-4","随遇而安","孟非","30.00"),
                    new Book("人民出版社","5-3631-4","1111111","余秋雨","40.00")
            };

    static String fileName="G:\\坤腾\\超汇VIPLog接口\\cc\\中华store.xml";

    //生成XML文件
    public static void BuildXML()  {
        // 创建根节点 并设置它的属性 ;
        Element root = new Element("bookstore");
//        Element version = new Element("version").setText("2.0");
//         将根节点添加到文档中
//        root.addContent(version);
        Document Doc = new Document(root);

        for (int i = 0; i < books.length; i++) {
            // 创建节点 book;
            Element elements = new Element("book");
            elements.setAttribute("genre",books[i].getGenre());
            elements.setAttribute("ISBN",books[i].getISBN());
            // 给 book 节点添加子节点并赋值；
            elements.addContent(new Element("title").setText(books[i].getTitle()));
            elements.addContent(new Element("author").setText(books[i].getAuthor()));
            elements.addContent(new Element("price").setText(books[i].getPrice()));
            root.addContent(elements);

        }

        // 使xml文件 缩进效果
        Format format = Format.getPrettyFormat();
        format.setEncoding("GBK");
        XMLOutputter XMLOut = new XMLOutputter(format);


        try {
            XMLOut.output(Doc, new FileOutputStream(fileName));
        } catch (IOException e) {
            log.error(fileName,e);

        }
        log.debug("生成XML文件成功！");
        System.out.println("生成XML文件成功！");

    }



    //读取xml文件
    public static void ReadXml()throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();//实例JDOM解析器
        File file = new File(fileName);
        Document document = builder.build(file);//读取xml文件
        Element root = document.getRootElement();//获得根节点<bookstore>
        List<Element> list = root.getChildren();//获得根节点的子节点
        for(Element e:list) {

//            System.out.println(e.getValue());
            System.out.println("出版社："+e.getAttributeValue("genre"));
            System.out.println("防伪码："+e.getAttributeValue("ISBN"));
            System.out.println("书名："+e.getChildText("title"));
            System.out.println("作者:"+e.getChildText("author"));
            System.out.println("价格:"+e.getChildText("price"));
            System.out.println("==========================================");
        }
    }

    //添加xml文件信息(即向xml中添加一个节点信息)
    public static void AddXml() throws JDOMException, IOException{
        SAXBuilder builder = new SAXBuilder();//实例JDOM解析器
        File file = new File(fileName);
        Document document = builder.build(file);//读取xml文件
        Element root = document.getRootElement();//获得根节点<bookstore>

        Element element=new Element("book");//添加一个新节点<book></book>
        element.setAttribute("genre","上海出版社");//给book节点添加genre属性
        element.setAttribute("ISBN","6-3631-4");//给book节点添加ISBN属性
        Element element1=new Element("title");
        element1.setText("悲伤逆流成河");
        Element element2=new Element("author");
        element2.setText("郭敬明");
        Element element3=new Element("price");
        element3.setText("32.00");
        element.addContent(element1);
        element.addContent(element2);
        element.addContent(element3);
        root.addContent(element);
        document.setRootElement(root);
        //文件处理
        Format format = Format.getPrettyFormat();
        format.setEncoding("GBK");
        XMLOutputter out = new XMLOutputter(format);
        out.output(document, new FileOutputStream(fileName));
    }

    //删除xml信息（即删除一个xml的节点）
    public static void DeleteXml(String str)throws JDOMException, IOException{
        SAXBuilder builder = new SAXBuilder();//实例JDOM解析器
        File file = new File(fileName);
        Document document = builder.build(file);//读取xml文件
        Element root = document.getRootElement();//获得根节点<bookstore>
        List<Element> list=root.getChildren();//获得所有根节点<bookstore>的子节点<book>
        for(Element e:list){
            //删除genre="上海出版社"这个book节点
            if(str.equals(e.getAttributeValue("genre"))){
                root.removeContent(e);
                System.out.println("删除成功！！！");
                break;
            }
        }
        //文件处理
        Format format = Format.getPrettyFormat();
        format.setEncoding("GBK");
        XMLOutputter out = new XMLOutputter(format);
        out.output(document, new FileOutputStream(fileName));
    }

    //修改xml文件
    public static void UpdateXml(String str)throws JDOMException, IOException{
        SAXBuilder builder = new SAXBuilder();//实例JDOM解析器
        File file = new File(fileName);
        Document document = builder.build(file);//读取xml文件
        Element root = document.getRootElement();//获得根节点<bookstore>
        List<Element> list=root.getChildren();//获得所有根节点<bookstore>的子节点<book>
        for(Element e:list){
            //修改genre="人民出版社"这个book节点
            if(str.equals(e.getAttributeValue("genre"))){
                e.getChild("title").setText("rrrrrrrr");
                System.out.println("修改成功！！！");
                break;
            }
        }
        //文件处理
        Format format = Format.getPrettyFormat();
        format.setEncoding("GBK");
        XMLOutputter out = new XMLOutputter(format);
        out.output(document, new FileOutputStream(fileName));
    }
    /**
     * 这个方法将JDom Document对象根据指定的编码转换字符串返回。
     * @param xmlDoc 将要被转换的JDom对象
     * @param encoding 输出字符串使用的编码
     * @return String Document经处理生成的字符串
     * @throws IOException
     */
    public static String xmlDoctoString(Document xmlDoc, String encoding) throws IOException
    {
        ByteArrayOutputStream byteRep = new ByteArrayOutputStream();
        PrintWriter out=new PrintWriter(byteRep);
        Format format = Format.getPrettyFormat();
        format.setEncoding(encoding);
        XMLOutputter docWriter = new XMLOutputter(format);
        try {
            docWriter.output(xmlDoc, out);
        } catch (Exception e) {
        }
        return byteRep.toString();
    }

    public static void main(String[] args) throws JDOMException, IOException {
        BuildXML();
//        ReadXml();//读取xml文件
//        AddXml();//添加xml信息
//        DeleteXml("上海出版社");//删除genre="上海出版社"这个book节点
//        UpdateXml("人民出版社");//修改genre="人民出版社"这个book节点
    }



}
