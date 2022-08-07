package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {

    // 이전에는 객체를 생성하고, 인터페이스에 어떤 것이 들어가는지 직접 했었음
    // AppConfig는 애플리케이션의 실제 동작에 필요한 "구현 객체를 생성" 한다.
    // AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 "생성자를 통해서 주입(연결)" 해준다.
    public MemberService memberService(){
        return new MemberServiceImpl(new MemoryMemberRepository());
    } // --> 생성자 주입

    public OrderService orderService(){
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }

}
