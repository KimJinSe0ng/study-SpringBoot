package study.querydsl.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.QMemberTeamDto;
import study.querydsl.entity.Member;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberTeamDto> search(MemberSearchCondition condition) {
        return queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .fetch();
    }

    @Override
    public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
        QueryResults<MemberTeamDto> results = queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults(); //fetch() 쓰면 반환타입이 데이터 컨텐츠를 바로 가져옴 List<MemberTeamDto>
                //fetchResults() 쓰면 컨텐츠용 쿼리도 날리고, 카운트용 쿼리도 날려서 2번 나림

        List<MemberTeamDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamDto> content = queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); //fetch() 쓰면 반환타입이 데이터 컨텐츠만 바로 가져옴 List<MemberTeamDto>
                //fetchResults() 쓰면 컨텐츠용 쿼리도 날리고, 카운트용 쿼리도 날려서 2번 나림

        //첫 번째는 알아서 카운트 쿼리 날리고, 두 번째는 내가 직접 카운트 쿼리 날림
        //토탈 카운트용 쿼리를 따로 만드는 것이 차이점
//        long total = queryFactory
//                .select(member)
//                .from(member)
//                .leftJoin(member.team, team) //어떤 상황에는 조인이 필요 없을 때도 있고, 컨텐츠 쿼리는 복잡한테 카운트 쿼리는 심플하게 만들 수 있을 때
//                //fetchResults()를 쓰면 join, where 다 붙어서 최적화가 불가능, 반대로 카운트 쿼리 먼저 후 조회되는게 없으면 컨텐츠 쿼리를 하지 않는 식으로 설계도 가능
//                .where(
//                        usernameEq(condition.getUsername()),
//                        teamNameEq(condition.getTeamName()),
//                        ageGoe(condition.getAgeGoe()),
//                        ageLoe(condition.getAgeLoe())
//                )
//                .fetchCount();

        JPAQuery<Member> countQuery = queryFactory
                .select(member)
                .from(member)
                .leftJoin(member.team, team) //어떤 상황에는 조인이 필요 없을 때도 있고, 컨텐츠 쿼리는 복잡한테 카운트 쿼리는 심플하게 만들 수 있을 때
                //fetchResults()를 쓰면 join, where 다 붙어서 최적화가 불가능, 반대로 카운트 쿼리 먼저 후 조회되는게 없으면 컨텐츠 쿼리를 하지 않는 식으로 설계도 가능
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                ); //countQuery 는 fetchCount()를 호출해야 카운트를 구함, 최적화하기 위해 여기서 호출하지 않음

//        List<MemberTeamDto> content = results.getResults();
//        long total = results.getTotal();

//        return new PageImpl<>(content, pageable, total);
        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
        //countQuery.fetchCount() 는 함수이기 때문에 컨텐츠가 작거나, 마지막 페이지인 경우 countQuery.fetchCount() 를 호출하지 않음
    }

    private BooleanExpression ageBetween(int ageLoe, int ageGoe) {
        return ageGoe(ageLoe).and(ageGoe(ageGoe));
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? member.username.eq(username) : null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? team.name.eq(teamName) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }
}
