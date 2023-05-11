package Inmobilaria.GyL.repository;

import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.Enums.Status;
import Inmobilaria.GyL.Enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("SELECT p FROM Property p WHERE p.type = ?1")
    List<Property> findByType(Type type);

    @Query("SELECT p FROM Property p WHERE p.surface >= ?1")
    List<Property> findBySurfaceBigger(Integer surface);

    @Query("SELECT p FROM Property p WHERE p.surface <= ?1")
    List<Property> findBySurfaceMinor(Integer surface);

    @Query("SELECT p FROM Property p WHERE p.surface BETWEEN ?1 AND ?2")
    List<Property> findBySurfaceBetween(Integer low, Integer high);

    @Query("SELECT p FROM Property p WHERE p.location = ?1")
    List<Property> findByLocation(String location);

    @Query("SELECT p FROM Property p WHERE p.status = ?1")
    List<Property> findByStatus(Status status);

    @Query("SELECT p FROM Property p WHERE p.price <= ?1")
    List<Property> findByPrice(Integer surface);
}
