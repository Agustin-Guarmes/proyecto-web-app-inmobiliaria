package Inmobilaria.GyL.service;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IAppointmentService {
    void createAppointment(User client, Property property, Date appointmentDate, LocalDateTime startTime);

    void updateAppointment(Long id, Date appointmentDate);

    void deleteAppointment(Long id);

    List<Appointment> findAppointmentByUserId(Long id);
}
