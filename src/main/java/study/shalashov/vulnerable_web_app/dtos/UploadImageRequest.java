package study.shalashov.vulnerable_web_app.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadImageRequest {
    @NotBlank
    @Size(min = 5, max = 50, message = "Title must be between {min} and {max} characters")
    private String title;

    @NotBlank
    private MultipartFile image;
}
