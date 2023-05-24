package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.service.impl.AppointmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
public class ApiController {

    private final AppointmentService appointmentService;

    public ApiController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @GetMapping("/user/{userId}/appointments")
    public List<Appointment> findAppointmentsForUser(@PathVariable("userId") Long userId) {
        return appointmentService.findAppointmentByUserId(userId);
    }
}
