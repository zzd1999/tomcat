package core;

import util.HttpContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {

    private File file;

    private OutputStream os;

    public HttpResponse(OutputStream os){
        this.os=os;
    }

    //设置发送给前端的文件
    public void setFile(File file) {
        this.file = file;
    }

    //外部调用该方法，可以发送数据给前端 调用该方法前必须给file赋值
    public void flush(){
        sendResponseLine();
        sendResponseHeader();
        sendResponseContent();
        try {
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendResponseLine(){
        try {
            os.write("HTTP/1.1 200 OK".getBytes("ISO8859-1"));
            os.write(13);
            os.write(10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendResponseHeader(){
        try {
            String name=file.getName();
            name=name.substring(name.lastIndexOf(".")+1);
            name=HttpContext.mime_types.get(name);
            os.write(("Content-Type: "+name).getBytes("iso8859-1"));
            os.write(13);
            os.write(10);
            os.write(("Content-Length: "+file.length()).getBytes("iso8859-1"));
            os.write(13);
            os.write(10);
            os.write(13);
            os.write(10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendResponseContent(){
        try {
            FileInputStream fis=new FileInputStream(file);
            byte[] data=new byte[(int)file.length()];
            fis.read(data);
            os.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
