package Inmobilaria.GyL.service;

import Inmobilaria.GyL.entity.Appointment;
import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPropertyService {
    void createProperty(User user, String address, String location, String province, String status, String type, Integer surface, Double price, String description, List<MultipartFile> imgs, Integer bathrooms, Integer bedrooms) throws IOException;

    void updateProperty(Long id, String address, String location, String province, String status, String type, Integer surface, Double price, String description, Integer bathrooms, Integer bedrooms);

    List<Property> findByUser(Long userId);

    Property findById(Long id);

    List<Property> listProperties();

    void deleteProperty(Long id);

    /*void updateProperty(Long id, String address, Integer surface, Double price);*/

    List<Appointment> findAllAppointmentsByProperty(Long id);

    void changeUser(Property property, User user);

    List<DayPlan> findAllTimetableByProperty(Long id);

    public void addImageToProperty(Long id, List<MultipartFile> imgs) throws IOException;
}
