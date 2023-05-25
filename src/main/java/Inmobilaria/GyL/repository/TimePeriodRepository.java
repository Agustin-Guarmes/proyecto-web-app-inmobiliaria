package Inmobilaria.GyL.repository;

import Inmobilaria.GyL.entity.TimePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimePeriodRepository extends JpaRepository<TimePeriod, Long> {
    List<TimePeriod> findAllByProperty(Long id);

    @Query("SELECT t " + "FROM TimePeriod t " + "WHERE (t.property.id = :id) AND (DATE(t.start) = DATE(:date)) AND (t.appointment is null)")
    List<TimePeriod> findAllTimePeriodsAvailableByDayAndProperty(@Param("id") Long id, @Param("date") LocalDate date);

    @Query("SELECT t " + "FROM TimePeriod t " + "WHERE (t.property.id = :id) AND (DATE(t.start) = DATE(:date))")
    List<TimePeriod> findAllTimePeriodsByDayAndProperty(@Param("id") Long id, @Param("date") LocalDate date);


}
