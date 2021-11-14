package BI.Totally_Spies.security;

import BI.Totally_Spies.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AppAuthProvider extends DaoAuthenticationProvider {

    @Autowired
    UserService userDetailsService; ///< The user detail service.
    @Autowired
    PasswordEncoder encoder; ///< The password encoder.

    ///
    /// Authenticate the user.
    /// @param authentication The user authentication values.
    /// @return The authentication token.
    ///
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails user;
        try {
            UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

            String name = auth.getName();
            String password = auth.getCredentials()
                    .toString();
            user = userDetailsService.loadUserByUsername(name);
            if (user == null || !encoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
            }
        } catch (Exception e){
            user = userDetailsService.loadUserByUsername(authentication.getName());;
        }
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    ///
    /// Support authentication
    /// @param authentication The user authentication.
    /// @return Always true.
    ///
    @Override
    public boolean supports(Class<?> authentication) {
        return true;

    }
}