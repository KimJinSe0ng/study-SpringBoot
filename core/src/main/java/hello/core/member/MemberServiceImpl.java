package hello.core.member;

public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository; //문제점: 추상화와 구현체에 둘 다 의존한다.

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
