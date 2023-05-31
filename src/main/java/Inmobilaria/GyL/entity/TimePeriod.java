package Inmobilaria.GyL.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class TimePeriod implements Comparable<TimePeriod> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime start;

    private LocalTime end;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date appointmentDate;

    @ManyToOne
    private Property property;

    @OneToOne
    private Appointment appointment;

    public TimePeriod(LocalTime start, LocalTime end, Property property) {
        this.start = start;
        this.end = end;
        this.property = property;
    }

    public TimePeriod() {

    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    @Override
    public int compareTo(TimePeriod o) {
        return this.getStart().compareTo(o.getStart());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimePeriod peroid = (TimePeriod) o;
        return this.start.equals(peroid.getStart()) && this.end.equals(peroid.getEnd());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
