package cn.maxinyue.plugins.jmeter.websocket;

import cn.maxinyue.plugins.jmeter.websocket.sampler.WebSocketSampler;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jorphan.logging.LoggingManager;
import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;

@ClientEndpoint
public class DefaultClientEndpoint {

    private static final org.apache.log.Logger logger = LoggingManager.getLoggerForClass();

    private final WebSocketSampler webSocketSampler;

    public DefaultClientEndpoint(WebSocketSampler webSocketSampler) {
        this.webSocketSampler = webSocketSampler;
    }

    @OnMessage
    public void onMessage(String s, Session session) {
        synchronized (webSocketSampler) {
            webSocketSampler.setResponseMessage(s);
            webSocketSampler.getCountDownLatch().countDown();
            webSocketSampler.notify();
        }

    }

    @OnOpen
    public void onOpen(Session session) {

    }

    @OnClose
    public void onClose(Session session) {
        //session.getAsyncRemote().sendText("{\"sender\":{\"staffDict\":{\"empId\":\"1227\"},\"applications\":{\"appId\":\"140304112743\"}},\"content\":\"LOGOUT\",\"messageType\":\"SYSTEM\"}");
    }

}
