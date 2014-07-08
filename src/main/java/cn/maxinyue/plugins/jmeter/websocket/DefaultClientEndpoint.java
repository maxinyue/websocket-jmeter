package cn.maxinyue.plugins.jmeter.websocket;

import cn.maxinyue.plugins.jmeter.websocket.sampler.WebSocketSampler;
import org.apache.jmeter.threads.JMeterContextService;
import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.util.regex.Pattern;

@ClientEndpoint
public class DefaultClientEndpoint {

    Logger logger = LoggerFactory.getLogger(DefaultClientEndpoint.class);

    private WebSocketSampler webSocketSampler;

    public DefaultClientEndpoint(WebSocketSampler webSocketSampler) {
        this.webSocketSampler = webSocketSampler;
    }

    @OnMessage
    public void onMessage(String s) {
        final Pattern regex = (webSocketSampler.getRecvMessage() != null) ? Pattern.compile(webSocketSampler.getRecvMessage()) : null;
        if (regex == null || regex.matcher(s).find()) {
            webSocketSampler.setResponseMessage(s);
        }
    }

    @OnOpen
    public void onOpen() {
        logger.debug("Connect " + JMeterContextService.getContext().getThread().getThreadName());
    }

    @OnClose
    public void onClose() {
        logger.debug("Disconnect " + JMeterContextService.getContext().getThread().getThreadName());
    }
}
