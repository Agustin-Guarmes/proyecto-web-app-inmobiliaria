package Inmobilaria.GyL.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Property property;

    @OneToOne
    private User user;

    @NotNull(message = ("Price is required"))
    private Double price;

    private Boolean state;

    public Offer(Long id, Date creationDate, Property property, User user, Double price, Boolean state) {
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

    public Date getCreationDate() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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
}
