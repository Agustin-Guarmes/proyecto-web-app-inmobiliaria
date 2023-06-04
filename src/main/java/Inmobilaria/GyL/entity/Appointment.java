package Inmobilaria.GyL.entity;

import Inmobilaria.GyL.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Appointment implements Comparable<Appointment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Property property;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @CreationTimestamp
    private LocalDate creationDate;

    private LocalDateTime start;

    private LocalDateTime end;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dayplan_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private DayPlan dayPlan;

    public Appointment(LocalDate appointmentDate, User client, Property property,
                       AppointmentStatus appointmentStatus, LocalDateTime start, LocalDateTime end) {
        this.date = appointmentDate;
        this.client = client;
        this.property = property;
        this.appointmentStatus = appointmentStatus;
        this.start = start;
        this.end = end;
    }

    public Appointment(LocalDate appointmentDate, Property property, DayPlan dayPlan,
                       AppointmentStatus appointmentStatus, LocalDateTime start, LocalDateTime end) {
        this.date = appointmentDate;
        this.property = property;
        this.dayPlan = dayPlan;
        this.appointmentStatus = appointmentStatus;
        this.start = start;
        this.end = end;
    }

    public Appointment(LocalDate appointmentDate, Property property,
                       AppointmentStatus appointmentStatus, LocalDateTime start, LocalDateTime end) {
        this.date = appointmentDate;
        this.property = property;
        this.appointmentStatus = appointmentStatus;
        this.start = start;
        this.end = end;
//        this.title = title;
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

    public LocalTime getStartTime() {
        return start.toLocalTime();
    }

    public LocalTime getEndTime() {
        return end.toLocalTime();
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

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getClient() {
        return client;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate appointmentDate) {
        this.date = appointmentDate;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setAppointmentStatus(AppointmentStatus state) {
        this.appointmentStatus = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DayPlan getDayPlan() {
        return dayPlan;
    }

    public void setDayPlan(DayPlan dayPlan) {
        this.dayPlan = dayPlan;
    }

    @Override
    public int compareTo(Appointment o) {
        return this.getStart().compareTo(o.getStart());
    }
}
