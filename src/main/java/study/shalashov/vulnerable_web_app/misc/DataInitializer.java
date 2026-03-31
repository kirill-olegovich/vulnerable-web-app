package study.shalashov.vulnerable_web_app.misc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import study.shalashov.vulnerable_web_app.models.User;
import study.shalashov.vulnerable_web_app.services.MyUserDetailsService;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        User user = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin"))
//                .build();
//        myUserDetailsService.createUser(user);
    }
}
