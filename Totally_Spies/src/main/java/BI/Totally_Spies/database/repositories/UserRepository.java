package BI.Totally_Spies.database.repositories;

import BI.Totally_Spies.database.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

///
/// \class UserRepository
/// \brief Repository for user table.
///
public interface UserRepository extends JpaRepository<User, Integer> {
    ///
    /// Find a user thanks to it's name.
    /// @param username The user name.
    /// @return The user found (empty if no one found).
    ///
    @Query(" select u from User u " +
            " where u.username = ?1")
    Optional<User> findUserWithName(String username);
    ///
    /// Check if a user exist thanks to his name.
    /// @param username The user name.
    /// @return True if the user exist else false.
    ///
    Boolean existsByUsername(String username);
}