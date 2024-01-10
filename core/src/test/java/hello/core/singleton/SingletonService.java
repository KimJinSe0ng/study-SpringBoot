package hello.core.singleton;

public class SingletonService {
    /**
     * 싱글톤 패턴
     * 클래스의 인스턴스가 딱 1개만 생성되는 패턴
     * 그래서 객체 인스턴스를 2개 이상 생성하지 못하도록 막아야 한다.
     * private 생성자를 사용해서 외부에서 임의로 new 키워드를 사용하지 못하도록 막아야 한다.
     */
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
