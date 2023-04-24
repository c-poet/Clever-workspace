package cn.cpoet.workspace.server;

import cn.cpoet.workspace.core.support.AppBootstrap;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author CPoet
 */
@SpringBootApplication
public class StarterApplication {
    public static void main(String[] args) {
        AppBootstrap.run(StarterApplication.class, args);
    }
}
