package study.querydsl.dto;

import lombok.Data;

@Data
public class MemberSearchCondition {
    //회원명, 팀원, 나이(ageOge, ageLoe)

    private String username;
    private String teamName;
    private Integer ageGoe; //크거나
    private Integer ageLoe; //작거나
}
