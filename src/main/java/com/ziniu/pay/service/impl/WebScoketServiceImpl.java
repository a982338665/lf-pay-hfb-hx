package com.ziniu.pay.service.impl;

import com.ziniu.pay.service.WebSocketService;
import com.ziniu.pay.util.SocketClient;
import org.springframework.stereotype.Component;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/6/3 15:28
 * @Description :
 */
@Component
public class WebScoketServiceImpl implements WebSocketService {

    @Override
    public String groupSending(String message) {
        return SocketClient.send(message);
    }

}
