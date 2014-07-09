package cn.maxinyue.plugins.jmeter.websocket;

import cn.maxinyue.plugins.jmeter.websocket.sampler.WebSocketSampler;
import org.apache.jmeter.threads.JMeterContextService;
import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;

@ClientEndpoint
public class DefaultClientEndpoint {

    static Logger logger = LoggerFactory.getLogger(DefaultClientEndpoint.class);

    private WebSocketSampler webSocketSampler;

    private CountDownLatch countDownLatch;

    public DefaultClientEndpoint(WebSocketSampler webSocketSampler) {
        this.webSocketSampler = webSocketSampler;
        countDownLatch=new CountDownLatch(1);
    }

    @OnMessage
    public void onMessage(String s) {
        webSocketSampler.setResponseMessage(s);
        countDownLatch.countDown();
    }

    @OnOpen
    public void onOpen(Session session) {

    }

    @OnClose
    public void onClose(Session session) {
        //session.getAsyncRemote().sendText("{\"sender\":{\"staffDict\":{\"empId\":\"1227\"},\"applications\":{\"appId\":\"140304112743\"}},\"content\":\"LOGOUT\",\"messageType\":\"SYSTEM\"}");
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }
}
