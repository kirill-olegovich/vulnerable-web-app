package study.shalashov.vulnerable_web_app.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import study.shalashov.vulnerable_web_app.models.ImageMetadata;
import study.shalashov.vulnerable_web_app.models.MyUserDetails;
import study.shalashov.vulnerable_web_app.repositories.ImageRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final FileService FileService;

    public List<ImageMetadata> getAllImages() {
        return imageRepository.findAll();
    }

    public ImageMetadata getImageMetadataById(UUID id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image metadata with id '" + id + "' does not exist!"));
    }

    public ImageMetadata getImageMetadataByTitle(String title) {
        return imageRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Image metadata with title '" + title + "' does not exist!"));
    }

    public Resource getImageFile(Path path) throws IOException {
        return FileService.get(path)
                .orElseThrow(() -> new EntityNotFoundException("Image file with path '" + path.toString() + "' does not exist!"));
    }

    public ImageMetadata createNew(MyUserDetails userDetails, String title, MultipartFile imageFile) throws IOException {
        FileService.validate(imageFile);

        String imagePath = FileService.store(imageFile.getInputStream(), imageFile.getName());
        ImageMetadata imageMetadata = ImageMetadata.builder()
                .title(title)
                .path(imagePath)
                .size(imageFile.getSize())
                .createdAt(Instant.now())
                .author(userDetails.getUser())
                .build();

        return imageRepository.save(imageMetadata);
    }

//    public void deleteImage(ImageMetadata imageMetadata) {
//        imageRepository.delete(imageMetadata);
//    }

//    public void deleteImageById(UUID id) {
//        ImageMetadata imageMetadata = getImageById(id);
//        deleteImage(imageMetadata);
//    }
//
//    public void deleteImageByTitle(String title) {
//        ImageMetadata imageMetadata = getImageByTitle(title);
//        deleteImage(imageMetadata);
//    }
}
