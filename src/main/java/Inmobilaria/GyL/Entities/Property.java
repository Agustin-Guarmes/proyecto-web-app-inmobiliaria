package Inmobilaria.GyL.Entities;

import Inmobilaria.GyL.Enums.Status;
import Inmobilaria.GyL.Enums.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    private String location;
    @Enumerated(EnumType.STRING)
    private Status status;
    @CreationTimestamp
    public Date createDate;

    //    @OneToMany(mappedBy = "property",fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Offer> offers;
    @Enumerated(EnumType.STRING)
    private Type type;
    private Integer surface;
    private Double price;
    private String description;
    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ImageProperty> images;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
    private List<Offer> offers;

    public Property() {
    }

    public Property(String address, String location, Status status, Date createDate, Type type, Integer surface, Double price, String description, List<ImageProperty> images) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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
