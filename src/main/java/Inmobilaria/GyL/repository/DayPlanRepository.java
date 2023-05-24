package Inmobilaria.GyL.repository;

import Inmobilaria.GyL.entity.DayPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayPlanRepository extends JpaRepository<DayPlan, Long> {
    List<DayPlan> findAllByProperty(Long id);
}
