//package priv.noby.rabbitmq2.rabbitmqSpringBoot;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import priv.noby.rabbitmq2.service.ProduceService;
//
//@SpringBootTest
//class ProduceSendTest {
//    @Autowired
//    ProduceService produceSend;
//    @Test
//    void send() {
//        String send = produceSend.send("exchangeTopic", "routingPC.routingQQ", "hello");
//        System.out.println(send);
//    }
//}