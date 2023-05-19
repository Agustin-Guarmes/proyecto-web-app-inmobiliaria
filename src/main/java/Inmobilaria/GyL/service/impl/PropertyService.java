package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.enums.PropertyStatus;
import Inmobilaria.GyL.enums.PropertyType;
import Inmobilaria.GyL.repository.PropertyRepository;
import Inmobilaria.GyL.service.IPropertyService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PropertyService implements IPropertyService {

    private final PropertyRepository pr;
    private final ImagePropertyService ips;

    public PropertyService(PropertyRepository pr, ImagePropertyService ips) {
        this.pr = pr;
        this.ips = ips;
    }

    public void createProperty(User user, String address, String location, String status, String type, Integer surface, Double price, String description, List<MultipartFile> imgs) throws IOException {
        Property property = new Property();
        property.setUser(user);
        setPropertyAttributes(address, location, status, type, surface, price, description, property);
        pr.save(property);
        for (MultipartFile img : imgs) {
            ips.saveImg(img, property);
        }
    }

    public void updateProperty(Long id, String address, String location, String status, String type, Integer surface, Double price, String description) {
        Optional<Property> isFound = pr.findById(id);
        if (isFound.isPresent()) {
            Property updatedProperty = isFound.get();
            setPropertyAttributes(address, location, status, type, surface, price, description, updatedProperty);
            pr.save(updatedProperty);
        }
    }

    public List<Property> findByUser(Long userId) {
        return pr.findByUser(userId);
    }

    public Property findById(Long id) {
        return pr.findById(id).get();
    }

    public List<Property> listProperties() {
        return pr.findAll();
    }

    public void deleteProperty(Long id) {
        pr.deleteById(id);
    }

    public void updateProperty(Long id, String address, Integer surface, Double price) {
        Optional<Property> isFound = pr.findById(id);
        if (isFound.isPresent()) {
            Property updatedProperty = isFound.get();
            updatedProperty.setSurface(surface);
            updatedProperty.setPrice(price);
            updatedProperty.setAddress(address);
            pr.save(updatedProperty);
        }
    }

    private void setPropertyAttributes(String address, String location, String status, String type, Integer surface, Double price, String description, Property updatedProperty) {
        updatedProperty.setAddress(address);
        updatedProperty.setLocation(location);
        updatedProperty.setStatus(PropertyStatus.valueOf(status));
        updatedProperty.setType(PropertyType.valueOf(type));
        updatedProperty.setSurface(surface);
        updatedProperty.setPrice(price);
        updatedProperty.setDescription(description);
    }
}
