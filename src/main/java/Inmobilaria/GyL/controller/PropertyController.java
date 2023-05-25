package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.service.IPropertyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@Controller
@RequestMapping("/propiedades")
public class PropertyController {

    private IPropertyService propertyService;

    public PropertyController(IPropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/lista")
    public String propiedades(ModelMap model) {
        model.put("properties", propertyService.listProperties());
        return "propertiesTest.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTITY')")
    @GetMapping("/formulario")
    public String formProperty() {
        return "formProperty.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTITY')")
    @GetMapping("/modificar/{id}")
    public String updateProperty(@PathVariable Long id, ModelMap model) {
        model.put("property", propertyService.findById(id));
        model.put("idUser", id);
        return "updateProperty.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTITY')")
    @PostMapping("/modificar/{idUser}")
    public String modifyProperty(@PathVariable Long idUser, @RequestParam Long id, @RequestParam String address, @RequestParam String location,
                                 @RequestParam String status, @RequestParam String type, @RequestParam int surface,
                                 @RequestParam Double price, @RequestParam String description, @RequestParam int bathrooms, @RequestParam int bedrooms) {

        propertyService.updateProperty(id, address, location, status, type, surface, price, description, bathrooms, bedrooms);

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
        return "detailProperty.html";
    }

    @PostMapping("/usuarioAgregaImagenes")
    public String addImgProperty(@RequestParam Long id, MultipartFile[] files) throws IOException {

        propertyService.addImageToProperty(id, Arrays.asList(files));
        return "redirect:/propiedades/modificar/" + id;
    }

}
