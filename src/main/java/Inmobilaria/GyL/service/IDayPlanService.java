package Inmobilaria.GyL.service;

import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IDayPlanService {
    DayPlan createDayPlan(LocalDate timetableDay, LocalTime starTime, LocalTime endTime, Property property);

    List<DayPlan> findAllDayPlanByProperty(Property property);

    void deleteDayPlan(Long id);
}
