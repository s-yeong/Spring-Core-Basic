package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        // 스프링 컨테이너는 생성자에 클래스 정보를 받는다(클래스 정보를 넘기면 해당 클래스가 스프링 빈으로 자동 등록됨)

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member memebr = new Member(1L, "userA", Grade.VIP);
        int discountPrice =  discountService.discount(memebr, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(memebr, 20000, "rateDiscountPolicy");;
        assertThat(rateDiscountPrice).isEqualTo(2000);
    }



    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap; // map의 키에 스프링 빈의 이름을 넣어주고, 그 값으로 DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아줌
            this.policies = policies;   // DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아줌
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }   // FixDiscountPolicy, RateDiscountPolicy 모두 주입됨

        public int discount(Member member, int price, String discountCode) {
            // discountCode로 FixDiscountPolicy가 넘어오면
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            // 찾아서 Map에 등록된 key가 fixDiscountPolicy인 스프링 빈을 꺼내서 로직을 호출해줌
            return discountPolicy.discount(member, price);
        }
    }

}
