package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    // final을 넣으면 초기값에서 넣거나 아니면 생성자에서만 값을 넣어줄 수 있음
    private final MemberRepository memberRepository;
//            = new MemoryMemberRepository();

//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
// DiscountPolicy에도 의존, FixDiscountPolicy도 의존 -> DIP 위반
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
// RateDiscountPolicy로 변경하는 순간, OrderServiceImpl 소스코드도 변경해야함 -> OCP 위반
    private final DiscountPolicy discountPolicy;
    // 인터페이스에만 의존! - 추상화에만 의존
    // 해결 방법: 누군가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야한다.

/*
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
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        System.out.println("OrderServiceImpl.OrderServiceImpl");
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
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
