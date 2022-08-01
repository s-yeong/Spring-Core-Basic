package hello.core.member;

import java.util.HashMap;
import java.util.Map;

// 인터페이스 implements 해주기
public class MemoryMemberRepository implements MemberRepository{
    // 저장소니까 Map이 있어야함
    private static Map<Long, Member> store = new HashMap<>();
    // key : Long, value : Member 사용

    @Override
    public void save(Member member) {
        // put은 인자로 key와 value를 받음
        store.put(member.getId(), member);

    }

    @Override
    public Member findById(Long memberId) {
        // get은 인자로 전달된 key에 해당하는 value 리턴
        return store.get(memberId);
    }





}
