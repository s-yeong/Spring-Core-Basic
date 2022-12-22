package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 순수 Java 코드로만 개발 진행
 */
public class MemberApp {

    public static void main(String[] args) {
        // before
//        MemberService memberService = new MemberServiceImpl();

        // before
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        // after - 스프링 컨테이너 적용
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(member.getId());
        System.out.println("new member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }

}
