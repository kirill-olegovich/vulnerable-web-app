package study.shalashov.vulnerable_web_app.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class ImageDirectoryConfig {
    @Value("${app.upload.dir}")
    private String uploadDir;

    @Bean
    CommandLineRunner init() {
        return args -> {
            try {
                Path path = Path.of(uploadDir);
                Files.createDirectories(path);
                System.out.println("Image directory initialized at: " + path.toAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize image directory", e);
            }
        };
    }
}
