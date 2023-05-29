package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.repository.AppointmentRepository;
import Inmobilaria.GyL.service.IAppointmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService implements IAppointmentService {

    private final AppointmentRepository ar;
    private final PropertyService ps;

    private final DayPlanService dps;

    public AppointmentService(AppointmentRepository ar, PropertyService ps, DayPlanService dps) {
        this.ar = ar;
        this.ps = ps;
        this.dps = dps;
    }

    public void createAppointment(User client, Property property, Date appointmentDate, LocalDateTime startTime) {
//        if(isAvailable(property.getId(),0 )) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDate);
        appointment.setProperty(property);
        appointment.setClient(client);
        appointment.setStart(startTime);
        ar.save(appointment);
//        }
    }

    public void updateAppointment(Long id, Date appointmentDate) {
        Optional<Appointment> response = ar.findById(id);

        if (response.isPresent()) {
            Appointment appointment = response.get();
            appointment.setAppointmentDate(appointmentDate);

            ar.save(appointment);
        }
    }

    public void deleteAppointment(Long id) {
        ar.deleteById(id);
    }

    public List<Appointment> findAppointmentByUserId(Long id) {
        return ar.findAllByUserId(id);
    }
}
