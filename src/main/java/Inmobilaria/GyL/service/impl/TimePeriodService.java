package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.TimePeriod;
import Inmobilaria.GyL.repository.TimePeriodRepository;
import Inmobilaria.GyL.service.ITimePeriodService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimePeriodService implements ITimePeriodService {

    private final TimePeriodRepository timePeriodRepository;

    public TimePeriodService(TimePeriodRepository timePeriodRepository) {
        this.timePeriodRepository = timePeriodRepository;
    }

    private List<TimePeriod> findAllPeriodsByProperty(Property property, LocalDate date) {
        return timePeriodRepository.findAllByProperty(property.getId());
    }

    private List<TimePeriod> findAllAvailablePeriodsByProperty(Property property, LocalDate date) {
        return timePeriodRepository.findAllByProperty(property.getId());
    }

    public List<TimePeriod> findAllTimePeriodsAvailableByDayAndProperty(Long id, LocalDate date) {
        List<TimePeriod> timePeriodsAvailable = timePeriodRepository.findAllTimePeriodsAvailableByDayAndProperty(id, date);
        return timePeriodsAvailable;
    }

    /*Calcula los turnos disponibles a partir de la disponibilidad ingresada por el Ente*/
    private List<TimePeriod> saveAvailablePeriods(List<DayPlan> availableDayPlan, Property property) {
        ArrayList<TimePeriod> availablePeriods = new ArrayList<>();
        for(DayPlan period: availableDayPlan)  {
            TimePeriod availablePeriod = new TimePeriod(period.getStart(), period.getStart().plusMinutes(property.getDuration()), property);
            while (availablePeriod.getEnd().isBefore(period.getEnd()) ||
                    availablePeriod.getEnd().equals(period.getEnd())) {
                availablePeriods.add(availablePeriod);
                timePeriodRepository.save(availablePeriod);
                availablePeriod.setStart(availablePeriod.getEnd());
                availablePeriod.setEnd(availablePeriod.getEnd().plusMinutes(property.getDuration()));
            }
        }
        return availablePeriods;
    }
}