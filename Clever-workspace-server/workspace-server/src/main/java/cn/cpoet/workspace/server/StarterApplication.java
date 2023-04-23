package cn.cpoet.workspace.server;

import cn.cpoet.workspace.core.support.CustomAnnotationBeanNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * blog starter
 *
 * @author CPoet
 */
@SpringBootApplication(nameGenerator = CustomAnnotationBeanNameGenerator.class)
public class StarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class, args);
    }
}
