package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @Test
    void createOrder() {

        /**
         * 순수 자바 코드로 진행 ->
         * 수정자 주입시, memberRepository, discountPolicy가 필요한데 누락되어 있어서 NPE 발생 (가짜라도 만들어서 넣어줘야함)
         * => 생성자 주입시 컴파일 오류로 바로 인지 가능!
         */
//        OrderServiceImpl orderService = new OrderServiceImpl();

        MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
        memoryMemberRepository.save(new Member(1L, "name", Grade.VIP));
        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}