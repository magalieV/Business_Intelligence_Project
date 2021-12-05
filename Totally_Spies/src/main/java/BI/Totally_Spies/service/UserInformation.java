package BI.Totally_Spies.service;

import BI.Totally_Spies.database.models.User;
import BI.Totally_Spies.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInformation {
    @Autowired
    UserRepository userRepository; ///< The user repository.

    ///
    /// Get the user information thanks to his authentication.
    /// @param principal Authentication for oauth2.
    /// @return The user found.
    ///
    public User getInformation(@AuthenticationPrincipal OAuth2User principal) {
        Optional<User> userInfo;
        try {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            String username = principal.getAttribute("name");
            if (this.userRepository.existsByUsername(username)) {
                userInfo = this.userRepository.findUserWithName(username);
                if (userInfo.isPresent()) {
                    return userInfo.get();
                }
            }
        }
        return new User();
    }
}