package Inmobilaria.GyL.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ScheduleDay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date timetableDay;

    private Date startTime;

    private Date endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public ScheduleDay(Long id, Date timetableDay, Date startTime, Date endTime) {
        this.id = id;
        this.timetableDay = timetableDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ScheduleDay() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimetableDay(Date timetableDay) {
        this.timetableDay = timetableDay;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public Date getTimetableDay() {
        return timetableDay;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
