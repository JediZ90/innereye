package cn.tm.ms.restful.service;

import org.innereye.ms.restful.annotation.RES;
import org.innereye.ms.restful.annotation.Serv;

@Serv
public class TestService {

    @RES
    public TestService testService;

    @RES
    public TestService testService1;

    public TestService service() {
        return testService;
    }
}
