package hackerschool.website.user.repository;


import hackerschool.website.user.domain.Role;
import hackerschool.website.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query(value = "UPDATE users SET role = :newRole WHERE id = :userId", nativeQuery = true)
    void updateUserRole(@Param("userId") Long userId, @Param("newRole") Role newRole);




}
