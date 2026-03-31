package study.shalashov.vulnerable_web_app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${app.upload.dir}")
    private final String rootDir;

    public void validate(MultipartFile image) {
        if (image.isEmpty() || !Objects.equals(image.getContentType(), "image/jpeg")) {
            throw new IllegalArgumentException("Bad file");
        }
    }

    private String getExt(String name) {
        int lastDot = name.lastIndexOf(".");
        return lastDot == -1 ? "" : name.substring(lastDot + 1);
    }

    public String store(InputStream inputStream, String originalImageName) throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();
        Path dirPath = Path.of(rootDir).resolve(localDateTime.getYear() + File.separator +
                String.format("%02d", localDateTime.getMonthValue()) + File.separator +
                String.format("%02d", localDateTime.getDayOfMonth())
        );
        Files.createDirectories(dirPath);
        String ext = getExt(originalImageName);
        String newImageName = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);
        Path fullPath = dirPath.resolve(newImageName);
        Files.copy(inputStream, fullPath, StandardCopyOption.REPLACE_EXISTING);
        return Path.of(rootDir).relativize(fullPath).toString();
    }

    public Optional<Resource> get(Path filePath) throws IOException {
        Path fullFilePath = Path.of(rootDir).resolve(filePath).normalize().toAbsolutePath();
        Path rootPath = Path.of(rootDir).normalize().toAbsolutePath();

        if (!fullFilePath.startsWith(rootPath)) {
            throw new SecurityException("Access denied");
        }

        if (!Files.exists(fullFilePath)) {
            throw new FileNotFoundException("File not found");
        }

        return Optional.of(new UrlResource(fullFilePath.toUri()));
    }
}
