package BI.Totally_Spies.security;

import BI.Totally_Spies.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

@EnableWebSecurity
public class Settings extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userDetailsService; ///< The user detail service.

    ///
    /// Configure the authentication manager.
    /// @param auth The authentication
    ///
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    ///
    /// Set up the entry point of the application if the user try to access a page that is reserved.
    /// @return The new entry point.
    ///
    @Bean
    public AuthenticationEntryPoint delegatingEntryPoint() {
        final LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> map = new LinkedHashMap();
        map.put(new AntPathRequestMatcher("/"), new LoginUrlAuthenticationEntryPoint("/signin"));

        final DelegatingAuthenticationEntryPoint entryPoint = new DelegatingAuthenticationEntryPoint(map);
        entryPoint.setDefaultEntryPoint(new LoginUrlAuthenticationEntryPoint("/signin"));
        return entryPoint;
    }

    ///
    /// Configure the security of the web page
    /// @param http The http value.
    ///
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO Remove service path from permit all
        http.csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(delegatingEntryPoint())
                .and()
                .authenticationProvider(getProvider())
                .formLogin()
                .loginPage("/login")
                .successHandler(new AuthentificationLoginSuccessHandler())
                .failureHandler(new AuthentificationLoginFailureHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new AuthentificationLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/main").permitAll()
                .antMatchers("/profile").authenticated()
                .antMatchers("/oauth/register").authenticated()
                .antMatchers("/services",
                        "/service-card/*",
                        "/service-card.html",
                        "/widgets").permitAll()
                .anyRequest().permitAll();
    }

    ///
    /// Redirect when the login is successful.
    ///
    private class AuthentificationLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {
            redirectStrategy.sendRedirect(request, response, "/main");
        }
    }

    ///
    /// Redirect when the login failed.
    ///
    private class AuthentificationLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        @Override
        public void onAuthenticationFailure(javax.servlet.http.HttpServletRequest request,
                                            javax.servlet.http.HttpServletResponse response,
                                            AuthenticationException exception) throws IOException, ServletException {
            redirectStrategy.sendRedirect(request, response, "/");
        }
    }

    ///
    /// Redirect when the logout is successful.
    ///
    private class AuthentificationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                    Authentication authentication) throws IOException, ServletException {
            redirectStrategy.sendRedirect(request, response, "/");
        }
    }

    ///
    /// Bean for the authentication provider
    /// @return The new authentication provider.
    ///
    @Bean
    public AuthenticationProvider getProvider() {

        AppAuthProvider provider = new AppAuthProvider();
        provider.setUserDetailsService(userDetailsService);
        return provider;

    }

    ///
    /// Bean for the password encoder.
    /// @return The new password encoder.
    ///
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}