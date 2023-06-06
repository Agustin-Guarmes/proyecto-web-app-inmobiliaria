package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.enums.PropertyStatus;
import Inmobilaria.GyL.enums.PropertyType;
import Inmobilaria.GyL.repository.PropertyRepository;
import Inmobilaria.GyL.service.IImagePropertyService;
import Inmobilaria.GyL.service.IPropertyService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropertyService implements IPropertyService {
    private final PropertyRepository pr;
    private final IImagePropertyService ips;

    public PropertyService(PropertyRepository pr, IImagePropertyService ips) {
        this.pr = pr;
        this.ips = ips;
    }

    @Override
    public void createProperty(User user, String address, String location, String province, String status, String type, Integer surface, Double price, String description, List<MultipartFile> imgs, Integer bathrooms, Integer bedrooms) throws IOException {
        Property property = new Property();
        property.setUser(user);
        setPropertyAttributes(address, location, province, status, type, surface, price, description, property, bathrooms, bedrooms);
        property.setRented(false);
        property.setDuration(30);
        property.setActive(true);
        pr.save(property);
        for (MultipartFile img : imgs) {
            ips.saveImg(img, property);
        }
    }

    @Override
    public void updateProperty(Long id, String address, String location, String province, String status, String type, Integer surface, Double price, String description, Integer bathrooms, Integer bedrooms) {
        Optional<Property> isFound = pr.findById(id);
        if (isFound.isPresent()) {
            Property updatedProperty = isFound.get();
            setPropertyAttributes(address, location, province, status, type, surface, price, description, updatedProperty, bathrooms, bedrooms);
            pr.save(updatedProperty);
        }
    }

    @Override
    public List<Property> findByUser(Long userId) {
        return pr.findByUser(userId);
    }

    @Override
    public List<Property> clientProperties(Long id) {
        return pr.clientProperties(id);
    }

    @Override
    public Property findById(Long id) {
        return pr.findById(id).get();
    }

    @Override
    public List<Property> filteredProperties(Long id) {
        return pr.filteredProperties(id);
    }

    @Override
    public List<Property> listProperties() {
        return pr.findAllEntity();
    }

    @Override
    public List<Property> listRandomProperties(Long id) {
        List<Property> randomProperties;
        if (id == 0) {
            randomProperties = pr.findAllEntity();
        } else {
            randomProperties = pr.filteredProperties(Long.valueOf(id));
        }
        Collections.shuffle(randomProperties);
        return randomProperties.stream().limit(3).collect(Collectors.toList());
    }

    @Override
    public void rentProperty(Property property) {
        property.setRented(true);
        pr.save(property);
    }

    @Override
    public void deleteProperty(Long id) {
        Optional<Property> property = pr.findById(id);
        if (property.isPresent()) {
            pr.deleteById(id);
        }
    }

    @Override
    public List<Appointment> findAllAppointmentsByProperty(Long id) {
        List<Appointment> appointments = pr.findAllAppointmentsByProperty(id);
        return appointments;
    }

    @Override
    public void changeUser(Property property, User user) {
        property.setUser(user);
        pr.save(property);
    }

    @Override
    public List<DayPlan> findAllTimetableByProperty(Long id) {
        List<DayPlan> timetable = pr.findAllTimetableByProperty(id);
        return timetable;
    }

    private void setPropertyAttributes(String address, String location, String province, String status, String type, Integer surface, Double price, String description, Property updatedProperty, Integer bathrooms, Integer bedrooms) {
        updatedProperty.setAddress(address);
        updatedProperty.setLocation(location);
//        if(Arrays.asList(PropertyStatus.values()).contains(status))
        updatedProperty.setStatus(PropertyStatus.valueOf(status));
        updatedProperty.setType(PropertyType.valueOf(type));
        updatedProperty.setSurface(surface);
        updatedProperty.setPrice(price);
        updatedProperty.setDescription(description);
        updatedProperty.setBathrooms(bathrooms);
        updatedProperty.setBedrooms(bedrooms);
        updatedProperty.setProvince(province);
    }

    @Override
    public void addImageToProperty(Long id, List<MultipartFile> imgs) throws IOException {
        Optional<Property> property = pr.findById(id);
        if (property.isPresent()) {
            for (MultipartFile img : imgs) {
                ips.saveImg(img, property.get());
            }
        }
    }

    @Override
    public List<Property> findByUserName(String word) {
        return pr.findByUserName(word);
    }

    @Override
    public void toggleActiveProperty(Long id, boolean isActive) {
        pr.deactivateProperty(id, isActive);
    }

    @Override
    public List<Property> findAll() {
        return pr.findAll();
    }

    @Override
    public void setPropertyState(Property p) {
        pr.save(p);
    }

    @Override
    public List<Property> filterProperties(String propertyStatus, String propertyType, Double minPrice, Double maxPrice, String province) {
        List<Property> availableProperties = pr.findAllEntity();
        if (!province.equals("null"))
            availableProperties = availableProperties.stream().filter(p -> p.getProvince().equals(province)).collect(Collectors.toList());
        if (!propertyStatus.equals("null"))
            availableProperties = availableProperties.stream().filter(p -> p.getStatus() == PropertyStatus.valueOf(propertyStatus)).collect(Collectors.toList());
        if (!propertyType.equals("null"))
            availableProperties = availableProperties.stream().filter(p -> p.getType() == PropertyType.valueOf(propertyType)).collect(Collectors.toList());
        if (minPrice != null || maxPrice != null)
            availableProperties = priceFilter(availableProperties, minPrice, maxPrice);

        return availableProperties;
    }

    private List<Property> priceFilter(List<Property> properties, Double minPrice, Double maxPrice) {
        if (minPrice != null && maxPrice != null && minPrice <= maxPrice) {
            properties = properties.stream().filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice).collect(Collectors.toList());
        } else if (minPrice != null) {
            properties = properties.stream().filter(p -> p.getPrice() >= minPrice).collect(Collectors.toList());
        } else if (maxPrice != null) {
            properties = properties.stream().filter(p -> p.getPrice() <= maxPrice).collect(Collectors.toList());
        }
        return properties;
    }

    public Property findByAddress(String address){
        return pr.findByAddress(address);
    }
}