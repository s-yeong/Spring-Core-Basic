package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        // ApplicationContext => Spring 컨테이너 (모든 것을 관리해줌)
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // AppConfig에 있는 환경설정 정보를 가지고 스프링 컨테어너에 안에 있는 객체 생성해서 관리해줌
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        // 스프링 컨테이너에서 꺼내기 (메서드 명을 스프링 빈 이름으로 사용)

        // appconfig에서 memberService 인터페이스를 받음
//        MemberService memberService = new MemberServiceImpl();
        Member member = new  Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member : " + member.getName());
        System.out.println("findMember : " + findMember.getName());

    }
}
    