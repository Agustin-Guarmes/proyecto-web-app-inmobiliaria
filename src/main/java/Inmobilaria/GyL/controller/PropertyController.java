package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.service.IAppointmentService;
import Inmobilaria.GyL.service.IDayPlanService;
import Inmobilaria.GyL.service.IPropertyService;
import org.springframework.format.annotation.DateTimeFormat;
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

    private final IPropertyService propertyService;

    private final IDayPlanService dayPlanService;

    private final IAppointmentService appointmentService;

    public PropertyController(IPropertyService propertyService, IDayPlanService dayPlanService,
                              IAppointmentService appointmentService) {
        this.propertyService = propertyService;
        this.dayPlanService = dayPlanService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/lista/{id}")
    public String propiedadesFiltradas(@PathVariable Long id,ModelMap model) {
        model.put("properties", propertyService.filteredProperties(id));
        model.put("title", "MrHouse | Propiedades");
        return "properties.html";
    }

    @GetMapping("/lista")
    public String propiedades(ModelMap model) {
        model.put("properties", propertyService.listProperties());
        model.put("title", "MrHouse | Propiedades");
        return "properties.html";
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
                             @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate timetableDay,
                             @RequestParam LocalTime start,
                             @RequestParam LocalTime end,
                             @SessionAttribute(required=false, name="userSession") User user) {
        dayPlanService.addDayPlan(propertyId, timetableDay, start, end);
        return "redirect:/usuario/gestion/" + user.getId();
    }

    @PostMapping("/{propertyId}/reservarTurno")
    public String makeAnAppointment(@PathVariable("propertyId") Long propertyId,
                                    @RequestParam Long appointmentId,
                                    @SessionAttribute(required=false, name="userSession") User user) {
        appointmentService.bookAppointment(appointmentId, user);
        return "redirect:/propiedades/modificar/" + propertyId;
    }

    @GetMapping("/turnos/{id}")
    public String appointment(@PathVariable Long id, ModelMap model){
        List<Property> properties = propertyService.findByUser(id);
        model.put("properties",properties);
        return "appointmentProperty.html";
    }

    @PostMapping("/filtrar")
    public String filterProperty(@RequestParam(required = false) String status, @RequestParam(required = false) String type, @RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice, @RequestParam(required = false) String province, ModelMap model){
        model.put("properties",propertyService.filterProperties(status,type,minPrice,maxPrice,province));
        return "properties";
    }
}
