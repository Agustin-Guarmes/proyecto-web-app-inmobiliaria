package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.service.IAppointmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return appointmentService.findAppointmentByUserId(userId);
    }


}
