package de.workshops.bookshelf.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends org.springframework.data.repository.Repository<User, Long> {

    Optional<User> findByUsername(String username);

    User save(User user);

    void delete(User user);
}
