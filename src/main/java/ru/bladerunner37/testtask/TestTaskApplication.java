package ru.bladerunner37.testtask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.bladerunner37.testtask.service.Merger;
import ru.bladerunner37.testtask.service.SerializeService;

@SpringBootApplication
public class TestTaskApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TestTaskApplication.class);

    private final SerializeService serializeService;
    private final Merger merger;

    @Autowired
    public TestTaskApplication(SerializeService serializeService, Merger merger) {
        this.serializeService = serializeService;
        this.merger = merger;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestTaskApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LOG.info("Application started");
        if (args.length != 2) {
            String msg = "Wrong arguments, expected 2, was: " + args.length;
            LOG.error(msg);
            System.out.println(msg);
            return;
        }
        String method = args[0];
        String path = args[1];
        try {
            switch (method) {
                case "serialize":
                    LOG.info("Start serialize to file: {}", path);
                    serializeService.serializeToFile(path);
                    break;
                case "deserialize":
                    LOG.info("Start deserialize from file: {}", path);
                    merger.merge(serializeService.deserializeFromFile(path));
                    break;
                default:
                    throw new RuntimeException("Unknown method: " + method);
            }
        } catch (Exception e) {
            LOG.error("Error while application running", e);
            System.out.println("Error while application running: ");
            e.printStackTrace();
        }
        LOG.info("Application finished");
    }
}
