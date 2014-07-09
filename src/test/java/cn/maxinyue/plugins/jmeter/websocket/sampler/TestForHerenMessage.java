package cn.maxinyue.plugins.jmeter.websocket.sampler;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.OnceOnlyController;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.engine.JMeterEngineException;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.testelement.property.BooleanProperty;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.timers.UniformRandomTimer;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Locale;

/**
 * Created by Obama on 14-7-9.
 */
public class TestForHerenMessage {


    public static void main(String[] args) throws Exception {

        JMeterEngine engine;
        HashTree config;
        TestPlan testPlan;
        ThreadGroup threadGroup;
        JMeterUtils.setJMeterHome("src/test/resources/");
        JMeterUtils.loadJMeterProperties("src/test/resources/jmeter.properties");
        JMeterUtils.setLocale(Locale.CHINA);

        engine = new StandardJMeterEngine();
        config = new ListedHashTree();
        testPlan = new TestPlan("websocket test");
        testPlan.setFunctionalMode(false);
        testPlan.setSerialized(false);
        testPlan.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        testPlan.setUserDefinedVariables(new Arguments());

        threadGroup = new ThreadGroup();
        threadGroup.setNumThreads(1);
        threadGroup.setRampUp(0);
        threadGroup.setDelay(0);
        threadGroup.setDuration(0);
        threadGroup.setProperty(new StringProperty(ThreadGroup.ON_SAMPLE_ERROR, "continue"));
        threadGroup.setScheduler(false);
        threadGroup.setName("Group1");
        threadGroup.setProperty(new BooleanProperty(TestElement.ENABLED, true));

        LoopController controller = new LoopController();
        controller.setLoops(1);
        controller.setContinueForever(false);
        controller.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        threadGroup.setProperty(new TestElementProperty(ThreadGroup.MAIN_CONTROLLER, controller));

        WebSocketSampler sampler = new WebSocketSampler();
        sampler.setName("WebSocket Test");
        sampler.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        sampler.setContentEncoding("UTF-8");
        sampler.setProtocol("ws");
        sampler.setDomain("localhost");
        sampler.setPort(9999);
        sampler.setPath("heren-message/message", "UTF-8");
        sampler.setSendMessage("{\"sender\":{\"staffDict\":{\"empId\":\"1419\"},\"applications\":{\"appId\":\"131012160730\"}},\"content\":\"患者住院流程?initParam=221717\",\"messageType\":\"BUSINESS\"}");
        sampler.setRecvTimeout(2000L);
        Summariser summariser = new Summariser();
        HashTree tpConfig = config.add(testPlan);
        HashTree tgConfig = tpConfig.add(threadGroup);

        HashTree samplerConfig = tgConfig.add(sampler);
//        samplerConfig.add(summariser);
        engine.configure(config);
        engine.runTest();
    }

}
