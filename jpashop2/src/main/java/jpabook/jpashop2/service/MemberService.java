package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.repository.MemberRepository;
import jpabook.jpashop2.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //JPA의 모든 데이터 변경이나 로직들은 트랜잭션 안에서 실행되어야 함, 클래스 레벨에서 사용하면 public 메서드들은 기본적으로 트랜잭션이 걸림
//readOnly = true 는 JPA가 조회하는 곳에서 성능을 최적화함, 읽기 전용이면 DB한테 리소스 너무 많이 쓰지마라고 해줌, 기본적으로 readOnly를 가져가지만 따로 설정한 것은 우선권을 가져 감
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입(쓰기) : 가입에는 readOnly = true 하면 변경이 안 됨
     */
    @Transactional //수동 설정은 우선권을 가져 감
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //멤버A가 둘이 동시에 DB 인서트하게 되면, validateDuplicateMember를 둘이 동시에 호출할 수 있는데 문제가 될 수 있음
        //-> 실무에서 멀티쓰레드나 이런 상황을 고려해서 DB의 멤버 이름을 유니크 제약 조건으로 잡는 것을 권장
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회(읽기)
     */
//    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 한 명 조회
     */
//    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);
        //커맨드랑 쿼리를 철저히 분리
        //반환하더라도 업데이트는 id정도만 반환해 줌
    }

//    @Transactional
//    public Member update(Long id, String name) {
//        Member member = memberRepository.findOne(id); //얘에서 id를 조회해서 Member를 조회하는 꼴이 된다. 쿼리랑 커맨드가 같이 있는 꼴이 됨
//        member.setName(name);
//        //return을 Member해도 되는데, 반환하면 영속성 끊긴 Member를 넘기게 되는데..
//        //커맨드랑 쿼리를 철저히 분리
//        //Member를 반환하면 업데이트를 하면서 결국 멤버를 쿼리하는 꼴이 되는데 커맨드 업데이트라는 건 엔티티를 변경하는 변경성 메서드인데
//        return member;
//    }
}
