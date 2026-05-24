package study.shalashov.vulnerable_web_app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import study.shalashov.vulnerable_web_app.dtos.UploadImageRequest;
import study.shalashov.vulnerable_web_app.models.ImageMetadata;
import study.shalashov.vulnerable_web_app.models.MyUserDetails;
import study.shalashov.vulnerable_web_app.services.ImageService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Controller
@RequestMapping(path = "images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

//    @GetMapping("/")
//    public String getAllImages(
//            Model model
//    ) {
//        model.addAttribute("images", imageService.getAllImages());
//        return "imagesList";
//    }

    @GetMapping("/{id}")
    public String getImage(
            @PathVariable UUID id,
            Model model
    ) throws IOException {
        ImageMetadata imageMetadata = imageService.getImageMetadataById(id);
        Resource image = imageService.getImageFile(Path.of(imageMetadata.getPath()));
        model.addAttribute("metadata", imageMetadata);
        model.addAttribute("image", image);
        return "image";
    }

    @GetMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public String uploadImage(
            Model model
    ) {
        model.addAttribute("uploadImageRequest", new UploadImageRequest());
        return "upload";
    }

    @PostMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public String uploadImage(
//            @RequestPart("metadata") String title,
//            @RequestPart("image") MultipartFile file,
            @Valid @ModelAttribute UploadImageRequest uploadImageRequest,
            @AuthenticationPrincipal MyUserDetails userDetails,
            Model model
    ) throws IOException {
//        try {
            ImageMetadata imageMetadata = imageService.createNew(
                    userDetails, uploadImageRequest.getTitle(), uploadImageRequest.getImage()
            );
            Resource image = imageService.getImageFile(Path.of(imageMetadata.getPath()));
            model.addAttribute("metadata", imageMetadata);
            model.addAttribute("image", image);
            return "image";
//        } catch (Exception e) {
//            return "home";
//        }
    }



}
