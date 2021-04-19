package com.ziniu.pay.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;

@Slf4j
@Component
public class SocketClientV2 {

    private static String IP;

    private static int PORT;

    @Value("${socket.ip}")
    public void setIP(String IP) {
        SocketClientV2.IP = IP.trim();
    }

    @Value("${socket.port}")
    public void setPORT(int PORT) {
        SocketClientV2.PORT = PORT;
    }

    private static Socket socket;

    /**
     * 示例：String str = "Xhj1001#1153285#201310230099112#10250001002171482#1#1#对私#1|6226310510420134|135067|500|J|#@@@@";
     *
     * @param str
     * @return
     */
    public static String send(String str) {
        log.info("connect:{}:{}", IP, PORT);
        if(socket == null){
            // 和服务器创建连接
            reconnect();
        }else{
            try {
                //短线重连
                socket.sendUrgentData(0xFF);
            } catch (IOException e) {
                reconnect();
            }
        }
        try (
                // 要发送给服务器的信息
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter pw = new OutputStreamWriter(os,"gbk");
                // 从服务器接收的信息
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"gbk"));
        ) {
//            str = DealStringUtils.toGbk(str);
            log.info("send: " + str);
            pw.write(str);
            pw.flush();
//            socket.shutdownOutput();
            String info = null;
            while ((info = br.readLine()) != null) {
                log.info("receive：" + info);
                return info;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void reconnect() {
        try {
            socket = new Socket(IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        IP = "223.72.175.152";
        PORT = 8008;
        String str = "Xhj1001#1153285#201310230099112#10250001002171482#1#1#对私#1|6226310510420134|135067|500|J|#@@@@";
        String send = send(str);
    }


}
