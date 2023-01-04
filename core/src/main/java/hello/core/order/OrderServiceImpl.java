package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor    // final이 붙은 것을 가지고 생성자를 만들어줌
public class OrderServiceImpl implements OrderService{

    // final을 넣으면 초기값에서 넣거나 아니면 생성자에서만 값을 넣어줄 수 있음
    private final MemberRepository memberRepository;
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * 객체 지향 설계 원칙 DIP, OCP 위반
     */
    // befroe
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    -> DiscountPolicy에 의존하지만, FixDiscountPolicy("구현체")도 의존 -> "DIP 위반"
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
//    RateDiscountPolicy로 변경하는 순간, OrderServiceImpl 소스코드도 "변경"해야함 -> "OCP 위반"

    /**
     * 해결 방법 : 누군가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야한다.
     * => AppConfig
     */
    private final DiscountPolicy discountPolicy;    // 인터페이스(추상화)에만 의존

/*
    // 수정자 주입
    @Autowired(required = false) // 선택적으로(주입할 대상이 없어도 동작)
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        System.out.println("discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }
*/


/*
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        System.out.println("memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }
*/

    // 생성자 호출시점에 딱 1번만 호출, "불변, 필수" 의존관계에 사용 (생성자가 1개인 경우 @Autowired 생략 가능)
    @Autowired // new OrderServiceImpl(memberRepository, discountPolicy); -> 생성자는 bean 등록할 때 자동 주입이 일어남
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        System.out.println("OrderServiceImpl.OrderServiceImpl");
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
        // @Autowired 매칭 : 타입으로 매칭하는데, 여러 개가 조회됐을 때 파라미터 명(필드 명) 매칭
        // @Qualifier 매칭 : 추가 구분자를 붙여주는 방법, @Qualifier끼리 매칭, 안되면 빈 이름으로 매칭(@Primary에 비해 우선순위 높음)
        // @Primary 매칭 : 우선순위를 정한느 방법
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        // 주문 생성 요청 -> 회원 정보 조회 -> 할인 정책에 넘기고 -> 주문 반환
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        // OrderService가 할인에 대해서는 discountPolicy에게 맡김 (단일 체계 원칙 잘지킨 것!)

        return new Order(memberId, itemName, itemPrice, discountPrice);


    }

    // 싱글톤 테스트용 - MemberRepository 조회
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
