package Inmobilaria.GyL.repository;

import Inmobilaria.GyL.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
    
    @Query("SELECT u FROM User u WHERE u.name like :word%")
    List<User> findByName(@Param("word")String word);
}
