package org.sun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Created by SongX on 2017/10/22.
 */
/*@Configuration
@EnableAutoConfiguration
@ComponentScan*/
//@EnableWebSecurity
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}