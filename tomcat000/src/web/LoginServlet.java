package web;

import core.HttpRequest;
import core.HttpResponse;
import core.HttpServlet;

import java.io.File;

public class LoginServlet extends HttpServlet {

    public void service(HttpRequest request, HttpResponse response){
        String username= request.getParamater("username");
        String password=request.getParamater("password");
        System.out.println(username);
        System.out.println(password);
        //模拟登录
        if("zs".equals(username)&& "1234".equals(password)){
            response.setFile(new File("webapps/index.html"));
        }else{
            response.setFile(new File("webapps/error.html"));
        }

    }
}
