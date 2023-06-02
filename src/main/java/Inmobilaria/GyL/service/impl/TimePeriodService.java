package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.TimePeriod;
import Inmobilaria.GyL.repository.TimePeriodRepository;
import Inmobilaria.GyL.service.IPropertyService;
import Inmobilaria.GyL.service.ITimePeriodService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimePeriodService implements ITimePeriodService {

    private final TimePeriodRepository timePeriodRepository;

    private final IPropertyService propertyService;

    public TimePeriodService(TimePeriodRepository timePeriodRepository, IPropertyService propertyService) {
        this.timePeriodRepository = timePeriodRepository;
        this.propertyService = propertyService;
    }

    private List<TimePeriod> findAllPeriodsByProperty(Property property, LocalDate date) {
        return timePeriodRepository.findAllByProperty(property.getId());
    }

    private List<TimePeriod> findAllAvailablePeriodsByProperty(Property property, LocalDate date) {
        return timePeriodRepository.findAllByProperty(property.getId());
    }

    @Override
    public List<TimePeriod> findAllTimePeriodsAvailableByDayAndProperty(Long id, LocalDate date) {
        List<TimePeriod> timePeriodsAvailable = timePeriodRepository.findAllTimePeriodsAvailableByDayAndProperty(id, date);
        return timePeriodsAvailable;
    }

    /*Calcula los turnos disponibles a partir de la disponibilidad ingresada por el Ente*/
    @Override
    public List<TimePeriod> saveAvailablePeriods(DayPlan dayPlan, Long id) {
        ArrayList<TimePeriod> availablePeriods = new ArrayList<>();
        Property property = propertyService.findById(id);
        TimePeriod availablePeriod = new TimePeriod(dayPlan.getStart(), dayPlan.getStart().plusMinutes(property.getDuration()), property);
        while (availablePeriod.getEnd().isBefore(dayPlan.getEnd()) || availablePeriod.getEnd().equals(dayPlan.getEnd())) {
            availablePeriods.add(new TimePeriod(availablePeriod.getStart(), availablePeriod.getStart().plusMinutes(property.getDuration()), property));
            timePeriodRepository.save(new TimePeriod(availablePeriod.getStart(), availablePeriod.getStart().plusMinutes(property.getDuration()), property));
            availablePeriod.setStart(availablePeriod.getEnd());
            availablePeriod.setEnd(availablePeriod.getEnd().plusMinutes(property.getDuration()));
        }
        return availablePeriods;
    }
}