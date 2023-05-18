package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.repository.AppointmentRepository;
import Inmobilaria.GyL.service.IAppointmentService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AppointmentService implements IAppointmentService {

    private final AppointmentRepository ar;

    public AppointmentService(AppointmentRepository ar) {
        this.ar = ar;
    }

    public void createAppointment(User client, Property property, Date appointmentDate) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDate);
        appointment.setProperty(property);
        appointment.setClient(client);
        ar.save(appointment);
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
}
