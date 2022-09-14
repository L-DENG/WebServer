package com.webserver.http;


import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP请求对象
 * 该类的每一个实例用于表示一个HTTP请求内容
 * 每个请求由三部分构成:
 * 请求行，消息头，消息正文
 */
public class HttpServletRequest {
    private Socket socket;
    //请求行相关信息
    private String method;//请求方式
    private String uri;//抽象路径
    private String protocol;//协议版本

    //消息头相关信息
    private Map<String,String> header =   new HashMap<>();

    public  HttpServletRequest(Socket socket) throws IOException {
        this.socket = socket;
        //1.1 解析请求行
        parseRequest();

        //1.2 解析消息头
        parseHeader();

        //1.3 解析消息正文
        parseContent();



    }

    /**
     * 解析请求行
     */
    private void parseRequest() throws IOException {
        //1.1 解析请求行
        String line = readLine();
        System.out.println(line);

        String[] lineArray = line.split("\\s");
        System.out.println(lineArray.length);
        method = lineArray[0];
        uri = lineArray[1];//这里可能会出现数组下标越界异常!原因:浏览器空请求，后期会解决
        protocol = lineArray[2];

        // http://localhost:8089/myweb/index.html
        System.out.println("method:"+method);
        System.out.println("uri:"+uri);
        System.out.println("protocol:"+protocol);
    }


    private void parseHeader() throws IOException {

        //1.2 解析消息头
        String line;
        while(!(line = readLine()).isEmpty()) {
            String[] headerArray = line.split(":\\s");
            header.put(headerArray[0],headerArray[1]);
            System.out.println("消息头：" + line);
        }
    }


    private void parseContent(){}





    /**
     * 通过socket获取的输入流读取客户端发送过来的一行字符串
     * @return
     */
    private String readLine() throws IOException {

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
        return biulder.toString().trim();

    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHeader(String name){
        return header.get(name);
    }
}
