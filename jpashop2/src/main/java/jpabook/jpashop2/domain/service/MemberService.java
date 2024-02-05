package jpabook.jpashop2.domain.service;

import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        return memberRepository.findOne(memberId);
    }
}
