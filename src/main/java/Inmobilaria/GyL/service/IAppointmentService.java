package Inmobilaria.GyL.service;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface IAppointmentService {

    void updateAppointment(Long id, LocalDate appointmentDate);

    Appointment updateAppointment(Long id, AppointmentStatus appointmentStatus);

    void deleteAppointment(Long id);

    List<Appointment> findAllBookedAppointmentByUser(Long id);

    List<Appointment> findAllAvailableAppointmentsByDayAndProperty(Long id, LocalDate date);

    List<Appointment> saveAvailableAppointments(DayPlan dayPlan, Property property);

    Appointment bookAppointment(Long id, User userSession);

    List<Appointment> findAllByDayPlan(Long id);

    List<Appointment>  findAllAppointmentByUser(Long id);

    void cancelAppointment(Long id, User user);

    List<Long> findAllUsersIdByProperty(Long id);
}
