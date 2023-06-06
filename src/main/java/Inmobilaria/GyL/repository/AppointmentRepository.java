package Inmobilaria.GyL.repository;

import Inmobilaria.GyL.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE (a.property.user.id = ?1) AND (a.client is not null)")
    List<Appointment> findAllBookedByUser(Long id);

    @Query("SELECT a FROM Appointment a " +
            "WHERE (a.property.id = :id) " +
            "AND ( a.date = DATE(:date)) " +
            "AND (a.client is null) " +
            "ORDER BY a.start")
    List<Appointment> findAllAvailableAppointmentsByDayAndProperty(@Param("id") Long id, @Param("date") LocalDate date);

    List<Appointment> findAllByDayPlan_Id(Long id);

    List<Appointment> findAllByPropertyIdAndClientId(Long propertyId, Long clientId);

    List<Appointment> findAllByProperty_User_Id(Long id);

    @Query("SELECT a.client.id " +
            "FROM Appointment a " +
            "WHERE (a.property.id = :id) ")
    List<Long> findAllUsersIdByProperty(@Param("id") Long id);
}
