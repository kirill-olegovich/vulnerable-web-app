package study.shalashov.vulnerable_web_app.services;

import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.shalashov.vulnerable_web_app.models.MyUserDetails;
import study.shalashov.vulnerable_web_app.models.User;
import study.shalashov.vulnerable_web_app.repositories.UserRepository;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username '" + username + "' does not exist!")
        );
        return new MyUserDetails(user);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Boolean userExistsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
