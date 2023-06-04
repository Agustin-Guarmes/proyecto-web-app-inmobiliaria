package Inmobilaria.GyL.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class DayPlan implements Comparable<DayPlan> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate timetableDay;

    private LocalTime start;

    private LocalTime end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Property property;

    @OneToMany(mappedBy = "dayPlan", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Appointment> appointments;

    private Boolean isActive;

    public DayPlan(Long id, LocalDate timetableDay, LocalTime start, LocalTime end, Property property) {
        this.id = id;
        this.timetableDay = timetableDay;
        this.start = start;
        this.end = end;
        this.property = property;
    }

    public DayPlan(LocalDate timetableDay, LocalTime startTime, LocalTime end, Property property) {
        this.timetableDay = timetableDay;
        this.start = startTime;
        this.end = end;
        this.property = property;
    }

    public DayPlan() {

    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimetableDay(LocalDate timetableDay) {
        this.timetableDay = timetableDay;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getTimetableDay() {
        return timetableDay;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int compareTo(DayPlan o) {
        return this.getStart().compareTo(o.getStart());
    }
}
