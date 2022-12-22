package hello.core.xml;

import hello.core.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class XmlAppContext {
    ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");

    @Test
    void xmlAppContext() {
        OrderService orderService = ac.getBean("orderService", OrderService.class);
        assertThat(orderService).isInstanceOf(OrderService.class);
    }

}
