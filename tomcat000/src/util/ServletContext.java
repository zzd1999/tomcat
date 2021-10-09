package util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这是一个工具类读取webapps里的web.xml
 */
public class ServletContext {

    public static Map<String,String> mappings;

    static{
        try{
            mappings=new HashMap<String,String>();
            SAXReader reader=new SAXReader();
            Document doc=reader.read(new File("webapps/WEB-INF/web.xml"));
            Element root=doc.getRootElement();
            List<Element> list=root.elements("servlet");
            for(Element e:list){
                String key=e.element("uri").getText();
                String value=e.element("class").getText();
                mappings.put(key,value);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("解析xml文件错误");
        }
    }

    public static void main(String[] args) {
        System.out.println(ServletContext.mappings);
    }
}
