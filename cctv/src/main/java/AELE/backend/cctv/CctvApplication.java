package AELE.backend.cctv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CctvApplication { public static void main(String[] args) {SpringApplication.run(CctvApplication.class, args);}}
