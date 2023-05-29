package Inmobilaria.GyL.entity;

import Inmobilaria.GyL.enums.AppointmentState;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Appointment implements Comparable<Appointment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = ("Appointment Date is required"))
    private Date appointmentDate;

    @ManyToOne
    private User client;

    @ManyToOne
    private Property property;

    @OneToOne
    private TimePeriod timePeriod;

    @Enumerated(EnumType.STRING)
    private AppointmentState state;

    @CreationTimestamp
    private Date creationDate;

    private LocalDateTime start;

    private LocalDateTime end;

    private String title;

    public Appointment(Long id, Date appointmentDate, User client, Property property, DayPlan dayPlan, TimePeriod timePeriod, AppointmentState state, Date creationDate, LocalDateTime startTime, LocalDateTime endTime, String title) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.client = client;
        this.property = property;
        this.timePeriod = timePeriod;
        this.state = state;
        this.creationDate = creationDate;
        this.start = startTime;
        this.end = endTime;
        this.title = title;
    }

    public Appointment() {

    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime endTime) {
        this.end = endTime;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime startTime) {
        this.start = startTime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(Appointment o) {
        return this.getStart().compareTo(o.getStart());
    }
}
