package BI.Totally_Spies.database.models;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

///
/// \class User
/// \brief The user sql class.
///
@Entity
@Table(name = "USER")
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId; ///< The user id.
    private String username; ///< The user name.
    private String password; ///< The user password.
    private String lastname;
    private String rsId;

    public String getRsId() {return rsId;}
    public void setRsId(String rsId) {this.rsId = rsId;}
    public String getLastname() {return lastname;}
    public void setLastname(String lastname) {this.lastname = lastname;}

    ///
    /// Get the user id.
    /// @return The user id.
    ///
    public Integer getUserId() {
        return userId;
    }
    ///
    /// Set the user id.
    /// @param userId The new user id.
    ///
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    ///
    /// Get the user name.
    /// @return The user name.
    ///
    public String getUsername() {
        return username;
    }
    ///
    /// Check if the account is not expired.
    /// @return Always false.
    ///
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    ///
    /// Check if the account is not locked.
    /// @return Always false.
    ///
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    ///
    /// Check if the credential is expired.
    /// @return Always false.
    ///
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    ///
    /// Check if teh user is enable.
    /// @return Always false.
    ///
    @Override
    public boolean isEnabled() {
        return false;
    }
    ///
    /// Set the username
    /// @param username The new user name.
    ///
    public void setUsername(String username) {
        this.username = username;
    }
    ///
    /// Get the authorities.
    /// @return The authorities.
    ///
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    ///
    /// Get the password.
    /// @return The user password.
    ///
    public String getPassword() {
        return password;
    }
    ///
    /// Set the password.
    /// @param password The new password.
    ///
    public void setPassword(String password) {
        this.password = password;
    }
}