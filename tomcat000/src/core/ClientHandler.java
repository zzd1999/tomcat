package core;

import util.ServletContext;
import web.LoginServlet;
import web.RegServlet;

import javax.print.attribute.standard.PresentationDirection;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket client;

    public ClientHandler(Socket client){
        this.client=client;
    }

    @Override
    public void run() {
        try {
            System.out.println("客户端有了请求");
            //创建请求对象
            InputStream is=client.getInputStream();
            HttpRequest request=new HttpRequest(is);
            //准备响应对象
            OutputStream os=client.getOutputStream();
            HttpResponse response=new HttpResponse(os);

            String uri=request.getUri();
            File file=new File("webapps"+uri);
            if(file.exists()){//静态资源
                response.setFile(file);
            }else if(ServletContext.mappings.containsKey(uri)){//动态资源
                //找到uri对应的类
                String className=ServletContext.mappings.get(uri);
                Class cls=Class.forName(className);
                //创建这个类的对象
                HttpServlet obj=(HttpServlet)cls.newInstance();
                //调用service方法
                obj.service(request,response);
            }else{
                response.setFile(new File("webapps/404.html"));
            }
            response.flush();
        }catch (EmptyRequestException e){
          return ;//不打印异常，直接结束
        } catch (Exception e){
            e.printStackTrace();//程序错误异常
        }
    }
}
