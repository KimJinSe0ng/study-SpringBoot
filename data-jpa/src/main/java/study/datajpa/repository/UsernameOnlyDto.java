package study.datajpa.repository;

public class UsernameOnlyDto {

    private final String username;
    public UsernameOnlyDto(String username) { //생성자 이름 중요: 생성자의 파라미터 이름으로 매칭시켜 프로젝션 함
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
}
