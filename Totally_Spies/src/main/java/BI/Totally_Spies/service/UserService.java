package BI.Totally_Spies.service;

import BI.Totally_Spies.database.models.User;
import BI.Totally_Spies.database.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

///
/// \class UserService
/// \brief The user service.
///
@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository; ///< The user repository.

    ///
    /// The constructor of the class.
    /// @param userRepository The user repository.
    ///
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    ///
    /// Load a user thanks to it's name.
    /// @param username The user name.
    /// @return The user details.
    ///
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Objects.requireNonNull(username);
        User user = userRepository.findUserWithName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user;
    }
}