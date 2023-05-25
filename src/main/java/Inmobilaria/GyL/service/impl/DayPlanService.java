package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.repository.DayPlanRepository;
import Inmobilaria.GyL.service.IDayPlanService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DayPlanService implements IDayPlanService {

    private final DayPlanRepository dayPlanRepository;


    public DayPlanService(DayPlanRepository dayPlanRepository) {
        this.dayPlanRepository = dayPlanRepository;
    }

    public DayPlan createDayPlan(LocalDate timetableDay, LocalTime starTime, LocalTime endTime, Property property) {
        DayPlan dayPlan = new DayPlan();
        dayPlan.setTimetableDay(timetableDay);
        dayPlan.setStart(starTime);
        dayPlan.setEnd(endTime);
        dayPlan.setProperty(property);
        return dayPlanRepository.save(dayPlan);
    }

    public List<DayPlan> findAllDayPlanByProperty(Property property) {
        return dayPlanRepository.findAllByProperty(property.getId());
    }

    public void deleteDayPlan(Long id) {
        dayPlanRepository.deleteById(id);
    }


}
