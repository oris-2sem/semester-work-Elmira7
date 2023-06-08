package ru.itis.sem_1.security.detail;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.sem_1.repository.UserRepository;


@Component
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new UserDetailImpl(userRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("Email " + email + " not found")));
    }
}

