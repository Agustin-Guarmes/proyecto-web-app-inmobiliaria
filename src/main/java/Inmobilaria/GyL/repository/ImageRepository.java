package Inmobilaria.GyL.repository;

import Inmobilaria.GyL.entity.ImageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageUser,String>{
    
}
