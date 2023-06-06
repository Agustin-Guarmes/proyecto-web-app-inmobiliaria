package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.service.IAppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ApiController {

    private final IAppointmentService appointmentService;

    public ApiController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/user/{userId}/appointments")
    public List<Appointment> findAppointmentsForUser(@PathVariable("userId") Long userId) {
        List<Appointment> appointments =  appointmentService.findAllBookedAppointmentByUser(userId);
        return appointments;
    }

    @GetMapping("/property/{propertyId}/availableAppointments")
    public List<Appointment> findAvailableAppointmentsForProperty(@PathVariable("propertyId") Long propertyId,
                                                                 @RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate date) {
        return appointmentService.findAllAvailableAppointmentsByDayAndProperty(propertyId, date);
    }


}
