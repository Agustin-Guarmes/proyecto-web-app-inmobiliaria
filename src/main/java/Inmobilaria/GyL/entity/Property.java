package Inmobilaria.GyL.entity;

import Inmobilaria.GyL.enums.PropertyStatus;
import Inmobilaria.GyL.enums.PropertyType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    private String location;
    @Enumerated(EnumType.STRING)
    private PropertyStatus status;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createDate;
    @Enumerated(EnumType.STRING)
    private PropertyType type;
    private Integer surface;
    private Double price;
    private String description;
    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ImageProperty> images;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Offer> offers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Appointment> appointments;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Property() {
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public Property(String address, String location, PropertyStatus status, LocalDate createDate, PropertyType type, Integer surface, Double price, String description, List<ImageProperty> images) {
        this.address = address;
        this.location = location;
        this.status = status;
        this.createDate = createDate;
        this.type = type;
        this.surface = surface;
        this.price = price;
        this.description = description;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public Integer getSurface() {
        return surface;
    }

    public void setSurface(Integer surface) {
        this.surface = surface;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ImageProperty> getImages() {
        return images;
    }

    public void setImages(List<ImageProperty> images) {
        this.images = images;
    }
}
