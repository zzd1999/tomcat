package core;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String uri;
    private String method;
    //请求中的头信息
    private Map<String,String> headers=new HashMap<String,String>();
    //请求中参数列表
    private Map<String,String> paramaters=new HashMap<>();
    //获得某个参数的值
    public String getParamater(String key){
        return paramaters.get(key);
    }

    private InputStream is;
    private BufferedReader br;
    public HttpRequest(InputStream is) throws EmptyRequestException{
        try{
            this.is=is;//把局部变量is扩大作用域为属性。
            br=new BufferedReader(
                    new InputStreamReader(is,"iso8859-1")
            );
            parseRequestLine();
            parseRequestHeaders();
            parseRequestContent();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            System.out.println("解析请求失败");
        }
    }

    private void parseRequestLine() throws EmptyRequestException{
        String line=readLine();
        if(line==null){
            throw new EmptyRequestException("空请求，不处理");
        }
        String[] tmp=line.split("\\s");
        method=tmp[0];
        uri=tmp[1];
        if("/".equals(uri)){
            uri="/index.html";
        }
        //继续解析带参数的uri
        //login.do?username=zs&password=1234&username=3456
        if(uri.indexOf("?")!=-1){
            try {
                uri= URLDecoder.decode(uri,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String [] tmp2=uri.split("\\?");
            uri=tmp2[0];
            String params=tmp2[1];
            String[] tmp3=params.split("&");
            for(String tmp4:tmp3){
                String[] tmp5=tmp4.split("=");
                String key=tmp5[0];
                String value=tmp5[1];
                paramaters.put(key,value);
            }
        }
    }
    private void parseRequestHeaders(){
        while(true){
            String line=readLine();
            if("".equals(line)){
                break;
            }
            String[] tmp=line.split(": ");
            headers.put(tmp[0],tmp[1]);
        }
    }
    private void parseRequestContent(){
        try{
            if(headers.containsKey("Content-Length")
                    && headers.containsKey("Content-Type")){
                String t="application/x-www-form-urlencoded";
                String t2=headers.get("Content-Type");
                if(t.equals(t2)){//认为有post正文
                    int length=Integer.parseInt(headers.get("Content-Length"));
                    byte[] data=new byte[length];
                    is.read(data);
                    String da=new String(data,"ISO8859-1");
                    da=URLDecoder.decode(da,"UTF-8");
                    System.out.println("data:"+da);
                    String[] params=da.split("&");
                    for(String tmp:params){
                        String[] tmp2=tmp.split("=");
                        paramaters.put(tmp2[0],tmp2[1]);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String readLine(){
        //取消之前用的是reader.readLine()
        //因为该方法认为换行的方式和我们这里不一样
        try {
            //保存一整行 的数据
            String str=new String("");
            //我们自己封装一个readLine  不用reader了
            int pre=-1;//上一个字节
            int current=-1;//当前字节
            while(true){
                current=is.read();
                if(pre==13 && current==10){// 出现回车和换行结束循环
                    break;
                }
                char c=(char)current;
                str += c;
                pre=current;//把当前交给上一个字节
            }

            return str.trim();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }
}
