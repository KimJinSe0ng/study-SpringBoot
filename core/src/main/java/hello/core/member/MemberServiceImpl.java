package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository; //문제점: 추상화와 구현체에 둘 다 의존한다.

    @Autowired //ComponentScan를 쓰게 되면 내 빈이 자동으로 등록되는데 의존 관계를 설정할 방법이 없기 때문에 생성자에서 Autowired를 써서 자동으로 의존 관계를 주입하게 해준다.
    //자동으로 ac.getBean(MemberRepository.class);를 해주는 느낌
    public MemberServiceImpl(MemberRepository memberRepository) { //memberRepository의 구현체에 뭐가 들어갈지 생성자를 통해 주입해준다.
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
