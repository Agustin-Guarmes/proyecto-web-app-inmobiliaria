package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.repository.DayPlanRepository;
import Inmobilaria.GyL.service.IDayPlanService;
import Inmobilaria.GyL.service.IPropertyService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DayPlanService implements IDayPlanService {

    private final DayPlanRepository dayPlanRepository;

    private final IPropertyService propertyService;

    public DayPlanService(DayPlanRepository dayPlanRepository, IPropertyService propertyService) {
        this.dayPlanRepository = dayPlanRepository;
        this.propertyService = propertyService;
    }

    @Override
    public DayPlan addDayPlan(Long propertyId, LocalDate timetableDay, LocalTime start, LocalTime end) {
        DayPlan dayPlan = new DayPlan();
        Property property = propertyService.findById(propertyId);
        dayPlan.setTimetableDay(timetableDay);
        dayPlan.setStart(start);
        dayPlan.setEnd(end);
        dayPlan.setProperty(property);
        return dayPlanRepository.save(dayPlan);
    }

    @Override
    public List<DayPlan> findAllDayPlanByProperty(Long id) {
        return dayPlanRepository.findAllByProperty(id);
    }

    @Override
    public void deleteDayPlan(Long id) {
        dayPlanRepository.deleteById(id);
    }

}
