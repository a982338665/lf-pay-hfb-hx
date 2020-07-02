package com.ziniu.pay.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;

@Slf4j
@Component
public class SocketClient {

    private static String IP;

    private static int PORT;

    @Value("${socket.ip}")
    public void setIP(String IP) {
        SocketClient.IP = IP.trim();
    }

    @Value("${socket.port}")
    public void setPORT(int PORT) {
        SocketClient.PORT = PORT;
    }

    /**
     * 示例：String str = "Xhj1001#1153285#201310230099112#10250001002171482#1#1#对私#1|6226310510420134|135067|500|J|#@@@@";
     *
     * @param str
     * @return
     */
    public static String send(String str) {
        log.info("connect:{}:{}", IP, PORT);
        try (
                // 和服务器创建连接
                Socket socket = new Socket(IP, PORT);
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
            socket.shutdownOutput();
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

    public static void main(String[] args) {
        IP = "223.XX.xx.xx";
        PORT = 8008;
        String str = "Xhj1001#1153285#201310230099112#10250001002171482#1#1#对私#1|6226310510420134|135067|500|J|#@@@@";
        String send = send(str);
    }


}
