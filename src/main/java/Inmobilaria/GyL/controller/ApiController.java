package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.entity.TimePeriod;
import Inmobilaria.GyL.service.IAppointmentService;
import Inmobilaria.GyL.service.ITimePeriodService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ApiController {

    private final IAppointmentService appointmentService;

    private final ITimePeriodService timePeriodService;

    public ApiController(IAppointmentService appointmentService, ITimePeriodService timePeriodService) {
        this.appointmentService = appointmentService;
        this.timePeriodService = timePeriodService;
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
