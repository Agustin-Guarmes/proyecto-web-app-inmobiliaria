package Inmobilaria.GyL.repository;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.enums.PropertyStatus;
import Inmobilaria.GyL.enums.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("SELECT DISTINCT p FROM Property p INNER JOIN p.offers po WHERE po.property.user.id = ?1 OR (po.property.status ='FOR_RENT' AND po.user.id = ?1 AND po.offerStatus = 'CLIENT_ACCEPTED' AND po.property.isRented = true AND po.property.isActive = true)")
    List<Property> clientProperties(Long id);

    @Query("SELECT p FROM Property p WHERE p.user.role = 'ENTITY' AND p.isRented = false AND p.isActive = true")
    List<Property> findAllEntity();

    @Query("UPDATE Property p SET p.isActive = ?2 WHERE p.id = ?1")
    void deactivateProperty(Long id, boolean isActive);

    @Query("SELECT p FROM Property p WHERE p.user.id != ?1 AND p.isRented = false AND p.user.role = 'ENTITY' AND AND p.isActive = true")
    List<Property> filteredProperties(Long id);

    @Query("SELECT p FROM Property p WHERE p.user.id = ?1 AND AND p.isActive = true")
    List<Property> findByUser(Long id);

    @Query("SELECT p FROM Property p WHERE p.type = ?1")
    List<Property> findByType(PropertyType type);

    @Query("SELECT p FROM Property p WHERE p.surface >= ?1")
    List<Property> findBySurfaceBigger(Integer surface);

    @Query("SELECT p FROM Property p WHERE p.surface <= ?1")
    List<Property> findBySurfaceMinor(Integer surface);

    @Query("SELECT p FROM Property p WHERE p.surface BETWEEN ?1 AND ?2")
    List<Property> findBySurfaceBetween(Integer low, Integer high);

    @Query("SELECT p FROM Property p WHERE p.location = ?1")
    List<Property> findByLocation(String location);

    @Query("SELECT p FROM Property p WHERE p.status = ?1")
    List<Property> findByStatus(PropertyStatus status);

    @Query("SELECT p FROM Property p WHERE p.price <= ?1")
    List<Property> findByPrice(Integer surface);

    @Query("SELECT a FROM Appointment a WHERE a.property.id = ?1")
    List<Appointment> findAllAppointmentsByProperty(Long id);

    @Query("SELECT d FROM DayPlan d WHERE d.property.id = ?1")
    List<DayPlan> findAllTimetableByProperty(Long id);

    @Query("SELECT p FROM Property p WHERE p.user.name like :word%")
    List<Property> findByUserName(@Param("word") String word);
}
