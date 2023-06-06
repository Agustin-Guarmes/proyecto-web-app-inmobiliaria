package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.enums.AppointmentStatus;
import Inmobilaria.GyL.repository.AppointmentRepository;
import Inmobilaria.GyL.service.IAppointmentService;
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


    public AppointmentService(AppointmentRepository ar, IPropertyService ps) {
        this.appointmentRepository = ar;
        this.propertyService = ps;
    }

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
    public Appointment updateAppointment(Long id, AppointmentStatus appointmentStatus) {
        Appointment appointment = appointmentRepository.findById(id).get();
        if (!(appointment == null)) {
            appointment.setAppointmentStatus(appointmentStatus);
            appointmentRepository.save(appointment);
        }
        return appointment;
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> findAllBookedAppointmentByUser(Long id) {
        return appointmentRepository.findAllBookedByUser(id);
    }

    @Override
    public List<Appointment> findAllAvailableAppointmentsByDayAndProperty(Long id, LocalDate date) {
        List<Appointment> availableAppointments = appointmentRepository.findAllAvailableAppointmentsByDayAndProperty(id, date);
        return availableAppointments;
    }

    @Override
    public List<Appointment> saveAvailableAppointments(DayPlan dayPlan, Property property) {
        ArrayList<Appointment> availablePeriods = new ArrayList<>();
        Appointment availablePeriod = new Appointment(dayPlan.getTimetableDay(),
                property,
                AppointmentStatus.AVAILABLE,
                dayPlan.getTimetableDay().atTime(dayPlan.getStart()),
                dayPlan.getTimetableDay().atTime(dayPlan.getStart()).plusMinutes(property.getDuration()));

        while (availablePeriod.getEnd().toLocalTime().isBefore(dayPlan.getEnd()) ||
                availablePeriod.getEnd().toLocalTime().equals(dayPlan.getEnd())) {
            availablePeriods.add(new Appointment(dayPlan.getTimetableDay(),
                    property,
                    dayPlan,
                    AppointmentStatus.AVAILABLE,
                    availablePeriod.getStart(),
                    availablePeriod.getStart().plusMinutes(property.getDuration())));
            appointmentRepository.save(new Appointment(dayPlan.getTimetableDay(),
                    property,
                    dayPlan,
                    AppointmentStatus.AVAILABLE,
                    availablePeriod.getStart(),
                    availablePeriod.getStart().plusMinutes(property.getDuration())));
            availablePeriod.setStart(availablePeriod.getEnd());
            availablePeriod.setEnd(availablePeriod.getEnd().plusMinutes(property.getDuration()));
        }
        return availablePeriods;
    }

    @Override
    public Appointment bookAppointment(Long id, User client) {
        Appointment appointment = appointmentRepository.findById(id).get();
        List<Appointment> appointments = appointmentRepository.findAllByPropertyIdAndClientId(appointment.getProperty().getId(), client.getId());
        if (appointments.isEmpty()) {
            appointment.setClient(client);
            appointment.setAppointmentStatus(AppointmentStatus.BOOKED);
            appointmentRepository.save(appointment);
            return appointment;
        }
        return null;
    }

    @Override
    public List<Appointment> findAllByDayPlan(Long id) {
        return appointmentRepository.findAllByDayPlan_Id(id);
    }

    @Override
    public List<Appointment> findAllAppointmentByUser(Long id) {
        return appointmentRepository.findAllByProperty_User_Id(id);
    }

    @Override
    public void cancelAppointment(Long id, User user) {
        Appointment appointment = appointmentRepository.findById(id).get();
        if(appointment.getAppointmentStatus()!=AppointmentStatus.CANCELED && appointment.getProperty().getUser().equals(user)) {
            appointment.setAppointmentStatus(AppointmentStatus.CANCELED);
            appointmentRepository.save(appointment);
        }
    }

    @Override
    public List<Long> findAllUsersIdByProperty(Long id) {
        return appointmentRepository.findAllUsersIdByProperty(id);
    }
}