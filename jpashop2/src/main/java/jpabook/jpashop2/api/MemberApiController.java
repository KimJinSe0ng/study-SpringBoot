package jpabook.jpashop2.api;

import jakarta.validation.Valid;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

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
