package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)   // HTTP 요청 당 하나씩 생성, 끝나는 시점에서 소멸
// MyLogger의 가짜 프록시 객체 생성(요청이 오면 그때 내부에서 진짜 빈을 요청)
@Component
public class MyLogger {
    // 로그를 출력하기 위한 클래스

    private String uuid;
    private String requestURL;

    // requestURL은 이 빈이 생성되는 시점에는 알 수 없으므로, 외부에서 setter로 입력 받음
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);

    }

    @PostConstruct
    public void init() {
        // 생성되는 시점에 uuid 생성해서 저장
        uuid = UUID.randomUUID().toString();    // 다른 HTTP 요청과 구분 위해
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }

 }
