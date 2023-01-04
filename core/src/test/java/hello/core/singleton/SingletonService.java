package hello.core.singleton;

public class SingletonService {

    // 자기 자신을 내부의 private으로 static으로 가지고 있음. 클래스 레벨에 올라가기 때문에 딱 하나만 존재하게 됨.
    // private - 현재 클래스 내부에서만 사용할 수 있음, static - 객체 생성없이 클래스명으로 접근 가능

    // 1. static 영역에 객체를 딱 1개만 생성해둔다. => 자기 자신 생성해서 instance 참조에 넣어둠
    private static final SingletonService instance = new SingletonService();

    // 2. public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용한다.
    public static SingletonService getInstance(){
        return instance;
    }
    // => 항상 같은 인스턴스 반환

    // 3. 생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.
    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
