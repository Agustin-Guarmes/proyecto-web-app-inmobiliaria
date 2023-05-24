package Inmobilaria.GyL.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class DayPlan implements Comparable<DayPlan>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate timetableDay;

    private LocalTime start;

    private LocalTime end;

    @ManyToOne(fetch = FetchType.LAZY)
    private Property property;

    public DayPlan(LocalDate timetableDay, LocalTime startTime, LocalTime end, Property property) {
        this.timetableDay = timetableDay;
        this.start = startTime;
        this.end = end;
        this.property = property;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public DayPlan() {

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

    @Override
    public int compareTo(DayPlan o) {
        return this.getStart().compareTo(o.getStart());
    }
}
