package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;
//    private final ObjectProvider<MyLogger> myLoggerProvider;
    // MyLogger를 주입받는게 아니라, MyLogger를 찾을 수 있는 DL할 수 있는 애가 주입됨


    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {

//        MyLogger myLogger = myLoggerProvider.getObject();
        String requestURL = request.getRequestURL().toString();  // 고객이 어떤 url로 요청했는지 알 수 있음
        System.out.println("myLogger = " + myLogger.getClass());
        myLogger.setRequestURL(requestURL);
        // 실제 기능을 호출하는 시점에 진짜 myLogger를 찾아서 넣어줌

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }

}
