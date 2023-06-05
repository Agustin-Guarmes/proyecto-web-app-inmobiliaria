package Inmobilaria.GyL.service;

import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IDayPlanService {

    DayPlan addDayPlan(Long propertyId, LocalDate timetableDay, LocalTime start, LocalTime end);

    List<DayPlan> findAllDayPlanByProperty(Long id);

    List<DayPlan> findAllDayPlanByUser(Long id);

    void deleteDayPlan(Long id, User user);
}
