package web;

import core.HttpRequest;
import core.HttpResponse;
import core.HttpServlet;

import java.io.File;

public class RegServlet extends HttpServlet {

    public void service(HttpRequest request, HttpResponse response){
        String username= request.getParamater("username");
        String password=request.getParamater("password");
        //模拟入库
        System.out.println("入库成功");
        response.setFile(new File("webapps/success.html"));
    }
}
