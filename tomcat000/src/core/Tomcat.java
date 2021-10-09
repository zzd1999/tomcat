package core;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Tomcat {

    ServerSocket server=null;

    public Tomcat(){
        try{
            server= new ServerSocket(8080);
            System.out.println("服务器启动成功");
        }catch (Exception e){
            System.out.println("服务器启动失败");
        }
    }

    public void start(){
        try{
            while (true){
                System.out.println("正在接受请求...");
                Socket client=server.accept();//
                Runnable r=new ClientHandler(client);
                Thread t=new Thread(r);
                t.start();
            }

        }catch (Exception e){
            System.out.println("服务器运行失败");
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        new Tomcat().start();
    }
}
