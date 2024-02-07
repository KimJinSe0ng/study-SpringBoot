package jpabook.jpashop2.api;

import jakarta.validation.Valid;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        /**
         * 만약에 회원에 대한 정보만 달라 했는데, 회원 주문 정보도 포함되어 넘어간다. 엔티티를 직접 노출하게 되면 외부에 다 노출이 된다.
         * 엔티티에 @JsonIgnore를 사용하면 프레젠테이션 계층 로직이 추가되고 이렇게 해야하는데 문제가 된다. 엔티티에서 의존관계가 나가버리는 것이 된다.
         */
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect); //배열을 감싸서 내보내주면 유연성이 생긴다.
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto { //내가 딱 노출할 것만 노출할 수 있음
        private String name;
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) { //@RequestBody는 json 데이터를 쫙 Member로 바꿔줌
        //엔티티를 바꿨다고 해서 API가 만들어 놓은 스펙이 바뀌는 것이 문제
        //API 스펙을 위한 DTO를 만들어야 한다.
        //엔티티를 외부에서 바인딩 받는데 쓰면 안 됨, 따라서 API 요청 스펙에 맞춰서 별도의 DTO를 파라미터로 받는게 좋다. -> v2
        //API를 만들 땐 항상 엔티티를 파라미터로 받지 마라. 엔티티를 외부애 노출하지도 마라.
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) { //멤버 엔티티 대신에 별도의 DTO를 받는 버전
        //요청이 온 것을 RequestBody로 CreateMemberRequest에 딱 바인딩이 되고,
        //엔티티와 프레젠테이션 계층을 위한 로직을 분리할 수 있음
        /**
         * 1번이 주는 유일한 장점은 CreateMemberRequest처럼 클래스를 만들지 않는 것. 과연 장점인가? 바꾸면 다 바뀜
         * 2번의 장점은 엔티티를 바꿔도 API 스펙이 안 바뀜. 예를 들어, 멤버 엔티티를 바꿨을 때(name -> userName) 그러면 다 오류가 남
         * Member의 파라미터가 API에 뭐가 들어올지 모르는데, API 스펙이 name(CreateMemberRequest)만 있구나. 하고 알기 쉬움
         * 엔티티와 API 스펙 명확하게 분리할 수 있고 엔티티가 변경이 되어도 API 스펙이 변하지 않는 장점이 있다.
         */
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {
        //업데이트용 DTO를 별도로 만들었다. 등록이랑 수정은 API 스펙이 다르다. 수정은 굉장히 제한적이다.
        memberService.update(id, request.getName()); //수정은 변경감지!
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest { //DTO는 데이터로 왔다 갔다 하기 때문에 롬복 애너테이션 활용 많이 함, 엔티티는 게터만
        private String name;
    }

    @Data
    @AllArgsConstructor //모든 파라미터를 넘겨야 하는 생성자
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }
    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
