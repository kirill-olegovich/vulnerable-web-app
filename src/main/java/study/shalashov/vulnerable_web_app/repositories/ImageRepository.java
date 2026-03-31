package study.shalashov.vulnerable_web_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.shalashov.vulnerable_web_app.models.ImageMetadata;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<ImageMetadata, UUID> {
    Optional<ImageMetadata> findByTitle(String Title);
}
