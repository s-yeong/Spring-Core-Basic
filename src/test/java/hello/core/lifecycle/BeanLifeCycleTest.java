package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifyCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close(); // 스프링 컨테이너 종료


    }


    @Configuration
    static class LifyCycleConfig {

        @Bean   // 호출된 결과물이 스피링 빈으로 등록
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev"); // 객체를 생성한 다음 수정자 주입을 통해 url을 넣어줌
            return networkClient;
        }
    }
}
