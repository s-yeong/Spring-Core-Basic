package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository  memberRepository= new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        // 주문 생성 요청 -> 회원 정보 조회 -> 할인 정책에 넘기고 -> 주문 반환
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        // OrderService가 할인에 대해서는 discountPolicy에게 맡김 (단일 체계 원칙 잘지킨 것!)

        return new Order(memberId, itemName, itemPrice, discountPrice);


    }

}
