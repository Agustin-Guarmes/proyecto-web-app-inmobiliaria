package Inmobilaria.GyL.entity;

import Inmobilaria.GyL.enums.AppointmentState;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = ("Appointment Date is required"))
    private Date appointmentDate;

    @ManyToOne
    private User client;

    @ManyToOne
    private Property property;

    @Enumerated(EnumType.STRING)
    private AppointmentState state;

    @CreationTimestamp
    private Date creationDate;

    public Appointment(Long id, Date appointmentDate, User client, Property property, AppointmentState state, Date creationDate) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.client = client;
        this.property = property;
        this.state = state;
        this.creationDate = creationDate;
    }

    public Appointment() {

    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
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

    public void setClient(User client) {
        this.client = client;
    }

    public void setState(AppointmentState state) {
        this.state = state;
    }
}
