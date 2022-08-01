package hello.core.member;

public class MemberServiceImpl implements MemberService{
// 구현체 하나만 있을 떄는 인터페이스명 뒤에 Impl이라고 씀

    // 인터페이스만 있으면 nullpointexception 발생함.. 구현객체를 선택해줘야함
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // join, findMember를 하려면 리포지토리를 가져와야함

    @Override
    public void join(Member member) {
        memberRepository.save(member);
        // join에서 save를 호출하면 다형성에 의해서 인터페이스가 아닌 MemoryMemberRepository가 호출됨
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
