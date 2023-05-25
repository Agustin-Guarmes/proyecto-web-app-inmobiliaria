package Inmobilaria.GyL.repository;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.enums.PropertyStatus;
import Inmobilaria.GyL.enums.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("SELECT p FROM Property p WHERE p.user.id = ?1")
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

}
