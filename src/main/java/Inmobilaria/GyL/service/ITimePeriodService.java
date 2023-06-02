package Inmobilaria.GyL.service;

import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.TimePeriod;

import java.time.LocalDate;
import java.util.List;

public interface ITimePeriodService {
    List<TimePeriod> findAllTimePeriodsAvailableByDayAndProperty(Long id, LocalDate date);

    List<TimePeriod> saveAvailablePeriods(DayPlan dayPlan, Long id);
}
