package study.wyy.security;

import com.spring4all.swagger.EnableSwagger2Doc;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan("study.wyy.security.mapper")
@SpringBootApplication
@EnableSwagger2Doc
@Slf4j
public class SecurityDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SecurityDemoApplication.class, args);
        log.info("Swagger-UI: http://127.0.0.1:{}/login.html",
                context.getEnvironment().getProperty("server.port", "8080"));
    }

}
