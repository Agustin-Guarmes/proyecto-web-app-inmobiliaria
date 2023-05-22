package Inmobilaria.GyL.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Property property;

    @OneToOne
    private User user;

    @NotNull(message = ("Price is required"))
    private Double price;

    private Boolean state;

    public Offer(Long id, LocalDate creationDate, Property property, User user, Double price, Boolean state) {
        this.id = id;
        this.creationDate = creationDate;
        this.property = property;
        this.user = user;
        this.price = price;
        this.state = state;
    }

    public Offer() {

    }

    public Long getId() {
        return id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Property getProperty() {
        return property;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getState() {
        return state;
    }


    public void setProperty(Property property) {
        this.property = property;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
