package com.cloakyloki;

import com.cloakyloki.config.RepositoryConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {

    public static void main(String[] args) {

        try (var context = new AnnotationConfigApplicationContext()) {
            context.register(RepositoryConfig.class);
            context.refresh();
        }
    }
}
