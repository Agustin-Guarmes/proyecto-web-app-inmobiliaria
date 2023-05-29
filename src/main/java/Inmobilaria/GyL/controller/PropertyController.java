package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Offer;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.service.IDayPlanService;
import Inmobilaria.GyL.service.IPropertyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/propiedades")
public class PropertyController {

    private IPropertyService propertyService;

    private IDayPlanService dayPlanService;

    public PropertyController(IPropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/lista/{id}")
    public String propiedadesFiltradas(@PathVariable Long id,ModelMap model) {
        System.out.println(id);
        model.put("properties", propertyService.filteredProperties(id));
        model.put("title", "MrHouse | Propiedades");
        return "propertiesTest.html";
    }

    @GetMapping("/lista")
    public String propiedades(ModelMap model) {
        model.put("properties", propertyService.listProperties());
        model.put("title", "MrHouse | Propiedades");
        return "propertiesTest.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTITY')")
    @GetMapping("/formulario")
    public String formProperty(ModelMap model) {
        model.put("title", "MrHouse | Propiedad");
        return "formProperty.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTITY')")
    @GetMapping("/modificar/{id}")
    public String updateProperty(@PathVariable Long id, ModelMap model) {
        model.put("property", propertyService.findById(id));
        model.put("idUser", id);
        model.put("title", "MrHouse | Propiedad");
        return "updateProperty.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTITY')")
    @PostMapping("/modificar/{idUser}")
    public String modifyProperty(@PathVariable Long idUser, @RequestParam Long id, @RequestParam String address, @RequestParam String location, @RequestParam String province,
                                 @RequestParam String status, @RequestParam String type, @RequestParam int surface,
                                 @RequestParam Double price, @RequestParam String description, @RequestParam int bathrooms, @RequestParam int bedrooms) {

        propertyService.updateProperty(id, address, location, province, status, type, surface, price, description, bathrooms, bedrooms);
        return "redirect:/usuario/propiedades/" + idUser;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTITY')")
    @GetMapping("/eliminar/{id}")
    public String deleteProperty(@PathVariable Long id, HttpSession session) {
        propertyService.deleteProperty(id);
        User log = (User) session.getAttribute("userSession");
        return "redirect:/usuario/propiedades/" + log.getId();
    }

    @GetMapping("/{id}")
    public String findByProperty(@PathVariable Long id, ModelMap model) {
        Property find = propertyService.findById(id);
        model.put("property", find);
        model.put("title", "MrHouse | Propiedad");
        return "detailProperty.html";
    }

    @PostMapping("/usuarioAgregaImagenes")
    public String addImgProperty(@RequestParam Long id, MultipartFile[] files) throws IOException {
        propertyService.addImageToProperty(id, Arrays.asList(files));
        return "redirect:/propiedades/modificar/" + id;
    }

    @PostMapping("/disponibilidad/{propertyId}")
    public String addDayPlan(@PathVariable("propertyId") Long propertyId,
                             @RequestParam LocalDate timetableDay,
                             @RequestParam LocalTime start,
                             @RequestParam LocalTime end) {
        dayPlanService.addDayPlan(propertyId, timetableDay, start, end);
        return "redirect:/propiedades/modificar" + propertyId;
    }



}
