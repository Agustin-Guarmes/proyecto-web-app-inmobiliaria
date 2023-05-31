package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.*;
import Inmobilaria.GyL.enums.AppointmentStatus;
import Inmobilaria.GyL.repository.AppointmentRepository;
import Inmobilaria.GyL.repository.TimePeriodRepository;
import Inmobilaria.GyL.service.IAppointmentService;
import Inmobilaria.GyL.service.IDayPlanService;
import Inmobilaria.GyL.service.IPropertyService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final IPropertyService propertyService;


    public AppointmentService(AppointmentRepository ar, IPropertyService ps, TimePeriodRepository timePeriodRepository) {
        this.appointmentRepository = ar;
        this.propertyService = ps;
    }

//    @Override
//    public void createAppointment(User client, Property property, Date appointmentDate, LocalTime start) {
////        if(isAvailable(property.getId(),0 )) {
//        Appointment appointment = new Appointment();
//        appointment.setAppointmentDate(appointmentDate);
//        appointment.setProperty(property);
//        appointment.setClient(client);
//        appointment.setStart(start);
//        appointmentRepository.save(appointment);
////        }
//    }

    @Override
    public void updateAppointment(Long id, LocalDate appointmentDate) {
        Optional<Appointment> response = appointmentRepository.findById(id);

        if (response.isPresent()) {
            Appointment appointment = response.get();
            appointment.setDate(appointmentDate);

            appointmentRepository.save(appointment);
        }
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> findAppointmentByUserId(Long id) {
        return appointmentRepository.findAllByUserId(id);
    }

    @Override
    public List<Appointment> findAllAvailableAppointmentsByDayAndProperty(Long id, LocalDate date) {
        List<Appointment> availableAppointments = appointmentRepository.findAllAvailableAppointmentsByDayAndProperty(id, date);
        return availableAppointments;
    }

    @Override
    public List<Appointment> saveAvailableAppointments(DayPlan dayPlan, Long id) {
        ArrayList<Appointment> availablePeriods = new ArrayList<>();
        Property property = propertyService.findById(id);
        Appointment availablePeriod = new Appointment(dayPlan.getTimetableDay().atTime(dayPlan.getStart()),
                dayPlan.getTimetableDay().atTime(dayPlan.getStart()).plusMinutes(property.getDuration()),
                dayPlan.getTimetableDay(),
                property,
                AppointmentStatus.AVAILABLE);
        while (availablePeriod.getEnd().toLocalTime().isBefore(dayPlan.getEnd()) ||
                availablePeriod.getEnd().toLocalTime().equals(dayPlan.getEnd())) {
            availablePeriods.add(new Appointment(availablePeriod.getStart(),
                    availablePeriod.getStart().plusMinutes(property.getDuration()),
                    dayPlan.getTimetableDay(),
                    property,
                    AppointmentStatus.AVAILABLE));
            appointmentRepository.save(new Appointment(availablePeriod.getStart(),
                    availablePeriod.getStart().plusMinutes(property.getDuration()),
                    dayPlan.getTimetableDay(),
                    property,
                    AppointmentStatus.AVAILABLE));
            availablePeriod.setStart(availablePeriod.getEnd());
            availablePeriod.setEnd(availablePeriod.getEnd().plusMinutes(property.getDuration()));
        }
        return availablePeriods;
    }

    @Override
    public Appointment bookAppointment(Long id, User client) {
        Appointment appointment = appointmentRepository.findById(id).get();
        appointment.setClient(client);
        appointment.setState(AppointmentStatus.BOOKED);
        appointmentRepository.save(appointment);
        return appointment;
    }
}
