package de.workshops.bookshelf.config;

import de.workshops.bookshelf.user.User;
import de.workshops.bookshelf.user.UserRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@NullMarked
public class UserService implements UserDetailsService {

    private final UserRepository users;

    public UserService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.findByUsername(username)
                .map(this::mapUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User not found, username: " + username));
    }

    private UserDetails mapUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole())
                .build();
    }
}
