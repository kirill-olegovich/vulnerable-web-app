package study.shalashov.vulnerable_web_app.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = uploadDir;

        if (!location.startsWith("file:")) {
            location = "file:" + location;
        }

        if (!location.endsWith("/")) {
            location = location + "/";
        }

        registry
            .addResourceHandler("/images/**")
            .addResourceLocations(location);
    }
}