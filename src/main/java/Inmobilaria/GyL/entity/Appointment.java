package Inmobilaria.GyL.Entities;

import Inmobilaria.GyL.Enums.AppointmentState;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = ("Appointment Date is required"))
    public Date appointmentDate;

    @OneToOne
    public User owner;

    @OneToOne
    public User client;

    @Enumerated(EnumType.STRING)
    public AppointmentState state;

    public Appointment(Long id, Date appointmentDate, User owner, User client, AppointmentState state) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.owner = owner;
        this.client = client;
        this.state = state;
    }

    public Appointment() {

    }

    public Long getId() {
        return id;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public User getOwner() {
        return owner;
    }

    public User getClient() {
        return client;
    }

    public AppointmentState getState() {
        return state;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setState(AppointmentState state) {
        this.state = state;
    }
}
