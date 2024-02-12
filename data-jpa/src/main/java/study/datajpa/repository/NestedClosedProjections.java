package study.datajpa.repository;

public interface NestedClosedProjections {
    String getUsername(); //중첩 구조에서 첫 번째 있는 건 최적화가 되지만 두 번째 부터는 엔티티 다 끌고 옴
    TeamInfo getTeam();

    interface TeamInfo {
        String getName();
    }
}
