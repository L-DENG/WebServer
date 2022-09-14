package com.webserver.core;


import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 该线程任务负责与指定客户端完成HTTP交互
 * 与客户端交流的流程分成三步:
 * 1:解析请求
 * 2:处理请求
 * 3:发送响应
 */
public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();


            char pre = 'a', cur ='a';
            StringBuilder biulder = new StringBuilder();
            int d;
            while((d=in.read())!=-1){
                cur = (char)d;
                if(pre==13&&cur==10){
                    break;
                }
                biulder.append(cur);
                pre = cur;
            }
            String line = biulder.toString().trim();
            System.out.println(line);


            //请求行相关信息
            String method;//请求方式
            String uri;//抽象路径
            String protocol;//协议版本

            String[] lineArray = line.split("\\s");
            System.out.println(lineArray.length);
            method = lineArray[0];
            uri = lineArray[1];//这里可能会出现数组下标越界异常!原因:浏览器空请求，后期会解决
            protocol = lineArray[2];


            // http://localhost:8089/myweb/index.html
            System.out.println("method:"+method);
            System.out.println("uri:"+uri);
            System.out.println("protocol:"+protocol);




        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
