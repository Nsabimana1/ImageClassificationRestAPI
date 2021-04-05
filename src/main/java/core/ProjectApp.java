package core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class ProjectApp {
    public static void main(String[] args) {
        SpringApplication.run(ProjectApp.class, args);

//        SpringApplication app = new SpringApplication(ProjectApp.class);
//        app.setDefaultProperties(Collections
//                .singletonMap("server.port", "8888"));
//        app.run(args);


    }
}
