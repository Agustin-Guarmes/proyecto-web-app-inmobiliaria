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
        property.setAddress(address);
        property.setLocation(location);
        property.setStatus(PropertyStatus.valueOf(status));
        property.setType(PropertyType.valueOf(type));
        property.setSurface(surface);
        property.setPrice(price);
        property.setDescription(description);
        pr.save(property);
        for (MultipartFile img : imgs) {
            ips.saveImg(img, property);
        }
    }

    public Property findById(Long id) {
        return pr.findById(id).get();
    }

    public List<Property> listProperties() {
        return pr.findAll();
    }

    public void deleteProperty(Long id){
        pr.deleteById(id);
    }
}
