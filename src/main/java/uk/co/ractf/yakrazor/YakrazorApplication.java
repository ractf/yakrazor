package uk.co.ractf.yakrazor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("uk.co.ractf.yakrazor.persistence.repository")
@EntityScan("uk.co.ractf.yakrazor.persistence.model")
@SpringBootApplication
public class YakrazorApplication {

    public static void main(final String[] args) {
        SpringApplication.run(YakrazorApplication.class, args);
    }

}
