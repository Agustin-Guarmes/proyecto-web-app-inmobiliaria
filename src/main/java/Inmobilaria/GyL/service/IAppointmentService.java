package Inmobilaria.GyL.service;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface IAppointmentService {

//    void createAppointment(User client, Property property, Date appointmentDate, LocalTime start);

    void updateAppointment(Long id, LocalDate appointmentDate);

    void deleteAppointment(Long id);

    List<Appointment> findAppointmentByUserId(Long id);

    List<Appointment> findAllAvailableAppointmentsByDayAndProperty(Long id, LocalDate date);

    List<Appointment> saveAvailableAppointments(DayPlan dayPlan, Long id);

    Appointment bookAppointment(Long id, User userSession);
}
