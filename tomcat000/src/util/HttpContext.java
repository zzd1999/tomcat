package util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具类，用于解析conf/web.xml
 */
public class HttpContext {
    //用于存放web.xml所有的contenttype类型
    public static Map<String,String> mime_types;

    static{
        try{
            mime_types=new HashMap<String,String>();
            SAXReader reader=new SAXReader();
            Document doc=reader.read(new File("conf/web.xml"));
            Element root=doc.getRootElement();
            List<Element> list=root.elements("mime-mapping");
            for(Element e:list){
                String key=e.element("extension").getText();
                String value=e.element("mime-type").getText();
                mime_types.put(key,value);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("解析xml文件错误");
        }
    }

    public static void main(String[] args) {
        System.out.println(HttpContext.mime_types);
    }
}
