package Inmobilaria.GyL.entity;

import Inmobilaria.GyL.enums.OfferStatus;
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


    private Long user;

    @NotNull(message = ("Price is required"))
    private Double price;
    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;

    public Offer(Property property, Long user, Double price, OfferStatus offerStatus) {
        this.property = property;
        this.user = user;
        this.price = price;
        this.offerStatus = offerStatus;
    }

    public Offer() {
    }

    public void setProperty(Property property) {
        this.property = property;
    }


    public void setPrice(Double price) {
        this.price = price;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
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

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }
}
