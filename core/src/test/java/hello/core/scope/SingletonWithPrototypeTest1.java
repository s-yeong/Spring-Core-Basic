package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        // 스프링 컨테이너에서 프로로타입 빈 직접 요청
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);


    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class ,PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean {
//        private final PrototypeBean prototypeBean;  // 생성시점에 주입

        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;    // 지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스 제공
//        @Autowired
//        private Provider<PrototypeBean> prototypeBeanProvider;
/*
        @Autowired
        public ClientBean(PrototypeBean prototypeBean) { // prototypeBean 내놓으라고 스프링 컨테이너에 요청
            // 컨테이너가 prototypeBean 만들어서 줌
            this.prototypeBean = prototypeBean;
        }
*/

        public int logic() {

            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();    // getObject 호출 시 그 때 스프링 컨테이너에서 해당 빈 찾아서 반환해줌 => DL
//            PrototypeBean prototypeBean = prototypeBeanProvider.get();    // build.gradle에서 라이브러리 추가해야함
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }

}
