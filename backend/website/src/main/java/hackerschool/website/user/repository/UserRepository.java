package hackerschool.website.user.repository;

import hackerschool.website.user.domain.Member;
import hackerschool.website.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);

	List<User> findAllByOrderByNameAsc();

	@Query("select u from User u where type(u) = :type order by u.name")
	List<User> findAllByType(@Param("type") Class<? extends User> type);

	@Query("select m from Member m where m.istId = :istId")
	Optional<Member> findMemberByIstId(@Param("istId") String istId);

}
