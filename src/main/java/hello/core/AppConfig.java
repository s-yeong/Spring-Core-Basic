package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // 이전에는 객체를 생성하고, 인터페이스에 어떤 것이 들어가는지 직접 했었음
    // AppConfig는 애플리케이션의 실제 동작에 필요한 "구현 객체를 생성" 한다.
    // AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 "생성자를 통해서 주입(연결)" 해준다

    // !- 역할을 드러나게 하는것이 중요함 - memberRepository, discountPolicy  (역할과 구현을 한눈에 들어오게)
    // 메서드 명을 보면 역할이 다 드러나게 했음 (현재 나의 애플리케이션에서는 memberService인데, memberServiceImpl을 쓸거야)
    // 현재 나의 애플리케이션에선 memberRepository에 대한 것은 memoryMemberRepository를 쓸거야
    // OrderService를 쓰는데, 현재 내 애플리케이션에서 memberRepository 쓰는 거, discountPolicy쓰는 거를 가져올 거야
    @Bean   // Spring 컨테이너에 등록!!
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    } // --> 생성자 주입

    // Q. Sington이 깨지는거 아닐까요?
    // @Bean memberService -> new MemoryMemberRepository()
    // @Bean orderService -> new MemoryMemberRepository()

    // 예상 ->
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // call AppConfig.memberRepository

    @Bean
    public MemoryMemberRepository memberRepository( ) {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();    // jdbc로 바뀌면 "이 코드"만 바꾸면 된다!!
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
    }

}
