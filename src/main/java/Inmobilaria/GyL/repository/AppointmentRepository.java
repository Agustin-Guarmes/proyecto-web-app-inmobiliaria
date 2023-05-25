package Inmobilaria.GyL.repository;

import Inmobilaria.GyL.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.property.user.id = ?1")
    List<Appointment> findAllByUserId(Long id);
}
