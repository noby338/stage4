package priv.noby.rabbitmq.rabbitmqSpringBoot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class ProduceSendTest {
    @Autowired
    ProduceSend produceSend;
    @Test
    void send() {
        String send = produceSend.send("exchangeTopic", "routingPC.routingQQ", "hello");
        System.out.println(send);
    }
}