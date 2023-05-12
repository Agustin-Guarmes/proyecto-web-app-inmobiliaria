package Inmobilaria.GyL.Repositories;

import Inmobilaria.GyL.Entities.ImageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageUser,String>{
    
}
