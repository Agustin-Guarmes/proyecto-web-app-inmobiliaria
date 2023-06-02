package Inmobilaria.GyL.entity;

import Inmobilaria.GyL.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Appointment implements Comparable<Appointment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Property property;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus state;

    @CreationTimestamp
    private LocalDate creationDate;

    private LocalDateTime start;

    private LocalDateTime end;

    private String title;

    public Appointment(LocalDate appointmentDate, User client, Property property,
                       AppointmentStatus state, LocalDate creationDate,
                       LocalDateTime start, LocalDateTime end) {
        this.date = appointmentDate;
        this.client = client;
        this.property = property;
        this.state = state;
        this.creationDate = creationDate;
        this.start = start;
        this.end = end;
    }

    public Appointment(LocalDateTime start, LocalDateTime end, LocalDate appointmentDate,Property property,
                       AppointmentStatus state) {
        this.start = start;
        this.end = end;
        this.date = appointmentDate;
        this.property = property;
        this.state = state;
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

    public void setStart(LocalDateTime startTime) {
        this.start = startTime;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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

    public AppointmentStatus getState() {
        return state;
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

    public void setState(AppointmentStatus state) {
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
