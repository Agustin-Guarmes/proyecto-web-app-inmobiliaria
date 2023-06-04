package Inmobilaria.GyL.repository;

import Inmobilaria.GyL.entity.DayPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayPlanRepository extends JpaRepository<DayPlan, Long> {

    List<DayPlan> findAllByProperty(Long id);

    @Query("SELECT d FROM DayPlan d " +
            "WHERE (d.property.user.id = ?1) AND (d.isActive = true)" +
            "ORDER BY d.property.id ASC, d.timetableDay ASC, d.start ASC")
    List<DayPlan> findAllByUser(Long id);
}
