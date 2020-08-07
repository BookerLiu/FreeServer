package com.ff.postpone;

import com.ff.postpone.constant.Profile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PostponeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostponeApplication.class, args);
        System.out.println(Profile.MAIL_SERVER_HOST);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(PostponeApplication.class);
//    }

}
