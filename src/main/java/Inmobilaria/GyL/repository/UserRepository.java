package Inmobilaria.GyL.repository;

import Inmobilaria.GyL.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.name like :word%")
    List<User> findByName(@Param("word") String word);

    @Query("SELECT u FROM User u WHERE u.dni = :dni")
    User findByDni(@Param("dni") Long dni);
}
