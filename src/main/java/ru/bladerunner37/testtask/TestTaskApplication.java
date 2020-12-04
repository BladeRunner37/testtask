package ru.bladerunner37.testtask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.bladerunner37.testtask.service.Merger;
import ru.bladerunner37.testtask.service.SerializeService;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class TestTaskApplication implements CommandLineRunner {

    private final SerializeService serializeService;
    private final Merger merger;

    public static void main(String[] args) {
        SpringApplication.run(TestTaskApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Application started");
        if (args.length != 2) {
            String msg = "Wrong arguments, expected 2, was: " + args.length;
            log.error(msg);
            System.out.println(msg);
            return;
        }
        String method = args[0];
        String path = args[1];
        try {
            switch (method) {
                case "serialize":
                    log.info("Start serialize to file: {}", path);
                    serializeService.serializeToFile(path);
                    break;
                case "deserialize":
                    log.info("Start deserialize from file: {}", path);
                    merger.merge(serializeService.deserializeFromFile(path));
                    break;
                default:
                    throw new RuntimeException("Unknown method: " + method);
            }
        } catch (Exception e) {
            log.error("Error while application running", e);
            System.out.println("Error while application running: ");
            e.printStackTrace();
        }
        log.info("Application finished");
    }
}
