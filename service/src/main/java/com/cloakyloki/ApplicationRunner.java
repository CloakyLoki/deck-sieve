package com.cloakyloki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationRunner {
    //    implements CommandLineRunner
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }


//    @Override
//    public void run(String... args) throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        AddCardId testcards = objectMapper.readValue(Paths.get("C:","Users", "Andruha", "IdeaProjects", "deck-sieve", "service", "src", "main",
//                        "resources", "json", "card-example.json")
//                .toFile(), AddCardId.class);
//        System.out.println();
//
//
//    }
}