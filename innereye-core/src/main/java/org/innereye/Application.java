package org.innereye;

import org.innereye.core.IEDataContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addListeners(new StartedEventListener());
        IEDataContext.setApplicationContext(springApplication.run(args));
    }
}
